package cleancode.minesweeper.tobe.minesweeper.board.cell;

public class CellState {
    private boolean isFlagged;
    private boolean isOpened;

    public CellState(boolean isFlagged, boolean isOpened) {
        this.isFlagged = isFlagged;
        this.isOpened = isOpened;
    }

    // Cell이 처음 생성되면 깃발도 꽂혀 있지 않고 열려있지도 않은 상태이다.

    public static CellState initialize() {
        return new CellState(false, false);
    }

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

    public boolean isFlagged() {
        return isFlagged;
    }

}
