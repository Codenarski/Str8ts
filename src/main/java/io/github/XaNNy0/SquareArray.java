package io.github.XaNNy0;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class SquareArray<T> {

    private final T[][] t;

    public SquareArray(final T[][] t) {
        this.t = t;
    }


    public T[][] getArray() {
        return this.t;
    }

    <Target> SquareArray<Target> map(final BiFunction<T, Integer, Target> mapper, final Function<Integer, Target[][]> supplier) {
        final int length = this.t.length;
        final Target[][] target = supplier.apply(length);
        this.forEach(v -> target[v.row][v.column] = mapper.apply(this.t[v.row][v.column], length));
        return new SquareArray<>(target);
    }

    public void forEachWhen(final Predicate<T> condition, final Consumer<ValueAtIndex<T>> consumer) {
        final int length = this.t.length;
        for (int x = 0; x < length; x++) {
            for (int y = 0; y < length; y++) {
                final ValueAtIndex<T> currentValueAtIndex = new ValueAtIndex<T>(x, y, this.t[x][y]);
                if (condition.test(currentValueAtIndex.value)) {
                    consumer.accept(currentValueAtIndex);
                }
            }
        }
    }

    public void forEach(final Consumer<ValueAtIndex<T>> consumer) {
        this.forEachWhen(f -> true, consumer);
    }

    public void forEachRow(final RowConsumer<T> consumer) {
        final int length = this.t.length;
        for (int x = 0; x < length; x++) {
            for (int y = 0; y < length; y++) {
                final ValueAtIndex<T> currentValueAtIndex = new ValueAtIndex<T>(x, y, this.t[x][y]);
                consumer.consumeField(currentValueAtIndex, y == length - 1);
            }
        }
    }

    public void forEachColumn(final ColumnConsumer<T> consumer) {
        final int length = this.t.length;
        for (int x = 0; x < length; x++) {
            for (int y = 0; y < length; y++) {
                final ValueAtIndex<T> currentValueAtIndex = new ValueAtIndex<T>(x, y, this.t[y][x]);
                consumer.consumeField(currentValueAtIndex, x == length - 1);
            }
        }
    }

}
