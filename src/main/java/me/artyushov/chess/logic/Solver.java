package me.artyushov.chess.logic;

import me.artyushov.chess.model.BoardSnapshot;
import me.artyushov.chess.model.BoardSquare;
import me.artyushov.chess.model.PieceType;
import me.artyushov.chess.model.Problem;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Author: artyushov
 * Date: 2016-06-04 23:57
 */
public class Solver {

    private final List<PieceType> pieces;
    private final BoardState board;

    private int currentPieceIndex = 0;

    public Solver(List<PieceType> pieces, BoardState board) {
        this.pieces = sortPieces(pieces);
        this.board = board;
    }

    public static Set<BoardSnapshot> solve(Problem problem) {

        Set<BoardSnapshot> result = new LinkedHashSet<>();

        new Solver(problem.getPieces(), new BoardState(problem.getRowCount(), problem.getColumnCount()))
                .solve(new Stack<>(), result);

        return result;
    }

    private void solve(Stack<BoardSquare> occupiedSquares,
                       Set<BoardSnapshot> result) {

        if (currentPieceIndex == pieces.size()) {
            result.add(board.snapshot());
            return;
        }
        PieceType pieceType = pieces.get(currentPieceIndex);
        ++currentPieceIndex;

        BoardSquare fromSquare = null;
        if (currentPieceIndex > 1 && pieces.get(currentPieceIndex - 2) == pieceType) {
            fromSquare = occupiedSquares.peek();
        }

        Set<BoardSquare> availableSquares = board.getAvailablePositionsFor(pieceType, fromSquare);
        for (BoardSquare freeSquare : availableSquares) {
            board.putPiece(pieceType, freeSquare);
            occupiedSquares.push(freeSquare);

            solve(occupiedSquares, result);

            occupiedSquares.pop();
            board.removePiece(freeSquare);
        }
        --currentPieceIndex;
    }

    // sorts initial pieces so that pieces of the same type are next to each other
    // this is crucial for algorithm optimization purposed
    private List<PieceType> sortPieces(List<PieceType> pieces) {
        return pieces.stream()
                .sorted((first, second) -> Integer.compare(first.ordinal(), second.ordinal()))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
