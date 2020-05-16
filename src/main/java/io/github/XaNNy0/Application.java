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

