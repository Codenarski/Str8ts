package io.github.XaNNy0;

import java.util.ArrayList;
import java.util.List;

public class Compartment {

    private final List<FieldIndex> fieldIndices;

    public Compartment(final List<FieldIndex> fieldIndices) {
        this.fieldIndices = new ArrayList<>(fieldIndices);
    }
}
