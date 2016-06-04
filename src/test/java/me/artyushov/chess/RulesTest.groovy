package me.artyushov.chess

import me.artyushov.chess.model.BoardSquare
import me.artyushov.chess.model.PieceType
import spock.lang.Specification

/**
 * Author: artyushov
 * Date: 2016-06-04 21:45
 */
class RulesTest extends Specification {

    def "Should correctly determine squares attacked by knight"() {
        def rules = new Rules(rowCount, columnCount)

        expect:
        rules.getAttackedSquares(PieceType.KNIGHT, new BoardSquare(row, column)) ==
                result.collect { new BoardSquare(it[0], it[1]) }.toSet()

        where:
        rowCount | columnCount | row | column || result
        8        | 8           | 1   | 1      || [[2, 3], [3, 2]]
        8        | 8           | 8   | 8      || [[7, 6], [6, 7]]
        8        | 8           | 4   | 4      || [[5, 6], [6, 5], [6, 3], [5, 2], [3, 2], [2, 3], [2, 5], [3, 6]]
    }

    def "Should correctly determine squares attacked by bishop"() {
        def rules = new Rules(rowCount, columnCount)

        expect:
        rules.getAttackedSquares(PieceType.BISHOP, new BoardSquare(row, column)) ==
                result.collect { new BoardSquare(it[0], it[1]) }.toSet()

        where:
        rowCount | columnCount | row | column || result
        3        | 3           | 1   | 1      || [[2, 2], [3, 3]]
        3        | 3           | 1   | 3      || [[2, 2], [3, 1]]
        3        | 3           | 2   | 2      || [[1, 1], [3, 1], [3, 3], [1, 3]]
        3        | 4           | 1   | 3      || [[2, 2], [3, 1], [2, 4]]
    }

    def "Should correctly determine squares attacked by king"() {
        def rules = new Rules(rowCount, columnCount)

        expect:
        rules.getAttackedSquares(PieceType.KING, new BoardSquare(row, column)) ==
                result.collect { new BoardSquare(it[0], it[1]) }.toSet()

        where:
        rowCount | columnCount | row | column || result
        3        | 3           | 1   | 1      || [[2, 1], [1, 2], [2, 2]]
        3        | 3           | 2   | 2      || [[1, 1], [1, 2], [1, 3], [2, 1], [2, 3], [3, 1], [3, 2], [3, 3]]
    }

    def "Should correctly determine squares attacked by rook"() {
        def rules = new Rules(rowCount, columnCount)

        expect:
        rules.getAttackedSquares(PieceType.ROOK, new BoardSquare(row, column)) ==
                result.collect { new BoardSquare(it[0], it[1]) }.toSet()

        where:
        rowCount | columnCount | row | column || result
        3        | 3           | 1   | 1      || [[1, 2], [1, 3], [2, 1], [3, 1]]
        3        | 3           | 2   | 2      || [[2, 1], [2, 3], [1, 2], [3, 2]]
    }

    def "Should correctly determine squares attacked by queen"() {
        def rules = new Rules(rowCount, columnCount)

        expect:
        rules.getAttackedSquares(PieceType.QUEEN, new BoardSquare(row, column)) ==
                result.collect { new BoardSquare(it[0], it[1]) }.toSet()

        where:
        rowCount | columnCount | row | column || result
        3        | 3           | 1   | 1      || [[1, 2], [1, 3], [2, 1], [3, 1], [2, 2], [3, 3]]
        3        | 3           | 2   | 1      || [[1, 1], [3, 1], [1, 2], [2, 2], [3, 2], [2, 3]]
    }

}
