package io.github.XaNNy0.UI;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(final String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) {
        final Controller controller = new Controller(primaryStage);
        controller.initializeGUI();
    }
}