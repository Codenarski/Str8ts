package io.github.XaNNy0.solvers.compartmentcheck;

import io.github.XaNNy0.Compartment;

import java.util.Collections;
import java.util.List;

public class ComputePossibleLowerRange {

    public ComputePossibleLowerRange() {
    }

    public int compute(final Compartment compartment) throws NoValueInsideCompartmentException {
        final List<Integer> values = compartment.getValues();
        if (values.isEmpty()) {
            throw new NoValueInsideCompartmentException("No value for calculation in compartment");
        }
        final int maximum = Collections.max(values);
        return Math.max(maximum - (compartment.size() - 1), 1);
    }
}
