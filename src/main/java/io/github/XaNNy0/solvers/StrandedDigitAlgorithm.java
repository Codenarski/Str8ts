package io.github.XaNNy0.solvers;

import io.github.XaNNy0.Board;
import io.github.XaNNy0.SolverAlgorithm;

import java.util.concurrent.atomic.AtomicBoolean;

public class StrandedDigitAlgorithm implements SolverAlgorithm {

    @Override
    public boolean solve(final Board board) {
        final AtomicBoolean changed = new AtomicBoolean();

        board.getCompartments().forEach(compartment -> {
            if (compartment.removeStrandedCandidates()) {
                changed.set(true);
            }
        });
        return changed.get();
    }
}
