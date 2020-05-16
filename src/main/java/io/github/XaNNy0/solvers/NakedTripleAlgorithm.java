package io.github.XaNNy0.solvers;

import io.github.XaNNy0.Board;
import io.github.XaNNy0.Field;
import io.github.XaNNy0.SolverAlgorithm;
import io.github.XaNNy0.ValueAtIndex;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NakedTripleAlgorithm implements SolverAlgorithm {

    @Override
    public boolean solve(final Board board) {

        board.getFields().forEachRow(row -> {

            final List<ValueAtIndex<Field>> possibleFields = row.stream()
                    .filter(field -> field.value.getCandidates().size() == 2 || field.value.getCandidates().size() == 3)
                    .collect(Collectors.toList());

            final List<List<ValueAtIndex<Field>>> tripleList = new ArrayList<>();
            for (final ValueAtIndex<Field> fieldToTest : possibleFields) {

                final List<ValueAtIndex<Field>> triple = new ArrayList<>();
                triple.add(fieldToTest);

                if (fieldToTest.value.getCandidates().size() == 2) {
                    for (final ValueAtIndex<Field> fieldToCompareAgainst : possibleFields) {
                        if (fieldToCompareAgainst.value.getCandidates().size() == 2) {

                        }

                        if (fieldToCompareAgainst.value.getCandidates().size() == 3) {

                        }
                    }
                }

                if (fieldToTest.value.getCandidates().size() == 3) {
                    for (final ValueAtIndex<Field> fieldToCompareAgainst : possibleFields) {
                        if (fieldToCompareAgainst.value.getCandidates().size() == 2) {

                        }
                        if (fieldToCompareAgainst.value.getCandidates().size() == 3) {

                        }
                    }
                }
            }
        });
        return false;
    }
}
