package io.github.XaNNy0;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.Assert;
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
    void testSpecStr8ts() {
        //This board seems to be diabolic
        final Type type = new TypeToken<String[][]>() {
        }.getType();
        final String[][] fields = this.filterNulls(this.gson.fromJson("[\n" +
                "[\"B\", \"W\", \"4W\", \"B\", \"1B\", \"W\", \"W\", \"W\", \"B\"],\n" +
                "[\"B\", \"W\", \"W\", \"4B\", \"W\", \"W\", \"W\", \"5W\", \"B\"],\n" +
                "[\"B\", \"W\", \"W\", \"W\", \"W\", \"B\", \"W\", \"W\", \"W\"],\n" +
                "[\"W\", \"4W\", \"W\", \"3W\", \"W\", \"B\", \"9B\", \"W\", \"W\"],\n" +
                "[\"W\", \"W\", \"B\", \"W\", \"W\", \"W\", \"B\", \"W\", \"W\"],\n" +
                "[\"7W\", \"W\", \"B\", \"B\", \"W\", \"W\", \"W\", \"W\", \"W\"],\n" +
                "[\"W\", \"W\", \"8W\", \"B\", \"W\", \"1W\", \"3W\", \"W\", \"B\"],\n" +
                "[\"B\", \"W\", \"W\", \"7W\", \"W\", \"5B\", \"W\", \"W\", \"2B\"],\n" +
                "[\"3B\", \"8W\", \"W\", \"W\", \"B\", \"B\", \"W\", \"1W\", \"B\"],\n" +
                "]", type));

        final SquareArray<FieldSpec> fieldSpecs = new SquareArray<String>(fields).map((field, ignoreLength) -> new FieldSpec.StringFieldSpec(field), length -> new FieldSpec[length][length]);
        final Board board = new Board(fieldSpecs);
        while (board.isNotSolved()) {
            board.nextStep();
        }

        final String[][] solutionFields = this.filterNulls(this.gson.fromJson("[\n" +
                "[\"B\", \"3W\", \"4W\", \"B\", \"1B\", \"7W\", \"8W\", \"9W\", \"B\"],\n" +
                "[\"B\", \"2W\", \"1W\", \"4B\", \"7W\", \"8W\", \"6W\", \"5W\", \"B\"],\n" +
                "[\"B\", \"1W\", \"3W\", \"2W\", \"4W\", \"B\", \"7W\", \"6W\", \"5W\"],\n" +
                "[\"5W\", \"4W\", \"2W\", \"3W\", \"6W\", \"B\", \"9B\", \"8W\", \"7W\"],\n" +
                "[\"4W\", \"5W\", \"B\", \"1W\", \"3W\", \"2W\", \"B\", \"7W\", \"6W\"],\n" +
                "[\"7W\", \"6W\", \"B\", \"B\", \"5W\", \"3W\", \"1W\", \"2W\", \"4W\"],\n" +
                "[\"6W\", \"7W\", \"8W\", \"B\", \"2W\", \"1W\", \"3W\", \"4W\", \"B\"],\n" +
                "[\"B\", \"9W\", \"6W\", \"7W\", \"8W\", \"5B\", \"4W\", \"3W\", \"2B\"],\n" +
                "[\"3B\", \"8W\", \"7W\", \"6W\", \"B\", \"B\", \"2W\", \"1W\", \"B\"],\n" +
                "]", type));


        final SquareArray<FieldSpec> solutionFieldSpecs = new SquareArray<String>(solutionFields).map((field, ignoreLength) -> new FieldSpec.StringFieldSpec(field), length -> new FieldSpec[length][length]);
        final Board solutionBoard = new Board(solutionFieldSpecs);

        Assert.assertTrue(board.equals(solutionBoard));
        Assert.assertTrue(board.isSolved());

    }

    @Test
    void testEasy1Str8ts() {
        //This Board only needs SolvedSquares and CompartmentCheck to be solved
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
        while (board.isNotSolved()) {
            board.nextStep();
        }

        final String[][] solutionFields = this.filterNulls(this.gson.fromJson("[\n" +
                "[\"6B\", \"4W\", \"3W\", \"B\", \"B\", \"8W\", \"7W\", \"B\", \"B\"],\n" +
                "[\"4W\", \"1W\", \"2W\", \"3W\", \"B\", \"7W\", \"8W\", \"9W\", \"6W\"],\n" +
                "[\"3W\", \"2W\", \"B\", \"4W\", \"5W\", \"B\", \"6W\", \"8W\", \"7W\"],\n" +
                "[\"5W\", \"3W\", \"4W\", \"2B\", \"8W\", \"6W\", \"9W\", \"7W\", \"B\"],\n" +
                "[\"B\", \"9B\", \"7W\", \"8W\", \"6W\", \"5W\", \"4W\", \"B\", \"B\"],\n" +
                "[\"B\", \"8W\", \"6W\", \"9W\", \"7W\", \"B\", \"5W\", \"4W\", \"3W\"],\n" +
                "[\"9W\", \"7W\", \"8W\", \"B\", \"4W\", \"3W\", \"B\", \"6W\", \"5W\"],\n" +
                "[\"8W\", \"6W\", \"9W\", \"7W\", \"1B\", \"2W\", \"3W\", \"5W\", \"4W\"],\n" +
                "[\"B\", \"B\", \"5W\", \"6W\", \"B\", \"4B\", \"2W\", \"3W\", \"B\"],\n" +
                "]", type));


        final SquareArray<FieldSpec> solutionFieldSpecs = new SquareArray<String>(solutionFields).map((field, ignoreLength) -> new FieldSpec.StringFieldSpec(field), length -> new FieldSpec[length][length]);
        final Board solutionBoard = new Board(solutionFieldSpecs);

        Assert.assertTrue(board.equals(solutionBoard));
        Assert.assertTrue(board.isSolved());
    }

    @Test
    void testEasy2Str8ts() {
        //This Board only needs SolvedSquares and CompartmentCheck to be solved
        final Type type = new TypeToken<String[][]>() {
        }.getType();
        final String[][] fields = this.filterNulls(this.gson.fromJson("[\n" +
                "[\"B\", \"B\", \"W\", \"W\", \"B\", \"3B\", \"1W\", \"W\", \"B\"],\n" +
                "[\"W\", \"W\", \"W\", \"W\", \"W\", \"B\", \"W\", \"W\", \"B\"],\n" +
                "[\"7W\", \"W\", \"B\", \"B\", \"3W\", \"W\", \"5B\", \"W\", \"W\"],\n" +
                "[\"W\", \"W\", \"B\", \"5W\", \"W\", \"W\", \"7W\", \"W\", \"W\"],\n" +
                "[\"W\", \"B\", \"W\", \"W\", \"B\", \"W\", \"6W\", \"9B\", \"W\"],\n" +
                "[\"4W\", \"W\", \"W\", \"W\", \"6W\", \"5W\", \"B\", \"W\", \"W\"],\n" +
                "[\"W\", \"2W\", \"8B\", \"W\", \"W\", \"B\", \"B\", \"W\", \"W\"],\n" +
                "[\"2B\", \"3W\", \"W\", \"B\", \"W\", \"W\", \"W\", \"W\", \"W\"],\n" +
                "[\"B\", \"4W\", \"3W\", \"6B\", \"B\", \"8W\", \"W\", \"B\", \"1B\"],\n" +
                "]", type));

        final SquareArray<FieldSpec> fieldSpecs = new SquareArray<String>(fields).map((field, ignoreLength) -> new FieldSpec.StringFieldSpec(field), length -> new FieldSpec[length][length]);
        final Board board = new Board(fieldSpecs);
        while (board.isNotSolved()) {
            board.nextStep();
        }

        final String[][] solutionFields = this.filterNulls(this.gson.fromJson("[\n" +
                "[\"B\", \"B\", \"6W\", \"7W\", \"B\", \"3B\", \"1W\", \"2W\", \"B\"],\n" +
                "[\"6W\", \"7W\", \"5W\", \"8W\", \"4W\", \"B\", \"2W\", \"3W\", \"B\"],\n" +
                "[\"7W\", \"8W\", \"B\", \"B\", \"3W\", \"4W\", \"5B\", \"1W\", \"2W\"],\n" +
                "[\"8W\", \"9W\", \"B\", \"5W\", \"2W\", \"6W\", \"7W\", \"4W\", \"3W\"],\n" +
                "[\"5W\", \"B\", \"1W\", \"2W\", \"B\", \"7W\", \"6W\", \"9B\", \"4W\"],\n" +
                "[\"4W\", \"1W\", \"2W\", \"3W\", \"6W\", \"5W\", \"B\", \"8W\", \"7W\"],\n" +
                "[\"3W\", \"2W\", \"8B\", \"4W\", \"5W\", \"B\", \"B\", \"7W\", \"6W\"],\n" +
                "[\"2B\", \"3W\", \"4W\", \"B\", \"7W\", \"9W\", \"8W\", \"6W\", \"5W\"],\n" +
                "[\"B\", \"4W\", \"3W\", \"6B\", \"B\", \"8W\", \"9W\", \"B\", \"1B\"],\n" +
                "]", type));


        final SquareArray<FieldSpec> solutionFieldSpecs = new SquareArray<String>(solutionFields).map((field, ignoreLength) -> new FieldSpec.StringFieldSpec(field), length -> new FieldSpec[length][length]);
        final Board solutionBoard = new Board(solutionFieldSpecs);

        Assert.assertTrue(board.equals(solutionBoard));
        Assert.assertTrue(board.isSolved());
    }

    @Test
    void testModerate1Str8ts() {
        //This Board only needs SolvedSquares, CompartmentCheck and IsolatedDigits                                                                                                                                                      to be solved
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

        while (board.isNotSolved()) {
            board.nextStep();
        }

        final String[][] solutionFields = this.filterNulls(this.gson.fromJson("[\n" +
                "[\"B\", \"5W\", \"8W\", \"6W\", \"7W\", \"B\", \"B\", \"2W\", \"1W\"],\n" +
                "[\"5B\", \"8W\", \"9W\", \"7W\", \"6W\", \"B\", \"3W\", \"1W\", \"2W\"],\n" +
                "[\"2W\", \"3W\", \"1B\", \"8W\", \"5W\", \"6W\", \"4W\", \"7W\", \"B\"],\n" +
                "[\"4W\", \"2W\", \"3W\", \"B\", \"8B\", \"7W\", \"6W\", \"5W\", \"B\"],\n" +
                "[\"3W\", \"1W\", \"4W\", \"2W\", \"B\", \"8W\", \"5W\", \"6W\", \"7W\"],\n" +
                "[\"B\", \"4W\", \"2W\", \"3W\", \"B\", \"B\", \"7W\", \"8W\", \"6W\"],\n" +
                "[\"B\", \"7W\", \"6W\", \"4W\", \"3W\", \"5W\", \"B\", \"9W\", \"8W\"],\n" +
                "[\"7W\", \"6W\", \"5W\", \"9B\", \"2W\", \"4W\", \"1W\", \"3W\", \"B\"],\n" +
                "[\"8W\", \"9W\", \"7B\", \"B\", \"1W\", \"3W\", \"2W\", \"4W\", \"B\"],\n" +
                "]", type));


        final SquareArray<FieldSpec> solutionFieldSpecs = new SquareArray<String>(solutionFields).map((field, ignoreLength) -> new FieldSpec.StringFieldSpec(field), length -> new FieldSpec[length][length]);
        final Board solutionBoard = new Board(solutionFieldSpecs);

        Assert.assertTrue(board.equals(solutionBoard));
        Assert.assertTrue(board.isSolved());

    }

    @Test
    void testModerate2Str8ts() {
        //This Board only needs SolvedSquares, CompartmentCheck and IsolatedDigits
        final Type type = new TypeToken<String[][]>() {
        }.getType();
        final String[][] fields = this.filterNulls(this.gson.fromJson("[\n" +
                "[\"B\", \"1B\", \"W\", \"W\", \"9W\", \"B\", \"W\", \"W\", \"B\"],\n" +
                "[\"W\", \"W\", \"W\", \"W\", \"W\", \"W\", \"4W\", \"W\", \"B\"],\n" +
                "[\"W\", \"W\", \"W\", \"7W\", \"5B\", \"4W\", \"W\", \"B\", \"B\"],\n" +
                "[\"B\", \"W\", \"5W\", \"B\", \"2W\", \"W\", \"B\", \"W\", \"6W\"],\n" +
                "[\"W\", \"W\", \"W\", \"W\", \"W\", \"W\", \"6W\", \"W\", \"W\"],\n" +
                "[\"5W\", \"W\", \"B\", \"W\", \"W\", \"9B\", \"7W\", \"W\", \"B\"],\n" +
                "[\"3B\", \"B\", \"W\", \"W\", \"4B\", \"W\", \"W\", \"W\", \"W\"],\n" +
                "[\"B\", \"W\", \"W\", \"W\", \"W\", \"5W\", \"W\", \"W\", \"W\"],\n" +
                "[\"B\", \"W\", \"W\", \"B\", \"W\", \"W\", \"5W\", \"B\", \"2B\"],\n" +
                "]", type));


        final SquareArray<FieldSpec> fieldSpecs = new SquareArray<String>(fields).map((field, ignoreLength) -> new FieldSpec.StringFieldSpec(field), length -> new FieldSpec[length][length]);
        final Board board = new Board(fieldSpecs);

        while (board.isNotSolved()) {
            board.nextStep();
        }

        final String[][] solutionFields = this.filterNulls(this.gson.fromJson("\t[\n" +
                "\t[\"B\", \"1B\", \"7W\", \"8W\", \"9W\", \"B\", \"2W\", \"3W\", \"B\"],\n" +
                "\t[\"7W\", \"5W\", \"6W\", \"9W\", \"8W\", \"3W\", \"4W\", \"2W\", \"B\"],\n" +
                "\t[\"6W\", \"8W\", \"9W\", \"7W\", \"5B\", \"4W\", \"3W\", \"B\", \"B\"],\n" +
                "\t[\"B\", \"4W\", \"5W\", \"B\", \"2W\", \"1W\", \"B\", \"7W\", \"6W\"],\n" +
                "\t[\"4W\", \"7W\", \"8W\", \"3W\", \"1W\", \"2W\", \"6W\", \"9W\", \"5W\"],\n" +
                "\t[\"5W\", \"6W\", \"B\", \"2W\", \"3W\", \"9B\", \"7W\", \"8W\", \"B\"],\n" +
                "\t[\"3B\", \"B\", \"2W\", \"1W\", \"4B\", \"6W\", \"8W\", \"5W\", \"7W\"],\n" +
                "\t[\"B\", \"2W\", \"3W\", \"4W\", \"7W\", \"5W\", \"9W\", \"6W\", \"8W\"],\n" +
                "\t[\"B\", \"3W\", \"4W\", \"B\", \"6W\", \"7W\", \"5W\", \"B\", \"2B\"],\n" +
                "\t]", type));


        final SquareArray<FieldSpec> solutionFieldSpecs = new SquareArray<String>(solutionFields).map((field, ignoreLength) -> new FieldSpec.StringFieldSpec(field), length -> new FieldSpec[length][length]);
        final Board solutionBoard = new Board(solutionFieldSpecs);

        Assert.assertTrue(board.equals(solutionBoard));
        Assert.assertTrue(board.isSolved());

    }

    @Test
    void testTough1Str8ts() {
        //This Board needs almost all strategies
        final Type type = new TypeToken<String[][]>() {
        }.getType();
        final String[][] fields = this.filterNulls(this.gson.fromJson("[\n" +
                "[\"B\", \"B\", \"W\", \"6W\", \"B\", \"W\", \"W\", \"B\", \"3B\"], \n" +
                "[\"B\", \"W\", \"W\", \"2W\", \"B\", \"W\", \"W\", \"W\", \"B\"], \n" +
                "[\"2W\", \"W\", \"B\", \"W\", \"3W\", \"W\", \"B\", \"W\", \"W\"], \n" +
                "[\"W\", \"W\", \"W\", \"5W\", \"W\", \"W\", \"W\", \"7W\", \"W\"], \n" +
                "[\"B\", \"B\", \"W\", \"W\", \"W\", \"1W\", \"W\", \"6B\", \"B\"], \n" +
                "[\"W\", \"7W\", \"W\", \"W\", \"W\", \"W\", \"W\", \"W\", \"W\"], \n" +
                "[\"W\", \"6W\", \"B\", \"W\", \"W\", \"W\", \"9B\", \"W\", \"W\"], \n" +
                "[\"5B\", \"W\", \"W\", \"W\", \"1B\", \"W\", \"W\", \"W\", \"B\"], \n" +
                "[\"B\", \"B\", \"W\", \"W\", \"7B\", \"W\", \"W\", \"B\", \"B\"], \n" +
                "]", type));


        final SquareArray<FieldSpec> fieldSpecs = new SquareArray<String>(fields).map((field, ignoreLength) -> new FieldSpec.StringFieldSpec(field), length -> new FieldSpec[length][length]);
        final Board board = new Board(fieldSpecs);


        final String[][] solutionFields = this.filterNulls(this.gson.fromJson("[\n" +
                "[\"B\", \"B\", \"5W\", \"6W\", \"B\", \"7W\", \"8W\", \"B\", \"3B\"],\n" +
                "[\"B\", \"3W\", \"4W\", \"2W\", \"B\", \"6W\", \"7W\", \"8W\", \"B\"],\n" +
                "[\"2W\", \"1W\", \"B\", \"4W\", \"3W\", \"5W\", \"B\", \"9W\", \"8W\"],\n" +
                "[\"1W\", \"2W\", \"3W\", \"5W\", \"6W\", \"8W\", \"4W\", \"7W\", \"9W\"],\n" +
                "[\"B\", \"B\", \"2W\", \"3W\", \"4W\", \"1W\", \"5W\", \"6B\", \"B\"],\n" +
                "[\"6W\", \"7W\", \"1W\", \"8W\", \"5W\", \"9W\", \"3W\", \"2W\", \"4W\"],\n" +
                "[\"7W\", \"6W\", \"B\", \"1W\", \"2W\", \"3W\", \"9B\", \"4W\", \"5W\"],\n" +
                "[\"5B\", \"8W\", \"9W\", \"7W\", \"1B\", \"4W\", \"2W\", \"3W\", \"B\"],\n" +
                "[\"B\", \"B\", \"8W\", \"9W\", \"7B\", \"2W\", \"1W\", \"B\", \"B\"],\n" +
                "]", type));


        final SquareArray<FieldSpec> solutionFieldSpecs = new SquareArray<String>(solutionFields).map((field, ignoreLength) -> new FieldSpec.StringFieldSpec(field), length -> new FieldSpec[length][length]);
        final Board solutionBoard = new Board(solutionFieldSpecs);

        while (board.isNotSolved()) {
            board.nextStep();
        }

        Assert.assertTrue(board.equals(solutionBoard));
        Assert.assertTrue(board.isSolved());

    }

    @Test
    void testTough2Str8ts() {
        //This Board needs almost all strategies
        final Type type = new TypeToken<String[][]>() {
        }.getType();
        final String[][] fields = this.filterNulls(this.gson.fromJson("[\n" +
                "[\"B\", \"W\", \"W\", \"B\", \"B\", \"7W\", \"W\", \"W\", \"B\"],\n" +
                "[\"B\", \"W\", \"W\", \"B\", \"W\", \"W\", \"W\", \"W\", \"2B\"],\n" +
                "[\"B\", \"W\", \"5W\", \"W\", \"2W\", \"6W\", \"W\", \"W\", \"W\"],\n" +
                "[\"W\", \"W\", \"W\", \"W\", \"W\", \"B\", \"B\", \"W\", \"W\"],\n" +
                "[\"6W\", \"W\", \"9B\", \"1W\", \"W\", \"W\", \"B\", \"W\", \"W\"],\n" +
                "[\"W\", \"W\", \"B\", \"8B\", \"5W\", \"W\", \"W\", \"W\", \"W\"],\n" +
                "[\"W\", \"W\", \"W\", \"W\", \"W\", \"W\", \"5W\", \"3W\", \"B\"],\n" +
                "[\"B\", \"W\", \"W\", \"5W\", \"W\", \"4B\", \"W\", \"W\", \"B\"],\n" +
                "[\"3B\", \"W\", \"W\", \"W\", \"1B\", \"B\", \"W\", \"W\", \"B\"],\n" +
                "]", type));


        final SquareArray<FieldSpec> fieldSpecs = new SquareArray<String>(fields).map((field, ignoreLength) -> new FieldSpec.StringFieldSpec(field), length -> new FieldSpec[length][length]);
        final Board board = new Board(fieldSpecs);


        final String[][] solutionFields = this.filterNulls(this.gson.fromJson("[\n" +
                "[\"B\", \"3W\", \"2W\", \"B\", \"B\", \"7W\", \"8W\", \"6W\", \"B\"],\n" +
                "[\"B\", \"5W\", \"4W\", \"B\", \"6W\", \"8W\", \"7W\", \"9W\", \"2B\"],\n" +
                "[\"B\", \"4W\", \"5W\", \"3W\", \"2W\", \"6W\", \"9W\", \"8W\", \"7W\"],\n" +
                "[\"5W\", \"1W\", \"3W\", \"2W\", \"4W\", \"B\", \"B\", \"7W\", \"6W\"],\n" +
                "[\"6W\", \"7W\", \"9B\", \"1W\", \"3W\", \"2W\", \"B\", \"4W\", \"5W\"],\n" +
                "[\"7W\", \"6W\", \"B\", \"8B\", \"5W\", \"3W\", \"2W\", \"1W\", \"4W\"],\n" +
                "[\"4W\", \"2W\", \"7W\", \"6W\", \"8W\", \"1W\", \"5W\", \"3W\", \"B\"],\n" +
                "[\"B\", \"8W\", \"6W\", \"5W\", \"7W\", \"4B\", \"3W\", \"2W\", \"B\"],\n" +
                "[\"3B\", \"9W\", \"8W\", \"7W\", \"1B\", \"B\", \"4W\", \"5W\", \"B\"],\n" +
                "]", type));


        final SquareArray<FieldSpec> solutionFieldSpecs = new SquareArray<String>(solutionFields).map((field, ignoreLength) -> new FieldSpec.StringFieldSpec(field), length -> new FieldSpec[length][length]);
        final Board solutionBoard = new Board(solutionFieldSpecs);

        while (board.isNotSolved()) {
            board.nextStep();
        }

        Assert.assertTrue(board.equals(solutionBoard));
        Assert.assertTrue(board.isSolved());
    }

    @Test
    void testDiabolic1Str8ts() {
        //This Board needs almost all strategies
        final Type type = new TypeToken<String[][]>() {
        }.getType();
        final String[][] fields = this.filterNulls(this.gson.fromJson("[\n" +
                "[\"4B\", \"9W\", \"W\", \"W\", \"3B\", \"6B\", \"W\", \"W\", \"5B\"],\n" +
                "[\"W\", \"W\", \"W\", \"W\", \"B\", \"W\", \"W\", \"W\", \"W\"],\n" +
                "[\"W\", \"W\", \"B\", \"2B\", \"W\", \"W\", \"B\", \"W\", \"W\"],\n" +
                "[\"W\", \"W\", \"W\", \"W\", \"W\", \"W\", \"W\", \"W\", \"W\"],\n" +
                "[\"B\", \"B\", \"3W\", \"W\", \"W\", \"4W\", \"W\", \"1B\", \"B\"],\n" +
                "[\"W\", \"W\", \"W\", \"W\", \"W\", \"W\", \"W\", \"W\", \"6W\"],\n" +
                "[\"W\", \"W\", \"B\", \"W\", \"W\", \"B\", \"B\", \"W\", \"8W\"],\n" +
                "[\"1W\", \"W\", \"W\", \"W\", \"9B\", \"W\", \"W\", \"W\", \"W\"],\n" +
                "[\"B\", \"W\", \"W\", \"B\", \"B\", \"W\", \"W\", \"W\", \"B\"],\n" +
                "]", type));


        final SquareArray<FieldSpec> fieldSpecs = new SquareArray<String>(fields).map((field, ignoreLength) -> new FieldSpec.StringFieldSpec(field), length -> new FieldSpec[length][length]);
        final Board board = new Board(fieldSpecs);


        final String[][] solutionFields = this.filterNulls(this.gson.fromJson("[\n" +
                "[\"4B\", \"9W\", \"7W\", \"8W\", \"3B\", \"6B\", \"1W\", \"2W\", \"5B\"],\n" +
                "[\"6W\", \"7W\", \"8W\", \"9W\", \"B\", \"3W\", \"2W\", \"5W\", \"4W\"],\n" +
                "[\"7W\", \"8W\", \"B\", \"2B\", \"6W\", \"5W\", \"B\", \"4W\", \"3W\"],\n" +
                "[\"5W\", \"6W\", \"4W\", \"7W\", \"8W\", \"1W\", \"9W\", \"3W\", \"2W\"],\n" +
                "[\"B\", \"B\", \"3W\", \"6W\", \"5W\", \"4W\", \"7W\", \"1B\", \"B\"],\n" +
                "[\"3W\", \"1W\", \"5W\", \"4W\", \"7W\", \"2W\", \"8W\", \"9W\", \"6W\"],\n" +
                "[\"2W\", \"3W\", \"B\", \"5W\", \"4W\", \"B\", \"B\", \"7W\", \"8W\"],\n" +
                "[\"1W\", \"4W\", \"2W\", \"3W\", \"9B\", \"8W\", \"5W\", \"6W\", \"7W\"],\n" +
                "[\"B\", \"2W\", \"1W\", \"B\", \"B\", \"7W\", \"6W\", \"8W\", \"B\"],\n" +
                "]", type));


        final SquareArray<FieldSpec> solutionFieldSpecs = new SquareArray<String>(solutionFields).map((field, ignoreLength) -> new FieldSpec.StringFieldSpec(field), length -> new FieldSpec[length][length]);
        final Board solutionBoard = new Board(solutionFieldSpecs);

        while (board.isNotSolved()) {
            board.nextStep();
        }

        Assert.assertTrue(board.equals(solutionBoard));
        Assert.assertTrue(board.isSolved());
    }

    @Test
    void testDiabolic2Str8ts() {
        //This Board needs almost all strategies
        final Type type = new TypeToken<String[][]>() {
        }.getType();
        final String[][] fields = this.filterNulls(this.gson.fromJson("[\n" +
                "[\"B\", \"2W\", \"W\", \"W\", \"6B\", \"W\", \"W\", \"W\", \"B\"],\n" +
                "[\"W\", \"W\", \"W\", \"W\", \"B\", \"W\", \"W\", \"6W\", \"8W\"],\n" +
                "[\"W\", \"W\", \"4B\", \"W\", \"W\", \"W\", \"B\", \"W\", \"W\"],\n" +
                "[\"B\", \"W\", \"W\", \"W\", \"W\", \"W\", \"W\", \"W\", \"W\"],\n" +
                "[\"B\", \"1B\", \"W\", \"2W\", \"W\", \"W\", \"W\", \"B\", \"B\"],\n" +
                "[\"W\", \"W\", \"W\", \"W\", \"W\", \"W\", \"1W\", \"W\", \"9B\"],\n" +
                "[\"W\", \"W\", \"B\", \"W\", \"W\", \"W\", \"B\", \"W\", \"W\"],\n" +
                "[\"7W\", \"W\", \"W\", \"W\", \"B\", \"W\", \"W\", \"W\", \"W\"],\n" +
                "[\"2B\", \"7W\", \"W\", \"W\", \"B\", \"W\", \"W\", \"3W\", \"B\"],\n" +
                "]", type));


        final SquareArray<FieldSpec> fieldSpecs = new SquareArray<String>(fields).map((field, ignoreLength) -> new FieldSpec.StringFieldSpec(field), length -> new FieldSpec[length][length]);
        final Board board = new Board(fieldSpecs);


        final String[][] solutionFields = this.filterNulls(this.gson.fromJson("[\n" +
                "[\"B\", \"2W\", \"3W\", \"1W\", \"6B\", \"7W\", \"8W\", \"9W\", \"B\"],\n" +
                "[\"5W\", \"3W\", \"2W\", \"4W\", \"B\", \"9W\", \"7W\", \"6W\", \"8W\"],\n" +
                "[\"6W\", \"5W\", \"4B\", \"3W\", \"1W\", \"2W\", \"B\", \"8W\", \"7W\"],\n" +
                "[\"B\", \"4W\", \"5W\", \"9W\", \"3W\", \"8W\", \"2W\", \"7W\", \"6W\"],\n" +
                "[\"B\", \"1B\", \"6W\", \"2W\", \"4W\", \"5W\", \"3W\", \"B\", \"B\"],\n" +
                "[\"8W\", \"6W\", \"7W\", \"5W\", \"2W\", \"3W\", \"1W\", \"4W\", \"9B\"],\n" +
                "[\"9W\", \"8W\", \"B\", \"7W\", \"5W\", \"6W\", \"B\", \"1W\", \"2W\"],\n" +
                "[\"7W\", \"9W\", \"8W\", \"6W\", \"B\", \"1W\", \"4W\", \"2W\", \"3W\"],\n" +
                "[\"2B\", \"7W\", \"9W\", \"8W\", \"B\", \"4W\", \"5W\", \"3W\", \"B\"],\n" +
                "]", type));


        final SquareArray<FieldSpec> solutionFieldSpecs = new SquareArray<String>(solutionFields).map((field, ignoreLength) -> new FieldSpec.StringFieldSpec(field), length -> new FieldSpec[length][length]);
        final Board solutionBoard = new Board(solutionFieldSpecs);

        while (board.isNotSolved()) {
            board.nextStep();
        }

        Assert.assertTrue(board.equals(solutionBoard));
        Assert.assertTrue(board.isSolved());
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
