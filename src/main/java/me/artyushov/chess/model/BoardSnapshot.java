package me.artyushov.chess.model;

/**
 * Author: artyushov
 * Date: 2016-06-05 21:42
 */
public class BoardSnapshot {

    private final int rowCount;
    private final int columnCount;

    private final PiecesPositions positions;

    public BoardSnapshot(int rowCount, int columnCount, PiecesPositions positions) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.positions = positions;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int row = rowCount; row >= 1; row--) {
            for (int column = 1; column <= columnCount; column++) {
                BoardSquare square = new BoardSquare(row, column);
                PieceType piece = positions.get(square);
                if (piece == null) {
                    result.append(" * ");
                } else {
                    result.append(" ").append(piece.name().charAt(0)).append(" ");
                }
            }
            result.append("\n");
        }
        return result.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BoardSnapshot that = (BoardSnapshot) o;

        if (rowCount != that.rowCount) return false;
        if (columnCount != that.columnCount) return false;
        if (!positions.equals(that.positions)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = rowCount;
        result = 31 * result + columnCount;
        result = 31 * result + positions.hashCode();
        return result;
    }
}
