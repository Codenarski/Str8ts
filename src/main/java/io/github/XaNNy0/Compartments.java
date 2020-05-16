package io.github.XaNNy0;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Compartments {
    private final List<Compartment> compartments;
    private final Map<String, List<Compartment>> compartmentsByKey;
    private final int boardSize;

    public Compartments(final SquareArray<Field> fields, final int boardSize) {
        this.boardSize = boardSize;
        this.compartments = this.detectCompartments(fields);
        this.compartmentsByKey = this.compartments.stream().collect(Collectors.groupingBy(Compartment::getKey));
    }

    public void forEach(final Consumer<Compartment> action) {
        this.compartments.forEach(action);
    }

    public List<Compartment> getCompartmentsByKey(final String key) {
        return this.compartmentsByKey.get(key);
    }

    public void forEachSiblings(final Compartment compartmentToCompare, final Consumer<Compartment> action) {
        this.compartmentsByKey.get(compartmentToCompare.getKey()).stream() //
                .filter(c -> !c.equals(compartmentToCompare)) //
                .forEach(action); //
    }

    public Stream<Compartment> stream() {
        return this.compartments.stream();
    }

    private List<Compartment> detectCompartments(final SquareArray<Field> fields) {
        final List<Compartment> compartments = new ArrayList<>();
        compartments.addAll(this.detectRowCompartments(fields));
        compartments.addAll(this.detectColumnCompartments(fields));
        return compartments;
    }

    public boolean areValid() {
        final AtomicBoolean areValid = new AtomicBoolean(true);
        this.compartments.forEach(compartment -> {
            if (!compartment.isValid()) {
                areValid.set(false);
            }
        });
        return areValid.get();
    }


    public List<Compartment> detectRowCompartments(final SquareArray<Field> fields) {
        return fields.streamRows()
                .map(this::createCompartment)
                .flatMap(Collection::stream)
                .filter(compartment -> compartment.size() > 1)
                .collect(Collectors.toList());
    }

    public List<Compartment> detectColumnCompartments(final SquareArray<Field> fields) {
        return fields.streamColumns()
                .map(this::createCompartment)
                .flatMap(Collection::stream)
                .filter(compartment -> compartment.size() > 1)
                .collect(Collectors.toList());
    }

    private List<Compartment> createCompartment(final List<ValueAtIndex<Field>> rowOrColumnFields) {
        final List<Compartment> compartments = new ArrayList<>();

        final List<ValueAtIndex<Field>> fieldIndexList = new ArrayList<>();
        for (final ValueAtIndex<Field> rowOrColumnField : rowOrColumnFields) {
            if (rowOrColumnField.value.isWhite()) {
                fieldIndexList.add(rowOrColumnField);
            }
            if (rowOrColumnField.value.isBlack()) {
                if (!fieldIndexList.isEmpty()) {
                    compartments.add(new Compartment(fieldIndexList, this.boardSize));
                    fieldIndexList.clear();
                }
            }
        }
        if (!fieldIndexList.isEmpty()) {
            compartments.add(new Compartment(fieldIndexList, this.boardSize));
            fieldIndexList.clear();
        }
        return compartments;
    }
}
