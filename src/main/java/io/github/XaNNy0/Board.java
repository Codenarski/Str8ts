package io.github.XaNNy0;


import java.util.concurrent.atomic.AtomicBoolean;

public class Board {
    private final SquareArray<Field> fields;
    private final Compartments compartments;
    private SolverAlgorithms currentStep = null;
    private boolean currentStepSolved = false;

    public Board(final SquareArray<FieldSpec> fieldSpecs) {
        this.fields = fieldSpecs.map((fieldSpec, length) -> new Field(length, fieldSpec), length -> new Field[length][length]);
        this.compartments = new Compartments(this.fields);
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

    public boolean isNotSolved() {
        final AtomicBoolean solved = new AtomicBoolean(true);

        this.fields.forEach(fieldValueAtIndex -> {
            if (!fieldValueAtIndex.value.hasValue() && fieldValueAtIndex.value.isWhite()) {
                solved.set(false);
            }
        });
        return !solved.get();
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
