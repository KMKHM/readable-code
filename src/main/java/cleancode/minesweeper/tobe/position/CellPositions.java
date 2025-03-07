package cleancode.minesweeper.tobe.position;

import cleancode.minesweeper.tobe.cell.Cell;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CellPositions {
    private final List<CellPosition> cellPositions;

    private CellPositions(List<CellPosition> cellPositions) {
        this.cellPositions = cellPositions;
    }

    public static CellPositions of(List<CellPosition> cellPositions) {
        return new CellPositions(cellPositions);
    }

    public List<CellPosition> extractRandomPositions(int count) {
        List<CellPosition> positions = new ArrayList<>(cellPositions);
        Collections.shuffle(positions);
        return positions.subList(0, count);
    }

    // 외부에서 참조 X
    public List<CellPosition> getCellPositions() {
        return new ArrayList<>(cellPositions);
    }

    public static CellPositions from(Cell[][] board) {
        List<CellPosition> cellPositions = new ArrayList<>();

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                CellPosition cellPosition = CellPosition.of(row, col);
                cellPositions.add(cellPosition);
            }
        }

        return of(cellPositions);
    }

    public List<CellPosition> subtract(List<CellPosition> positions) {
        List<CellPosition> cellPositions  = new ArrayList<>(this.cellPositions);
        CellPositions positionsToSubtract = CellPositions.of(positions);
        return cellPositions.stream()
                .filter(positionsToSubtract::doesNotContain)
                .toList();
    }

    private boolean doesNotContain(CellPosition position) {
        return !cellPositions.contains(position);
    }
}
