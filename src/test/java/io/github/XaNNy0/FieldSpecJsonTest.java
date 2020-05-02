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
        final String[][] fields = this.gson.fromJson("[[\"1W\", \"1B\"],[\"B\",\"W\"]]", type);

        final SquareArray<FieldSpec> fieldSpecs = new SquareArray<String>(fields).map((field, ignoreLength) -> new FieldSpec.StringFieldSpec(field), length -> new FieldSpec[length][length]);
    }

    @Test
    void testEasy1Str8ts() {
        final Type type = new TypeToken<String[][]>() {
        }.getType();
        final String[][] fields = this.filterNulls(this.gson.fromJson("[" +
                "[\"6B\", \"W\", \"3W\", \"B\", \"B\", \"W\", \"W\", \"B\", \"B\"]," +
                "[\"4W\", \"1W\", \"W\", \"3W\", \"B\", \"W\", \"8W\", \"W\", \"6W\"]," +
                "[\"W\", \"W\", \"B\", \"W\", \"W\", \"B\", \"W\", \"W\", \"W\"]," +
                "[\"W\", \"W\", \"W\", \"2B\", \"W\", \"W\", \"W\", \"7W\", \"B\"]," +
                "[\"B\", \"9B\", \"W\", \"W\", \"6W\", \"W\", \"W\", \"B\", \"B\"]," +
                "[\"B\", \"W\", \"W\", \"W\", \"W\", \"B\", \"W\", \"W\", \"W\"]," +
                "[\"W\", \"W\", \"8W\", \"B\", \"4W\", \"W\", \"B\", \"6W\", \"W\"]," +
                "[\"W\", \"W\", \"9W\", \"W\", \"1B\", \"2W\", \"W\", \"W\", \"W\"]," +
                "[\"B\", \"B\", \"5W\", \"6W\", \"B\", \"4B\", \"2W\", \"W\", \"B\"]," +
                "]", type));

        final SquareArray<FieldSpec> fieldSpecs = new SquareArray<String>(fields).map((field, ignoreLength) -> new FieldSpec.StringFieldSpec(field), length -> new FieldSpec[length][length]);
        final Board board = new Board(fieldSpecs);
        int counter = 0;
        while (true) {
            board.nextStep();
            counter++;
        }
    }

    @Test
    void testModerate1Str8ts() {
        final Type type = new TypeToken<String[][]>() {
        }.getType();
        final String[][] fields = this.filterNulls(this.gson.fromJson("[\n" +
                "[\"B\", \"W\", \"W\", \"W\", \"7W\", \"B\", \"B\", \"W\", \"1W\"],\n" +
                "[\"5B\", \"W\", \"W\", \"7W\", \"W\", \"B\", \"W\", \"W\", \"W\"],\n" +
                "[\"W\", \"3W\", \"1B\", \"W\", \"W\", \"W\", \"W\", \"W\", \"B\"],\n" +
                "[\"4W\", \"2W\", \"W\", \"B\", \"8B\", \"W\", \"W\", \"W\", \"B\"],\n" +
                "[\"W\", \"W\", \"W\", \"W\", \"B\", \"W\", \"5W\", \"W\", \"W\"],\n" +
                "[\"B\", \"4W\", \"W\", \"W\", \"B\", \"B\", \"W\", \"W\", \"6W\"],\n" +
                "[\"B\", \"7W\", \"W\", \"W\", \"W\", \"W\", \"B\", \"W\", \"W\"],\n" +
                "[\"W\", \"W\", \"W\", \"9B\", \"W\", \"W\", \"W\", \"3W\", \"B\"],\n" +
                "[\"W\", \"9W\", \"7B\", \"B\", \"1W\", \"W\", \"W\", \"W\", \"B\"],\n" +
                "]", type));

        final SquareArray<FieldSpec> fieldSpecs = new SquareArray<String>(fields).map((field, ignoreLength) -> new FieldSpec.StringFieldSpec(field), length -> new FieldSpec[length][length]);
        final Board board = new Board(fieldSpecs);
        int counter = 0;
        while (!board.isSolved()) {
            board.nextStep();
            counter++;
        }
        System.out.println(counter);
    }

    public String[][] filterNulls(final String[][] old) {
        int count = 0;
        for (int i = 0; i < old.length; i++) {
            if (old[i] != null) {
                count++;
            }
        }

        final String[][] notOld = new String[count][count];

        for (int i = 0; i < notOld.length; i++) {
            if (old[i] == null) {
                continue;
            }
            for (int j = 0; j < notOld.length; j++) {
                notOld[i][j] = old[i][j];
            }
        }
        return notOld;
    }
}
