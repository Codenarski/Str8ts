package io.github.XaNNy0;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Compartment {

    private final List<ValueAtIndex<Field>> fields;
    private final String key;

    public Compartment(final List<ValueAtIndex<Field>> fields) {
        this.fields = new ArrayList<>(fields);

        final List<Integer> rowIds = fields.stream().map(field -> field.row).distinct().collect(Collectors.toList());
        final List<Integer> columnIds = fields.stream().map(field -> field.column).distinct().collect(Collectors.toList());

        this.key = rowIds.size() == 1 ? "R" + rowIds.iterator().next() : "C" + columnIds.iterator().next();
    }

    public String getKey() {
        return this.key;
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

    public boolean retainCandidates(final Collection<Integer> candidatesToRetain) {
        boolean changed = false;
        for (final ValueAtIndex<Field> field : this.fields) {
            if (field.value.retainCandidates(candidatesToRetain)) {
                changed = true;
            }
        }
        return changed;
    }

    public boolean removeCandidates(final Collection<Integer> candidatesToRemove) {
        boolean changed = false;
        for (final ValueAtIndex<Field> field : this.fields) {
            if (field.value.removeCandidates(candidatesToRemove)) {
                changed = true;
            }
        }
        return changed;
    }

    public boolean removeIsolatedCandidates() {
        boolean changed = false;

        final List<List<Integer>> clearedStraits = this.getClearedStraits();
        final Set<Integer> remainingCandidates = this.getRemainingCandidates(clearedStraits);

        for (final ValueAtIndex<Field> field : this.fields) {
            if (field.value.retainCandidates(remainingCandidates)) {
                changed = true;
            }
        }
        return changed;
    }

    private Set<Integer> getRemainingCandidates(final List<List<Integer>> clearedStraits) {
        return clearedStraits.stream().flatMap(Collection::stream).collect(Collectors.toSet());
    }

    private List<List<Integer>> getClearedStraits() {

        final List<List<Integer>> possibleStraits = this.getPossibleStraits();

        final Set<Integer> values = this.fields.stream().
                filter(field -> field.value.hasValue()).
                map(field -> field.value.getValue()).
                collect(Collectors.toSet());

        final Set<Integer> currentCandidates = this.fields.stream().map(v -> v.value.getCandidates()).flatMap(Collection::stream).collect(Collectors.toSet());
        currentCandidates.addAll(values);

        return possibleStraits.stream().filter(currentCandidates::containsAll).collect(Collectors.toList());
    }

    public List<List<Integer>> getPossibleStraits() {
        final Set<Integer> values = this.fields.stream().
                filter(field -> field.value.hasValue()).
                map(field -> field.value.getValue()).
                collect(Collectors.toSet());

        final int boardSize = this.fields.get(0).value.getSize();

        return IntStream.iterate(1, i -> (i + this.fields.size() - 1) <= boardSize, i -> i + 1)
                .mapToObj(i -> IntStream.rangeClosed(i, i + this.fields.size() - 1)
                        .boxed()
                        .collect(Collectors.toList()))
                .filter(j -> j.containsAll(values))
                .collect(Collectors.toList());
    }

    public List<Integer> getRequiredDigits() {
        final List<Integer> requiredDigits = new ArrayList<>();
        final List<List<Integer>> possibleStraits = this.getPossibleStraits();
        for (final List<Integer> possibleStrait : possibleStraits) {
            if (requiredDigits.isEmpty()) {
                requiredDigits.addAll(possibleStrait);
            } else {
                requiredDigits.retainAll(possibleStrait);
            }
        }
        return requiredDigits;
    }
}
