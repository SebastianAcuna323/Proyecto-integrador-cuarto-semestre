package controllers;

import application.app;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import utils.paths;

public class RegistroController {
    @FXML
    private JFXButton buttonBack;

    @FXML
    private JFXButton buttonClear;

    @FXML
    private JFXButton buttonSignUp;

    @FXML
    private ImageView imgLogoPequeño;

    @FXML
    private ImageView imgLogoPequeño1;

    @FXML
    private Label lblCreateAccount;

    @FXML
    private AnchorPane panelFondo;

    @FXML
    private AnchorPane panelLogin;

    @FXML
    private AnchorPane panelLogoYLogin;

    @FXML
    private AnchorPane panelPrincipalRegister;

    @FXML
    private TextField txtConfirmPassword;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtLastName;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUsername;

    @FXML
    void botonFlechaVolver(ActionEvent event) {
        app.App.setScene(paths.SCENELOGIN, true, app.AnimationDirection.RIGHT);}

    @FXML
    void botonLimpiar(ActionEvent event) {
        txtName.setText("");
        txtLastName.setText("");
        txtUsername.setText("");
        txtEmail.setText("");
        txtPassword.setText("");
        txtConfirmPassword.setText("");
    }

    @FXML
    void botonRegistrarse(ActionEvent event) {

    }




}
