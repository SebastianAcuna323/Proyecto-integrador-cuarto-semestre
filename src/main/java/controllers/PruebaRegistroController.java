package controllers;

import application.app;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import utils.paths;

public class PruebaRegistroController {

    @FXML
    private JFXButton buttonBack;

    @FXML
    private JFXButton buttonClear;

    @FXML
    private JFXButton buttonSignUp;

    @FXML
    private Label lblCreateAccount;

    @FXML
    private AnchorPane panelLogin;

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
        app.App.setScene(paths.SCENEPRUEBALOGIN, true, app.AnimationDirection.RIGHT);}
    }




