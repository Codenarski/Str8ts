package io.github.XaNNy0;


public class FieldSpec {
    private final boolean black;
    private int value;

    public FieldSpec(final boolean black, final int value) {
        this.black = black;
        this.value = value;
    }

    public boolean isBlack() {
        return this.black;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(final int value) {
        this.value = value;
    }

    public static class StringFieldSpec extends FieldSpec {

        public StringFieldSpec(final String text) {
            super(text.contains("B"), parseValue(text));
        }

        public static int parseValue(final String symbol) {
            if (symbol.equals("W")) {
                return 0;
            } else if (symbol.equals("B")) {
                return 0;
            } else if (symbol.endsWith("B")) {
                return Integer.parseInt(symbol.replace("B", ""));
            } else if (symbol.endsWith("W")) {
                return Integer.parseInt(symbol.replace("W", ""));
            } else {
                return Integer.parseInt(symbol);
            }
        }
    }
}
