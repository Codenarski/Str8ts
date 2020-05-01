package io.github.XaNNy0.solvers;

import io.github.XaNNy0.Board;
import io.github.XaNNy0.SolverAlgorithm;
import io.github.XaNNy0.solvers.compartmentcheck.ComputePossibleLowerRange;
import io.github.XaNNy0.solvers.compartmentcheck.ComputePossibleUpperRange;
import io.github.XaNNy0.solvers.compartmentcheck.NoValueInsideCompartmentException;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CompartmentCheckAlgorithm implements SolverAlgorithm {
    @Override
    public boolean solve(final Board board) {

        final AtomicBoolean changed = new AtomicBoolean();
        final ComputePossibleLowerRange computePossibleLowerRange = new ComputePossibleLowerRange();
        final ComputePossibleUpperRange computePossibleUpperRange = new ComputePossibleUpperRange(board.getFields().getArray().length);

        board.getCompartments().forEach(compartment -> {
            try {
                final int lowRangeEnd = computePossibleLowerRange.compute(compartment);
                final int topRangeEnd = computePossibleUpperRange.compute(compartment);
                final List<Integer> range = IntStream.rangeClosed(lowRangeEnd, topRangeEnd).boxed().collect(Collectors.toList());
                if (compartment.retainCandidates(range)) {
                    changed.set(true);
                }
            } catch (final NoValueInsideCompartmentException e) {
                return;
            }
        });
        return changed.get();
    }
}
