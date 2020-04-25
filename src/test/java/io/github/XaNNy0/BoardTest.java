package io.github.XaNNy0;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class BoardTest {

    @Test
    public void testArrayWithSizeZero() {
        Assert.assertThrows(IllegalArgumentException.class, () -> new Board(new SquareArray<>(new FieldSpec[0][0])));
    }

    @Test
    public void testArrayWithUnequalLength() {
        Assert.assertThrows(IllegalArgumentException.class, () -> new Board(new SquareArray<>(new FieldSpec[5][10])));
    }

    @Test
    public void testWithEqualLengthButUninitialized() {
        Assert.assertThrows(IllegalArgumentException.class, () -> new Board(new SquareArray<>(new FieldSpec[2][2])));
    }

    @Test
    public void test() {
        final FieldSpec[][] fieldSpec = {
                {new FieldSpec.Black(1), new FieldSpec.Black(1)},
                {new FieldSpec.Black(5), new FieldSpec.Black(5)}
        };
        final Board board = new Board(new SquareArray<>(fieldSpec));
    }

    @Test
    public void fieldSpec() {
        final FieldSpec[][] fieldSpec = {
                {new FieldSpec.Black(1), null},
                {new FieldSpec.Black(5), new FieldSpec.Black(5)}
        };
        Assert.assertThrows(IllegalArgumentException.class, () -> new Board(new SquareArray<>(fieldSpec)));
    }

    @Test
    public void test3() {
        final FieldSpec[][] fieldSpec = {
                null,
                {new FieldSpec.Black(5), new FieldSpec.Black(5)}
        };
        Assert.assertThrows(IllegalArgumentException.class, () -> new Board(new SquareArray<>(fieldSpec)));
    }

    @Test
    public void test4() {
        Assert.assertThrows(IllegalArgumentException.class, () -> new Board(null));
    }
}