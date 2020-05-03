package io.github.XaNNy0;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    public boolean removeIsolatedCandidatesCrap() {
        boolean changed = false;
        final int boardSize = this.fields.get(0).value.getSize();
        final List<Integer> values;
        final List<List<Integer>> straits;
        final List<List<Integer>> straitsToRemove = new ArrayList<>();
        final Set<Integer> currentCandidates = new HashSet<>();
        final Set<Integer> remainingCandidates = new HashSet<>();

        values = this.fields.stream().
                filter(field -> field.value.hasValue()).
                map(field -> field.value.getValue()).
                collect(Collectors.toList());

        straits = IntStream.iterate(1, i -> (i + this.fields.size() - 1) <= boardSize, i -> i + 1)
                .mapToObj(i -> IntStream.rangeClosed(i, i + this.fields.size() - 1)
                        .boxed()
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());

        straits.forEach(strait -> {
            values.stream()
                    .filter(value -> !strait.contains(value))
                    .map(value -> strait)
                    .forEach(straitsToRemove::add);
        });

        straits.removeAll(straitsToRemove);
        straitsToRemove.clear();

        this.fields.stream().map(field -> field.value.getCandidates())
                .forEach(currentCandidates::addAll);

        currentCandidates.addAll(values);

        for (final List<Integer> strait : straits) {
            if (!currentCandidates.containsAll(strait)) {
                straitsToRemove.add(strait);
            }
        }

        straits.removeAll(straitsToRemove);
        straitsToRemove.clear();

        for (final List<Integer> strait : straits) {
            remainingCandidates.addAll(strait);
        }

        for (final ValueAtIndex<Field> field : this.fields) {
            if (field.value.retainCandidates(new ArrayList<>(remainingCandidates))) {
                changed = true;
            }
        }
        return changed;
    }

    //TODO: eigenes foreach schreiben, so die gelösten compartments übersprungen werden
}
