package me.artyushov.chess.model;

/**
 * Author: artyushov
 * Date: 2016-05-31 22:01
 */
public class BoardSquare {

    private final int row;
    private final int column;

    public BoardSquare(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BoardSquare that = (BoardSquare) o;

        if (row != that.row) return false;
        return column == that.column;
    }

    @Override
    public int hashCode() {
        int result = row;
        result = 31 * result + column;
        return result;
    }

    @Override
    public String toString() {
        return "{row=" + row + ", column=" + column + '}';
    }
}
