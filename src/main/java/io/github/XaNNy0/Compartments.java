package io.github.XaNNy0;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Compartments {
    private final List<Compartment> compartments;
    private final Map<String, List<Compartment>> compartmentsByKey;

    public Compartments(final SquareArray<Field> fields) {
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

    private List<Compartment> detectCompartments(final SquareArray<Field> fields) {
        final List<Compartment> compartments = new ArrayList<>();
        compartments.addAll(this.detectRowCompartments(fields));
        compartments.addAll(this.detectColumnCompartments(fields));
        return compartments;
    }


    public List<Compartment> detectRowCompartments(final SquareArray<Field> fields) {
        return fields.streamRows().map(this::createCompartment).flatMap(Collection::stream).collect(Collectors.toList());
    }

    public List<Compartment> detectColumnCompartments(final SquareArray<Field> fields) {
        return fields.streamColumns().map(this::createCompartment).flatMap(Collection::stream).collect(Collectors.toList());
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
                    compartments.add(new Compartment(fieldIndexList));
                    fieldIndexList.clear();
                }
            }
        }
        if (!fieldIndexList.isEmpty()) {
            compartments.add(new Compartment(fieldIndexList));
            fieldIndexList.clear();
        }
        return compartments;
    }

}
