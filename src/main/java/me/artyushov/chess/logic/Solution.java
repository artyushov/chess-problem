package me.artyushov.chess.logic;

import me.artyushov.chess.model.BoardSnapshot;
import me.artyushov.chess.model.BoardSquare;
import me.artyushov.chess.model.PieceType;
import me.artyushov.chess.model.Problem;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Stack;

/**
 * Author: artyushov
 * Date: 2016-06-04 23:57
 */
public class Solution {

    public static Set<BoardSnapshot> solve(Problem problem) {
        Stack<PieceType> pieces = new Stack<>();
        pieces.addAll(problem.getPieces());

        Set<BoardSnapshot> result = new LinkedHashSet<>();

        solve(pieces, new BoardState(problem.getRowCount(), problem.getColumnCount()), result);

        return result;
    }

    private static void solve(Stack<PieceType> pieces, BoardState state, Set<BoardSnapshot> result) {

        if (pieces.isEmpty()) {
            result.add(state.snapshot());
            return;
        }
        PieceType pieceType = pieces.pop();
        Set<BoardSquare> availableSquares = state.getAvailablePositionsFor(pieceType);
        for (BoardSquare freeSquare : availableSquares) {
            state.putPiece(pieceType, freeSquare);

            solve(pieces, state, result);

            state.removePiece(freeSquare);
        }
        pieces.push(pieceType);
    }
}
