package io.github.XaNNy0;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FieldSpecTest {

    @Test
    void testValidBlackWithoutValue() {
        final FieldSpec field = new FieldSpec.StringFieldSpec("B");
        assertTrue(field.isBlack());
        Assert.assertEquals(field.getValue(), 0);
    }

    @Test
    void testValidBlackWithValue() {
        final FieldSpec field = new FieldSpec.StringFieldSpec("2B");
        assertTrue(field.isBlack());
        Assert.assertEquals(field.getValue(), 2);
    }

    @Test
    void testValidWhiteWithoutValue() {
        final FieldSpec field = new FieldSpec.StringFieldSpec("W");
        assertFalse(field.isBlack());
        Assert.assertEquals(field.getValue(), 0);
    }

    @Test
    void testValidWhiteWithValue() {
        final FieldSpec field = new FieldSpec.StringFieldSpec("3W");
        assertFalse(field.isBlack());
        Assert.assertEquals(field.getValue(), 3);
    }

    @Test
    void testInValid() {
        Assert.assertThrows(NumberFormatException.class, () -> new FieldSpec.StringFieldSpec("BB"));
        Assert.assertThrows(NumberFormatException.class, () -> new FieldSpec.StringFieldSpec("WW"));
        Assert.assertThrows(NumberFormatException.class, () -> new FieldSpec.StringFieldSpec("B1"));
        Assert.assertThrows(NumberFormatException.class, () -> new FieldSpec.StringFieldSpec("W7"));
        Assert.assertThrows(NumberFormatException.class, () -> new FieldSpec.StringFieldSpec(""));
    }
}