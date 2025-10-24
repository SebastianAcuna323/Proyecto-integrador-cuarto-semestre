package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.ConexionDatabase;
import java.sql.*;

public class AgregarAdminController {
    @FXML
    private JFXButton buttonBack;

    @FXML
    private JFXButton buttonClear;

    @FXML
    private JFXButton buttonSignUp;

    @FXML
    private JFXComboBox<String> EstadoCombo;

    @FXML
    private JFXComboBox<String> RolCombo;

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
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void botonLimpiar(ActionEvent event) {
        limpiarCampos();
    }

    @FXML
    void botonRegistrar(ActionEvent event) {
        String nombre = txtName.getText();
        String apellido = txtLastName.getText();
        String documento = txtDocumentNumber.getText();
        String correo = txtEmail.getText();
        String contrasena = txtPassword.getText();
        String confirmar = txtConfirmPassword.getText();
        String rolNombre = RolCombo.getValue();
        String estadoNombre = EstadoCombo.getValue();

        //Revisa que no hayan campos vacios en el formulario
        if (nombre.isEmpty() || apellido.isEmpty() || documento.isEmpty() || correo.isEmpty() || contrasena.isEmpty()
                || confirmar.isEmpty() || rolNombre == null || estadoNombre == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos incompletos", "Debes llenar todos los campos.");
            return;
        }
        //Revisa que las contraseñas sean iguales
        if (!contrasena.equals(confirmar)) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Las contraseñas no coinciden.");
            return;
        }
        //Conexion a base de datos
        try (Connection conn = ConexionDatabase.getConnection();) {

            // Buscar id del rol
            int idRol = obtenerId(conn, "rol", "descripcion", rolNombre, "id_rol");
            int idEstado = obtenerId(conn, "estado", "descripcion", estadoNombre, "id_estado");

            if (idRol == 0 || idEstado == 0) {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "Rol o estado no válido.");
                return;
            }

            // Insertar nuevo usuario
            String sqlInsert = "INSERT INTO usuario (nombre, apellido, cedula, correo, contraseña, id_rol, id_estado) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmtInsert = conn.prepareStatement(sqlInsert);
            stmtInsert.setString(1, nombre);
            stmtInsert.setString(2, apellido);
            stmtInsert.setString(3, documento);
            stmtInsert.setString(4, correo);
            stmtInsert.setString(5, contrasena);
            stmtInsert.setInt(6, idRol);
            stmtInsert.setInt(7, idEstado);

            int filas = stmtInsert.executeUpdate();

            if (filas > 0) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Registro exitoso", "El usuario ha sido registrado correctamente.");
                limpiarCampos();
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo registrar el usuario.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Error SQL", e.getMessage());
        }
    }
    //Obtiene los nombres de los roles, ya que en la BDD estan en tablas distintas
    private int obtenerId(Connection conexion, String tabla, String columna, String valor, String idColumna) throws SQLException {
        String sql = "SELECT " + idColumna + " FROM " + tabla + " WHERE " + columna + " = ?";
        PreparedStatement stmt = conexion.prepareStatement(sql);
        stmt.setString(1, valor);
        ResultSet rs = stmt.executeQuery();
        return rs.next() ? rs.getInt(idColumna) : 0;
    }

    //Se asignan que opciones va a tener el formulario en rol y estado
    public void cargarCombos() {
        RolCombo.setItems(FXCollections.observableArrayList(
                "Administrador", "Recepcionista", "Cliente", "Entrenador"
        ));
        EstadoCombo.setItems(FXCollections.observableArrayList(
                "Activo", "Inactivo", "Suspendido"
        ));
    }

    private void cargarRoles() {
        ObservableList<String> roles = FXCollections.observableArrayList();
        try {
            Connection conn = ConexionDatabase.getConnection();
            String sql = "SELECT descripcion FROM rol";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                roles.add(rs.getString("descripcion"));
            }
            RolCombo.setItems(roles);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void cargarEstados() {
        ObservableList<String> estados = FXCollections.observableArrayList();
        try {
            Connection conn = ConexionDatabase.getConnection();
            String sql = "SELECT descripcion FROM estado";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                estados.add(rs.getString("descripcion"));
            }
            EstadoCombo.setItems(estados);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void limpiarCampos() {
        txtName.clear();
        txtLastName.clear();
        txtDocumentNumber.clear();
        txtEmail.clear();
        txtPassword.clear();
        txtConfirmPassword.clear();
        RolCombo.setValue(null);
        EstadoCombo.setValue(null);
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}





