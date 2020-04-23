package io.github.XaNNy0;

import java.util.function.BiFunction;
import java.util.function.Function;

public class SquareArray<Source, Target> {

    Target[][] map(final Source[][] source, final BiFunction<Source, Integer, Target> mapper, final Function<Integer, Target[][]> supplier) {
        final int length = source.length;
        final Target[][] target = supplier.apply(length);
        for (int x = 0; x < length; x++) {
            for (int y = 0; y < length; y++) {
                target[x][y] = mapper.apply(source[x][y], length);
            }
        }
        return target;
    }
}
