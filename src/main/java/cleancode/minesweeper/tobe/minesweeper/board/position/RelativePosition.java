package cleancode.minesweeper.tobe.minesweeper.board.position;

import java.util.List;
import java.util.Objects;

public class RelativePosition {

    private final int deltaRow;
    private final int deltaCol;

    public static final List<RelativePosition> SURROUNDED_POSITIONS = List.of(
            RelativePosition.of(-1, -1),
            RelativePosition.of(-1, 0),
            RelativePosition.of(-1, 1),
            RelativePosition.of(0, -1),
            RelativePosition.of(0, 1),
            RelativePosition.of(1, -1),
            RelativePosition.of(1, 0),
            RelativePosition.of(1, 1)
    );

    public RelativePosition(int deltaCol, int deltaRow) {
        this.deltaCol = deltaCol;
        this.deltaRow = deltaRow;
    }

    public static RelativePosition of(int deltaCol, int deltaRow) {
        return new RelativePosition(deltaCol, deltaRow);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RelativePosition that = (RelativePosition) o;
        return deltaRow == that.deltaRow && deltaCol == that.deltaCol;
    }

    @Override
    public int hashCode() {
        return Objects.hash(deltaRow, deltaCol);
    }

    public int getDeltaRow() {
        return deltaRow;
    }

    public int getDeltaCol() {
        return deltaCol;
    }
}
