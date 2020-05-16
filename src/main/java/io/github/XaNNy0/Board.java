package io.github.XaNNy0;


import java.util.Comparator;
import java.util.List;
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

    public boolean isCurrentStepSolved() {
        return this.currentStepSolved;
    }

    public SolverAlgorithms getCurrentStep() {
        return this.currentStep;
    }

    public void nextStep() {
        if (this.currentStep == null) {
            this.currentStep = SolverAlgorithms.getFirst();
            this.currentStepSolved = this.currentStep.solve(this);
            return;
        } else if (!this.currentStepSolved) {
            this.currentStep = SolverAlgorithms.getNext(this.currentStep);
            this.currentStepSolved = this.currentStep.solve(this);
            return;
        } else {
            this.currentStep = null;
            return;
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
        return this.compartments.stream().allMatch(compartment -> {
            final List<Integer> values = compartment.getValues();
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
    }

    public boolean checkDistinctValues() {
        return Stream.concat(this.fields.streamRows(), this.fields.streamColumns()).allMatch(rowOrColumn -> {
            final long amountOfValueFields = rowOrColumn.stream()
                    .filter(this::isValueField)
                    .count();
            final long amountOfDistinctValues = rowOrColumn.stream()
                    .filter(field -> field.value.hasValue())
                    .map(field -> field.value.getValue())
                    .distinct()
                    .count();
            return amountOfValueFields == amountOfDistinctValues;
        });
    }

    //TODO: sollte eig ins Feld
    private boolean isValueField(final ValueAtIndex<Field> field) {
        return field.value.isWhite() || (field.value.isBlack() && field.value.hasValue());
    }

    public boolean isNotSolved() {
        return !this.isSolved();
    }

    //TODO: kann müll wenn isSolved da ist :)
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
