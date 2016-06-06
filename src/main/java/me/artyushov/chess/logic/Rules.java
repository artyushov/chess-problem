package me.artyushov.chess.logic;

import me.artyushov.chess.model.BoardSquare;
import me.artyushov.chess.model.PieceType;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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

    private final Cache cache = new Cache();

    public Rules(int rowCount, int columnCount) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
    }

    public Set<BoardSquare> getAttackedSquares(PieceType piece, BoardSquare destination) {
        Set<BoardSquare> result = cache.get(piece, destination);
        if (result != null) {
            return result;
        }
        switch (piece) {
            case KING: result = getAttackedByKing(destination); break;
            case KNIGHT: result = getAttackedByKnight(destination); break;
            case BISHOP: result = getAttackedByBishop(destination); break;
            case ROOK: result = getAttackedByRook(destination); break;
            case QUEEN: result = getAttackedByQueen(destination); break;
            default:
                throw new IllegalArgumentException("Only KING, KNIGHT, BISHOP, ROOK and QUEEN are allowed in this context");
        }
        cache.put(piece, destination, result);
        return result;
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

    private static class Cache {

        private final Map<CacheKey, Set<BoardSquare>> map = new HashMap<>();

        public void put(PieceType piece, BoardSquare square, Set<BoardSquare> attackedSquares) {
            map.put(new CacheKey(piece, square), attackedSquares);
        }

        public Set<BoardSquare> get(PieceType piece, BoardSquare square) {
            return map.get(new CacheKey(piece, square));
        }
    }

    private static class CacheKey {
        final PieceType pieceType;
        final BoardSquare square;

        private CacheKey(PieceType pieceType, BoardSquare square) {
            this.pieceType = pieceType;
            this.square = square;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            CacheKey cacheKey = (CacheKey) o;

            if (pieceType != cacheKey.pieceType) return false;
            return square.equals(cacheKey.square);

        }

        @Override
        public int hashCode() {
            int result = pieceType.hashCode();
            result = 31 * result + square.hashCode();
            return result;
        }
    }
}
