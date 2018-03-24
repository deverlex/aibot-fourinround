package ai;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//idea: kiem tra tat cac cac vi tri trong co kha nang nguy hiem
// tien hanh danh trong so
class Logic {

    private final static int LEVEL_DEAD = 12;
    private final static int LEVEL_DOUBLE_STEP = 7;
    private final static int LEVEL_MAYBE = 2;

    private ITables iTables;

    public Logic(ITables iTables) {
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
        // [_][_][_][_]
        // [?][?][*][*]
        // [*][X][_][_]
        // [*][_][_][_]
        IPoint[] botP1 = {points[0][2], points[0][3], points[2][1], points[3][1]};
        IPoint[] hasP1 = {points[1][2]};
        IPoint[] empP1 = {points[0][1], points[1][1]};
        validateDangerMulti(botP1, hasP1, empP1, points[0][1]);
        // [_][_][_][_]
        // [?][?][*][*]
        // [X][*][_][_]
        // [_][*][_][_]
        IPoint[] botP2 = {points[1][2], points[1][3], points[2][1], points[3][1]};
        IPoint[] hasP2 = {points[0][2]};
        IPoint[] empP2 = {points[0][1], points[1][1]};
        validateDangerMulti(botP2, hasP2, empP2, points[1][1]);
        // [_][_][_][_]
        // [*][*][?][?]
        // [_][_][*][X]
        // [_][_][*][_]
        IPoint[] botP3 = {points[0][1], points[1][1], points[2][2], points[2][3]};
        IPoint[] hasP3 = {points[3][2]};
        IPoint[] empP3 = {points[2][1], points[3][1]};
        validateDangerMulti(botP3, hasP3, empP3, points[2][1]);
        // [_][_][_][_]
        // [*][*][?][?]
        // [_][_][X][*]
        // [_][_][_][*]
        IPoint[] botP4 = {points[0][1], points[1][1], points[3][2], points[3][3]};
        IPoint[] hasP4 = {points[2][2]};
        IPoint[] empP4 = {points[3][1], points[2][1]};
        validateDangerMulti(botP4, hasP4, empP4, points[3][1]);
        // double
        // [?][_][_][_]
        // [X][?][_][_]
        // [_][*][*][_]
        // [_][*][_][*]
        IPoint[] botP5 = {points[1][2], points[1][3], points[2][2], points[3][3]};
        IPoint[] hasP5 = {points[0][1]};
        IPoint[] empP5 = {points[1][1], points[0][0]};
        validateDangerMulti(botP5, hasP5, empP5, points[1][1]);
        // [_][_][_][?]
        // [_][_][?][X]
        // [_][*][*][_]
        // [*][_][*][_]
        IPoint[] botP6 = {points[0][3], points[1][2], points[2][2], points[2][3]};
        IPoint[] hasP6 = {points[3][1]};
        IPoint[] empP6 = {points[2][1], points[3][0]};
        validateDangerMulti(botP6, hasP6, empP6, points[2][1]);
        // [_][_][_][_]
        // [*][*][?][?]
        // [_][*][X][X]
        // [*][_][_][_]
        IPoint[] botP7 = {points[0][1], points[1][1], points[0][3], points[1][2]};
        IPoint[] hasP7 = {points[2][2], points[3][2]};
        IPoint[] empP7 = {points[2][1], points[3][1]};
        validateDangerMulti(botP7, hasP7, empP7, points[2][1]);
        // [_][_][_][_]
        // [?][?][*][*]
        // [X][X][*][_]
        // [_][_][_][*]
        IPoint[] botP8 = {points[3][3], points[2][2], points[2][1], points[3][1]};
        IPoint[] hasP8 = {points[0][2], points[1][2]};
        IPoint[] empP8 = {points[0][1], points[1][1]};
        validateDangerMulti(botP8, hasP8, empP8, points[1][1]);

        // [_][_][_][_]
        // [*][?][?][*]
        // [_][*][X][_]
        // [_][*][_][_]
        IPoint[] botP9 = {points[0][1], points[3][1], points[1][2], points[1][3]};
        IPoint[] hasP9 = {points[2][2]};
        IPoint[] empP9 = {points[1][1], points[2][1]};
        validateDangerMulti(botP9, hasP9, empP9, points[1][1]);
        // [_][_][_][_]
        // [*][?][?][*]
        // [_][X][*][_]
        // [_][_][*][_]
        IPoint[] botP10 = {points[0][1], points[3][1], points[2][2], points[2][3]};
        IPoint[] hasP10 = {points[1][2]};
        IPoint[] empP10 = {points[2][1], points[1][1]};
        validateDangerMulti(botP10, hasP10, empP10, points[2][1]);
        // double crossover
        // [_][_][_][_]
        // [*][?][?][*]
        // [_][*][X][_]
        // [*][_][_][_]
        IPoint[] botP11 = {points[0][1], points[3][1], points[1][2], points[0][3]};
        IPoint[] hasP11 = {points[2][2]};
        IPoint[] empP11 = {points[2][1], points[1][1]};
        validateDangerMulti(botP11, hasP11, empP11, points[2][1]);
        // [_][_][_][_]
        // [*][?][?][*]
        // [_][X][*][_]
        // [_][_][_][*]
        IPoint[] botP12 = {points[0][1], points[3][1], points[2][2], points[3][3]};
        IPoint[] hasP12 = {points[1][2]};
        IPoint[] empP12 = {points[1][1], points[2][1]};
        validateDangerMulti(botP12, hasP12, empP12, points[1][1]);
        // [_][_][_][_]
        // [?][?][*][?]
        // [*][*][_][X]
        // [*][_][_][_]
        IPoint[] botP13 = {points[0][3], points[0][2], points[1][2], points[2][1]};
        IPoint[] hasP13 = {points[3][2]};
        IPoint[] empP13 = {points[0][1], points[1][1], points[3][1]};
        validateDangerMulti(botP13, hasP13, empP13, points[0][1]);
        // [_][_][_][_]
        // [?][*][?][?]
        // [X][_][*][*]
        // [_][_][_][*]
        IPoint[] botP14 = {points[1][1], points[2][2], points[3][3], points[3][2]};
        IPoint[] hasP14 = {points[0][2]};
        IPoint[] empP14 = {points[0][1], points[2][1], points[3][1]};
        validateDangerMulti(botP14, hasP14, empP14, points[3][1]);
        // [*][_][_][_]
        // [*][*][?][_]
        // [_][_][?][_]
        // [_][_][*][?]

        // [_][_][_][*]
        // [_][?][*][*]
        // [_][?][_][_]
        // [?][*][_][_]

        // [_][_][_][?]
        // [_][?][?][*]
        // [_][*][X][_]
        // [*][*][_][_]

        // [?][_][_][_]
        // [*][?][?][_]
        // [_][X][*][_]
        // [_][_][*][*]


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

    private void validateDangerMulti(IPoint[] botPoints, IPoint[] hasPoints, IPoint[] emptyPoints, IPoint dangerPoint) {
        for (IPoint point : botPoints) {
            if (!iTables.isBot(point)) return;
        }

        for (IPoint point : hasPoints) {
            if (point.isEmpty()) return;
        }

        for (IPoint point : emptyPoints) {
            if (!point.isEmpty()) return;
        }
        dangerPoint.setDanger(dangerPoint.getDanger() + LEVEL_DOUBLE_STEP);
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
