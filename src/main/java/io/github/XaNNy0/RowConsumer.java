package io.github.XaNNy0;

public interface RowConsumer<T> {
    void consumeField(SquareArray.ValueAtIndex<T> field, boolean endOfRow);
}
