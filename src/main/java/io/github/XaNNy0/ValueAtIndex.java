package io.github.XaNNy0;

public class ValueAtIndex<T> {
    public final int row;
    public final int column;
    public final T value;

    public ValueAtIndex(final int row, final int column, final T value) {
        this.row = row;
        this.column = column;
        this.value = value;
    }
}
