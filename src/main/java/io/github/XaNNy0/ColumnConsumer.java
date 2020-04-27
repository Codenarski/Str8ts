package io.github.XaNNy0;

public interface ColumnConsumer<T> {
    void consumeField(SquareArray.ValueAtIndex<T> field, boolean endOfColumn);
}
