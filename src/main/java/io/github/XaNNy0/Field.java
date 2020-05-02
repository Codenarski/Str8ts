package io.github.XaNNy0;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Field {
    private final boolean editable;
    private final Set<Integer> candidates;
    private final int size;
    private int value;

    public Field(final int size, final FieldSpec fieldSpec) {
        this.editable = !fieldSpec.isBlack();
        this.value = fieldSpec.getValue();
        this.size = size;
        if (this.value == 0) {
            this.candidates = IntStream.rangeClosed(1, size).boxed().collect(Collectors.toSet());
        } else {
            this.candidates = new HashSet<>(this.value);
        }
    }

    public boolean isBlack() {
        return !this.isWhite();
    }

    public boolean isWhite() {
        return this.editable;
    }

    public Set<Integer> getCandidates() {
        return this.candidates;
    }

    public int getValue() {
        return this.value;
    }

    public boolean hasValue() {
        return this.getValue() != 0;
    }

    public boolean removeCandidates(final List<Integer> values) {
        if (this.isWhite() && !this.hasValue()) {
            final boolean changes = this.candidates.removeAll(values);
            if (this.candidates.size() == 1) {
                this.value = this.candidates.iterator().next();
            }
            return changes;
        }
        return false;
    }

    public boolean removeCandidate(final int value) {
        return this.removeCandidates(new ArrayList<>(value));
    }

    public boolean retainCandidates(final List<Integer> values) {
        final List<Integer> candidatesToRemove = IntStream.rangeClosed(1, this.size).boxed().collect(Collectors.toList());
        candidatesToRemove.removeAll(values);
        return this.removeCandidates(candidatesToRemove);
    }
}
