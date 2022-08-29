package ar.edu.itba.ss;

public class DataNode {
    private long timeStamp;
    private int cellCount;

    public DataNode(long timeStamp, int cellCount) {
        this.timeStamp = timeStamp;
        this.cellCount = cellCount;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getCellCount() {
        return cellCount;
    }

    public void setCellCount(int cellCount) {
        this.cellCount = cellCount;
    }
}
