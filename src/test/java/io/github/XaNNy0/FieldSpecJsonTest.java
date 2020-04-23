package io.github.XaNNy0;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;

public class FieldSpecJsonTest {

    private final Gson gson = new Gson();

    @Test
    void testJson() {
        final Type type = new TypeToken<String[][]>() {
        }.getType();
        final String[][] fields = this.gson.fromJson("[[\"1\", \"1\"],[\"1\",\"1\"]]", type);

        final FieldSpec[][] fieldSpecs = new SquareArray<String, FieldSpec>().map(fields, (field, ignoreLength) -> new FieldSpec.StringFieldSpec(field), length -> new FieldSpec[length][length]);

    }
}