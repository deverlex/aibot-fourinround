package ai;

public class IPoint {

    private ITables iTables;
    private int x, y;
    // [".", "myId", "bootId"]
    private String pointId;
    /// trong so
    private int danger;

    public IPoint(ITables iTables, int x, int y) {
        this.iTables = iTables;
        this.x = x;
        this.y = y;
        this.danger = 0;
    }

    public void setPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getPointId() {
        return pointId;
    }

    public void setPointId(String pointId) {
        this.pointId = pointId;
    }

    public int getDanger() {
        return danger;
    }

    public void setDanger(int danger) {
        this.danger = danger;
    }

    public boolean isEmpty() {
        return pointId.equals(".");
    }

    public boolean isBelowEmpty() {
        try {
            return iTables.getPoints()[this.x][this.y + 1].pointId.equals(".");
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isLeftEmpty() {
        try {
            return iTables.getPoints()[x - 1][y].pointId.equals(".");
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isRightEmpty() {
        try {
            return iTables.getPoints()[x + 1][y].pointId.equals(".");
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isLeftDanger() {
        try {
            return isLeftEmpty() && !iTables.getPoints()[x - 1][y].isBelowEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isRightDanger() {
        try {
            return isRightEmpty() && !iTables.getPoints()[x + 1][y].isBelowEmpty();
        } catch (Exception e) {
            return false;
        }
    }

}
