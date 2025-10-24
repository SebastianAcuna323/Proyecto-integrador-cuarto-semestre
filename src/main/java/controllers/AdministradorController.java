package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToggleNode;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    private TextField buscador;

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
    void crearPlan(ActionEvent event) {

    }

    @FXML
    void editarPlan(ActionEvent event) {

    }

    @FXML
    void buscarPlan(ActionEvent event) {

    }

    @FXML
    void eliminarPlan(ActionEvent event) {

    }

    //------------FUNCIONALIDAD MENU USUARIOS-----------------

    //Metodo para añadir nuevos usuarios
    @FXML
    void añadirUsuario(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(paths.AGREGARUSUARIOADMIN));
            Parent root = loader.load();

            AgregarAdminController controller = loader.getController();
            controller.cargarCombos();


            Stage stage = new Stage();
            stage.setMaximized(true);
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


    //Mostrar tabla de usuarios
    @FXML
    void buscarUsuario(ActionEvent event) {
        listaUsuarios.clear();
        String filtro = buscador.getText().trim(); // Lo que escribió el usuario

        String sql = """
        SELECT 
            u.id_usuario, 
            u.cedula, 
            u.nombre, 
            u.apellido, 
            u.correo,
            r.descripcion AS nombre_rol,
            e.descripcion AS nombre_estado
        FROM usuario u
        JOIN rol r ON u.id_rol = r.id_rol
        JOIN estado e ON u.id_estado = e.id_estado
        WHERE u.nombre LIKE ? 
           OR u.apellido LIKE ? 
           OR u.cedula LIKE ? 
           OR u.correo LIKE ? 
        ORDER BY u.id_usuario
    """;

        try (Connection conn = ConexionDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Si el campo está vacío, mostramos todos los usuarios
            if (filtro.isEmpty()) {
                filtro = "%"; // equivale a "todos"
            } else {
                filtro = "%" + filtro + "%"; // búsqueda parcial
            }

            // Asignamos el mismo filtro a cada campo
            for (int i = 1; i <= 4; i++) {
                stmt.setString(i, filtro);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Usuario u = new Usuario();
                    u.setId_usuario(rs.getInt("id_usuario"));
                    u.setCedula(rs.getString("cedula"));
                    u.setNombre(rs.getString("nombre"));
                    u.setApellido(rs.getString("apellido"));
                    u.setCorreo(rs.getString("correo"));
                    u.setNombreRol(rs.getString("nombre_rol"));
                    u.setNombreEstado(rs.getString("nombre_estado"));
                    listaUsuarios.add(u);
                }
            }

            tablaUsuarios.setItems(listaUsuarios);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Metodo editar usuario seleccionado
    @FXML
    void editarUsuario(ActionEvent event) {

        Usuario seleccionado = tablaUsuarios.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Advertencia");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, seleccione un usuario de la tabla.");
            alert.showAndWait();
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(paths.EDITARUSUARIO));
            Parent root = loader.load();

            EditarUsuarioController controller = loader.getController();
            controller.setUsuario(seleccionado);

            Stage stage = new Stage();
            stage.setTitle("Editar Usuario");
            stage.setScene(new Scene(root));
            stage.setMaximized(true);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Eliminar usuario seleccionado
    @FXML
    void eliminarUsuario(ActionEvent event) {
        Usuario usuarioSeleccionado = tablaUsuarios.getSelectionModel().getSelectedItem();

        if (usuarioSeleccionado == null) {
            // si no se ha seleccionado ningún usuario
            javafx.scene.control.Alert alerta = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.WARNING);
            alerta.setTitle("Eliminar usuario");
            alerta.setHeaderText(null);
            alerta.setContentText("Por favor, selecciona un usuario de la tabla para eliminarlo.");
            alerta.showAndWait();
            return;
        }

        // Confirmación antes de eliminar
        javafx.scene.control.Alert confirmacion = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText("¿Estás seguro de eliminar este usuario?");
        confirmacion.setContentText(
                "Nombre: " + usuarioSeleccionado.getNombre() + " " + usuarioSeleccionado.getApellido() + "\n" +
                        "Correo: " + usuarioSeleccionado.getCorreo()
        );

        java.util.Optional<javafx.scene.control.ButtonType> resultado = confirmacion.showAndWait();

        if (resultado.isPresent() && resultado.get() == javafx.scene.control.ButtonType.OK) {
            String sql = "DELETE FROM usuario WHERE id_usuario = ?";

            try (Connection conn = ConexionDatabase.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setInt(1, usuarioSeleccionado.getId_usuario());
                int filasAfectadas = stmt.executeUpdate();

                if (filasAfectadas > 0) {
                    javafx.scene.control.Alert exito = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
                    exito.setTitle("Usuario eliminado");
                    exito.setHeaderText(null);
                    exito.setContentText("El usuario ha sido eliminado correctamente.");
                    exito.showAndWait();

                    // Refresca la tabla
                    buscarUsuario(null);
                }

            } catch (Exception e) {
                e.printStackTrace();
                javafx.scene.control.Alert error = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
                error.setTitle("Error");
                error.setHeaderText("No se pudo eliminar el usuario");
                error.setContentText("Error: " + e.getMessage());
                error.showAndWait();
            }
        }


    }


    //------------MOSTRAR MENUS LATERAL IZQUIERDA--------------------
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
