package io.github.XaNNy0;

import io.github.XaNNy0.solvers.*;

import java.util.Arrays;

public enum SolverAlgorithms implements SolverAlgorithm {
    CheckForSolvedSquares(new SolvedSquaresAlgorithm(), "Check for Solved Squares"),
    CompartmentCheck(new CompartmentCheckAlgorithm(), "Compartment Check"),
    StrandedDigitA(new StrandedDigitAlgorithm(), "StrandedDigit"),
    IsolatedDigit(new IsolatedDigitAlgorithm(), "IsolatedDigit"),
    RequiredDigits(new RequiredDigitsAlgorithm(), "RequiredDigits"),
    NakedPairs(new NakedPairsAlgorithm(), "NakedPairs"),
    Backtracking(new Backtracking(), "Backtracking");

    private final String description;
    private final SolverAlgorithm solverAlgorithm;

    SolverAlgorithms(final SolverAlgorithm solverAlgorithm, final String description) {
        this.solverAlgorithm = solverAlgorithm;
        this.description = description;
    }

    public static SolverAlgorithms getFirst() {
        return Arrays.stream(values()).findFirst().orElse(null);
    }

    public static SolverAlgorithms getNext(final SolverAlgorithms current) {
        boolean breakPoint = false;

        for (final SolverAlgorithms value : values()) {
            if (breakPoint) {
                return value;
            } else {
                if (value == current) {
                    breakPoint = true;
                }
            }
        }
        throw new IllegalStateException("Next algorithm could not be found");
    }

    public String getDescription() {
        return this.description;
    }

    @Override
    public boolean solve(final Board board) {
        if (this.solverAlgorithm == null) {
            return true;
        }
        return this.solverAlgorithm.solve(board);
    }
}
