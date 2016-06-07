package me.artyushov.chess.logic;

import me.artyushov.chess.model.BoardSnapshot;
import me.artyushov.chess.model.BoardSquare;
import me.artyushov.chess.model.PieceType;
import me.artyushov.chess.model.PiecesPositions;

import java.util.Set;

/**
 * Author: artyushov
 * Date: 2016-05-31 22:01
 */
public class BoardState {

    private final int rowCount;
    private final int columnCount;

    private final PiecesPositions positions;

    private final GameLogic gameLogic;

    public BoardState(int rowCount, int columnCount) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.positions = new PiecesPositions(rowCount, columnCount);
        this.gameLogic = new GameLogic(rowCount, columnCount);
    }

    /**
     * Will returns only those squares that are after specified square.
     * Will not perform filtering if `after` is null.
     *
     * This is useful in order not to process all permutations of the same pieces
     * which produce the same result.
     *
     */
    public Set<BoardSquare> getAvailablePositionsFor(PieceType piece, BoardSquare after) {
        return gameLogic.getAvailableSquares(piece, after);
    }

    public void putPiece(PieceType pieceType, BoardSquare destination) {
        rangeCheck(destination);

        gameLogic.putPiece(pieceType, destination);
        positions.putPiece(pieceType, destination);
    }

    public void removePiece(BoardSquare fromSquare) {
        rangeCheck(fromSquare);

        PieceType piece = positions.removePiece(fromSquare);
        if (piece == null) {
            throw new IllegalStateException("Specified square is already empty: " + fromSquare);
        }

        gameLogic.removePiece(piece, fromSquare);
    }

    public BoardSnapshot snapshot() {
        return new BoardSnapshot(rowCount, columnCount, positions.copy());
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
