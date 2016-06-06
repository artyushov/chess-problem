package me.artyushov.chess.logic

import me.artyushov.chess.model.BoardSquare
import me.artyushov.chess.model.PieceType
import spock.lang.Specification
/**
 * Author: artyushov
 * Date: 2016-06-04 20:42
 */
class BoardStateTest extends Specification {

    def "Cannot put piece outside the board"() {
        setup:
        def state = new BoardState(2, 2)

        when:
        state.putPiece(PieceType.BISHOP, new BoardSquare(row, column))

        then:
        thrown(IllegalArgumentException)

        where:
        row | column
        -1  | -1
        -1  | 1
        1   | -1
        3   | 2
        2   | 3
    }

    def "Cannot put multiple pieces to the same square"() {
        setup:
        def state = new BoardState(4, 4)
        def destination = new BoardSquare(2, 3)

        when:
        state.putPiece(PieceType.BISHOP, destination)
        state.putPiece(PieceType.KING, destination)

        then:
        thrown(IllegalStateException)
    }
}
