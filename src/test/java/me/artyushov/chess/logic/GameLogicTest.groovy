package me.artyushov.chess.logic

import me.artyushov.chess.model.BoardSquare
import me.artyushov.chess.model.PieceType
import spock.lang.Specification
/**
 * Author: artyushov
 * Date: 2016-06-05 23:18
 */
class GameLogicTest extends Specification {

    def "Correctly tracks free squares"() {
        def logic = new GameLogic(3, 3)
        logic.putPiece(PieceType.KING, new BoardSquare(1, 1))

        expect:
        logic.getAvailableSquares(PieceType.QUEEN) == [new BoardSquare(2, 3), new BoardSquare(3, 2)].toSet()
        logic.getAvailableSquares(PieceType.BISHOP) ==
                [new BoardSquare(2, 3), new BoardSquare(3, 2),
                 new BoardSquare(3, 1), new BoardSquare(1, 3)].toSet()
        logic.getAvailableSquares(PieceType.ROOK) == [new BoardSquare(2, 3), new BoardSquare(3, 2),
                                                      new BoardSquare(3, 3)].toSet()
    }
}
