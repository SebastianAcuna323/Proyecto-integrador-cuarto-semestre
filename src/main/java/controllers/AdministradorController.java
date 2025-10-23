package controllers;

import application.app;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToggleNode;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.ConexionDatabase;
import model.Usuario;
import utils.paths;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdministradorController {
    @FXML
    private JFXButton botonAñadirUsuarios;

    @FXML
    private JFXButton botonBuscar;

    @FXML
    private JFXButton botonCrear;

    @FXML
    private JFXToggleNode botonDashboard;

    @FXML
    private JFXButton botonEditar;

    @FXML
    private JFXButton botonEditarPlan;

    @FXML
    private JFXButton botonEliminar;

    @FXML
    private JFXToggleNode botonPlanes;

    @FXML
    private JFXToggleNode botonUsuario;

    @FXML
    private ToggleGroup menuDash;

    @FXML
    private AnchorPane panelDashboard;

    @FXML
    private AnchorPane panelPlanes;

    @FXML
    private AnchorPane panelUsuarios;

    @FXML
    private TableView<Usuario> tablaUsuarios;

    @FXML
    private TableColumn<?, ?> colApellido;

    @FXML
    private TableColumn<?, ?> colCedula;

    @FXML
    private TableColumn<?, ?> colContra;

    @FXML
    private TableColumn<?, ?> colCorreo;

    @FXML
    private TableColumn<?, ?> colEstado;

    @FXML
    private TableColumn<?, ?> colNombre;

    @FXML
    private TableColumn<?, ?> colRol;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id_usuario"));
        colCedula.setCellValueFactory(new PropertyValueFactory<>("cedula"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        colRol.setCellValueFactory(new PropertyValueFactory<>("nombreRol"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("nombreEstado"));
    }
    private ObservableList<Usuario> listaUsuarios = FXCollections.observableArrayList();

    @FXML
    void añadirUsuario(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(paths.AGREGARUSUARIOADMIN));
            Parent root = loader.load();

            AgregarAdminController controller = loader.getController();
            controller.cargarCombos();

            Stage stage = new Stage();
            stage.setTitle("Registrar nuevo usuario");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL); // bloquea la ventana principal hasta cerrar
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("No se pudo abrir la ventana de registro");
            alerta.setContentText("Verifica que 'agregar_usuario_admin.fxml' esté en /resources/vistas/");
            alerta.showAndWait();
        }

    }

    @FXML
    void buscarPlan(ActionEvent event) {

    }

    @FXML
    void buscarUsuario(ActionEvent event) {
        listaUsuarios.clear();

        String sql = """
            SELECT u.id_usuario, u.cedula, u.nombre, u.apellido, u.correo,
                   r.descripcion, e.descripcion
            FROM usuario u
            JOIN rol r ON u.id_rol = r.id_rol
            JOIN estado e ON u.id_estado = e.id_estado
            ORDER BY u.id_usuario
        """;

        try (Connection conn = ConexionDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Usuario u = new Usuario();
                u.setId_usuario(rs.getInt("id_usuario"));
                u.setCedula(rs.getString("cedula"));
                u.setNombre(rs.getString("nombre"));
                u.setApellido(rs.getString("apellido"));
                u.setCorreo(rs.getString("correo"));
                u.setNombreRol(rs.getString("descripcion"));
                u.setNombreEstado(rs.getString("descripcion"));
                listaUsuarios.add(u);
            }

            tablaUsuarios.setItems(listaUsuarios);

        } catch (Exception e) {
            e.printStackTrace();
        }

}

    @FXML
    void crearPlan(ActionEvent event) {

    }

    @FXML
    void editarPlan(ActionEvent event) {

    }

    @FXML
    void editarUsuario(ActionEvent event) {

    }

    @FXML
    void eliminarPlan(ActionEvent event) {

    }

    @FXML
    void eliminarUsuario(ActionEvent event) {

    }

    @FXML
    void mostrarDashboard(ActionEvent event) {
        hideAll();
        panelDashboard.setVisible(true);
    }

    @FXML
    void mostrarPlanes(ActionEvent event) {
        hideAll();
        panelPlanes.setVisible(true);
    }

    @FXML
    void mostrarUsuario(ActionEvent event) {
        hideAll();
        panelUsuarios.setVisible(true);
    }

    private void hideAll() {
        panelDashboard.setVisible(false);
        panelUsuarios.setVisible(false);
        panelPlanes.setVisible(false);
    }

}
