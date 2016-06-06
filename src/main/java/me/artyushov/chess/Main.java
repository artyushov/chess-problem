package me.artyushov.chess;

import me.artyushov.chess.logic.Solution;
import me.artyushov.chess.model.BoardSnapshot;
import me.artyushov.chess.model.PieceType;
import me.artyushov.chess.model.Problem;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

/**
 * Author: artyushov
 * Date: 2016-06-05 22:16
 */
public class Main {

    public static final int PARAMETERS_COUNT = 7;

    public static void main(String[] args) {
        Problem problem = parseArgs(args);
        if (problem == null) {
            System.err.println("Couldn't parse arguments.");
            printHelp();
        } else {
            long start = System.currentTimeMillis();
            Set<BoardSnapshot> solutions = Solution.solve(problem);
            long elapsedTime = System.currentTimeMillis() - start;

            System.err.println("Found " + solutions.size() + " solutions in " + elapsedTime + " milliseconds.");

            try (PrintWriter out = getOutputWriter(args)) {
                solutions.forEach(out::println);
            } catch (FileNotFoundException e) {
                System.err.println("Cannot write to the specified file.");
                printHelp();
            }
        }
    }

    private static Problem parseArgs(String[] args) {
        if (args.length < PARAMETERS_COUNT) {
            return null;
        }

        try {
            List<Integer> intArgs = Stream.of(args).limit(PARAMETERS_COUNT).map(Integer::parseInt).collect(toList());
            List<PieceType> pieces = createPieces(intArgs.subList(2, intArgs.size()));
            return new Problem(intArgs.get(0), intArgs.get(1), pieces);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static List<PieceType> createPieces(List<Integer> pieceCounts) {
        List<PieceType> result = new ArrayList<>();
        for (PieceType pieceType : PieceType.values()) {
            result.addAll(Collections.nCopies(pieceCounts.get(pieceType.ordinal()), pieceType));
        }
        return result;
    }

    private static void printHelp() {
        System.err.println("Usage:");
        System.err.println("java -jar chessProblem.jar rows columns " + createPieceCountParams() + "[output_file]");
    }

    private static String createPieceCountParams() {
        return Stream.of(PieceType.values())
                .map(pieceType -> "n_" + pieceType.name().toLowerCase())
                .collect(joining(" "));
    }

    private static PrintWriter getOutputWriter(String[] args) throws FileNotFoundException {
        if (args.length > PARAMETERS_COUNT) {
            String fileName = args[PARAMETERS_COUNT];
            return new PrintWriter(fileName);
        } else {
            return new PrintWriter(System.out);
        }
    }
}
