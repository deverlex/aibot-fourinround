package ai;

public class LearnLogic {

    public static LearnLogic learnLogic = new LearnLogic();

    private ITables iTables;

    private LearnLogic() {}

    public static LearnLogic getInstance() {
        return learnLogic;
    }

    public LearnLogic updateTable(ITables iTables) {
        this.iTables = iTables;
        return this;
    }

    public void checkDanger(IPoint[][] points) {
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
        IPoint[] botP15 = {points[0][0], points[0][1], points[1][1], points[2][3]};
        IPoint[] hasP15 = {};
        IPoint[] empP15 = {points[2][1], points[2][2], points[3][3]};
        validateDangerMulti(botP15, hasP15, empP15, points[2][2]);
        // [_][_][_][*]
        // [_][?][*][*]
        // [_][?][_][_]
        // [?][*][_][_]
        IPoint[] botP16 = {points[3][0], points[2][1], points[3][1], points[1][3]};
        IPoint[] hasP16 = {};
        IPoint[] empP16 = {points[0][3], points[1][2], points[1][1]};
        validateDangerMulti(botP16, hasP16, empP16, points[1][2]);
        // [_][_][_][?]
        // [_][?][?][*]
        // [_][*][X][_]
        // [*][*][_][_]
        IPoint[] botP17 = {points[0][3], points[1][2], points[1][3], points[3][1]};
        IPoint[] hasP17 = {points[2][1]};
        IPoint[] empP17 = {points[1][1], points[2][1], points[3][0]};
        validateDangerMulti(botP17, hasP17, empP17, points[1][2]);
        // [?][_][_][_]
        // [*][?][?][_]
        // [_][X][*][_]
        // [_][_][*][*]
        IPoint[] botP18 = {points[0][1], points[2][2], points[2][3], points[3][3]};
        IPoint[] hasP18 = {points[1][2]};
        IPoint[] empP18 = {points[0][0], points[1][1], points[2][1]};
        validateDangerMulti(botP18, hasP18, empP18, points[1][1]);
        // [_][_][_][_]
        // [_][*][*][?]
        // [_][*][_][X]
        // [*][_][_][_]
        IPoint[] botP19 = {points[0][3], points[1][2], points[1][1], points[2][1]};
        IPoint[] hasP19 = {points[3][2]};
        IPoint[] empP19 = {points[3][1]};
        validateDangerMulti(botP19, hasP19, empP19, points[3][1]);
        // [_][_][_][_]
        // [?][*][*][_]
        // [X][_][*][_]
        // [_][_][_][*]
        IPoint[] botP20 = {points[1][1], points[2][1], points[2][2], points[3][3]};
        IPoint[] hasP20 = {points[0][2]};
        IPoint[] empP20 = {points[0][1]};
        validateDangerMulti(botP20, hasP20, empP20, points[0][1]);
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
        dangerPoint.setDanger(dangerPoint.getDanger() + Logic.LEVEL_DOUBLE_STEP);
    }
}
