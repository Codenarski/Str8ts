package io.github.XaNNy0;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.swing.*;
import java.lang.reflect.Type;

public class Application {

    public static void main(final String[] args) {
        System.out.println("test");

        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                final JFrame frame = new JFrame("Str8ts");
                final str8ts grid;
                frame.getContentPane().add(grid = new str8ts(9, this.createNewBoard()));
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setResizable(false);
                frame.setVisible(true);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }

            private Board createNewBoard() {
                final Gson gson = new Gson();
                final Type type = new TypeToken<String[][]>() {
                }.getType();
                final String[][] fields = this.filterNulls(gson.fromJson("[\n" +
                        "[\"W\", \"W\", \"4B\", \"W\", \"W\", \"W\", \"B\", \"7W\", \"W\"],\n" +
                        "[\"W\", \"W\", \"W\", \"W\", \"W\", \"W\", \"1W\", \"W\", \"W\"],\n" +
                        "[\"6W\", \"W\", \"W\", \"B\", \"W\", \"W\", \"W\", \"B\", \"W\"],\n" +
                        "[\"W\", \"W\", \"W\", \"3W\", \"W\", \"5W\", \"2W\", \"B\", \"B\"],\n" +
                        "[\"W\", \"1B\", \"9B\", \"W\", \"4W\", \"W\", \"W\", \"3W\", \"W\"],\n" +
                        "[\"B\", \"W\", \"W\", \"W\", \"W\", \"W\", \"W\", \"W\", \"W\"],\n" +
                        "[\"W\", \"W\", \"W\", \"4W\", \"W\", \"W\", \"W\", \"6W\", \"W\"],\n" +
                        "[\"W\", \"W\", \"2W\", \"W\", \"W\", \"7W\", \"W\", \"W\", \"W\"],\n" +
                        "[\"3B\", \"B\", \"B\", \"W\", \"W\", \"W\", \"5W\", \"W\", \"W\"],\n" +
                        "]", type));

                final SquareArray<FieldSpec> fieldSpecs = new SquareArray<String>(fields).map((field, ignoreLength) -> new FieldSpec.StringFieldSpec(field), length -> new FieldSpec[length][length]);
                return new Board(fieldSpecs);
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
        });
    }
}

