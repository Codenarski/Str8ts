package io.github.XaNNy0.UI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(final String[] args) {
        //TODO:UI, JSON Drag & Drop, Tests, Delete ApplicationOLD and remove all swing things
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) throws Exception {
        final FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/UI.fxml"));
        final Parent root = fxmlLoader.load();
        primaryStage.setTitle("Str8ts");
        primaryStage.setScene(new Scene(root, 750, 750));
        primaryStage.show();
    }
}