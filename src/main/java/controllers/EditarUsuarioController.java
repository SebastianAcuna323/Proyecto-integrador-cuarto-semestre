package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.ConexionDatabase;
import model.Usuario;

import java.sql.*;

public class EditarUsuarioController {
    @FXML
    private JFXComboBox<String> EstadoCombo;

    @FXML
    private JFXComboBox<String> RolCombo;

    @FXML
    private JFXButton buttonBack;

    @FXML
    private JFXButton buttonClear;

    @FXML
    private JFXButton buttonUpdate;

    @FXML
    private ImageView imgLogoGoogle;

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
    void botonFlechaVolver(ActionEvent event) {
        cerrarVentana();
    }

    @FXML
    void botonLimpiar(ActionEvent event) {
        limpiarCampos();

    }

    @FXML
    void botonRegistrar(ActionEvent event) {
        String nombre = txtName.getText();
        String apellido = txtLastName.getText();
        String correo = txtEmail.getText();
        String password = txtPassword.getText();
        String confirm = txtConfirmPassword.getText();
        String rol = RolCombo.getValue();
        String estado = EstadoCombo.getValue();

        if (nombre.isEmpty() || apellido.isEmpty() || correo.isEmpty() || rol == null || estado == null) {
            mostrarAlerta("⚠️ Debes completar todos los campos obligatorios.");
            return;
        }

        if (!password.equals(confirm)) {
            mostrarAlerta("⚠️ Las contraseñas no coinciden.");
            return;
        }

        String sql = """
            UPDATE usuario
            SET nombre = ?, apellido = ?, correo = ?, 
                id_rol = (SELECT id_rol FROM rol WHERE descripcion = ?),
                id_estado = (SELECT id_estado FROM estado WHERE descripcion = ?)
            WHERE cedula = ?
        """;

        try (Connection conn = ConexionDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombre);
            stmt.setString(2, apellido);
            stmt.setString(3, correo);
            stmt.setString(4, rol);
            stmt.setString(5, estado);
            stmt.setString(6, txtDocumentNumber.getText());

            int filas = stmt.executeUpdate();

            if (filas > 0) {
                mostrarAlerta("✅ Usuario actualizado correctamente.");
                cerrarVentana();
            } else {
                mostrarAlerta("⚠️ No se pudo actualizar el usuario.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta("❌ Error al actualizar el usuario: " + e.getMessage());
        }
    }



    private Usuario usuarioSeleccionado;

    // Este metodo lo llama el controlador de Administrador al abrir esta ventana
    public void setUsuario(Usuario usuario) {
        this.usuarioSeleccionado = usuario;
        cargarCombos();
        cargarDatos();
    }

    private void cargarDatos() {
        txtName.setText(usuarioSeleccionado.getNombre());
        txtLastName.setText(usuarioSeleccionado.getApellido());
        txtDocumentNumber.setText(usuarioSeleccionado.getCedula());
        txtEmail.setText(usuarioSeleccionado.getCorreo());
        txtPassword.setText(""); // Se puede dejar vacío por seguridad
        txtConfirmPassword.setText("");
        txtDocumentNumber.setEditable(false); // 🔒 No se puede editar la cédula
    }

    private void cargarCombos() {
        try (Connection conn = ConexionDatabase.getConnection();
             Statement stmt = conn.createStatement()) {

            // Cargar roles
            ResultSet rsRol = stmt.executeQuery("SELECT descripcion FROM rol");
            while (rsRol.next()) {
                RolCombo.getItems().add(rsRol.getString("descripcion"));
            }
            rsRol.close();

            // Cargar estados
            ResultSet rsEstado = stmt.executeQuery("SELECT descripcion FROM estado");
            while (rsEstado.next()) {
                EstadoCombo.getItems().add(rsEstado.getString("descripcion"));
            }
            rsEstado.close();

            RolCombo.setValue(usuarioSeleccionado.getNombreRol());
            EstadoCombo.setValue(usuarioSeleccionado.getNombreEstado());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) txtName.getScene().getWindow();
        stage.close();
    }

    private void limpiarCampos() {
        txtName.clear();
        txtLastName.clear();
        txtEmail.clear();
        txtPassword.clear();
        txtConfirmPassword.clear();
        RolCombo.setValue(null);
        EstadoCombo.setValue(null);
    }


}


