package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToggleNode;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.ConexionDatabase;
import model.Entrenador;
import model.UsuarioSesion;
import utils.paths;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;


public class EntrenadorController implements Initializable {

    @FXML
    private JFXButton botonActualizarRutina;

    @FXML
    private JFXButton botonBuscarCliente;

    @FXML
    private JFXToggleNode botonCerrarSesion;

    @FXML
    private JFXButton botonEliminarRutina;

    @FXML
    private JFXToggleNode botonInicio;

    @FXML
    private JFXButton botonLimpiar;

    @FXML
    private JFXToggleNode botonMisClientes;

    @FXML
    private JFXButton botonRegistrarRutina;

    @FXML
    private JFXToggleNode botonRutinas;

    @FXML
    private JFXButton botonVerRutinas;

    @FXML
    private VBox boxBasico;

    @FXML
    private VBox boxBasico2;

    @FXML
    private VBox boxBasico21;

    @FXML
    private TableColumn<?, ?> colCedula;

    @FXML
    private TableColumn<?, ?> colCliente;

    @FXML
    private TableColumn<?, ?> colCorreo;

    @FXML
    private TableColumn<Entrenador.ClienteAsignado, String> colEstado;

    @FXML
    private TableColumn<Entrenador.ClienteAsignado, String> colNombre;

    @FXML
    private TableColumn<Entrenador.ClienteAsignado, String> colPlan;

    @FXML
    private TableColumn<?, ?> colTelefono;

    @FXML
    private TableColumn<Entrenador.PlanEntrenamiento, String> colNombreRutina;

    @FXML
    private TableColumn<Entrenador.PlanEntrenamiento, String> colDescripcion;

    @FXML
    private TableColumn<Entrenador.PlanEntrenamiento, String> colClienteRutina;

    private Entrenador.PlanEntrenamiento planSeleccionado = null;

    @FXML
    private ComboBox<Entrenador.ClienteSimple> comboCliente;

    @FXML
    private ComboBox<?> comboDuracion;

    @FXML
    private ComboBox<?> comboNivel;

    @FXML
    private ComboBox<?> comboTipo;

    @FXML
    private Label lblBienvenida;

    @FXML
    private Label lblClientesAsignados;

    @FXML
    private Label lblRutinasCreadas;

    @FXML
    private Label lblTitulo;

    @FXML
    private Label lblTitulo1;

    @FXML
    private Label lblTituloRutinas;

    @FXML
    private ToggleGroup menuDash;

    @FXML
    private ToggleGroup menuDash111;

    @FXML
    private AnchorPane panelClientes;

    @FXML
    private AnchorPane panelInicio;

    @FXML
    private AnchorPane panelRutinas;

    @FXML
    private Pane tablaCliente;

    @FXML
    private TableView<Entrenador.ClienteAsignado> tablaClientesEntrenador;

    @FXML
    private TableView<Entrenador.ClienteAsignado> tablaResumenClientes;

    @FXML
    private TableView<Entrenador.PlanEntrenamiento> tablaRutinasAsignadas;

    @FXML
    private TextField txtBuscarCliente;

    @FXML
    private TextField txtNombreRutina;

    @FXML
    private TextField txtObjetivo;

    private Connection conn;




    private Entrenador entrenador;
    private int idEntrenador = 1; // 🔹 luego se reemplaza por el ID del login


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicializar conexión y modelo
        conn = ConexionDatabase.getConnection();
        entrenador = new Entrenador();

        // Obtener ID del entrenador desde la sesión
        idEntrenador = UsuarioSesion.getIdUsuario();
        System.out.println("ID entrenador en sesión: " + idEntrenador);

        // Configurar tabla de resumen en panel Inicio
        colCliente.setCellValueFactory(new PropertyValueFactory<>("nombreCompleto"));
        colPlan.setCellValueFactory(new PropertyValueFactory<>("plan"));

        // Configurar tabla de Mis Clientes
        configurarTablaClientes();

        // Configurar tabla de planes de entrenamiento
        configurarTablaPlanesEntrenamiento();

        // Cargar ComboBox de clientes
        cargarComboClientes();

        // Cargar datos iniciales
        cargarResumen();
        cargarClientes();
        cargarPlanesEntrenamiento();
    }




    //-----------------------------------------Metodos inicio-------------------------------------------------------


    private void cargarResumen() {
        Entrenador.ResumenEntrenador resumen = entrenador.obtenerResumen(idEntrenador);
        lblClientesAsignados.setText(String.valueOf(resumen.getTotalClientes()));
        lblRutinasCreadas.setText(String.valueOf(resumen.getTotalRutinas()));
    }

    private void cargarClientes() {
        ObservableList<Entrenador.ClienteAsignado> clientes = entrenador.obtenerClientesAsignados(idEntrenador);
        tablaResumenClientes.setItems(clientes);

        configurarTablaClientes();
    }

    //-----------------------------------------Metodos rutinas-------------------------------------------------------


     //Configura la tabla de planes de entrenamiento

    private void configurarTablaPlanesEntrenamiento() {
        // Obtener las columnas de la tabla por índice
        colNombreRutina = (TableColumn<Entrenador.PlanEntrenamiento, String>) tablaRutinasAsignadas.getColumns().get(0);
        colDescripcion = (TableColumn<Entrenador.PlanEntrenamiento, String>) tablaRutinasAsignadas.getColumns().get(1);
        colClienteRutina = (TableColumn<Entrenador.PlanEntrenamiento, String>) tablaRutinasAsignadas.getColumns().get(2);

        colNombreRutina.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colClienteRutina.setCellValueFactory(new PropertyValueFactory<>("cliente"));

        tablaRutinasAsignadas.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        cargarDatosPlan((Entrenador.PlanEntrenamiento) newSelection);
                    }
                }
        );
    }


     //Carga los datos del plan seleccionado en los campos

    private void cargarDatosPlan(Entrenador.PlanEntrenamiento plan) {
        planSeleccionado = plan;
        txtNombreRutina.setText(plan.getNombre());
        txtObjetivo.setText(plan.getDescripcion());

        // Seleccionar el cliente en el ComboBox
        for (Entrenador.ClienteSimple cliente : comboCliente.getItems()) {
            if (cliente.getIdCliente() == plan.getIdCliente()) {
                comboCliente.setValue(cliente);
                break;
            }
        }
    }


    // Carga los clientes en el ComboBox

    private void cargarComboClientes() {
        ObservableList<Entrenador.ClienteSimple> clientes = entrenador.obtenerClientesParaCombo(idEntrenador);
        comboCliente.setItems(clientes);
    }


     //Carga los planes de entrenamiento en la tabla

    private void cargarPlanesEntrenamiento() {
        ObservableList<Entrenador.PlanEntrenamiento> planes = entrenador.obtenerPlanesEntrenamiento(idEntrenador);
        tablaRutinasAsignadas.setItems(planes);
    }

    @FXML
    void registrarRutina(ActionEvent event) {
        // Validar campos
        if (txtNombreRutina.getText().trim().isEmpty()) {
            mostrarAlerta("Por favor ingrese el nombre del plan");
            return;
        }

        if (txtObjetivo.getText().trim().isEmpty()) {
            mostrarAlerta("Por favor ingrese la descripción del plan");
            return;
        }

        if (comboCliente.getValue() == null) {
            mostrarAlerta("Por favor seleccione un cliente");
            return;
        }

        // Obtener el cliente seleccionado
        Entrenador.ClienteSimple clienteSeleccionado = (Entrenador.ClienteSimple) comboCliente.getValue();

        // Registrar plan
        boolean exito = entrenador.registrarPlan(
                txtNombreRutina.getText().trim(),
                txtObjetivo.getText().trim(),
                clienteSeleccionado.getIdCliente(),
                idEntrenador
        );

        if (exito) {
            mostrarAlerta("Plan de entrenamiento registrado exitosamente");
            limpiarCamposPlan();
            cargarPlanesEntrenamiento();
            cargarResumen(); // Actualizar el resumen en el panel inicio
        } else {
            mostrarAlerta("Error al registrar el plan de entrenamiento");
        }
    }

    @FXML
    void ActualizarRutina(ActionEvent event) {
        if (planSeleccionado == null) {
            mostrarAlerta("Por favor seleccione un plan de la tabla para actualizar");
            return;
        }

        // Validar campos
        if (txtNombreRutina.getText().trim().isEmpty()) {
            mostrarAlerta("Por favor ingrese el nombre del plan");
            return;
        }

        if (txtObjetivo.getText().trim().isEmpty()) {
            mostrarAlerta("Por favor ingrese la descripción del plan");
            return;
        }

        // Actualizar plan
        boolean exito = entrenador.actualizarPlan(
                planSeleccionado.getIdPlan(),
                txtNombreRutina.getText().trim(),
                txtObjetivo.getText().trim()
        );

        if (exito) {
            mostrarAlerta("Plan de entrenamiento actualizado exitosamente");
            limpiarCamposPlan();
            cargarPlanesEntrenamiento();
            planSeleccionado = null;
        } else {
            mostrarAlerta("Error al actualizar el plan de entrenamiento");
        }
    }

    @FXML
    void limpiar(ActionEvent event) {
        limpiarCamposPlan();
    }


     //Limpia todos los campos del formulario

    private void limpiarCamposPlan() {
        txtNombreRutina.clear();
        txtObjetivo.clear();
        comboCliente.setValue(null);

        // Limpiar los otros combos si existen
        if (comboNivel != null) comboNivel.setValue(null);
        if (comboTipo != null) comboTipo.setValue(null);
        if (comboDuracion != null) comboDuracion.setValue(null);

        planSeleccionado = null;
        tablaRutinasAsignadas.getSelectionModel().clearSelection();
    }

    @FXML
    void EliminarRutina(ActionEvent event) {
        // Por ahora no funciona, como solicitaste
        mostrarAlerta("Función de eliminar no disponible aún");

    /* Cuando quieras activar esta función, descomenta:
    if (planSeleccionado == null) {
        mostrarAlerta("Por favor seleccione un plan de la tabla para eliminar");
        return;
    }

    // Confirmar eliminación
    Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    confirmacion.setTitle("Confirmar eliminación");
    confirmacion.setHeaderText("¿Está seguro de eliminar este plan?");
    confirmacion.setContentText("Plan: " + planSeleccionado.getNombre());

    Optional<ButtonType> resultado = confirmacion.showAndWait();

    if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
        boolean exito = entrenador.eliminarPlan(planSeleccionado.getIdPlan());

        if (exito) {
            mostrarAlerta("Plan eliminado exitosamente");
            limpiarCamposPlan();
            cargarPlanesEntrenamiento();
            cargarResumen();
        } else {
            mostrarAlerta("Error al eliminar el plan");
        }
    }
    */
    }


    //-----------------------------------------Metodos mis clientes-------------------------------------------------------

    private void configurarTablaClientes() {
        colCedula.setCellValueFactory(new PropertyValueFactory<>("cedula"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombreCompleto"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        colPlan.setCellValueFactory(new PropertyValueFactory<>("plan"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
    }

    public ObservableList<Entrenador.ClienteAsignado> buscarClientes(int idEntrenador, String filtro) {
        ObservableList<Entrenador.ClienteAsignado> lista = FXCollections.observableArrayList();

        String sql = """
            SELECT u.cedula, CONCAT(u.nombre, ' ', u.apellido) AS nombre_completo,
                   c.telefono, u.correo,
                   p.nombre AS plan, tp.nombre AS tipo_plan,
                   e.descripcion AS estado
            FROM PlanEntrenamiento p
            JOIN Cliente c ON p.id_cliente = c.id_cliente
            JOIN Usuario u ON c.id_cliente = u.id_usuario
            JOIN TipoPlan tp ON c.id_tipo_plan = tp.id_tipo_plan
            JOIN Estado e ON u.id_estado = e.id_estado
            WHERE p.id_entrenador = ?
              AND (u.nombre LIKE ? OR u.cedula LIKE ?);
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idEntrenador);
            ps.setString(2, "%" + filtro + "%");
            ps.setString(3, "%" + filtro + "%");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(new Entrenador.ClienteAsignado(
                        rs.getString("cedula"),
                        rs.getString("nombre_completo"),
                        rs.getString("telefono"),
                        rs.getString("correo"),
                        rs.getString("plan"),
                        rs.getString("tipo_plan"),
                        rs.getString("estado")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }


     //Obtiene la rutina del cliente según su cédula.



    @FXML
    void buscarClienteEntrenador(ActionEvent event) {
        String filtro = txtBuscarCliente.getText().trim();

        if (filtro.isEmpty()) {
            mostrarAlerta("Por favor ingrese un nombre o cédula para buscar");
            return;
        }

        // Buscar clientes usando el modelo
        ObservableList<Entrenador.ClienteAsignado> resultados =
                entrenador.buscarClientes(idEntrenador, filtro);

        if (resultados.isEmpty()) {
            mostrarAlerta("No se encontraron clientes con ese criterio de búsqueda");
            tablaClientesEntrenador.setItems(FXCollections.observableArrayList());
        } else {
            tablaClientesEntrenador.setItems(resultados);
        }
    }

    @FXML
    void verRutinasCliente(ActionEvent event) {
        Entrenador.ClienteAsignado clienteSeleccionado =
                tablaClientesEntrenador.getSelectionModel().getSelectedItem();

        if (clienteSeleccionado == null) {
            mostrarAlerta("Por favor seleccione un cliente de la tabla");
            return;
        }

        // Obtener la rutina del cliente
        String rutina = entrenador.obtenerRutinaPorCedula(clienteSeleccionado.getCedula());

        // TODO: Aquí abrirás la ventana de rutinas cuando la tengas
        // Por ahora solo mostramos un mensaje
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Rutina del Cliente");
        alert.setHeaderText("Cliente: " + clienteSeleccionado.getNombreCompleto());
        alert.setContentText("Rutina asignada: " + rutina);
        alert.showAndWait();

    /* Cuando tengas la ventana de rutinas, descomenta esto:
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ruta/a/RutinasView.fxml"));
        Parent root = loader.load();

        // Pasar datos al controlador de rutinas si es necesario
        RutinasController controller = loader.getController();
        controller.setCliente(clienteSeleccionado);

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Rutinas de " + clienteSeleccionado.getNombreCompleto());
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
        mostrarAlerta("Error al abrir la ventana de rutinas");
    }
    */
    }



    //-----------------------------------------Barra lateral inicio del cliente----------------------------------------------
    @FXML
    void mostrarInicio(ActionEvent event) {
        hideAll();
        panelInicio.setVisible(true);
    }

    @FXML
    void mostrarMisClientes(ActionEvent event) {
        hideAll();
        panelClientes.setVisible(true);
    }

    @FXML
    void mostrarRutina(ActionEvent event) {
        hideAll();
        panelRutinas.setVisible(true);
    }


    @FXML
    void mostrarCerrarSesion(ActionEvent event) {
        // Mostrar alerta de confirmación
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar cierre de sesión");
        alert.setHeaderText(null);
        alert.setContentText("¿Está seguro de que desea cerrar sesión?");

        Optional<ButtonType> resultado = alert.showAndWait();

        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            try {
                // Cargar la ventana de Login.fxml
                FXMLLoader loader = new FXMLLoader(getClass().getResource(paths.SCENEPRUEBALOGIN));
                Parent root = loader.load();

                Stage loginStage = new Stage();
                loginStage.setScene(new Scene(root));
                loginStage.setTitle("Iniciar sesión");
                loginStage.show();

                // Cerrar la ventana actual
                Stage stageActual = (Stage) botonCerrarSesion.getScene().getWindow();
                stageActual.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

            botonCerrarSesion.setSelected(false);
        }
    }

    private void hideAll() {
        panelInicio.setVisible(false);
        panelClientes.setVisible(false);
        panelRutinas.setVisible(false);
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}


