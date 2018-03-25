package ai;

import field.Field;

import java.util.*;

public class IProcessing {

    private int listHit;
    private static IProcessing iProcessing = new IProcessing();
    private IProcessing() {}
    public static IProcessing getInstance() {
        return iProcessing;
    }

    public int doProcessing(Field field) {
        ITables iTables = ITables.build()
                .setSize(field.getWidth(), field.getHeight())
                .setMyId(String.valueOf(field.getMyId()))
                .updateTable(field.getField());

        List<IPoint> myDangers = new LinkedList<>();
        myDangers.addAll(iTables.getDangerPoints());
        iTables.swapIds();
        List<IPoint> botDangers = new LinkedList<>();
        botDangers.addAll(iTables.getDangerPoints());
        iTables.swapIds();

        List<IPoint> willHitPoint = new LinkedList<>();
        willHitPoint.addAll(iTables.getHitPoints());

        List<IPoint> canHitPoints = iTables.getCanHitPoints();
        IPoint hit = canHitPoints.get(canHitPoints.size() / 2);

        boolean loop = true;
        while (loop) {
            listHit = -1;
            if (myDangers.size() > 0 && botDangers.size() > 0) {
                hit = choiceDangerPoint(myDangers, botDangers);
            } else if (myDangers.size() == 0 && botDangers.size() > 0) {
                listHit = 1;
                hit = botDangers.get(0);
            } else if (myDangers.size() > 0) {
                listHit = 0;
                hit = myDangers.get(0);
            } else {
                hit = willHitPoint.get(0);
                listHit = 2;
            }

            iTables.getPoints()[hit.getX()][hit.getY()].setPointId(iTables.getMyId());
            List<IPoint> afterHit = new LinkedList<>();
            afterHit.addAll(iTables.getDangerPoints());
            if (afterHit.size() > 0 && afterHit.get(0).getDanger() >= Logic.LEVEL_DEAD) {
                if (listHit == 0) {
                    myDangers.remove(0);
                } else if (listHit == 1) {
                    botDangers.remove(0);
                } else if (listHit == 2) {
                    willHitPoint.remove(0);
                }
            } else {
                loop = false;
            }
        }

        return hit.getX();
    }

    public IPoint choiceDangerPoint(List<IPoint> dg1, List<IPoint> dg2) {
        if (dg1.get(0).getDanger() >= Logic.LEVEL_DEAD
                || dg1.get(0).getDanger() >= dg2.get(0).getDanger()) {
            listHit = 0;
            return dg1.get(0);
        } else {
            listHit = 1;
            return dg2.get(0);
        }
    }

}
