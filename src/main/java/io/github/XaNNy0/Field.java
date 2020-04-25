package io.github.XaNNy0;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Field {
    private final boolean editable;
    private final Set<Integer> candidates;
    private int value;

    public Field(final int size, final FieldSpec fieldSpec) {
        this.editable = !fieldSpec.isBlack();
        this.value = fieldSpec.getValue();
        this.candidates = IntStream.rangeClosed(1, size).boxed().collect(Collectors.toSet());
    }

    public int getValue() {
        return this.value;
    }

    public boolean hasValue() {
        return this.getValue() != 0;
    }

    public boolean removeCandidate(final List<Integer> values) {
        final boolean changes = this.candidates.removeAll(values);
        if (this.candidates.size() == 1) {
            this.value = this.candidates.iterator().next();
        }
        return changes;
    }
}
