package application;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import utils.paths;

import java.io.IOException;

public class app extends Application {

    public static app App;
    private Stage stageWindow;

    public static void main(String[] args) {

        launch();
    }


    @Override
    public void start(Stage stage) throws Exception {
        App = this;
        stageWindow = stage;

        // Carga el contenedor principal (main.fxml)
        FXMLLoader loader = new FXMLLoader(getClass().getResource(paths.MAIN));
        //StackPane root = loader.load();
        //MainController mainController = loader.getController();
        Scene scene = new Scene(loader.load());
        stageWindow.setScene(scene);
        stageWindow.setMaximized(false);
        stageWindow.show();
        stageWindow.setFullScreen(false);

    }

    public enum AnimationDirection {
        LEFT,
        RIGHT,
        UP,
        DOWN
    }
    public void setScene(String path, boolean withAnimation, AnimationDirection direction) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Parent pane = loader.load();

            StackPane rootPane = (StackPane) stageWindow.getScene().getRoot();
            if (rootPane.getChildren().size() > 1) {
                rootPane.getChildren().remove(1, rootPane.getChildren().size());
            }
            rootPane.getChildren().add(pane);
            StackPane.setAlignment(pane, javafx.geometry.Pos.CENTER);

            if (pane instanceof javafx.scene.layout.Region) {
                javafx.scene.layout.Region region = (javafx.scene.layout.Region) pane;
                region.prefWidthProperty().bind(rootPane.widthProperty());
                region.prefHeightProperty().bind(rootPane.heightProperty());
                region.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            }

            if (withAnimation) {
                double sceneWidth = stageWindow.getWidth();

                // Configurar dirección
                switch (direction) {
                    case LEFT -> pane.setTranslateX(sceneWidth);
                    case RIGHT ->pane.setTranslateX(-sceneWidth);
                    case UP -> pane.setTranslateY(sceneWidth);
                    case DOWN -> pane.setTranslateY(-sceneWidth);
                }

                javafx.animation.TranslateTransition tt =
                        new javafx.animation.TranslateTransition(javafx.util.Duration.millis(500), pane);

                if (direction == AnimationDirection.LEFT || direction == AnimationDirection.RIGHT) {
                    tt.setFromX(pane.getTranslateX());
                    tt.setToX(0);
                } else {
                    tt.setFromY(pane.getTranslateY());
                    tt.setToY(0);
                }
                tt.setOnFinished(e -> rootPane.getChildren().setAll(pane));
                tt.play();

            } else {
                if (rootPane.getChildren().size() > 1) {
                rootPane.getChildren().remove(1, rootPane.getChildren().size());
            }
                rootPane.getChildren().add(pane);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
