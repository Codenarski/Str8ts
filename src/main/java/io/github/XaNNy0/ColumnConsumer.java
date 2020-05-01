package io.github.XaNNy0;

public interface ColumnConsumer<T> {
    void consumeField(ValueAtIndex<T> field, boolean endOfColumn);
}
