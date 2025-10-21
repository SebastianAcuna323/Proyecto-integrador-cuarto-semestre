package controllers;

import application.app;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import utils.paths;

public class PruebaLoginController {
    @FXML
    private JFXButton buttonContinuarGoogle;

    @FXML
    private JFXButton buttonLogin;

    @FXML
    private JFXButton buttonRegister;

    @FXML
    private ImageView imgLogoGoogle;

    @FXML
    private Label lblAccessAcount;

    @FXML
    private Label lblOr;

    @FXML
    private Label lblWelcome;

    @FXML
    private AnchorPane panelLogin;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUsername;

    @FXML
    void cambiarAregistro(ActionEvent event) {app.App.setScene(paths.SCENEPRUEBAREGISTRO, true, app.AnimationDirection.LEFT);
    }

}
