package me.artyushov.chess;

import me.artyushov.chess.model.BoardSquare;
import me.artyushov.chess.model.PieceType;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

/**
 * Author: artyushov
 * Date: 2016-06-04 21:02
 */
public class Rules {

    private final int rowCount;
    private final int columnCount;

    public Rules(int rowCount, int columnCount) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
    }

    public Set<BoardSquare> getAttackedSquares(PieceType piece, BoardSquare destination) {
        switch (piece) {
            case KING: return getAttackedByKing(destination);
            case KNIGHT: return getAttackedByKnight(destination);
            case BISHOP: return getAttackedByBishop(destination);
            case ROOK: return getAttackedByRook(destination);
            case QUEEN: return getAttackedByQueen(destination);
            default:
                throw new IllegalArgumentException("Only KING, KNIGHT, BISHOP, ROOK and QUEEN are allowed in this context");
        }
    }

    private Set<BoardSquare> getAttackedByQueen(BoardSquare destination) {
        Set<BoardSquare> result = getAttackedByBishop(destination);
        result.addAll(getAttackedByRook(destination));
        return result;
    }

    private Set<BoardSquare> getAttackedByRook(BoardSquare destination) {
        Set<BoardSquare> result = walkFrom(destination, 0, 1);
        result.addAll(walkFrom(destination, 1, 0));
        result.addAll(walkFrom(destination, 0, -1));
        result.addAll(walkFrom(destination, -1, 0));
        return result;
    }

    private Set<BoardSquare> getAttackedByBishop(BoardSquare destination) {
        Set<BoardSquare> result = walkFrom(destination, 1, 1);
        result.addAll(walkFrom(destination, 1, -1));
        result.addAll(walkFrom(destination, -1, -1));
        result.addAll(walkFrom(destination, -1, 1));
        return result;
    }

    private Set<BoardSquare> getAttackedByKing(BoardSquare destination) {
        return Stream.of(
                new BoardSquare(destination.getRow(), destination.getColumn() + 1),
                new BoardSquare(destination.getRow() + 1, destination.getColumn() + 1),
                new BoardSquare(destination.getRow() + 1, destination.getColumn()),
                new BoardSquare(destination.getRow() + 1, destination.getColumn() - 1),
                new BoardSquare(destination.getRow(), destination.getColumn() - 1),
                new BoardSquare(destination.getRow() - 1, destination.getColumn() - 1),
                new BoardSquare(destination.getRow() - 1, destination.getColumn()),
                new BoardSquare(destination.getRow() - 1, destination.getColumn() + 1)
        )
                .filter(square -> inRange(square.getRow(), square.getColumn()))
                .collect(toSet());
    }

    private Set<BoardSquare> getAttackedByKnight(BoardSquare destination) {
        return Stream.of(
                new BoardSquare(destination.getRow() + 1, destination.getColumn() + 2),
                new BoardSquare(destination.getRow() + 2, destination.getColumn() + 1),
                new BoardSquare(destination.getRow() + 2, destination.getColumn() - 1),
                new BoardSquare(destination.getRow() + 1, destination.getColumn() - 2),
                new BoardSquare(destination.getRow() - 1, destination.getColumn() - 2),
                new BoardSquare(destination.getRow() - 2, destination.getColumn() - 1),
                new BoardSquare(destination.getRow() - 2, destination.getColumn() + 1),
                new BoardSquare(destination.getRow() - 1, destination.getColumn() + 2)
        )
                .filter(square -> inRange(square.getRow(), square.getColumn()))
                .collect(toSet());
    }

    private Set<BoardSquare> walkFrom(BoardSquare start, int deltaRow, int deltaColumn) {
        Set<BoardSquare> result = new HashSet<>();
        int row = start.getRow() + deltaRow;
        int column = start.getColumn() + deltaColumn;
        while (inRange(row, column)) {
            result.add(new BoardSquare(row, column));
            row += deltaRow;
            column += deltaColumn;
        }
        return result;
    }

    private boolean inRange(int row, int column) {
        return (row > 0 && row <= rowCount
                && column > 0 && column <= columnCount);
    }
}
