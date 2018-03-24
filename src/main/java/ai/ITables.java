package ai;

public class ITables {

    private int width;
    private int height;
    private IPoint[][] points;
    private String myId;
    private String botId;

    private LearnLogic learnLogic;

    private static ITables iTables;

    private ITables() {
        learnLogic = new LearnLogic(this);
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
        return learnLogic.makeRunFind();
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

    public boolean isEmpty(int i, int j) {
        return points[i][j].isEmpty();
    }

    public boolean isBot(IPoint iPoint) {
        return !iPoint.getPointId().equals(".") && !iPoint.getPointId().equals(myId);
    }

    public boolean isMe(IPoint iPoint) {
        return !iPoint.getPointId().equals(".") && iPoint.getPointId().equals(myId);
    }

}
