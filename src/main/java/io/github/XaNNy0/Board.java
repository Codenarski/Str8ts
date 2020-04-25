package io.github.XaNNy0;

public class Board {
    private final SquareArray<Field> fields;
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
            this.currentStepSolved = this.currentStep.solve(this.fields);
            return;
        } else if (!this.currentStepSolved) {
            this.currentStep = SolverAlgorithms.getNext(this.currentStep);
            this.currentStepSolved = this.currentStep.solve(this.fields);
            return;
        } else {
            this.currentStep = null;
            return;
        }
    }
}
