package me.artyushov.chess;

import me.artyushov.chess.model.BoardSquare;

/**
 * Author: artyushov
 * Date: 2016-06-07 01:05
 */
public class Util {

    public static int convertSquareToIndex(BoardSquare square, int columnCount) {
        return (square.getRow() - 1) * columnCount + square.getColumn() - 1;
    }

    public static BoardSquare convertIndexToSquare(int index, int columnCount) {
        return new BoardSquare(index / columnCount + 1, index % columnCount + 1);
    }
}
