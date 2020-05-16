package io.github.XaNNy0;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

public class StreamTest {
    @Test
    void name() throws Exception {
        IntStream.rangeClosed(0, 10) //
                //.parallel() //
                .peek(value -> this.print("start", value)) //
                .boxed()
                .peek(value -> this.print("boxed", value)) //
                .map(Wrapper::new)
                .peek(value -> this.print("wrapping", value)) //
                .map(Wrapper::unwrap)
                .peek(value -> this.print("unwrapping", value)) //
                .map(value -> value + "")
                .peek(value -> this.print("map", value)) //
                .sorted()
                .forEach(v -> this.print("finished", v));
        ;
    }

    private void print(final String operation, final Object value) {
        System.out.println(value + " : performing " + operation + " (Thread: " + Thread.currentThread().getName() + ")");
    }

    public static class Wrapper<T> {
        private final T value;

        public Wrapper(final T value) {
            this.value = value;
        }

        public T unwrap() {
            return this.value;
        }

        @Override
        public String toString() {
            return this.value + "";
        }
    }


}
