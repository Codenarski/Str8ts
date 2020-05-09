package io.github.XaNNy0;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CompartmentTest {

    private final Gson gson = new Gson();

    @BeforeClass
    public Board beforeEachTestMethod() {
        final Type type = new TypeToken<String[][]>() {
        }.getType();
        final String[][] fields = this.filterNulls(this.gson.fromJson("[" +
                "[\"6B\", \"W\", \"3W\", \"B\", \"B\", \"W\", \"W\", \"B\", \"B\"]," +
                "[\"4W\", \"1W\", \"W\", \"3W\", \"B\", \"W\", \"8W\", \"W\", \"6W\"]," + //<- ... 8 ... 6 for RequiredDigitsTest
                "[\"W\", \"W\", \"B\", \"W\", \"W\", \"B\", \"4W\", \"W\", \"6W\"]," + //<- 4 ... 6 for PossibleStraitsTest
                "[\"W\", \"W\", \"W\", \"2B\", \"W\", \"W\", \"W\", \"7W\", \"B\"]," +
                "[\"B\", \"9B\", \"W\", \"W\", \"6W\", \"W\", \"W\", \"B\", \"B\"]," +
                "[\"B\", \"W\", \"W\", \"W\", \"W\", \"B\", \"W\", \"W\", \"W\"]," +
                "[\"W\", \"W\", \"8W\", \"B\", \"4W\", \"W\", \"B\", \"6W\", \"W\"]," +
                "[\"W\", \"W\", \"9W\", \"W\", \"1B\", \"2W\", \"W\", \"W\", \"W\"]," +
                "[\"B\", \"B\", \"5W\", \"6W\", \"B\", \"4B\", \"2W\", \"W\", \"B\"]," +
                "]", type));

        final SquareArray<FieldSpec> fieldSpecs = new SquareArray<String>(fields).map((field, ignoreLength) -> new FieldSpec.StringFieldSpec(field), length -> new FieldSpec[length][length]);
        return new Board(fieldSpecs);
    }

    @Test
    void getPossibleStraitsTest() {
        final Board board = this.beforeEachTestMethod();
        final Compartments testCompartments = board.getCompartments();
        final List<Compartment> compartmentList = testCompartments.getCompartmentsByKey("R2");

        final List<List<Integer>> possibleStraits = compartmentList.get(2).getPossibleStraits();
        final List<Integer> realStraits = new ArrayList<Integer>();
        realStraits.add(4);
        realStraits.add(5);
        realStraits.add(6);
        Assert.assertEquals(possibleStraits.get(0), realStraits);
    }

    @Test
    void getRequiredDigitsTest() {
        final Board board = this.beforeEachTestMethod();
        final Compartments testCompartments = board.getCompartments();
        final List<Compartment> compartmentList = testCompartments.getCompartmentsByKey("R1");

        final List<Integer> requiredDigitsC0 = compartmentList.get(0).getRequiredDigits();
        final List<Integer> requiredDigitsC1 = compartmentList.get(1).getRequiredDigits();

        //Liste bauen und vergleichen
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
