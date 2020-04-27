package io.github.XaNNy0;


import java.util.ArrayList;
import java.util.List;

public class Board {
    private final SquareArray<Field> fields;
    private final List<Compartment> compartments;
    private SolverAlgorithms currentStep = null;
    private boolean currentStepSolved = false;

    public Board(final SquareArray<FieldSpec> fieldSpecs) {
        /**
         try {
         this.validateFieldSpec(fieldSpecs);
         } catch (final IllegalArgumentException e) {
         throw new IllegalArgumentException("Board couldnt be initialized", e);
         }
         **/
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
        final List<FieldIndex> fieldIndexList = new ArrayList<>();
        final List<Compartment> compartments = new ArrayList<>();

        this.fields.forEachRow((fieldValueAtIndex, endOfRow) -> {
            if (fieldValueAtIndex.value.isWhite()) {
                fieldIndexList.add(new FieldIndex(fieldValueAtIndex));
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
        final List<FieldIndex> fieldIndexList = new ArrayList<>();
        final List<Compartment> compartments = new ArrayList<>();

        this.fields.forEachColumn((fieldValueAtIndex, endOfColumn) -> {
            if (fieldValueAtIndex.value.isWhite()) {
                fieldIndexList.add(new FieldIndex(fieldValueAtIndex));
            }
            if (fieldValueAtIndex.value.isBlack() || endOfColumn) {
                compartments.add(new Compartment(fieldIndexList));
                fieldIndexList.clear();
            }
        });
        return compartments;
    }

    public List<Compartment> getCompartments() {
        return this.compartments;
    }
}
