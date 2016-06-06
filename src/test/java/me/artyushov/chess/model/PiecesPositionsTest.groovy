package me.artyushov.chess.model

import spock.lang.Specification

/**
 * Author: artyushov
 * Date: 2016-06-06 23:22
 */
class PiecesPositionsTest extends Specification {

    def "Positions are persisted correctly"() {
        def positions = new PiecesPositions(4, 4)

        when:
        positions.putPiece(PieceType.KING, new BoardSquare(2, 3))

        then:
        positions.get(new BoardSquare(2, 3)) == PieceType.KING
        positions.removePiece(new BoardSquare(2, 3)) == PieceType.KING
        positions.get(new BoardSquare(2, 3)) == null
    }

    def "A new copy doesn't depend on the initial object"() {
        def positions = new PiecesPositions(4, 4)
        positions.putPiece(PieceType.BISHOP, new BoardSquare(3, 1))

        when:
        def copy = positions.copy()
        positions.removePiece(new BoardSquare(3, 1))

        then:
        positions.get(new BoardSquare(3 ,1)) == null
        copy.get(new BoardSquare(3, 1)) == PieceType.BISHOP

    }
}
