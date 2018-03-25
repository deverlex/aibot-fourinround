package ai;

import java.util.*;

//idea: kiem tra tat cac cac vi tri trong co kha nang nguy hiem
// tien hanh danh trong so
class Logic {

    public final static int LEVEL_DEAD = 12;
    public final static int LEVEL_DOUBLE_STEP = 7;
    public final static int LEVEL_MAYBE = 2;

    private List<IPoint> dangerPoints;
    private ITables iTables;

    public Logic(ITables iTables) {
        this.dangerPoints = new ArrayList<>();
        this.iTables = iTables;
    }

    public List<IPoint> makeRunFind() {
        dangerPoints.clear();
        IPoint[][] points = new IPoint[4][4];
        for (int i = 0; i < iTables.getWidth() - 3; ++i) {
            for (int j = 0; j <  iTables.getHeight() - 3; ++j) {
                for (int i1 = 0; i1 < 4; ++i1) {
                    for (int j1 = 0; j1 < 4; ++j1) {
                        points[i1][j1] = iTables.getPoints()[i + i1][j + j1];
                    }
                }
                // check square
                checkDangerInChildTable(points);
            }
        }

        for (int i = 0; i < iTables.getWidth(); ++i) {
            for (int j = 0; j < iTables.getHeight(); ++j) {
                if (iTables.getPoints()[i][j].getDanger() >= LEVEL_MAYBE) {
                    dangerPoints.add(iTables.getPoints()[i][j]);
                }
            }
        }

        if (dangerPoints.size() > 1) {
            dangerPoints.sort(new CompareIPoint());
        }
        return dangerPoints;
    }

    public List<IPoint> makeRunHit() {
        List<IPoint> canHitPoints = iTables.getCanHitPoints();
        iTables.swapIds();
        for (int i = 0; i < canHitPoints.size(); ++i) {
            canHitPoints.get(i).setPointId(iTables.getMyId());
            List<IPoint> dangerPoints = makeRunFind();
            if (dangerPoints.size() > 0) {
                iTables.swapIds();
                canHitPoints.get(i).setPointId(".");
                return dangerPoints;
            }
            canHitPoints.get(i).setPointId(".");
        }
        iTables.swapIds();
        return canHitPoints;
    }

    /// IPoint[4][4]
    private void checkDangerInChildTable(IPoint[][] points) {
        LearnLogic.getInstance().updateTable(iTables).checkDanger(points);

        // crossover
        validateDangerCrossover(points[0][0], points[1][1], points[2][2], points[3][3]);
        validateDangerCrossover(points[3][0], points[2][1], points[1][2], points[0][3]);

        // horizontal
        for (int j = 0; j < 4; ++j) {
            validateDangerHorizontal(points[0][j], points[1][j], points[2][j], points[3][j]);
        }
        // vertical
        for (int i = 0; i < 4; ++i) {
            validateDangerVertical(points[i][0], points[i][1], points[i][2], points[i][3]);
        }
    }

    //check cho ngang
    private void validateDangerHorizontal(IPoint pos1, IPoint pos2, IPoint pos3, IPoint pos4) {
        // [*][_][*][_]
        if (iTables.isBot(pos1) && pos2.isEmpty()
                && iTables.isBot(pos3) && pos4.isEmpty()
                && !pos2.isBelowEmpty() && !pos4.isBelowEmpty()) {
            if (pos1.isLeftDanger()) {
                pos2.setDanger(pos2.getDanger() + LEVEL_DEAD);
            } else {
                pos2.setDanger(pos2.getDanger() + LEVEL_MAYBE);
            }
        }
        // [_][*][_][*]
        if (pos1.isEmpty() && iTables.isBot(pos2)
                &&  pos3.isEmpty() && iTables.isBot(pos4)
                && !pos1.isBelowEmpty() && !pos3.isBelowEmpty()) {
            if (pos4.isRightDanger()) {
                pos3.setDanger(pos3.getDanger() + LEVEL_DEAD);
            } else {
                pos3.setDanger(pos3.getDanger() + LEVEL_MAYBE);
            }
        }
        // [_][*][*][_]
        if (pos1.isEmpty() && iTables.isBot(pos2)
                && iTables.isBot(pos3) && pos4.isEmpty()) {
            if (!pos1.isBelowEmpty() && !pos4.isBelowEmpty()) {
                pos1.setDanger(pos1.getDanger() + LEVEL_DEAD);
                pos4.setDanger(pos4.getDanger() + LEVEL_DEAD);
            }
        }
        // [*][*][*][_]
        if (iTables.isBot(pos1) && iTables.isBot(pos2)
                && iTables.isBot(pos3) && pos4.isEmpty()
                && !pos4.isBelowEmpty()) {
            pos4.setDanger(pos4.getDanger() + LEVEL_DEAD);
        }
        // [_][*][*][*]
        if (pos1.isEmpty() && iTables.isBot(pos2)
                && iTables.isBot(pos3) && iTables.isBot(pos4)
                && !pos1.isBelowEmpty()) {
            pos1.setDanger(pos1.getDanger() + LEVEL_DEAD);
        }
        // [*][*][_][*]
        if (iTables.isBot(pos1) && iTables.isBot(pos2)
                && pos3.isEmpty() && iTables.isBot(pos4)
                && !pos3.isBelowEmpty()) {
            pos3.setDanger(pos3.getDanger() + LEVEL_DEAD);
        }
        // [*][_][*][*]
        if (iTables.isBot(pos1) && pos2.isEmpty()
                && iTables.isBot(pos3) && iTables.isBot(pos4)
                && !pos2.isBelowEmpty()) {
            pos2.setDanger(pos2.getDanger() + LEVEL_DEAD);
        }
    }

    private void validateDangerVertical(IPoint pos1, IPoint pos2, IPoint pos3, IPoint pos4) {
        // [_][*][*][*]
        if (pos1.isEmpty() && iTables.isBot(pos2)
                && iTables.isBot(pos3) && iTables.isBot(pos4)) {
            pos1.setDanger(pos1.getDanger() + LEVEL_DEAD);
        }
    }

    private void validateDangerCrossover(IPoint pos1, IPoint pos2, IPoint pos3, IPoint pos4) {
        validateDangerHorizontal(pos1, pos2, pos3, pos4);
    }

    public boolean isGameState(String pointId) {
        IPoint[][] points = iTables.getPoints();
        for (int i = 0; i < iTables.getWidth() - 4; ++i) {
            for (int j = 0; j < iTables.getHeight() - 4; ++j) {
                if (isFourInRow(pointId, points[i][j], points[i + 1][j], points[i + 2][j], points[i + 3][j])) {
                    return true;
                }
                if (isFourInRow(pointId, points[i][j], points[i][j + 1], points[i][j + 2], points[i][j + 3])) {
                    return true;
                }
                if (isFourInRow(pointId, points[i][j], points[i + 1][j + 1], points[i + 2][j + 2], points[i + 3][j + 3])) {
                    return true;
                }
                if (isFourInRow(pointId, points[i][j + 3], points[i + 1][j + 2], points[i + 2][j + 1], points[i + 3][j])) {
                    return true;
                }

            }
        }
        return false;
    }

    private boolean isFourInRow(String pointId, IPoint p1, IPoint p2, IPoint p3, IPoint p4) {
        if (p1.getPointId().equals(pointId) && p2.getPointId().equals(pointId)
                && p3.getPointId().equals(pointId) && p4.getPointId().equals(pointId)) return true;
        return false;
    }
    
}
