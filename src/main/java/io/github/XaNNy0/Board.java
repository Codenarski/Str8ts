package io.github.XaNNy0;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

public class Board {
    private final SquareArray<Field> fields;
    private final Compartments compartments;
    private SolverAlgorithms currentStep = null;
    private boolean currentStepSolved = false;

    public Board(final SquareArray<FieldSpec> fieldSpecs) {
        this.fields = fieldSpecs.map((fieldSpec, length) -> new Field(length, fieldSpec), length -> new Field[length][length]);
        this.compartments = new Compartments(this.fields, this.fields.getArray().length);
    }

    public void nextStep() {
        if (this.currentStep == null) {
            this.currentStep = SolverAlgorithms.getFirst();
            this.currentStepSolved = this.currentStep.solve(this);
        } else if (!this.currentStepSolved) {
            this.currentStep = SolverAlgorithms.getNext(this.currentStep);
            this.currentStepSolved = this.currentStep.solve(this);
        } else {
            this.currentStep = null;
        }
    }

    public SquareArray<Field> getFields() {
        return this.fields;
    }

    public Compartments getCompartments() {
        return this.compartments;
    }

    public boolean isSolved() {
        return this.checkDistinctValues() && this.checkCompartmentLogic();
    }

    public boolean checkCompartmentLogic() {
        final AtomicBoolean isValid = new AtomicBoolean(true);
        this.compartments.forEach(compartment -> {
            if (!compartment.isValid()) {
                isValid.set(false);
            }
        });
        return isValid.get();
    }

    public boolean checkDistinctValues() {
        return Stream.concat(this.fields.streamRows(), this.fields.streamColumns()).allMatch(rowOrColumn -> {
            final long amountOfValueFields = rowOrColumn.stream()
                    .filter(field -> field.value.isValueField())
                    .count();
            final long amountOfDistinctValues = rowOrColumn.stream()
                    .filter(field -> field.value.hasValue())
                    .map(field -> field.value.getValue())
                    .distinct()
                    .count();
            return amountOfValueFields == amountOfDistinctValues;
        });
    }

    public boolean isNotSolved() {
        return !this.isSolved();
    }

    public boolean equals(final Board board) {
        for (int x = 0; x < this.fields.getArray().length; x++) {
            for (int y = 0; y < this.fields.getArray().length; y++) {
                if (this.fields.getArray()[x][y].getValue() != board.getFields().getArray()[x][y].getValue()) {
                    return false;
                }
            }
        }
        return true;
    }
}
