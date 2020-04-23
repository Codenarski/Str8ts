package io.github.XaNNy0;

public class Board {
    private final Field[][] fields;

    public Board(final FieldSpec[][] fieldSpecs) {

        try {
            this.validateFieldSpec(fieldSpecs);
        } catch (final IllegalArgumentException e) {
            throw new IllegalArgumentException("Board couldnt be initialized", e);
        }

        this.fields = new SquareArray<FieldSpec, Field>().map(fieldSpecs, (fieldSpec, length) -> new Field(length, fieldSpec), length -> new Field[length][length]);
    }

    private void validateFieldSpec(final FieldSpec[][] fieldSpecs) {
        if (fieldSpecs == null) {
            throw new IllegalArgumentException("Fieldspec is null");
        }
        if (fieldSpecs.length != fieldSpecs[0].length) {
            throw new IllegalArgumentException("Fieldspecs are not a square");
        }
    }
}
