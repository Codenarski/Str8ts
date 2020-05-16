package io.github.XaNNy0.solvers;

import io.github.XaNNy0.Board;
import io.github.XaNNy0.Field;
import io.github.XaNNy0.SolverAlgorithm;
import io.github.XaNNy0.ValueAtIndex;
import io.github.XaNNy0.solvers.nakedx.NakedX;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

public class NakedXAlgorithm implements SolverAlgorithm {

    private final Function<Field, NakedX> nakedXSupplier;
    private final int threshold;

    public NakedXAlgorithm(final Function<Field, NakedX> nakedXSupplier, final int threshold) {
        this.nakedXSupplier = nakedXSupplier;
        this.threshold = threshold;
    }

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

        final Map<String, List<NakedX>> nakedX = rowOrColumnFields.stream() //
                .map(v -> v.value) //
                .map(this.nakedXSupplier) //
                .filter(NakedX::isNaked) //
                .collect(Collectors.groupingBy(NakedX::getGroupingKey)); //

        final List<NakedX> qualifiedNakedX = nakedX.values().stream() //
                .filter(v -> v.size() >= this.threshold) //
                .flatMap(Collection::stream) //
                .collect(Collectors.toList()); //

        rowOrColumnFields.stream()
                .filter(field -> !field.value.hasValue())
                .forEach(field -> {
                    qualifiedNakedX.forEach(qualifiedField -> {
                        final NakedX current = this.nakedXSupplier.apply(field.value);
                        if (!current.equals(qualifiedField)) {
                            if (field.value.removeCandidates(qualifiedField.getField().getCandidates())) {
                                changed.set(true);
                            }
                        }
                    });
                });
        return changed.get();
    }
}
