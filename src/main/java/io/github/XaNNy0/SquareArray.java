package io.github.XaNNy0;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class SquareArray<T> {

    private final T[][] t;
    private final Map<Integer, List<ValueAtIndex<T>>> rows;
    private final Map<Integer, List<ValueAtIndex<T>>> columns;

    public SquareArray(final T[][] t) {
        this.t = t;
        this.rows = new HashMap<>();
        this.columns = new HashMap<>();
        this.forEach(valueAtIndex -> {
            this.rows.computeIfAbsent(valueAtIndex.row, ignore -> new ArrayList<>()).add(valueAtIndex);
            this.columns.computeIfAbsent(valueAtIndex.column, ignore -> new ArrayList<>()).add(valueAtIndex);
        });
    }

    public T[][] getArray() {
        return this.t;
    }

    public Stream<List<ValueAtIndex<T>>> streamRows() {
        final List<List<ValueAtIndex<T>>> list = new ArrayList<>();
        for (int i = 0; i < this.t.length; i++) {
            list.add(new ArrayList<>(this.rows.get(i)));
        }
        return list.stream();
    }

    public Stream<List<ValueAtIndex<T>>> streamColumns() {
        final List<List<ValueAtIndex<T>>> list = new ArrayList<>();
        for (int i = 0; i < this.t.length; i++) {
            list.add(new ArrayList<>(this.columns.get(i)));
        }
        return list.stream();
    }

    public <Target> SquareArray<Target> map(final BiFunction<T, Integer, Target> mapper, final Function<Integer, Target[][]> supplier) {
        final int length = this.t.length;
        final Target[][] target = supplier.apply(length);
        this.forEach(v -> target[v.row][v.column] = mapper.apply(this.t[v.row][v.column], length));
        return new SquareArray<>(target);
    }

    public void forEachWhen(final Predicate<T> condition, final Consumer<ValueAtIndex<T>> consumer) {
        final int length = this.t.length;
        for (int x = 0; x < length; x++) {
            for (int y = 0; y < length; y++) {
                final ValueAtIndex<T> currentValueAtIndex = new ValueAtIndex<>(x, y, this.t[x][y]);
                if (condition.test(currentValueAtIndex.value)) {
                    consumer.accept(currentValueAtIndex);
                }
            }
        }
    }

    public void forEach(final Consumer<ValueAtIndex<T>> consumer) {
        this.forEachWhen(f -> true, consumer);
    }

    public void forEachRow(final Consumer<List<ValueAtIndex<T>>> consumer) {
        for (int i = 0; i < this.t.length; i++) {
            consumer.accept(new ArrayList<>(this.rows.get(i)));
        }
    }

    public void forEachColumn(final Consumer<List<ValueAtIndex<T>>> consumer) {
        for (int i = 0; i < this.t.length; i++) {
            consumer.accept(new ArrayList<>(this.columns.get(i)));
        }
    }
}
