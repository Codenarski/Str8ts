package io.github.XaNNy0;

public interface RowConsumer<T> {
    void consumeField(ValueAtIndex<T> field, boolean endOfRow);
}
