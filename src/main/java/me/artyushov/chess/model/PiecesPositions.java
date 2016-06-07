package me.artyushov.chess.model;

import java.util.BitSet;

/**
 *
 * This class uses a bitset to keep track of every piece position.
 * The bitset represents all squares of the board as if we started counting them
 * from the bottom-left corner to the right, then next row from left to right, etc.
 *
 * There are six possible states for a particular square. Either it is free or it is
 * occupied by 1 of 5 possible piece types. Therefore, the minimum number of bits
 * used to represent the square's state is 3.
 *
 * Author: artyushov
 * Date: 2016-06-06 00:01
 */
public class PiecesPositions {

    private static final PieceType[] PIECE_TYPES = PieceType.values();

    private final int rowCount;
    private final int columnCount;

    private final BitSet positions;

    public PiecesPositions(int rowCount, int columnCount) {
        this(rowCount, columnCount, new BitSet(rowCount * columnCount));
    }

    private PiecesPositions(int rowCount, int columnCount, BitSet positions) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.positions = positions;
    }

    public void putPiece(PieceType pieceType, BoardSquare square) {
        writeToPosition(squareToIndex(square), pieceType.ordinal() + 1);
    }

    public PieceType removePiece(BoardSquare square) {
        int position = squareToIndex(square);
        int ordinal = readFromPosition(position) - 1;
        writeToPosition(position, 0);
        return PIECE_TYPES[ordinal];
    }

    public PieceType get(BoardSquare square) {
        int position = squareToIndex(square);
        int pieceId = readFromPosition(position);
        if (pieceId == 0) {
            return null;
        } else {
            return PIECE_TYPES[pieceId - 1];
        }
    }

    private int squareToIndex(BoardSquare square) {
        return ((square.getRow() - 1) * columnCount + square.getColumn() - 1) * 3;
    }

    private int readFromPosition(int position) {
        boolean b1 = positions.get(position);
        boolean b2 = positions.get(position + 1);
        boolean b3 = positions.get(position + 2);
        int result = 0;
        if (b1) {
            result += 4;
        }
        if (b2) {
            result += 2;
        }
        if (b3) {
            result += 1;
        }
        return result;
    }

    private void writeToPosition(int position, int value) {
        positions.set(position, value / 4 == 1);
        value = value % 4;
        positions.set(position + 1, value / 2 == 1);
        value = value % 2;
        positions.set(position + 2, value == 1);
    }

    public PiecesPositions copy() {
        return new PiecesPositions(rowCount, columnCount, (BitSet) positions.clone());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PiecesPositions that = (PiecesPositions) o;

        if (rowCount != that.rowCount) return false;
        if (columnCount != that.columnCount) return false;
        return positions.equals(that.positions);

    }

    @Override
    public int hashCode() {
        int result = rowCount;
        result = 31 * result + columnCount;
        result = 31 * result + positions.hashCode();
        return result;
    }
}
