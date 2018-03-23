package ai;

public class ITables {

    private int width;
    private int height;
    private IPoint[][] points;
    private String myId;
    private String botId;

    private CheckDanger checkDanger;

    private static ITables iTables;

    private ITables() {
        checkDanger = new CheckDanger(this);
    }

    public ITables setSize(int width, int height) {
        this.width = width;
        this.height = height;
        points = new IPoint[width][height];
        return this;
    }

    public ITables setMyId(String myId) {
        this.myId = myId;
        return this;
    }

    public static ITables build() {
        iTables = new ITables();
        return iTables;
    }

    public IPoint findBestPoint() {
        return checkDanger.makeRunFind();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public ITables swapIds() {
        String temp = myId;
        myId = botId;
        botId = temp;
        cleanDanger();
        return this;
    }

    private void cleanDanger() {
        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {
                points[i][j].setDanger(0);
            }
        }
    }

    public ITables updateTable(String[][] fields) {
        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {
                IPoint iPoint = new IPoint(this, i, j);
                iPoint.setPointId(fields[i][j]);
                points[i][j] = iPoint;
                if (!iPoint.getPointId().equals(".") && !iPoint.getPointId().equals(myId)) {
                    botId = iPoint.getPointId();
                }
            }
        }
        return this;
    }

    public IPoint[][] getPoints() {
        return points;
    }

    /**** Check point ***/
    public boolean isBotPoint(int i, int j) {
        return isBotPoint(points[i][j].getPointId());
    }

    public boolean isMyPoint(int i, int j) {
        return isMyPoint(points[i][j].getPointId());
    }

    public boolean isEmpty(int i, int j) {
        return isEmpty(points[i][j].getPointId());
    }

    public boolean isBotPoint(String pos) {
        return !pos.equals(".") && !pos.equals(myId);
    }

    public boolean isMyPoint(String pos) {
        return !pos.equals(".") && pos.equals(myId);
    }

    public boolean isEmpty(String pos) {
        return pos.equals(".");
    }
}
