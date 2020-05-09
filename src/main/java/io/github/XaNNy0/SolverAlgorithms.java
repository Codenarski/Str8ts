package io.github.XaNNy0;

import io.github.XaNNy0.solvers.CompartmentCheckAlgorithm;
import io.github.XaNNy0.solvers.IsolatedDigitsAlgorithm;
import io.github.XaNNy0.solvers.RequiredDigitsAlgorithm;
import io.github.XaNNy0.solvers.SolvedSquaresAlgorithm;

import java.util.Arrays;

public enum SolverAlgorithms implements SolverAlgorithm {
    CheckForSolvedSquares(new SolvedSquaresAlgorithm(), "Check for Solved Squares"),
    //ShowPossibles("ShowPossibles"),
    CompartmentCheck(new CompartmentCheckAlgorithm(), "Compartment Check"),
    StrandedDigitA(new IsolatedDigitsAlgorithm(), "StrandedDigitA"),
    //All included in StrandedDigit
    /*
    StrandedSequenceA("StrandedSequenceA"),
    CompartmentHighLowA("CompartmentHigh/LowA"),
    */
    //All included in RequiredDigits
    /*
    HighLowStr8tsRowsA("High/LowStr8ts(rows)A"),
    HighLowStr8tsColsA("High/LowStr8ts(cols)A"),
    */

    //IsolatedDigit("IsolatedDigit"),
    RequiredDigits(new RequiredDigitsAlgorithm(), "RequiredDigits"),

    //Probably not splitting between A & B strategies
    /*
    StrandedDigitB("StrandedDigitB"),
    StrandedSequenceB("StrandedSequenceB"),
    CompartmentHighLowB("CompartmentHigh/LowB"),
    HighLowStr8tsRowsB("High/LowStr8ts(rows)B"),
    HighLowStr8tsColsB("High/LowStr8ts(cols)B"),
     */
    NakedPairs("NakedPairs"),
    NakedTriples("NakedTriples"),
    ;

    private final String description;
    private final SolverAlgorithm solverAlgorithm;

    SolverAlgorithms(final String description) {
        this.solverAlgorithm = null;
        this.description = description;
    }

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

    @Override
    public boolean solve(final Board board) {
        if (this.solverAlgorithm == null) {
            return true;
        }
        return this.solverAlgorithm.solve(board);
    }
}
