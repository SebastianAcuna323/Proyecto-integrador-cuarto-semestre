package controllers;

import application.app;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.ConexionDatabase;
import utils.paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginController {
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
    private TextField txtCorreo;

    @FXML
    void cambiarAregistro(ActionEvent event) {app.App.setScene(paths.SCENEPRUEBAREGISTRO, true, app.AnimationDirection.LEFT);
    }



    @FXML
    void iniciarSesion(ActionEvent event) {
        String correo = txtCorreo.getText().trim();
        String password = txtPassword.getText().trim();

        if (correo.isEmpty() || password.isEmpty()) {
            mostrarAlerta("Campos vacíos", "⚠️ Ingresa usuario y contraseña.", Alert.AlertType.WARNING);
            return;
        }

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConexionDatabase.getConnection();
            if (conn == null) {
                mostrarAlerta("Error de conexión", "❌ No se pudo conectar a la base de datos.", Alert.AlertType.ERROR);
                return;
            }

            // Consulta el usuario y el id_rol (es un numero)
            String sql = "SELECT correo, id_rol FROM usuario WHERE correo = ? AND contraseña = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, correo);
            stmt.setString(2, password);
            rs = stmt.executeQuery();

            if (rs.next()) {
                int idRol = rs.getInt("id_rol");
                System.out.println("✅ Inicio de sesión correcto. id_rol detectado: " + idRol);

                mostrarAlerta("Bienvenido", "✅ Sesión iniciada correctamente.", Alert.AlertType.INFORMATION);
                abrirVentanaPorIdRol(idRol);
            } else {
                mostrarAlerta("Error", "❌ Usuario o contraseña incorrectos.", Alert.AlertType.ERROR);
            }

        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error inesperado", e.getMessage(), Alert.AlertType.ERROR);
        } finally {
            try {
                if (rs != null) rs.close();
            } catch (Exception ignored) {
            }
            try {
                if (stmt != null) stmt.close();
            } catch (Exception ignored) {
            }
        }
    }

        // Abre la ventana segun el idrol
        private void abrirVentanaPorIdRol(int idRol) {
            String fxmlPath = null;
            String titulo = null;

            switch (idRol) {
                case 1: // Administrador
                    fxmlPath = paths.ADMINISTRADOR;
                    titulo = "Panel del Administrador";
                    break;
                case 2: // Entrenador
                    fxmlPath = "";
                    titulo = "Panel del Entrenador";
                    break;
                case 3: // Cliente
                    fxmlPath = "";
                    titulo = "Panel del Cliente";
                    break;
                case 4: // Recepcionista
                    fxmlPath = "";
                    titulo = "Panel del Recepcionista";
                    break;
                default:
                    mostrarAlerta("Rol no reconocido", "⚠️ No existe una ventana configurada para este rol (" + idRol + ").", Alert.AlertType.WARNING);
                    return;
            }

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
                Parent root = loader.load();

                Stage stage = new Stage();
                stage.setTitle(titulo);
                Scene scene = new Scene(root);
                stage.setResizable(true);
                stage.setMinWidth(1300);
                stage.setMinHeight(900);
                stage.sizeToScene();

                stage.setScene(scene);
                stage.show();

                // Cierra la ventana actual (login)
                Stage ventanaActual = (Stage) txtCorreo.getScene().getWindow();
                ventanaActual.close();

            } catch (Exception e) {
                e.printStackTrace();
                mostrarAlerta("Error", "❌ No se pudo abrir la ventana: " + fxmlPath, Alert.AlertType.ERROR);
            }
        }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    }







