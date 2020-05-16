package io.github.XaNNy0.solvers;

import io.github.XaNNy0.Board;
import io.github.XaNNy0.Field;
import io.github.XaNNy0.SolverAlgorithm;
import io.github.XaNNy0.ValueAtIndex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class NakedPairsAlgorithm implements SolverAlgorithm {

    @Override
    public boolean solve(final Board board) {
        final AtomicBoolean changed = new AtomicBoolean(false);
        board.getFields().forEachRow(v -> {
            if (this.detectAndRemoveNakedPairs(v)) {
                changed.set(true);
            }
        });
        board.getFields().forEachColumn(w -> {
            if (this.detectAndRemoveNakedPairs(w)) {
                changed.set(true);
            }
        });
        return changed.get();
    }

    private boolean detectAndRemoveNakedPairs(final List<ValueAtIndex<Field>> rowOrColumnFields) {
        final AtomicBoolean changed = new AtomicBoolean(false);
        final Map<String, AtomicInteger> test = new HashMap<>();

        rowOrColumnFields.forEach(field -> {
            if (field.value.getCandidates().size() == 2) {
                test.computeIfAbsent(field.value.getCandidates().toString(), ignore -> new AtomicInteger(0)).incrementAndGet();
            }
        });
        rowOrColumnFields.forEach(field -> test.forEach((k, v) -> {
            if (v.get() >= 2) {
                if (!field.value.getCandidates().toString().equals(k) && !field.value.hasValue()) {
                    final String[] str = k.replace("[", "").replace("]", "").replace(" ", "").split(",");
                    final List<Integer> candidatesToRemove = new ArrayList<>();
                    for (final String s : str) {
                        candidatesToRemove.add(Integer.parseInt(s));
                    }
                    if (field.value.removeCandidates(candidatesToRemove)) {
                        changed.set(true);
                    }
                }
            }
        }));
        return changed.get();
    }
}
