package me.artyushov.chess.logic;

import me.artyushov.chess.model.BoardSquare;
import me.artyushov.chess.model.PieceType;

import java.util.HashSet;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

/**
 * Author: artyushov
 * Date: 2016-06-05 22:00
 */
public class GameLogic {

    private final int rowCount;
    private final int columnCount;
    private final Rules rules;

    private final int[][] attackersCount;
    private final Set<BoardSquare> freeSquares;
    private final Set<BoardSquare> occupiedSquares = new HashSet<>();

    public GameLogic(int rowCount, int columnCount) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.rules = new Rules(rowCount, columnCount);

        this.attackersCount = new int[rowCount][columnCount];

        this.freeSquares = new HashSet<>(rowCount * columnCount, 1.0f);
        fillFreeSquares();
    }

    public void putPiece(PieceType pieceType, BoardSquare destination) {
        Set<BoardSquare> attackedSquares = rules.getAttackedSquares(pieceType, destination);
        attackedSquares.forEach(square -> {
            freeSquares.remove(square);
            attackersCount[square.getRow() - 1][square.getColumn() - 1]++;
        });

        freeSquares.remove(destination);
        occupiedSquares.add(destination);
        attackersCount[destination.getRow() - 1][destination.getColumn() - 1] = 1;
    }

    public void removePiece(PieceType piece, BoardSquare fromSquare) {
        Set<BoardSquare> attackedSquares = rules.getAttackedSquares(piece, fromSquare);
        attackedSquares.forEach(square -> {
            attackersCount[square.getRow() - 1][square.getColumn() - 1]--;
            if (attackersCount[square.getRow() - 1][square.getColumn() - 1] == 0) {
                freeSquares.add(square);
            }
        });
        attackersCount[fromSquare.getRow() - 1][fromSquare.getColumn() - 1] = 0;
        occupiedSquares.remove(fromSquare);
        freeSquares.add(fromSquare);
    }

    public Set<BoardSquare> getAvailableSquares(PieceType piece) {
        return freeSquares.stream()
                .filter(freeSquare -> !containsAny(rules.getAttackedSquares(piece, freeSquare), occupiedSquares))
                .collect(toSet());
    }

    private boolean containsAny(Set<BoardSquare> first, Set<BoardSquare> second) {
        return second.stream().anyMatch(first::contains);
    }

    private void fillFreeSquares() {
        for (int row = 1; row <= rowCount; row++) {
            for (int column = 1; column <= columnCount; column++) {
                freeSquares.add(new BoardSquare(row, column));
            }
        }
    }
}
