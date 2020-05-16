package io.github.XaNNy0.solvers;

import io.github.XaNNy0.Board;
import io.github.XaNNy0.Field;
import io.github.XaNNy0.SolverAlgorithm;
import io.github.XaNNy0.ValueAtIndex;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class Backtracking implements SolverAlgorithm {

    @Override
    public boolean solve(final Board board) {
        return this.backtracking(board);

    }

    private boolean backtracking(final Board board) {
        final Field[][] boardFields = board.getFields().getArray();
        for (int row = 0; row < board.getFields().getArray().length; row++) {
            for (int column = 0; column < board.getFields().getArray().length; column++) {
                if (boardFields[row][column].isWhite() && !boardFields[row][column].hasValue()) {
                    for (final Integer candidate : boardFields[row][column].getCandidates()) {

                        if (this.isOk(board)) {
                            boardFields[row][column].setValue(candidate);
                            if (this.backtracking(board)) {
                                return true;
                            } else {
                                boardFields[row][column].setValue(0);
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return this.isOk(board);
    }

    private boolean isOk(final Board board) {
        final AtomicBoolean isOk = new AtomicBoolean(true);
        board.getFields().forEachRow(this.getListConsumer(isOk));
        if (isOk.get()) {
            board.getFields().forEachColumn(this.getListConsumer(isOk));
        }
        if (isOk.get()) {
            this.checkCompartments(board, isOk);
        }
        return isOk.get();
    }

    private void checkCompartments(final Board board, final AtomicBoolean isOk) {
        if (!board.getCompartments().areValid()) {
            isOk.set(false);
        }
    }

    private Consumer<List<ValueAtIndex<Field>>> getListConsumer(final AtomicBoolean isOk) {
        return row -> {
            final Map<Integer, AtomicInteger> amountOfValue = new HashMap<>();
            row.forEach(field -> {
                if (field.value.hasValue()) {
                    amountOfValue.computeIfAbsent(field.value.getValue(), ignore -> new AtomicInteger(0)).incrementAndGet();
                }
            });
            amountOfValue.forEach((k, v) -> {
                if (v.get() >= 2) {
                    isOk.set(false);
                }
            });
        };
    }
}
