package io.github.XaNNy0.solvers.compartmentcheck;

import io.github.XaNNy0.Compartment;

import java.util.Collections;
import java.util.List;

public class ComputePossibleUpperRange {


    private final int boardSize;

    public ComputePossibleUpperRange(final int boardSize) {
        this.boardSize = boardSize;
    }

    public int compute(final Compartment compartment) throws NoValueInsideCompartmentException {

        final List<Integer> values = compartment.getValues();
        if (values.isEmpty()) {
            throw new NoValueInsideCompartmentException("No value for calculation in compartment");
        }
        final int minimum = Collections.min(values);
        return Math.min(minimum + compartment.size() - 1, this.boardSize);
    }
}
