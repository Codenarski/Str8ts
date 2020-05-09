package io.github.XaNNy0.solvers;

import io.github.XaNNy0.Board;
import io.github.XaNNy0.Compartment;
import io.github.XaNNy0.SolverAlgorithm;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class RequiredDigitsAlgorithm implements SolverAlgorithm {

    @Override
    public boolean solve(final Board board) {
        final AtomicBoolean changed = new AtomicBoolean(false);

        board.getCompartments().forEach(compartment -> this.removeRequiredDigits(compartment, board, changed));
        return changed.get();
    }

    private void removeRequiredDigits(final Compartment compartment, final Board board, final AtomicBoolean changed) {
        final List<Integer> requiredDigits = compartment.getRequiredDigits();

        if (!requiredDigits.isEmpty()) {
            board.getCompartments().forEachSiblings(compartment, c -> {
                if (c.removeCandidates(requiredDigits)) {
                    changed.set(true);
                }
            });
        }
    }
}
