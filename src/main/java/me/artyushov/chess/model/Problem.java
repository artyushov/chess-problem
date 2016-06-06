package me.artyushov.chess.model;

import java.util.List;

/**
 * Author: artyushov
 * Date: 2016-06-04 23:56
 */
public class Problem {

    private final int rowCount;
    private final int columnCount;
    private final List<PieceType> pieces;

    public Problem(int rowCount, int columnCount, List<PieceType> pieces) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.pieces = pieces;
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public List<PieceType> getPieces() {
        return pieces;
    }
}
