package controllers;

import application.app;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import utils.paths;
import model.Usuario;

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
    private TextField txtDocumentNumber;

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
        app.App.setScene(paths.SCENEPRUEBALOGIN, true, app.AnimationDirection.RIGHT);
    }

    @FXML
    void botonLimpiar(ActionEvent event) {
        txtName.setText("");
        txtLastName.setText("");
        txtUsername.setText("");
        txtDocumentNumber.setText("");
        txtEmail.setText("");
        txtPassword.setText("");
        txtConfirmPassword.setText("");
    }

    @FXML
    void botonRegistrarse(ActionEvent event) {

        // Obtiene los datos de los campos del formulario
        String nombre = txtName.getText();
        String apellido = txtLastName.getText();
        String correo = txtEmail.getText();
        String contrasena = txtPassword.getText();
        String confirmar = txtConfirmPassword.getText();
        String cedula = txtDocumentNumber.getText();

        // Validaciones básicas
        if (nombre.isEmpty() || apellido.isEmpty() || correo.isEmpty() ||
                contrasena.isEmpty() || confirmar.isEmpty() || cedula.isEmpty()) {
            mostrarAlerta("Campos vacíos", "Todos los campos son obligatorios.", Alert.AlertType.WARNING);
            return;
        }
        if (!contrasena.equals(confirmar)) {
            mostrarAlerta("Contraseñas diferentes", "Las contraseñas no coinciden.", Alert.AlertType.ERROR);
            return;
        }
        // Crear usuario y registrar
        Usuario nuevo = new Usuario(nombre, apellido, correo, contrasena, cedula);
        String resultado = nuevo.registrar();

        // Mostrar mensaje según el resultado
        if (resultado.contains("✅")) {
            mostrarAlerta("Registro exitoso", resultado, Alert.AlertType.INFORMATION);
        } else {
            mostrarAlerta("Error de registro", resultado, Alert.AlertType.ERROR);
        }
    }

    //Metodo alertas
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }


}




