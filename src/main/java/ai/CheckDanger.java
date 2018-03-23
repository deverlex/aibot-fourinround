package ai;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//idea: kiem tra tat cac cac vi tri trong co kha nang nguy hiem
// tien hanh danh trong so
class CheckDanger {

    private final static int L10_DANGER = 10;
    private final static int L06_DANGER = 6;
    private final static int L02_DANGER = 2;

    private ITables iTables;

    public CheckDanger(ITables iTables) {
        this.iTables = iTables;
    }

    public IPoint makeRunFind() {
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

        return findMaxDanger();
    }

    /// IPoint[4][4]
    private void checkDangerInChildTable(IPoint[][] points) {
        // horizontal
        for (int j = 0; j < 4; ++j) {
            validateDangerHorizontal(points[0][j], points[1][j], points[2][j], points[3][j]);
        }
        // vertical
        for (int i = 0; i < 4; ++i) {
            validateDangerVertical(points[i][0], points[i][1], points[i][2], points[i][3]);
        }

        // crossover
        validateDangerCrossover(points[0][0], points[1][1], points[2][2], points[3][3]);
        validateDangerCrossover(points[3][0], points[2][1], points[1][2], points[0][3]);

        // double hoz & ver
        validateDangerDoubleOnly(points[0][2], points[0][3], points[2][1], points[3][1], points[0][1], points[1][1]);
        validateDangerDoubleOnly(points[1][2], points[1][3], points[2][1], points[3][1], points[1][1], points[0][1]);
        validateDangerDoubleOnly(points[0][1], points[1][1], points[2][2], points[2][3], points[2][1], points[3][1]);
        validateDangerDoubleOnly(points[0][1], points[1][1], points[3][2], points[3][3], points[3][1], points[2][1]);

        // double
        validateDangerDoubleOnly(points[1][2], points[1][3], points[2][2], points[3][3], points[1][1], points[0][0]);
        validateDangerDoubleOnly(points[0][3], points[1][2], points[2][2], points[2][3], points[2][1], points[3][0]);

        validateDangerDoubleDouble(points[0][1], points[1][1], points[0][3], points[1][2], points[2][1], points[3][1]);
        validateDangerDoubleDouble(points[3][3], points[2][2], points[2][1], points[3][1], points[1][1], points[0][1]);

        validateDangerDoubleOnly(points[0][1], points[3][1], points[1][2], points[1][3], points[1][1], points[2][1]);
        validateDangerDoubleOnly(points[0][1], points[3][1], points[2][2], points[2][3], points[2][1], points[1][1]);
        // double crossover
        validateDangerDoubleCrossover(points[0][1], points[3][1], points[1][2], points[0][3], points[2][1], points[1][1], points[3][0]);
        validateDangerDoubleCrossover(points[0][1], points[3][1], points[2][2], points[3][3], points[1][1], points[2][1], points[0][0]);

        validateDangerHozCrossover(points[0][3], points[1][2], points[1][1], points[2][1], points[2][2]);
        validateDangerHozCrossover(points[3][3], points[2][2], points[2][1], points[1][1], points[1][2]);
    }

    //check cho ngang
    private void validateDangerHorizontal(IPoint pos1, IPoint pos2, IPoint pos3, IPoint pos4) {
        // [*][*][*][_]
        if (iTables.isBotPoint(pos1.getPointId()) && iTables.isBotPoint(pos2.getPointId())
                && iTables.isBotPoint(pos3.getPointId()) && iTables.isEmpty(pos4.getPointId())
                && !pos4.isBelowEmpty()) {
            pos4.setDanger(pos4.getDanger() + L10_DANGER);
        }
        // [_][*][*][_]
        else if (iTables.isEmpty(pos1.getPointId()) && iTables.isBotPoint(pos2.getPointId())
                && iTables.isBotPoint(pos3.getPointId()) && iTables.isEmpty(pos4.getPointId())) {
            if (!pos1.isBelowEmpty()) {
                pos1.setDanger(pos1.getDanger() + L10_DANGER);
            }
            if (!pos4.isBelowEmpty()) {
                pos4.setDanger(pos4.getDanger() + L10_DANGER);
            }
        }
        // [_][*][*][*]
        else if (iTables.isEmpty(pos1.getPointId()) && iTables.isBotPoint(pos2.getPointId())
                && iTables.isBotPoint(pos3.getPointId()) && iTables.isBotPoint(pos4.getPointId())
                && !pos1.isBelowEmpty()) {
            pos1.setDanger(pos1.getDanger() + L10_DANGER);
        }
        // [*][*][_][*]
        else if (iTables.isBotPoint(pos1.getPointId()) && iTables.isBotPoint(pos2.getPointId())
                && iTables.isEmpty(pos3.getPointId()) && iTables.isBotPoint(pos4.getPointId())
                && !pos3.isBelowEmpty()) {
            pos3.setDanger(pos3.getDanger() + L10_DANGER);
        }
        // [*][_][*][*]
        else if (iTables.isBotPoint(pos1.getPointId()) && iTables.isEmpty(pos2.getPointId())
                && iTables.isBotPoint(pos3.getPointId()) && iTables.isBotPoint(pos4.getPointId())
                && !pos2.isBelowEmpty()) {
            pos2.setDanger(pos2.getDanger() + L10_DANGER);
        }
    }

    private void validateDangerVertical(IPoint pos1, IPoint pos2, IPoint pos3, IPoint pos4) {
        // [_][*][*][*]
        if (iTables.isEmpty(pos1.getPointId()) && iTables.isBotPoint(pos2.getPointId())
                && iTables.isBotPoint(pos3.getPointId()) && iTables.isBotPoint(pos4.getPointId())) {
            pos1.setDanger(pos1.getDanger() + L10_DANGER);
        }
    }

    private void validateDangerCrossover(IPoint pos1, IPoint pos2, IPoint pos3, IPoint pos4) {
        validateDangerHorizontal(pos1, pos2, pos3, pos4);
    }

    private void validateDangerDoubleOnly(IPoint pos1, IPoint pos2, IPoint pos3, IPoint pos4, IPoint pos5, IPoint pos6) {
        if (pos5.isActive() && pos6.isActive() && !pos6.isBelowEmpty()) {
            if (iTables.isBotPoint(pos1.getPointId()) && iTables.isBotPoint(pos2.getPointId())
                    && iTables.isBotPoint(pos3.getPointId()) && iTables.isBotPoint(pos4.getPointId())) {
                pos5.setDanger(pos5.getDanger() + L06_DANGER);
            }
        }
    }
    
    private void validateDangerDoubleDouble(IPoint pos1, IPoint pos2, IPoint pos3, IPoint pos4, IPoint pos5, IPoint pos6) {
        if (pos5.isActive() && pos6.isActive() && !pos5.isBelowEmpty() && !pos6.isBelowEmpty()) {
            if (iTables.isBotPoint(pos1.getPointId()) && iTables.isBotPoint(pos2.getPointId())
                    && iTables.isBotPoint(pos3.getPointId()) && iTables.isBotPoint(pos4.getPointId())) {
                pos5.setDanger(pos5.getDanger() + L06_DANGER);
            }
        }
    }

    public void validateDangerDoubleCrossover(IPoint pos1, IPoint pos2, IPoint pos3, IPoint pos4,
                                              IPoint pos5, IPoint pos6, IPoint pos7) {
        if (pos5.isActive() && pos6.isActive() && pos7.isActive() && !pos5.isBelowEmpty()) {
            if (iTables.isBotPoint(pos1.getPointId()) && iTables.isBotPoint(pos2.getPointId())
                    && iTables.isBotPoint(pos3.getPointId()) && iTables.isBotPoint(pos4.getPointId())) {
                pos5.setDanger(pos5.getDanger() + L06_DANGER);
            }
        }
    }

    private void validateDangerHozCrossover(IPoint pos1, IPoint pos2, IPoint pos3, IPoint pos4, IPoint pos5) {
        if (pos4.isActive() && !pos5.isActive() && pos4.isRightEmpty() && !pos5.isRightEmpty()
                && pos3.isLeftEmpty() && !pos2.isLeftEmpty()) {
            if (iTables.isBotPoint(pos1.getPointId()) && iTables.isBotPoint(pos2.getPointId()) && iTables.isBotPoint(pos3.getPointId())) {
                pos4.setDanger(pos4.getDanger() + L06_DANGER);
            }
        }

        if (pos4.isActive() && !pos5.isActive() && pos4.isLeftEmpty() && !pos5.isLeftEmpty()
                && pos3.isRightEmpty() && !pos2.isRightEmpty()) {
            if (iTables.isBotPoint(pos1.getPointId()) && iTables.isBotPoint(pos2.getPointId()) && iTables.isBotPoint(pos3.getPointId())) {
                pos4.setDanger(pos4.getDanger() + L06_DANGER);
            }
        }
    }

    private IPoint findMaxDanger() {
        IPoint iPoint = findEmptyPoint();
        for (int i = 0; i < iTables.getWidth(); ++i) {
            for (int j = 0; j < iTables.getHeight(); ++j) {
                if (iTables.getPoints()[i][j].getDanger() > iPoint.getDanger()) {
                    iPoint = iTables.getPoints()[i][j];
                }
            }
        }
        return iPoint;
    }

    private IPoint findEmptyPoint() {
        List<IPoint> empties = new ArrayList<>();
        for (int i = 0; i < iTables.getWidth(); ++i) {
            for (int j = 0; j < iTables.getHeight(); ++j) {
                if (iTables.isEmpty(i, j)) {
                    empties.add(iTables.getPoints()[i][j]);
                }
            }
        }
        int random = new Random().nextInt(empties.size());
        return empties.get(random);
    }
}
