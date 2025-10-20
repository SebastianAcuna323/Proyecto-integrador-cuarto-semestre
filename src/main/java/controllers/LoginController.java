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

public class LoginController {


    @FXML
    private JFXButton buttonContinuarGoogle;

    @FXML
    private JFXButton buttonLogin;

    @FXML
    private JFXButton buttonRegister;

    @FXML
    private ImageView imgLogo1;

    @FXML
    private ImageView imgLogoGoogle;

    @FXML
    private ImageView imgLogoPequeño;

    @FXML
    private Label lblAccessAcount;

    @FXML
    private Label lblOr;

    @FXML
    private Label lblWelcome;

    @FXML
    private AnchorPane panelFondo;

    @FXML
    private AnchorPane panelLogin;

    @FXML
    private AnchorPane panelLogoYLogin;

    @FXML
    private AnchorPane panelPrincipalLogin;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUsername;

    @FXML
    void cambiarAregistro(ActionEvent event) {app.App.setScene(paths.SCENEREGISTER, true, app.AnimationDirection.LEFT);
    }




}
