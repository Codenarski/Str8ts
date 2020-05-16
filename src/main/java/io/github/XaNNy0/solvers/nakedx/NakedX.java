package io.github.XaNNy0.solvers.nakedx;

import io.github.XaNNy0.Field;

import java.util.Objects;

public interface NakedX {
    boolean isNaked();

    Field getField();

    String getGroupingKey();

    final class NakedPair implements NakedX {

        private final Field field;
        private final String key;

        public NakedPair(final Field field) {
            this.field = field;
            this.key = field.getCandidates().toString();
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || this.getClass() != o.getClass()) return false;
            final NakedPair nakedPair = (NakedPair) o;
            return this.key.equals(nakedPair.key);
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.key);
        }

        @Override
        public boolean isNaked() {
            return this.field.getCandidates().size() == 2;
        }

        @Override
        public Field getField() {
            return this.field;
        }

        @Override
        public String getGroupingKey() {
            return this.key;
        }
    }


    final class NakedTriple implements NakedX {

        public NakedTriple(final Field field) {

        }

        @Override
        public boolean isNaked() {
            return false;
        }

        @Override
        public Field getField() {
            return null;
        }

        @Override
        public String getGroupingKey() {
            return null;
        }
    }
}

