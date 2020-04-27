package io.github.XaNNy0;

public class FieldIndex {
    public final int rowIndex;
    public final int columnIndex;

    public FieldIndex(final int rowIndex, final int columnIndex) {
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
    }

    public FieldIndex(final SquareArray.ValueAtIndex<Field> fieldValueAtIndex) {
        this.rowIndex = fieldValueAtIndex.row;
        this.columnIndex = fieldValueAtIndex.column;
    }
}
