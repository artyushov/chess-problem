package me.artyushov.chess.logic

import me.artyushov.chess.model.PieceType
import me.artyushov.chess.model.Problem
import spock.lang.Specification

/**
 * Author: artyushov
 * Date: 2016-06-07 22:32
 */
class SolverTest extends Specification {

    def "Assert answers for queens puzzle"() {
        expect:
        Solver.solve(new Problem(n, n, [PieceType.QUEEN] * n)).size() == result

        where:
        n || result
        1 || 1
        2 || 0
        3 || 0
        4 || 2
        5 || 10
        6 || 4
        7 || 40
        8 || 92
    }
}
