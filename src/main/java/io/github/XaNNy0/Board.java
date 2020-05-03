package io.github.XaNNy0;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class Board {
    private final SquareArray<Field> fields;
    private final List<Compartment> compartments;
    private SolverAlgorithms currentStep = null;
    private boolean currentStepSolved = false;

    public Board(final SquareArray<FieldSpec> fieldSpecs) {
        this.fields = fieldSpecs.map((fieldSpec, length) -> new Field(length, fieldSpec), length -> new Field[length][length]);
        this.compartments = this.detectCompartments();
    }

    private void validateFieldSpec(final FieldSpec[][] fieldSpecs) {
        if (fieldSpecs == null) {
            throw new IllegalArgumentException("Fieldspec is null");
        }
        if (fieldSpecs.length != fieldSpecs[0].length) {
            throw new IllegalArgumentException("Fieldspecs are not a square");
        }
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

    private List<Compartment> detectCompartments() {
        final List<Compartment> compartments = new ArrayList<>();
        compartments.addAll(this.detectRowCompartments());
        compartments.addAll(this.detectColumnCompartments());
        return compartments;
    }

    public List<Compartment> detectRowCompartments() {
        final List<ValueAtIndex<Field>> fieldIndexList = new ArrayList<>();
        final List<Compartment> compartments = new ArrayList<>();

        this.fields.forEachRow((fieldValueAtIndex, endOfRow) -> {
            if (fieldValueAtIndex.value.isWhite()) {
                fieldIndexList.add(fieldValueAtIndex);
            }
            if (fieldValueAtIndex.value.isBlack() || endOfRow) {
                if (!fieldIndexList.isEmpty()) {
                    compartments.add(new Compartment(fieldIndexList));
                    fieldIndexList.clear();
                }
            }
        });
        return compartments;
    }

    public List<Compartment> detectColumnCompartments() {
        final List<ValueAtIndex<Field>> fieldIndexList = new ArrayList<>();
        final List<Compartment> compartments = new ArrayList<>();

        this.fields.forEachColumn((fieldValueAtIndex, endOfColumn) -> {
            if (fieldValueAtIndex.value.isWhite()) {
                fieldIndexList.add(fieldValueAtIndex);
            }
            if (fieldValueAtIndex.value.isBlack() || endOfColumn) {
                if (!fieldIndexList.isEmpty()) {
                    compartments.add(new Compartment(fieldIndexList));
                    fieldIndexList.clear();
                }
            }
        });
        return compartments;
    }

    public List<Compartment> getCompartments() {
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
