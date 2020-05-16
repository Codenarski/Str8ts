package io.github.XaNNy0.solvers;

import io.github.XaNNy0.Board;
import io.github.XaNNy0.Field;
import io.github.XaNNy0.SolverAlgorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class SolvedSquaresAlgorithm implements SolverAlgorithm {

    @Override
    public boolean solve(final Board board) {

        final AtomicBoolean changed = new AtomicBoolean();
        final Map<Integer, List<Integer>> rowMap = new HashMap<>();
        final Map<Integer, List<Integer>> columnMap = new HashMap<>();

        board.getFields().forEachWhen(Field::hasValue, v -> {
            rowMap.computeIfAbsent(v.row, ignore -> new ArrayList<>()).add(v.value.getValue());
            columnMap.computeIfAbsent(v.column, ignore -> new ArrayList<>()).add(v.value.getValue());
        });

        board.getFields().forEach(v -> {
            if (v.value.removeCandidates(rowMap.getOrDefault(v.row, new ArrayList<>()))) {
                changed.set(true);
            }

            if (v.value.removeCandidates(columnMap.getOrDefault(v.column, new ArrayList<>()))) {
                changed.set(true);
            }

        });
        return changed.get();
    }
}
