package io.github.XaNNy0;

import java.util.Objects;

public class ValueAtIndex<T> {
    public final int row;
    public final int column;
    public final T value;

    public ValueAtIndex(final int row, final int column, final T value) {
        this.row = row;
        this.column = column;
        this.value = value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        final ValueAtIndex<?> that = (ValueAtIndex<?>) o;
        return this.row == that.row && this.column == that.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.row, this.column);
    }
}
