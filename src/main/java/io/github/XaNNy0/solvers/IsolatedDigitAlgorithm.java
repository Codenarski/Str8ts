package io.github.XaNNy0.solvers;

import io.github.XaNNy0.Board;
import io.github.XaNNy0.Field;
import io.github.XaNNy0.SolverAlgorithm;
import io.github.XaNNy0.ValueAtIndex;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public class IsolatedDigitAlgorithm implements SolverAlgorithm {

    @Override
    public boolean solve(final Board board) {
        final AtomicBoolean changed = new AtomicBoolean(false);

        final List<Map<ValueAtIndex<Field>, Set<Integer>>> candidatesToRemove = new ArrayList<>();
        board.getCompartments().forEach(compartment -> candidatesToRemove.add(compartment.detectIsolatedCandidates()));

        for (final Map<ValueAtIndex<Field>, Set<Integer>> valueAtIndexSetMap : candidatesToRemove) {
            valueAtIndexSetMap.forEach((v, k) -> {
                if (v.value.removeCandidates(k)) {
                    changed.set(true);
                }
            });
        }
        return changed.get();
    }
}
