package cleancode.minesweeper.tobe.minesweeper.board.cell;

import java.util.Objects;

public class CellSnapshot {
    private final CellSnapshotStatus status;
    private final int nearByLandMineCount;

    private CellSnapshot(CellSnapshotStatus status, int nearByLandMineCount) {
        this.nearByLandMineCount = nearByLandMineCount;
        this.status = status;
    }

    public static CellSnapshot of(CellSnapshotStatus status, int nearByLandMineCount) {
        return new CellSnapshot(status, nearByLandMineCount);
    }

    public static CellSnapshot ofEmpty() {
        return new CellSnapshot(CellSnapshotStatus.EMPTY, 0);
    }

    public static CellSnapshot ofFlag() {
        return new CellSnapshot(CellSnapshotStatus.FLAGGED, 0);
    }

    public static CellSnapshot ofLandMine() {
        return new CellSnapshot(CellSnapshotStatus.LANDMINE, 0);
    }

    public static CellSnapshot ofNumber(int nearByLandMineCount) {
        return new CellSnapshot(CellSnapshotStatus.NUMBER, nearByLandMineCount);
    }

    public static CellSnapshot ofUnchecked() {
        return new CellSnapshot(CellSnapshotStatus.UNCHECKED, 0);
    }

    public CellSnapshotStatus getStatus() {
        return status;
    }

    public int getNearByLandMineCount() {
        return nearByLandMineCount;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CellSnapshot snapshot = (CellSnapshot) o;
        return nearByLandMineCount == snapshot.nearByLandMineCount && status == snapshot.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, nearByLandMineCount);
    }

    public boolean isSameStatus(CellSnapshotStatus cellSnapshotStatus) {
        return this.status == cellSnapshotStatus;
    }
}
