package io.github.XaNNy0;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Compartment {

    private final List<ValueAtIndex<Field>> fields;

    public Compartment(final List<ValueAtIndex<Field>> fields) {
        this.fields = new ArrayList<>(fields);
    }

    public List<ValueAtIndex<Field>> getFields() {
        return this.fields;
    }

    public int size() {
        return this.fields.size();
    }

    public List<Integer> getValues() {
        return this.fields.stream() //
                .filter(v -> v.value.hasValue()) //
                .map(v -> v.value.getValue()) //
                .collect(Collectors.toList()); //
    }

    public boolean retainCandidates(final List<Integer> candidatesToRetain) {
        boolean changed = false;
        for (final ValueAtIndex<Field> field : this.fields) {
            if (field.value.retainCandidates(candidatesToRetain)) {
                changed = true;
            }
        }
        return changed;
    }

    //TODO: Fehler, aktuell wird die Isloation falsch ermittelt
    public boolean removeIsolatedCandidates() {
        boolean changed = false;
        for (final ValueAtIndex<Field> field : this.fields) {
            for (final Integer candidate : field.value.getCandidates()) {
                for (final Integer candidateToCompareAgainst : field.value.getCandidates()) {
                    if (candidate.equals(candidateToCompareAgainst)) {
                        continue;
                    }
                    if (Math.abs(candidate - candidateToCompareAgainst) > this.fields.size()) {
                        field.value.removeCandidate(candidate);
                        changed = true;
                    }
                }
            }
        }
        return changed;
    }
}
