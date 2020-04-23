package io.github.XaNNy0;

import java.util.stream.IntStream;

public class Field {
    private final boolean editable;
    private final int[] candidates;
    private final int value;

    public Field(final int size, final FieldSpec fieldSpec) {
        this.editable = !fieldSpec.isBlack();
        this.value = fieldSpec.getValue();
        this.candidates = IntStream.range(1, size).toArray();
    }
}
