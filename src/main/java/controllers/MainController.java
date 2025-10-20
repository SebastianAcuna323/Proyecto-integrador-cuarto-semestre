package controllers;

import application.app;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import utils.paths;

public class MainController {

    @FXML
    private StackPane panelPrincipalMain;

    @FXML
    public void initialize() {
        // ✅ Carga la primera escena después de que el FXML esté inicializado
        Platform.runLater(() -> {
            app.App.setScene(paths.SCENEPRUEBALOGIN, false, app.AnimationDirection.LEFT);
        });
    }

}
