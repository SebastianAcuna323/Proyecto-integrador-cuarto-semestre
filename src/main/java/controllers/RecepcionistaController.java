package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXToggleNode;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.*;
import utils.paths;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public class RecepcionistaController {

    @FXML
    private JFXComboBox<?> ComboTodos;

    @FXML
    private JFXButton botonActualizarCliente;

    @FXML
    private JFXToggleNode botonPagos;

    @FXML
    private JFXButton botonBuscarAsistencia;

    @FXML
    private JFXButton botonBuscarCliente;

    @FXML
    private JFXToggleNode botonCerrarSesion;

    @FXML
    private JFXButton botonEliminarCliente;

    @FXML
    private JFXToggleNode botonInicio;

    @FXML
    private JFXButton botonLimpiar;

    @FXML
    private JFXToggleNode botonAsistencias;

    @FXML
    private JFXButton botonRegistrarAsistecia;

    @FXML
    private JFXButton botonRegistrarCliente;

    @FXML
    private JFXButton botonRegistrarPago;

    @FXML
    private JFXToggleNode botonClientes;

    @FXML
    private VBox boxBasico;

    @FXML
    private VBox boxBasico11;

    @FXML
    private VBox boxBasico2;

    @FXML
    private VBox boxBasico21;

    @FXML
    private VBox boxBasico211;

    @FXML
    private VBox boxBasico2111;

    @FXML
    private TableColumn<ClienteRecepcionista, String> colCedula;

    @FXML
    private TableColumn<AsistenciaRegistrar, String> colCliente;


    @FXML
    private TableColumn<?, ?> colClientePagos;

    @FXML
    private TableColumn<ClienteRecepcionista, String> colCorreo;


    @FXML
    private TableColumn<ClienteRecepcionista, String> colEstado;

    @FXML
    private TableColumn<AsistenciaRegistrar, LocalDate> colFecha;

    @FXML
    private TableColumn<?, ?> colFechaPago;

    @FXML
    private TableColumn<AsistenciaRegistrar, LocalTime> colHora;


    @FXML
    private TableColumn<?, ?> colMetodoPagos;

    @FXML
    private TableColumn<?, ?> colMonto;

    @FXML
    private TableColumn<ClienteRecepcionista, String> colNombre;

    @FXML
    private TableColumn<ClienteRecepcionista, String> colPlan;

    @FXML
    private TableColumn<AsistenciaRegistrar, String> colPlanAsistencias;

    @FXML
    private TableColumn<AsistenciaRegistrar, String> colEstadoAsistencias;

    @FXML
    private TableColumn<?, ?> colPlanPagos;

    @FXML
    private TableColumn<ClienteRecepcionista, String> colTelefono;

    @FXML
    private ComboBox<?> comboMetodoPagos;

    @FXML
    private JFXComboBox<String> comboPlan;

    @FXML
    private ComboBox<?> comboPlanPago;

    @FXML
    private DatePicker datePagos;

    @FXML
    private DatePicker dpFechaAsistencia;

    @FXML
    private Label lblAsistenciasHoy;

    @FXML
    private Label lblAsistenciasSemana;

    @FXML
    private Label lblBienvenida;

    @FXML
    private Label lblHoraActual;

    @FXML
    private Label lblNombreCliente;

    @FXML
    private Label lblNombrePagos;

    @FXML
    private Label lblTitulo;

    @FXML
    private Label lblTitulo1;

    @FXML
    private Label lblTitulo11;

    @FXML
    private Label lblTitulo111;

    @FXML
    private Label lblTituloRutinas;

    @FXML
    private ToggleGroup menuDash;

    @FXML
    private ToggleGroup menuDash111;

    @FXML
    private AnchorPane panelAsistencias;

    @FXML
    private AnchorPane panelClientes;

    @FXML
    private AnchorPane panelInicio;

    @FXML
    private AnchorPane panelPagos;

    @FXML
    private TableView<AsistenciaRegistrar> tablaAsistencias;

    @FXML
    private Pane tablaCliente;

    @FXML
    private TableView<ClienteRecepcionista> tablaClientes;

    @FXML
    private TableView<?> tablaPagosRegistrados;

    @FXML
    private TableView<AsistenciaRecienteModuloRecepcionista> tablaUltimasAsistencias;

    @FXML
    private Label lblClientesActivos;

    @FXML
    private Label lblPagosPendientes;

    @FXML
    private Label lblNuevosClientes;

    @FXML
    private TableColumn<AsistenciaRecienteModuloRecepcionista, String> colClienteInicio;

    @FXML
    private TableColumn<AsistenciaRecienteModuloRecepcionista, String> colHoraInicio;

    @FXML
    private TableColumn<AsistenciaRecienteModuloRecepcionista, String> colPlanInicio;

    @FXML
    private TableColumn<AsistenciaRecienteModuloRecepcionista, String> colFechaInicio;

    private Connection conn;


    @FXML
    private TextField txtApellido;

    @FXML
    private TextField txtBuscarCliente;

    @FXML
    private TextField txtCedula;

    @FXML
    private TextField txtCedulaCliente;

    @FXML
    private TextField txtCedulaPago;

    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtDireccion;

    @FXML
    private TextField txtMonto;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtTelefono;

    private int idCliente;


    @FXML
    void registrarPago(ActionEvent event) {

    }

    @FXML
    public void initialize() {
        conn = ConexionDatabase.getConnection();
        configurarTabla();

        cargarUltimasAsistencias();
        cargarClientesActivos();
        cargarPagosPendientes();
        cargarNuevosClientes();

        inicializarModuloClientes();

        configurarTablaAsistencia();
        lblHoraActual.setText(LocalTime.now().withNano(0).toString());
        cargarAsistenciasDelDia();
        actualizarTotales();



    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String encabezado, String contenido) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(encabezado);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }

    private void mostrarAlertaa(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }


    //--------------------------------------METODOS DEL MODULO ASISTENCIAS DEL RECEPCIONISTA-----------------------------------------
    private void configurarTablaAsistencia() {
        colCliente.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getCliente()));
        colFecha.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getFecha()));
        colHora.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getHora()));
        colPlanAsistencias.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getPlan()));
        colEstadoAsistencias.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getEstado()));
    }

    @FXML
    void buscarAsistencia(ActionEvent event) {
        String cedula = txtCedulaCliente.getText().trim();

        if (cedula.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Advertencia", "Campo vacío", "Por favor ingresa una cédula para buscar al cliente.");
            return;
        }

        String sql = """
        SELECT 
            c.id_cliente,
            CONCAT(u.nombre, ' ', u.apellido) AS nombre_completo,
            t.nombre AS plan
        FROM Cliente c
        JOIN Usuario u ON c.id_cliente = u.id_usuario
        JOIN TipoPlan t ON c.id_tipo_plan = t.id_tipo_plan
        WHERE u.cedula = ?
    """;

        try (Connection con = ConexionDatabase.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, cedula);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    int idCliente = rs.getInt("id_cliente");
                    String nombre = rs.getString("nombre_completo");
                    String plan = rs.getString("plan");

                    // Mostramos los datos del cliente
                    lblNombreCliente.setText(nombre);
                    lblTituloRutinas.setText("Plan: " + plan);

                    // Guardamos el ID del cliente para el registro posterior
                    txtCedulaCliente.setUserData(idCliente);

                    mostrarAlerta(Alert.AlertType.INFORMATION, "Cliente encontrado", null,
                            "Cliente: " + nombre + "\nPlan: " + plan);
                } else {
                    mostrarAlerta(Alert.AlertType.WARNING, "No encontrado", null,
                            "No se encontró ningún cliente con esa cédula.");
                    lblNombreCliente.setText("");
                    lblTituloRutinas.setText("");
                    txtCedulaCliente.setUserData(null);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Error al buscar cliente", e.getMessage());
        }
    }

    @FXML
    void registrarAsistencia(ActionEvent event) {
  // Verificar que haya cédula en el campo
        String cedula = txtCedulaCliente.getText().trim(); // usar el campo correcto de cédula
        if (cedula.isEmpty()) {
            mostrarAlertaa("Campo vacío", "Por favor ingresa la cédula del cliente antes de registrar la asistencia.");
            return;
        }

        try (Connection conn = ConexionDatabase.getConnection()) {
            // Buscar el ID del cliente a partir de la cédula
            String sqlBuscarCliente = """
            SELECT c.id_cliente 
            FROM Cliente c
            INNER JOIN Usuario u ON c.id_cliente = u.id_usuario
            WHERE u.cedula = ?
        """;

            int idCliente = -1;
            try (PreparedStatement stmt = conn.prepareStatement(sqlBuscarCliente)) {
                stmt.setString(1, cedula); // ✅ sintaxis correcta
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        idCliente = rs.getInt("id_cliente"); // ✅ sintaxis correcta
                    }
                }
            }

            if (idCliente == -1) {
                mostrarAlertaa("Cliente no encontrado", "No se encontró un cliente registrado con esa cédula.");
                return;
            }

            // Obtener la fecha del DatePicker o la actual si no se seleccionó
            LocalDate fechaSeleccionada = dpFechaAsistencia.getValue() != null
                    ? dpFechaAsistencia.getValue()
                    : LocalDate.now();

            java.sql.Date fechaSQL = java.sql.Date.valueOf(fechaSeleccionada);
            java.sql.Time horaSQL = java.sql.Time.valueOf(LocalTime.now());

            // Obtener el ID del recepcionista logueado
            int idRecepcionista = UsuarioSesion.getIdUsuario();

            // Insertar la asistencia
            String sqlInsert = """
            INSERT INTO Asistencia (fecha, hora, id_cliente, registrado_por)
            VALUES (?, ?, ?, ?)
        """;

            try (PreparedStatement stmt = conn.prepareStatement(sqlInsert)) {
                stmt.setDate(1, fechaSQL);
                stmt.setTime(2, horaSQL);
                stmt.setInt(3, idCliente);
                stmt.setInt(4, idRecepcionista);
                stmt.executeUpdate();
            }

            mostrarAlertaa("✅ Éxito", "Asistencia registrada correctamente.");

            // Actualizar tabla y totales
            cargarAsistenciasDelDia();
            actualizarTotales();
            limpiarCamposAsistencia();

        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlertaa("❌ Error", "No se pudo registrar la asistencia.\n" + e.getMessage());
        }
    }

    private void limpiarCamposAsistencia() {
        txtCedulaPago.clear();
        dpFechaAsistencia.setValue(null);
    }

    private void cargarAsistenciasDelDia() {
        ObservableList<AsistenciaRegistrar> lista = FXCollections.observableArrayList();

        try (Connection conn = ConexionDatabase.getConnection()) {
            String sql = """
                    SELECT CONCAT(u.nombre, ' ', u.apellido) AS cliente, a.fecha, a.hora,
                           t.nombre AS plan, e.descripcion AS estado
                    FROM Asistencia a
                    JOIN Cliente c ON a.id_cliente = c.id_cliente
                    JOIN Usuario u ON c.id_cliente = u.id_usuario
                    JOIN TipoPlan t ON c.id_tipo_plan = t.id_tipo_plan
                    JOIN Estado e ON u.id_estado = e.id_estado
                    WHERE a.fecha = CURDATE()
                    ORDER BY a.hora ASC
                    """;
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                lista.add(new AsistenciaRegistrar(
                        rs.getString("cliente"),
                        rs.getDate("fecha").toLocalDate(),
                        rs.getTime("hora").toLocalTime(),
                        rs.getString("plan"),
                        rs.getString("estado")
                ));
            }

            tablaAsistencias.setItems(lista);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void actualizarTotales() {
        try (Connection conn = ConexionDatabase.getConnection()) {
            Statement st = conn.createStatement();

            ResultSet rsDia = st.executeQuery("SELECT COUNT(*) FROM Asistencia WHERE fecha = CURDATE()");
            if (rsDia.next()) lblAsistenciasHoy.setText(String.valueOf(rsDia.getInt(1)));

            ResultSet rsSemana = st.executeQuery("SELECT COUNT(*) FROM Asistencia WHERE YEARWEEK(fecha, 1) = YEARWEEK(CURDATE(), 1)");
            if (rsSemana.next()) lblAsistenciasSemana.setText(String.valueOf(rsSemana.getInt(1)));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //--------------------------------------METODOS DEL MODULO CLIENTES DEL RECEPCIONISTA-----------------------------------------

    private ObservableList<ClienteRecepcionista> listaClientes = FXCollections.observableArrayList();
    private ClienteRecepcionista clienteSeleccionado;

    private void inicializarModuloClientes() {
        // Configurar columnas de la tabla
        colCedula.setCellValueFactory(new PropertyValueFactory<>("cedula"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        colPlan.setCellValueFactory(new PropertyValueFactory<>("plan"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        // ComboBox con tipos de plan
        comboPlan.getItems().addAll("Básico", "Premium");

        tablaClientes.setItems(listaClientes);

        tablaClientes.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                txtCedula.setText(newSel.getCedula());
                txtNombre.setText(newSel.getNombreCompleto());
                txtApellido.setText("");

                txtTelefono.setText(newSel.getTelefono() != null ? newSel.getTelefono() : "");
                txtCorreo.setText(newSel.getCorreo() != null ? newSel.getCorreo() : "");
                txtDireccion.setText(newSel.getDireccion() != null ? newSel.getDireccion() : "");
                comboPlan.setValue(newSel.getPlan()); // puede quedar null

                // Bloquear campos no editables
                txtCedula.setDisable(true);
                txtNombre.setDisable(true);
                txtApellido.setDisable(true);

                // Guarda idUsuario en clienteSeleccionado para operaciones
                clienteSeleccionado = newSel;
            }
        });

    }

    @FXML
    void limpiarCampos(ActionEvent event) {

        txtTelefono.clear();
        txtDireccion.clear();
        txtCorreo.clear();
        comboPlan.setValue(null);
    }

    @FXML
    void eliminarCliente(ActionEvent event) {
        if (clienteSeleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Advertencia", "Selecciona un cliente", "Debes seleccionar un cliente de la tabla para poder eliminarlo.");
            return;
        }

        // Confirmación antes de eliminar
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText("¿Estás seguro de eliminar este cliente?");
        confirmacion.setContentText("Esta acción no se puede deshacer.\n\nCliente: " + clienteSeleccionado.getNombreCompleto());

        if (confirmacion.showAndWait().get() != ButtonType.OK) {
            return; // Canceló
        }

        String sql = "DELETE FROM cliente WHERE id_cliente = ?";

        try (Connection con = ConexionDatabase.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, clienteSeleccionado.getIdUsuario());
            int filas = pst.executeUpdate();

            if (filas > 0) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Cliente eliminado", "El cliente fue eliminado correctamente.");
                listaClientes.remove(clienteSeleccionado);
                limpiarCampos(null);
            } else {
                mostrarAlerta(Alert.AlertType.WARNING, "Advertencia", "No se encontró el cliente", "El cliente no existe o ya fue eliminado.");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Error SQL", "No se pudo eliminar el cliente", ex.getMessage());
        }
    }

    @FXML
    void actualizarCliente(ActionEvent event) {
        if (clienteSeleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Advertencia", "Selecciona un cliente", "Primero selecciona un cliente en la tabla para actualizar sus datos.");
            return;
        }

        String telefono = txtTelefono.getText().trim();
        String direccion = txtDireccion.getText().trim();
        String planSeleccionado = comboPlan.getValue();

        if (telefono.isEmpty() || direccion.isEmpty() || planSeleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos incompletos", "Faltan datos por llenar", "Debes llenar todos los campos: teléfono, dirección y tipo de plan.");
            return;
        }

        String sqlTipoPlan = "SELECT id_tipo_plan FROM tipoplan WHERE nombre = ?";
        int idTipoPlan = -1;

        try (Connection con = ConexionDatabase.getConnection();
             PreparedStatement pstPlan = con.prepareStatement(sqlTipoPlan)) {

            pstPlan.setString(1, planSeleccionado);
            try (ResultSet rs = pstPlan.executeQuery()) {
                if (rs.next()) {
                    idTipoPlan = rs.getInt("id_tipo_plan");
                } else {
                    mostrarAlerta(Alert.AlertType.ERROR, "Error", "Plan no encontrado", "El tipo de plan seleccionado no existe en la base de datos.");
                    return;
                }
            }

            String updateSQL = """
            UPDATE cliente
            SET telefono = ?, direccion = ?, id_tipo_plan = ?
            WHERE id_cliente = ?
        """;

            try (PreparedStatement pstUpdate = con.prepareStatement(updateSQL)) {
                pstUpdate.setString(1, telefono);
                pstUpdate.setString(2, direccion);
                pstUpdate.setInt(3, idTipoPlan);
                pstUpdate.setInt(4, clienteSeleccionado.getIdUsuario());

                int filas = pstUpdate.executeUpdate();
                if (filas > 0) {
                    mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Cliente actualizado", "Los datos del cliente se actualizaron correctamente.");
                    buscarCliente(null); // 🔄 Refrescar tabla
                } else {
                    mostrarAlerta(Alert.AlertType.WARNING, "Sin cambios", "No se actualizó ningún registro", "Verifica que el cliente exista en la tabla de clientes.");
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Error SQL", "No se pudo actualizar el cliente", ex.getMessage());
        }
    }

    @FXML
    void registrarCliente(ActionEvent event) {
        if (clienteSeleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Advertencia", "Selecciona un usuario", "Primero selecciona un usuario de la tabla antes de registrar como cliente.");
            return;
        }

        String telefono = txtTelefono.getText().trim();
        String direccion = txtDireccion.getText().trim();
        String planSeleccionado = comboPlan.getValue();

        if (telefono.isEmpty() || direccion.isEmpty() || planSeleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos incompletos", "Faltan datos por llenar", "Debes llenar todos los campos: teléfono, dirección y tipo de plan.");
            return;
        }

        String sqlTipoPlan = "SELECT id_tipo_plan FROM tipoplan WHERE nombre = ?";
        int idTipoPlan = -1;

        try (Connection con = ConexionDatabase.getConnection();
             PreparedStatement pstPlan = con.prepareStatement(sqlTipoPlan)) {

            pstPlan.setString(1, planSeleccionado);
            try (ResultSet rs = pstPlan.executeQuery()) {
                if (rs.next()) {
                    idTipoPlan = rs.getInt("id_tipo_plan");
                } else {
                    mostrarAlerta(Alert.AlertType.ERROR, "Error", "Plan no encontrado", "El tipo de plan seleccionado no existe en la base de datos.");
                    return;
                }
            }

            String insertSQL = """
            INSERT INTO cliente (id_cliente, telefono, direccion, id_tipo_plan, fecha_matricula)
            VALUES (?, ?, ?, ?, CURDATE())
        """;

            try (PreparedStatement pstInsert = con.prepareStatement(insertSQL)) {
                pstInsert.setInt(1, clienteSeleccionado.getIdUsuario());
                pstInsert.setString(2, telefono);
                pstInsert.setString(3, direccion);
                pstInsert.setInt(4, idTipoPlan);

                int filas = pstInsert.executeUpdate();
                if (filas > 0) {
                    mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Cliente registrado", "El cliente fue registrado correctamente con fecha de matrícula actual.");
                    buscarCliente(null);
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Error SQL", "No se pudo registrar el cliente", ex.getMessage());
        }
    }


    @FXML
    void buscarCliente(ActionEvent event) {
        String filtro = txtBuscarCliente.getText().trim();
        listaClientes.clear();

        String baseQuery = """
        SELECT 
            u.id_usuario,
            u.cedula,
            u.nombre,
            u.apellido,
            c.telefono,
            c.direccion,
            u.correo,
            t.nombre AS plan,
            e.descripcion AS estado
        FROM usuario u
        LEFT JOIN cliente c ON u.id_usuario = c.id_cliente
        LEFT JOIN tipoplan t ON c.id_tipo_plan = t.id_tipo_plan
        LEFT JOIN estado e ON u.id_estado = e.id_estado
        WHERE u.id_rol = 3
    """;

        boolean useFilter = !filtro.isEmpty();
        if (useFilter) {
            baseQuery += " AND (u.nombre LIKE ? OR u.apellido LIKE ? OR u.cedula LIKE ?)";
        }

        try (Connection con = ConexionDatabase.getConnection();
             PreparedStatement pst = con.prepareStatement(baseQuery)) {

            if (useFilter) {
                pst.setString(1, "%" + filtro + "%");
                pst.setString(2, "%" + filtro + "%");
                pst.setString(3, "%" + filtro + "%");
            }

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    int idUsuario = rs.getInt("id_usuario");
                    String cedula = rs.getString("cedula");
                    String nombreCompleto = rs.getString("nombre") + " " + rs.getString("apellido");
                    String telefono = rs.getString("telefono");    // puede ser NULL
                    String direccion = rs.getString("direccion");  // puede ser NULL
                    String correo = rs.getString("correo");
                    String plan = rs.getString("plan");            // puede ser NULL si no es cliente aún
                    String estado = rs.getString("estado");        // puede ser NULL

                    listaClientes.add(new ClienteRecepcionista(
                            idUsuario,
                            cedula,
                            nombreCompleto,
                            telefono,
                            correo,
                            direccion,
                            plan,
                            estado
                    ));
                }
            }

            tablaClientes.setItems(listaClientes);
            System.out.println("✅ Clientes/usuarios encontrados: " + listaClientes.size());

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("❌ Error al buscar cliente: " + ex.getMessage());
        }
    }



    //--------------------------------------METODOS DEL MODULO DE INICIO DEL RECEPCIONISTA-----------------------------------------


    private void configurarTabla() {
        colClienteInicio.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCliente()));
        colHoraInicio.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getHora()));
        colFechaInicio.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getFecha()));
        colPlanInicio.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getPlan()));
    }


    private void cargarClientesActivos() {
        String sql = "SELECT COUNT(DISTINCT id_cliente) AS total FROM asistencia WHERE DATE(fecha_asistencia) = CURDATE()";
        try (Connection conn = ConexionDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                int total = rs.getInt("total");
                lblClientesActivos.setText(String.valueOf(total));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void cargarPagosPendientes() {
        String sql = "SELECT COUNT(*) AS total FROM Pago WHERE estado = 'Pendiente'";

        try (Connection conn = ConexionDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                int total = rs.getInt("total");
                lblPagosPendientes.setText(String.valueOf(total));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("❌ Error al cargar pagos pendientes: " + e.getMessage());
        }
    }

    private void cargarNuevosClientes() {
        String sql = "SELECT COUNT(*) AS total FROM Cliente";

        try (Connection conn = ConexionDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                int total = rs.getInt("total");
                lblNuevosClientes.setText(String.valueOf(total));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("❌ Error al cargar clientes registrados: " + e.getMessage());
        }
    }

    private void cargarUltimasAsistencias() {
        String sql = """
        SELECT 
            CONCAT(u.nombre, ' ', u.apellido) AS cliente,
            a.hora,
            a.fecha,
            t.nombre AS plan
        FROM Asistencia a
        JOIN Cliente c ON a.id_cliente = c.id_cliente
        JOIN Usuario u ON c.id_cliente = u.id_usuario
        JOIN TipoPlan t ON c.id_tipo_plan = t.id_tipo_plan
        ORDER BY a.fecha DESC, a.hora DESC
        LIMIT 10
    """;

        ObservableList<AsistenciaRecienteModuloRecepcionista> lista = FXCollections.observableArrayList();

        try (Connection conn = ConexionDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(new AsistenciaRecienteModuloRecepcionista(
                        rs.getString("cliente"),
                        rs.getString("hora"),
                        rs.getString("fecha"),
                        rs.getString("plan")
                ));
            }

            colClienteInicio.setCellValueFactory(new PropertyValueFactory<>("cliente"));
            colHoraInicio.setCellValueFactory(new PropertyValueFactory<>("hora"));
            colFechaInicio.setCellValueFactory(new PropertyValueFactory<>("fecha"));
            colPlanInicio.setCellValueFactory(new PropertyValueFactory<>("plan"));

            tablaUltimasAsistencias.setItems(lista);

            System.out.println("✅ Total asistencias encontradas: " + lista.size());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //-----------------------------------------Barra lateral inicio del cliente----------------------------------------------

    private void hideAll() {
        panelInicio.setVisible(false);
        panelAsistencias.setVisible(false);
        panelPagos.setVisible(false);
        panelClientes.setVisible(false);
    }

    @FXML
    void mostrarAsistencias(ActionEvent event) {
        hideAll();
        panelAsistencias.setVisible(true);
    }

    @FXML
    void mostrarInicio(ActionEvent event) {
        hideAll();
        panelInicio.setVisible(true);
    }

    @FXML
    void mostrarPagos(ActionEvent event) {
        hideAll();
        panelPagos.setVisible(true);
    }

    @FXML
    void mostrarClientes(ActionEvent event) {
        hideAll();
        panelClientes.setVisible(true);

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
            // Si cancela, regresa el toggle a su estado original
            botonCerrarSesion.setSelected(false);
        }
    }

}


