package me.artyushov.chess.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: artyushov
 * Date: 2016-05-31 22:01
 */
public class BoardState {

    private final int rowCount;
    private final int columnCount;

    private final Map<BoardSquare, PieceType> occupiedSquares = new HashMap<>();

    public BoardState(int rowCount, int columnCount) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
    }

    public void putPiece(PieceType pieceType, BoardSquare destination) {
        rangeCheck(destination);
        if (occupiedSquares.containsKey(destination)) {
            throw new IllegalStateException("Specified square is already occupied: " + destination);
        }
        occupiedSquares.put(destination, pieceType);
    }

    private void rangeCheck(BoardSquare square) {
        if (square.getRow() < 1
                || square.getRow() > rowCount
                || square.getColumn() < 1
                || square.getColumn() > columnCount) {
            throw new IllegalArgumentException("Row should be between 1 and " + rowCount
                    + " and column should be between 1 and " + columnCount
                    + ". Row: " + square.getRow() + "; column: " + square.getColumn());
        }
    }
}
