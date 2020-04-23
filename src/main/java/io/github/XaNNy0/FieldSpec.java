package io.github.XaNNy0;


public class FieldSpec {
    private boolean black;
    private int value;

    public FieldSpec(final boolean black, final int value) {
        this.black = black;
        this.value = value;
    }

    public boolean isBlack() {
        return this.black;
    }

    public void setBlack(final boolean black) {
        this.black = black;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(final int value) {
        this.value = value;
    }

    public static class Black extends FieldSpec {
        public Black(final int value) {
            super(true, value);
        }
    }

    public static class StringFieldSpec extends FieldSpec {

        public StringFieldSpec(final String text) {
            super(text.contains("B"), Integer.parseInt(text.replace("W", "").replace("B", "")));
        }
    }

    public static class White extends FieldSpec {
        public White(final int value) {
            super(true, value);
        }
    }
}
