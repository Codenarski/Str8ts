package io.github.XaNNy0.UI;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.github.XaNNy0.Board;
import io.github.XaNNy0.FieldSpec;
import io.github.XaNNy0.SquareArray;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.IntStream;

public class Controller {

    GridPane gridPane;
    StackPane stackPane;
    Stage stage;
    Board board;
    TextField[][] grid;

    public Controller(final Stage stage) {
        this.gridPane = new GridPane();
        this.stackPane = new StackPane();
        this.stage = stage;
        this.gridPane.getStylesheets().add("board.css");
    }

    public void initializeGUI() {
        this.stackPane = new StackPane();
        this.stackPane.getChildren().add(this.dragAndDrop());
        this.stage.setScene(new Scene(this.stackPane, 900, 900));
        this.stackPane.getChildren().add(this.dragAndDrop());
        this.stage.setTitle("Str8ts");
        this.stage.setResizable(false);
        this.stage.show();
    }

    private VBox dragAndDrop() {
        final Label dropped = new Label("Drag and Drop JSON game file here.");
        dropped.setMaxWidth(Double.MAX_VALUE);
        dropped.setAlignment(Pos.CENTER);
        final VBox dragTarget = new VBox();

        dragTarget.getChildren().add(dropped);
        dragTarget.setOnDragOver(event -> {
            if (event.getGestureSource() != dragTarget
                    && event.getDragboard().hasFiles()) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        });

        dragTarget.setOnDragDropped(event -> {
            try {
                final Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasFiles()) {
                    final File jsonFile = db.getFiles().get(0);
                    if (jsonFile.getName().toLowerCase().endsWith("json")) {
                        success = true;
                        try {
                            this.board = this.parseJSONToBoard(jsonFile);
                            final Scene scene = new Scene(this.gridPane, 900, 900);
                            this.stage.setScene(scene);
                            this.addKeyHandler(scene);
                            this.renderBoard();
                        } catch (final Exception e) {
                            throw new Exception(db.getFiles().toString() + " is not an invalid JSON file!");
                        }
                    } else {
                        throw new Exception(db.getFiles().toString() + " is not a JSON file!");
                    }
                }
                event.setDropCompleted(success);
                event.consume();
            } catch (final Exception e) {
                this.stackPane.getChildren().clear();
                dropped.setText("ERROR: " + e.getMessage() + "\nRestart program and try again with valid file!");
                this.stackPane.getChildren().add(dropped);
            }
        });
        return dragTarget;
    }

    public Board parseJSONToBoard(final File file) throws IOException {
        final Gson gson = new Gson();
        final Type type = new TypeToken<String[][]>() {
        }.getType();
        final String jsonContent = (Files.readString(Paths.get(file.getAbsolutePath())));
        final String[][] fields = this.filterNulls(gson.fromJson(jsonContent, type));

        final SquareArray<FieldSpec> fieldSpecs = new SquareArray<>(fields).map((field, ignoreLength) -> new FieldSpec.StringFieldSpec(field), length -> new FieldSpec[length][length]);

        return new Board(fieldSpecs);
    }

    public String[][] filterNulls(final String[][] old) {
        final int count = (int) Arrays.stream(old)
                .filter(Objects::nonNull)
                .count();

        final String[][] cleaned = new String[count][count];

        IntStream.range(0, cleaned.length)
                .filter(i -> old[i] != null)
                .forEach(i -> System.arraycopy(old[i], 0, cleaned[i], 0, cleaned.length));
        return cleaned;
    }

    public void renderBoard() {
        final int size = this.board.getFields().getArray().length;
        this.grid = new TextField[size][size];

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                final Border border = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));
                final TextField field = new TextField();
                field.setBorder(border);
                field.setPrefSize(100, 100);

                if (this.board.getFields().getArray()[x][y].isBlack() && this.board.getFields().getArray()[x][y].hasValue()) {
                    field.setText(this.board.getFields().getArray()[x][y].getValue() + "");
                    field.setEditable(false);
                    field.getStyleClass().add("blackFieldWithValue");
                } else if (this.board.getFields().getArray()[x][y].isBlack() && !this.board.getFields().getArray()[x][y].hasValue()) {
                    field.setEditable(false);
                    field.getStyleClass().add("blackFieldWithoutValue");
                } else if (this.board.getFields().getArray()[x][y].isWhite() && this.board.getFields().getArray()[x][y].hasValue()) {
                    field.setText(this.board.getFields().getArray()[x][y].getValue() + "");
                    field.setEditable(false);
                    field.getStyleClass().add("whiteFieldWithValue");
                } else {
                    final String text = this.board.getFields().getArray()[x][y].getCandidates().toString();
                    field.setText(text);
                    field.setEditable(true);
                    field.getStyleClass().add("whiteFieldWithoutValue");
                }

                this.grid[x][y] = field;
                this.gridPane.add(this.grid[x][y], y, x);
            }
        }
    }

    public void step() {
        if (this.board != null) {
            this.board.nextStep();
            this.renderBoard();
        }
    }

    public void solve() {
        if (this.board != null) {
            while (this.board.isNotSolved()) {
                this.board.nextStep();
            }
            this.renderBoard();
        }
    }

    private void addKeyHandler(final Scene scene) {
        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case F5:
                    this.step();
                    break;
                case F6:
                    this.solve();
                    break;
                case F8:
                    this.stage.close();
                    break;
            }
        });
    }
}