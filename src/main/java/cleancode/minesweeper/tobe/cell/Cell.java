package cleancode.minesweeper.tobe.cell;

public abstract class Cell {

    protected static final String FLAG_SIGN = "⚑";
    protected static final String UNCHECKED_SIGN = "□";

    private int nearbyLandMineCount;
//    private boolean isLandMine;
    protected boolean isFlagged;
    protected boolean isOpened;

//    private Cell2(int nearbyLandMineCount, boolean isLandMine, boolean isFlagged, boolean isOpened) {
//        this.nearbyLandMineCount = nearbyLandMineCount;
//        this.isLandMine = isLandMine;
//        this.isFlagged = isFlagged;
//        this.isOpened = isOpened;
//    }


    public abstract boolean isLandMine();

    public abstract boolean hasLandMineCount();

    public abstract String getSign();

    public void flag() {
        this.isFlagged = true;
    }

    public void open() {
        this.isOpened = true;
    }

    public boolean isChecked() {
        return isFlagged || isOpened;
    }

    public boolean isOpened() {
        return isOpened;
    }
}
