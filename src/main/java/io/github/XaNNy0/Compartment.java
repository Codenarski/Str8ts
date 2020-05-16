package io.github.XaNNy0;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Compartment {

    private final List<ValueAtIndex<Field>> fields;
    private final String key;
    private final int boardSize;

    public Compartment(final List<ValueAtIndex<Field>> fields, final int boardSize) {
        this.fields = new ArrayList<>(fields);
        this.boardSize = boardSize;
        final List<Integer> rowIds = fields.stream().map(field -> field.row).distinct().collect(Collectors.toList());
        final List<Integer> columnIds = fields.stream().map(field -> field.column).distinct().collect(Collectors.toList());

        this.key = rowIds.size() == 1 ? "R" + rowIds.iterator().next() : "C" + columnIds.iterator().next();
    }

    public String getKey() {
        return this.key;
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

    public boolean removeStrandedCandidates() {
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
        final Map<Integer, AtomicInteger> amountOfNumber = new HashMap<>();

        for (final List<Integer> possibleStrait : possibleStraits) {
            for (final Integer integer : possibleStrait) {
                amountOfNumber.computeIfAbsent(integer, ignore -> new AtomicInteger(0)).incrementAndGet();
            }
        }

        amountOfNumber.forEach((key, value) -> {
            if (value.get() == possibleStraits.size()) {
                requiredDigits.add(key);
            }
        });
        return requiredDigits;
    }

    public Map<ValueAtIndex<Field>, Set<Integer>> detectIsolatedCandidates() {

        final Map<ValueAtIndex<Field>, Set<Integer>> candidatesToRemove = new HashMap<>();

        this.fields.forEach(field -> {
            final Set<Integer> availableCandidates = this.computeAllCandidatesExcept(field);
            field.value.getCandidates().forEach(candidate -> {
                if (candidate == 1) {
                    if (!availableCandidates.contains(candidate + 1)) {
                        candidatesToRemove.computeIfAbsent(field, v -> new HashSet<Integer>()).add(candidate);
                    }
                }
                if (candidate == this.boardSize) {
                    if (!availableCandidates.contains(candidate - 1)) {
                        candidatesToRemove.computeIfAbsent(field, v -> new HashSet<Integer>()).add(candidate);
                    }
                }
                if (!availableCandidates.contains(candidate - 1) && !availableCandidates.contains(candidate + 1)) {
                    candidatesToRemove.computeIfAbsent(field, v -> new HashSet<Integer>()).add(candidate);
                }
            });
        });
        return candidatesToRemove;
    }

    private Set<Integer> computeAllCandidatesExcept(final ValueAtIndex<Field> fieldToIgnore) {
        return this.fields.stream() //
                .filter(field -> !field.equals(fieldToIgnore)) //
                .map(this::computeCandidatesAndValues) //
                .flatMap(Set::stream) //
                .collect(Collectors.toSet()); //
    }

    private Set<Integer> computeCandidatesAndValues(final ValueAtIndex<Field> field) {
        final Set<Integer> set = new HashSet<>();
        if (field.value.hasValue()) {
            set.add(field.value.getValue());
        } else {
            set.addAll(field.value.getCandidates());
        }
        return set;
    }

    public boolean isValid() {
        final List<Integer> values = this.getValues();
        if (values.size() == this.fields.size()) {
            return this.fields.stream().allMatch(field -> {
                values.sort(Comparator.naturalOrder());
                Integer previous = null;
                for (final Integer value : values) {
                    if (previous == null) {
                        previous = value;
                        continue;
                    }
                    if (previous + 1 != value) {
                        return false;
                    }
                    previous = value;
                }
                return true;
            });
        } else {
            return this.getClearedStraits().size() >= 1;
        }
    }
}
