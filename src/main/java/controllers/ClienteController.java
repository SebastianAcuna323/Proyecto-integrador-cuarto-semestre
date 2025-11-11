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
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.*;
import utils.paths;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

public class ClienteController {

    @FXML
    private JFXComboBox<String> ComboRutina;

    @FXML
    private JFXToggleNode botonAsistencia;

    @FXML
    private JFXButton botonBasico;

    @FXML
    private JFXButton botonCambiarContra;

    @FXML
    private JFXButton botonCancelar;

    @FXML
    private JFXToggleNode botonCerrarSesion;

    @FXML
    private JFXToggleNode botonConfiguracion;

    @FXML
    private JFXButton botonGuardarCambios;

    @FXML
    private JFXToggleNode botonInicio;

    @FXML
    private JFXButton botonPagarAhora;

    @FXML
    private JFXToggleNode botonPago;

    @FXML
    private JFXButton botonPremium;

    @FXML
    private JFXToggleNode botonRutina;

    @FXML
    private JFXButton botonVerRutina;

    @FXML
    private VBox boxBasico;

    @FXML
    private VBox boxBasico1;

    @FXML
    private VBox boxBasico11;

    @FXML
    private VBox boxPremium;

    @FXML
    private JFXButton buttonBuscar;

    @FXML
    private JFXButton buttonClear1;

    @FXML
    private NumberAxis cantidadAsistencias;

    @FXML
    private TableColumn<Map<String, Object>, String> colEjercicio;
    @FXML
    private TableColumn<Map<String, Object>, Integer> colSerie;
    @FXML
    private TableColumn<Map<String, Object>, Integer> colRepeticiones;
    @FXML
    private TableColumn<Map<String, Object>, String> colPesoRecomendado;
    @FXML
    private TableColumn<Map<String, Object>, String> colDescanso;

    @FXML
    private TableColumn<?, ?> colMonto;



    @FXML
    private TableColumn<?, ?> colPlan;


    @FXML
    private ComboBox<String> comboMetodoPago;

    @FXML
    private DatePicker dateDesde;

    @FXML
    private DatePicker dateHasta;

    @FXML
    private CategoryAxis ejeX;

    @FXML
    private NumberAxis ejeY;

    @FXML
    private BarChart< String,Number> graficaAsistencia;

    @FXML
    private BarChart<String, Number> graficoAsistencias;

    @FXML
    private Label lblAsistenciaMesInicio;

    @FXML
    private Label lblAsistenciaMes1;

    @FXML
    private Label lblAsistenciaMes11;

    @FXML
    private Label lblAsistenciaMes111;

    @FXML
    private Label lblAsistenciaTotalInicio;

    @FXML
    private Label lblAsistenciasMes;

    @FXML
    private Label lblBienvenida;

    @FXML
    private Label lblPlanRutinas;

    @FXML
    private Label lblEntrenadorRutinas;

    @FXML
    private Label lblBienvenida1;

    @FXML
    private Label lblDuracion;

    @FXML
    private Label lblEntrenadorAsignada;

    @FXML
    private Label lblEstado;

    @FXML
    private Label lblEstadoPlan;

    @FXML
    private Label lblFechaCreacion;

    @FXML
    private Label lblNombreCliente;

    @FXML
    private Label lblPagos;

    @FXML
    private Label lblPlanActivo;

    @FXML
    private Label lblPlan1;

    @FXML
    private Label lblPlan11;

    @FXML
    private Label lblPromedioSemanal;

    @FXML
    private Label lblRol;

    @FXML
    private Label lblSesion;

    @FXML
    private Label lblTitulo;

    @FXML
    private Label lblTituloRutinas;

    @FXML
    private Label lblUltimaAsistencia;

    @FXML
    private ToggleGroup menuDash;

    @FXML
    private ToggleGroup menuDash1;

    @FXML
    private ToggleGroup menuDash11;

    @FXML
    private ToggleGroup menuDash111;

    @FXML
    private AnchorPane panelAsistencias;

    @FXML
    private AnchorPane panelConfiguracion;

    @FXML
    private AnchorPane panelInicio;

    @FXML
    private AnchorPane panelPagos;

    @FXML
    private AnchorPane panelRutinas;

    @FXML
    private CategoryAxis semanas;

    @FXML
    private TableView<AsistenciaModuloCliente> tablaAsistencias;

    @FXML
    private TableColumn<AsistenciaModuloCliente, String> colEstado;

    @FXML
    private TableColumn<AsistenciaModuloCliente, String> colFecha;

    @FXML
    private TableColumn<AsistenciaModuloCliente, String> colHora;

    @FXML
    private TableView<Map<String, Object>> tablaEjercicios;

    @FXML
    private TableView<PagoModuloCliente> tablaPagos;

    @FXML
    private TextField txtCedula;

    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtDireccion;

    @FXML
    private PasswordField txtPassActual;

    @FXML
    private PasswordField txtPassConfirm;

    @FXML
    private PasswordField txtPassNueva;

    @FXML
    private TextField txtTelefono;

    @FXML
    void pagarAhora(ActionEvent event) {

    }

    @FXML
    void verRutina(ActionEvent event) {

    }

    private Connection conn;
    private int idCliente;
    private String contrasenaActualBD;
    private int planSeleccionado = 0; // 1 = Básico, 2 = Premium
    private String metodoPagoSeleccionado = "";
    private double montoPlanSeleccionado = 0.0;


    //INICIALIZADOR DE METODOS
    @FXML
    public void initialize() {
        conn = ConexionDatabase.getConnection();
        idCliente = UsuarioSesion.getIdUsuario(); // el cliente que inició sesión

        //Metodos inicio
        cargarPlan();
        cargarEntrenador();
        cargarSesion();
        cargarAsistencias();
        cargarPagos();
        cargarGrafico();

        //Metodos asistencias
        configurarTablaAsistencias();
        cargarResumenAsistencias();
        cargarGraficaSemanal();

        //Metodos configuracion
        cargarDatosConfiguracion();
        configurarCamposCedula();

        //Metodos configuracion
        cargarPlanActual();
        cargarHistorialPagos();
        configurarComboMetodoPago();
        configurarTablaHistorialPagos();

    }

    //--------------------------------METODOS DEL MODULO DE PAGOS DEL CLIENTE------------------------------------

     //Configura las columnas de la tabla de historial de pagos

    private void configurarTablaHistorialPagos() {
        // Verificar que las columnas existan en tu FXML con estos fx:id
        colFecha.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("fecha"));
        colPlan.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("plan"));
        colMonto.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("monto"));
        colEstado.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("estado"));
    }

    //Carga el plan actual del cliente

    private void cargarPlanActual() {
        String sql = """
        SELECT t.nombre 
        FROM Cliente c 
        JOIN TipoPlan t ON c.id_tipo_plan = t.id_tipo_plan 
        WHERE c.id_cliente = ?
    """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idCliente);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String planNombre = rs.getString("nombre");
                lblEstadoPlan.setText("Plan actual: " + planNombre);
                System.out.println("✅ Plan actual cargado: " + planNombre);
            } else {
                lblEstadoPlan.setText("Plan actual: Ninguno");
                System.out.println("❌ No se encontró plan para el cliente ID: " + idCliente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            lblPlan1.setText("Plan actual: Error al cargar");
        }
    }


     //Carga el historial de pagos del cliente en la tabla

    private void cargarHistorialPagos() {
        tablaPagos.getItems().clear();

        String sql = """
        SELECT p.fecha_pago, t.nombre AS plan, p.monto, p.estado
        FROM Pago p
        JOIN Cliente c ON p.id_cliente = c.id_cliente
        JOIN TipoPlan t ON c.id_tipo_plan = t.id_tipo_plan
        WHERE p.id_cliente = ?
        ORDER BY p.fecha_pago DESC
    """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idCliente);
            ResultSet rs = ps.executeQuery();

            javafx.collections.ObservableList<PagoModuloCliente> listaPagos =
                    javafx.collections.FXCollections.observableArrayList();

            while (rs.next()) {
                PagoModuloCliente pago = new PagoModuloCliente(
                        rs.getString("fecha_pago"),
                        rs.getString("plan"),
                        rs.getDouble("monto"),
                        rs.getString("estado")
                );
                listaPagos.add(pago);
            }

            tablaPagos.setItems(listaPagos);
            System.out.println("✅ Historial de pagos cargado: " + listaPagos.size() + " registros");

        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta("Error al cargar el historial de pagos");
        }
    }


      //Configura el ComboBox de métodos de pago

    private void configurarComboMetodoPago() {
        javafx.collections.ObservableList<String> metodos =
                javafx.collections.FXCollections.observableArrayList("Efectivo", "Tarjeta");
        comboMetodoPago.setItems(metodos);

        // Listener para cuando seleccionen un método
        comboMetodoPago.setOnAction(e -> {
            metodoPagoSeleccionado = (String) comboMetodoPago.getValue();
            System.out.println("✅ Método de pago seleccionado: " + metodoPagoSeleccionado);
        });
    }

    @FXML
    void seleccionarBasico(ActionEvent event) {
        planSeleccionado = 1;
        montoPlanSeleccionado = 60000.0; // Precio del plan básico (ajustar según tu BD)

        // Resaltar visualmente el plan seleccionado
        boxBasico.setStyle("-fx-border-color: #0a1929; -fx-border-width: 3px;");
        boxPremium.setStyle("-fx-border-color: transparent;");

        System.out.println("✅ Plan Básico seleccionado - Monto: $" + montoPlanSeleccionado);

        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Plan Seleccionado");
        alerta.setHeaderText(null);
        alerta.setContentText("✅ Has seleccionado el Plan Básico\nMonto: $60.000/mes");
        alerta.showAndWait();
    }

    @FXML
    void seleccionarPremium(ActionEvent event) {
        planSeleccionado = 2;
        montoPlanSeleccionado = 90000.0; // Precio del plan premium (ajustar según tu BD)

        // Resaltar visualmente el plan seleccionado
        boxPremium.setStyle("-fx-border-color: #0a1929; -fx-border-width: 3px;");
        boxBasico.setStyle("-fx-border-color: transparent;");

        System.out.println("✅ Plan Premium seleccionado - Monto: $" + montoPlanSeleccionado);

        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Plan Seleccionado");
        alerta.setHeaderText(null);
        alerta.setContentText("✅ Has seleccionado el Plan Premium\nMonto: $90.000/mes");
        alerta.showAndWait();
    }

    @FXML
    void pagarAhoraPlan(ActionEvent event) {
        // Validación 1: Verificar que se haya seleccionado un plan
        if (planSeleccionado == 0) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Advertencia");
            alerta.setHeaderText(null);
            alerta.setContentText("⚠️ Por favor, selecciona un plan antes de continuar");
            alerta.showAndWait();
            return;
        }

        // Obtener los datos de los campos
        String direccion = txtDireccion.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String metodoPago = (String) comboMetodoPago.getValue();

        // Validación 2: Verificar que los campos obligatorios no estén vacíos
        if (direccion.isEmpty()) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Advertencia");
            alerta.setHeaderText(null);
            alerta.setContentText("⚠️ Por favor, ingresa tu dirección");
            alerta.showAndWait();
            return;
        }

        if (telefono.isEmpty()) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Advertencia");
            alerta.setHeaderText(null);
            alerta.setContentText("⚠️ Por favor, ingresa tu teléfono");
            alerta.showAndWait();
            return;
        }

        // Validación 3: Verificar que se haya seleccionado un método de pago
        if (metodoPago == null || metodoPago.isEmpty()) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Advertencia");
            alerta.setHeaderText(null);
            alerta.setContentText("⚠️ Por favor, selecciona un método de pago");
            alerta.showAndWait();
            return;
        }


    }


    //--------------------------------METODOS DEL MODULO DE CONFIGURACIÓN DEL CLIENTE------------------------------------

     //Carga todos los datos del cliente en el panel de configuración

    private void cargarDatosConfiguracion() {
        String query = """
        SELECT u.nombre, u.apellido, u.cedula, u.correo, u.contraseña, 
               r.descripcion AS rol, e.descripcion AS estado, 
               c.telefono, c.direccion, c.fecha_matricula 
        FROM Usuario u 
        JOIN Rol r ON u.id_rol = r.id_rol 
        JOIN Estado e ON u.id_estado = e.id_estado 
        LEFT JOIN Cliente c ON c.id_cliente = u.id_usuario 
        WHERE u.id_usuario = ?
    """;

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, idCliente);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                // Cargar nombre completo en el label
                String nombreCompleto = rs.getString("nombre") + " " + rs.getString("apellido");
                lblNombreCliente.setText(nombreCompleto);

                // Cargar fecha de creación (fecha de matrícula)
                java.sql.Date fechaMatricula = rs.getDate("fecha_matricula");
                if (fechaMatricula != null) {
                    lblFechaCreacion.setText("Fecha de creación: " + fechaMatricula.toString());
                } else {
                    lblFechaCreacion.setText("Fecha de creación: N/A");
                }

                // Cargar rol y estado (ya están cargados en initialize, pero por si acaso)
                lblRol.setText("Rol: " + rs.getString("rol"));
                lblEstado.setText("Estado: " + rs.getString("estado"));

                // Cargar datos personales en los campos de texto
                txtCedula.setText(rs.getString("cedula"));
                txtCorreo.setText(rs.getString("correo"));

                String telefono = rs.getString("telefono");
                txtTelefono.setText(telefono != null ? telefono : "");

                String direccion = rs.getString("direccion");
                txtDireccion.setText(direccion != null ? direccion : "");

                // Guardar contraseña actual para validación posterior
                contrasenaActualBD = rs.getString("contraseña");

                System.out.println("✅ Datos de configuración cargados correctamente");
            } else {
                System.out.println("❌ No se encontraron datos para el cliente ID: " + idCliente);
            }

        } catch (SQLException e) {
            mostrarAlerta("Error al cargar los datos del cliente");
            e.printStackTrace();
        }
    }


      //Configura el campo de cédula para que no sea editable

    private void configurarCamposCedula() {
        txtCedula.setEditable(false);
        txtCedula.setStyle("-fx-background-color: #f0f0f0; -fx-opacity: 0.7;");
    }

    @FXML
    void guardarCambios(ActionEvent event) {
        // Validar que el correo no esté vacío
        if (txtCorreo.getText().trim().isEmpty()) {
            mostrarAlerta("El correo es obligatorio");
            return;
        }

        // Actualizar datos en la tabla Usuario
        String queryUsuario = "UPDATE Usuario SET correo = ? WHERE id_usuario = ?";

        // Actualizar datos en la tabla Cliente
        String queryCliente = "UPDATE Cliente SET telefono = ?, direccion = ? WHERE id_cliente = ?";

        try {
            // Iniciar transacción manual
            conn.setAutoCommit(false);

            try (PreparedStatement pstmtUsuario = conn.prepareStatement(queryUsuario);
                 PreparedStatement pstmtCliente = conn.prepareStatement(queryCliente)) {

                // Actualizar Usuario
                pstmtUsuario.setString(1, txtCorreo.getText().trim());
                pstmtUsuario.setInt(2, idCliente);
                int filasUsuario = pstmtUsuario.executeUpdate();

                // Actualizar Cliente
                pstmtCliente.setString(1, txtTelefono.getText().trim());
                pstmtCliente.setString(2, txtDireccion.getText().trim());
                pstmtCliente.setInt(3, idCliente);
                int filasCliente = pstmtCliente.executeUpdate();

                // Confirmar transacción
                conn.commit();

                // Mostrar mensaje de éxito
                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle("Éxito");
                alerta.setHeaderText(null);
                alerta.setContentText("✅ Los datos se han actualizado correctamente");
                alerta.showAndWait();

                System.out.println("✅ Datos actualizados - Usuario: " + filasUsuario + " fila(s), Cliente: " + filasCliente + " fila(s)");

                // Recargar datos
                cargarDatosConfiguracion();

            } catch (SQLException e) {
                // Revertir cambios en caso de error
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }

        } catch (SQLException e) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText(null);
            alerta.setContentText("❌ No se pudieron guardar los cambios: " + e.getMessage());
            alerta.showAndWait();
            e.printStackTrace();
        }
    }

    @FXML
    void cambiarContraseña(ActionEvent event) {
        String contrasenaActual = txtPassActual.getText();
        String contrasenaNueva = txtPassNueva.getText();
        String confirmarContrasena = txtPassConfirm.getText();

        // Validación 1: Campos vacíos
        if (contrasenaActual.isEmpty() || contrasenaNueva.isEmpty() || confirmarContrasena.isEmpty()) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Advertencia");
            alerta.setHeaderText(null);
            alerta.setContentText("⚠️ Todos los campos de contraseña son obligatorios");
            alerta.showAndWait();
            return;
        }

        // Validación 2: Verificar que la contraseña actual sea correcta
        if (!contrasenaActual.equals(contrasenaActualBD)) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText(null);
            alerta.setContentText("❌ La contraseña actual es incorrecta");
            alerta.showAndWait();
            return;
        }

        // Validación 3: Verificar que las contraseñas nuevas coincidan
        if (!contrasenaNueva.equals(confirmarContrasena)) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText(null);
            alerta.setContentText("❌ Las contraseñas nuevas no coinciden");
            alerta.showAndWait();
            return;
        }

        // Validación 4: Verificar que la nueva contraseña sea diferente a la actual
        if (contrasenaActual.equals(contrasenaNueva)) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Advertencia");
            alerta.setHeaderText(null);
            alerta.setContentText("⚠️ La nueva contraseña debe ser diferente a la actual");
            alerta.showAndWait();
            return;
        }

        // Actualizar contraseña en la base de datos
        String query = "UPDATE Usuario SET contraseña = ? WHERE id_usuario = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, contrasenaNueva);
            pstmt.setInt(2, idCliente);

            int filasActualizadas = pstmt.executeUpdate();

            if (filasActualizadas > 0) {
                // Actualizar contraseña en memoria
                contrasenaActualBD = contrasenaNueva;

                // Mostrar mensaje de éxito
                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle("Éxito");
                alerta.setHeaderText(null);
                alerta.setContentText("✅ La contraseña se ha cambiado correctamente");
                alerta.showAndWait();

                // Limpiar campos de contraseña
                txtPassActual.clear();
                txtPassNueva.clear();
                txtPassConfirm.clear();

                System.out.println("✅ Contraseña actualizada correctamente");
            } else {
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setTitle("Error");
                alerta.setHeaderText(null);
                alerta.setContentText("❌ No se pudo cambiar la contraseña");
                alerta.showAndWait();
            }

        } catch (SQLException e) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText(null);
            alerta.setContentText("❌ Error al cambiar la contraseña: " + e.getMessage());
            alerta.showAndWait();
            e.printStackTrace();
        }
    }

    @FXML
    void cancelar(ActionEvent event) {
        // Limpiar los campos de contraseña si hay algo escrito
        txtPassActual.clear();
        txtPassNueva.clear();
        txtPassConfirm.clear();

        // Recargar los datos originales por si el usuario modificó algo
        cargarDatosConfiguracion();

        // Activar el toggle de INICIO para volver a la pantalla principal
        if (botonInicio != null) {
            botonInicio.setSelected(true);
            hideAll();
            panelInicio.setVisible(true);
        }

        System.out.println("✅ Cambios cancelados, regresando a Inicio");
    }



    //--------------------------------------METODOS DEL MODULO DE ASISTENCIA DEL CLIENTE-----------------------------------------

    private void configurarTablaAsistencias() {
        colFecha.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getFecha()));
        colHora.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getHora()));
        colEstado.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getEstado()));
    }

    @FXML
    void botonBuscar(ActionEvent event) {
        LocalDate desde = dateDesde.getValue();
        LocalDate hasta = dateHasta.getValue();

        if (desde == null || hasta == null) {
            mostrarAlerta("Por favor selecciona ambas fechas para buscar.");
            return;
        }

        String sql = """
        SELECT fecha, hora
        FROM Asistencia
        WHERE id_cliente = ? AND fecha BETWEEN ? AND ?
        ORDER BY fecha ASC;
    """;

        ObservableList<AsistenciaModuloCliente> lista = FXCollections.observableArrayList();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idCliente);
            ps.setDate(2, Date.valueOf(desde));
            ps.setDate(3, Date.valueOf(hasta));
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(new AsistenciaModuloCliente(
                        rs.getString("fecha"),
                        rs.getString("hora"),
                        "",   // duración vacía
                        ""    // estado vacío
                ));
            }

            tablaAsistencias.setItems(lista);
            cargarResumenAsistencias();
            cargarGraficaSemanal();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void botonLimpiarAsis(ActionEvent event) {

        dateDesde.setValue(null);
        dateHasta.setValue(null);
        tablaAsistencias.getItems().clear();
        cargarResumenAsistencias();
        cargarGraficaSemanal();
    }

    private void cargarResumenAsistencias() {
        String sqlTotal = """
        SELECT COUNT(*) AS total_mes
        FROM Asistencia
        WHERE id_cliente = ? 
          AND MONTH(fecha) = MONTH(CURDATE())
          AND YEAR(fecha) = YEAR(CURDATE());
    """;

        try (PreparedStatement ps = conn.prepareStatement(sqlTotal)) {
            ps.setInt(1, idCliente);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) lblAsistenciasMes.setText(String.valueOf(rs.getInt("total_mes")));
        } catch (SQLException e) { e.printStackTrace(); }

        String sqlPromedio = """
        SELECT COUNT(*)/4 AS promedio
        FROM Asistencia
        WHERE id_cliente = ? 
          AND MONTH(fecha) = MONTH(CURDATE())
          AND YEAR(fecha) = YEAR(CURDATE());
    """;

        try (PreparedStatement ps = conn.prepareStatement(sqlPromedio)) {
            ps.setInt(1, idCliente);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) lblPromedioSemanal.setText(String.format("%.1f", rs.getDouble("promedio")));
        } catch (SQLException e) { e.printStackTrace(); }

        String sqlUltima = "SELECT MAX(fecha) AS ultima FROM Asistencia WHERE id_cliente = ?;";
        try (PreparedStatement ps = conn.prepareStatement(sqlUltima)) {
            ps.setInt(1, idCliente);
            ResultSet rs = ps.executeQuery();
            if (rs.next() && rs.getDate("ultima") != null)
                lblUltimaAsistencia.setText(rs.getString("ultima"));
            else
                lblUltimaAsistencia.setText("--");
        } catch (SQLException e) { e.printStackTrace(); }
    }


    private void cargarGraficaSemanal() {
        graficaAsistencia.getData().clear();

        String sql = """
        SELECT WEEK(fecha, 1) AS semana, COUNT(*) AS total
        FROM Asistencia
        WHERE id_cliente = ? 
          AND MONTH(fecha) = MONTH(CURDATE())
          AND YEAR(fecha) = YEAR(CURDATE())
        GROUP BY WEEK(fecha, 1);
    """;

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Asistencias");

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idCliente);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                series.getData().add(new XYChart.Data<>("Semana " + rs.getInt("semana"), rs.getInt("total")));
            }
            graficaAsistencia.getData().add(series);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //----------------------------------------------METODOS MIS RUTINAS-----------------------------------------

   /* private Cliente clienteModel = new Cliente();
    *

    // 🔹 Inicializa la vista del cliente
    public void inicializarCliente(int idCliente) {
        this.idCliente = idCliente;

        // Llenar datos del plan actual
        Map<String, String> planData = clienteModel.obtenerPlanYEntrenador(idCliente);
        lblPlanRutinas.setText(planData.getOrDefault("plan", "Sin plan asignado"));
        lblEntrenadorRutinas.setText(planData.getOrDefault("entrenador", "Entrenador no asignado"));
        lblDuracion.setText(planData.getOrDefault("duracion", "N/A"));

        // Cargar rutinas en el ComboBox
        List<String> rutinas = clienteModel.obtenerRutinas(idCliente);
        ComboRutina.getItems().setAll(rutinas);

        // Configurar columnas de la tabla
        colEjercicio.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty((String) cellData.getValue().get("ejercicio")));
        colSerie.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleIntegerProperty((Integer) cellData.getValue().get("series")).asObject());
        colRepeticiones.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleIntegerProperty((Integer) cellData.getValue().get("repeticiones")).asObject());
        colPesoRecomendado.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty((String) cellData.getValue().get("peso_recomendado")));
        colDescanso.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty((String) cellData.getValue().get("descanso")));
    }

    // 🔹 Botón "Ver" para mostrar ejercicios
    @FXML
    private void verRutina() {
        String rutinaSeleccionada = ComboRutina.getValue();
        if (rutinaSeleccionada == null || rutinaSeleccionada.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Por favor, selecciona una rutina.").show();
            return;
        }

        List<Map<String, Object>> ejercicios = clienteModel.obtenerEjerciciosPorRutina(rutinaSeleccionada);
        tablaEjercicios.setItems(FXCollections.observableArrayList(ejercicios));
    }
*/
    //----------------------------------------------METODOS DEL MODULO DE INICIO DEL CLIENTE-----------------------------------------
        //Metodo para que al cliente le salga su tipo de plan en el inicio
    private void cargarPlan() {
        String sql = "SELECT t.nombre FROM Cliente c JOIN TipoPlan t ON c.id_tipo_plan = t.id_tipo_plan WHERE c.id_cliente = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idCliente);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                lblPlanActivo.setText(rs.getString("nombre"));
                System.out.println("✅ Plan encontrado: " + rs.getString("nombre"));
            } else {
                lblPlanActivo.setText("Sin plan");
                System.out.println("❌ No se encontró plan para el cliente con ID: " + idCliente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

        //Metodo para que al cliente le salga su proxima sesion en el inicio
    private void cargarSesion() {
        String sql = """
            SELECT fecha, hora
            FROM SesionEntrenador
            WHERE id_cliente = ? AND estado = 'reservada'
            ORDER BY fecha ASC LIMIT 1
        """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idCliente);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                lblSesion.setText(rs.getDate("fecha") + " " + rs.getTime("hora"));
            else
                lblSesion.setText("No programada");
        } catch (SQLException e) { e.printStackTrace(); }
    }

        //Metodo para que al cliente le salga si tiene sus pagos al dia en el inicio
    private void cargarPagos() {
        String sql = """
            SELECT CASE WHEN SUM(CASE WHEN estado='pendiente' THEN 1 ELSE 0 END) > 0 THEN 'No' ELSE 'Sí' END AS al_dia
            FROM Pago WHERE id_cliente = ?
        """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idCliente);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) lblPagos.setText(rs.getString("al_dia"));
        } catch (SQLException e) { e.printStackTrace(); }
    }

        //Metodo para que al cliente le salga el nombre de su entrenador en el inicio
    private void cargarEntrenador() {
        String sql = """
                    SELECT u.nombre AS entrenador
                    FROM PlanEntrenamiento p
                    JOIN Usuario u ON p.id_entrenador = u.id_usuario
                    WHERE p.id_cliente = ? LIMIT 1
                """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idCliente);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String nombre = rs.getString("entrenador");
                lblEntrenadorAsignada.setText(nombre);
                System.out.println("✅ Entrenador encontrado: " + nombre);
            } else {
                lblEntrenadorAsignada.setText("No asignado");
                System.out.println("❌ Sin entrenador asignado para cliente ID: " + idCliente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
            //Metodo para que al cliente le salga sus asistencias en el mes y totales en el inicio
    private void cargarAsistencias() {
        // Asistencias del mes
        String sqlMes = "SELECT COUNT(*) FROM Asistencia WHERE id_cliente = ? AND MONTH(fecha) = MONTH(CURDATE()) AND YEAR(fecha) = YEAR(CURDATE())";
        // Totales
        String sqlTot = "SELECT COUNT(*) FROM Asistencia WHERE id_cliente = ?";
        try {
            PreparedStatement psMes = conn.prepareStatement(sqlMes);
            psMes.setInt(1, idCliente);
            ResultSet rsMes = psMes.executeQuery();
            if (rsMes.next()) lblAsistenciaMesInicio.setText(rsMes.getString(1));

            PreparedStatement psTot = conn.prepareStatement(sqlTot);
            psTot.setInt(1, idCliente);
            ResultSet rsTot = psTot.executeQuery();
            if (rsTot.next()) lblAsistenciaTotalInicio.setText(rsTot.getString(1));
        } catch (SQLException e) { e.printStackTrace(); }
    }

    //Metodo para que al cliente le salga la grafica de sus asistencias por semana desde el inicio
    private void cargarGrafico() {
        String sql = """
            SELECT WEEK(fecha) AS semana, COUNT(*) AS total
            FROM Asistencia
            WHERE id_cliente = ?
            GROUP BY WEEK(fecha)
            ORDER BY semana
        """;
        XYChart.Series<String, Number> serie = new XYChart.Series<>();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idCliente);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
                serie.getData().add(new XYChart.Data<>("Semana " + rs.getInt("semana"), rs.getInt("total")));
            graficoAsistencias.getData().clear();
            graficoAsistencias.getData().add(serie);
        } catch (SQLException e) { e.printStackTrace(); }
    }

    //-----------------------------------------Barra lateral inicio del cliente----------------------------------------------
    @FXML
    void mostrarAsistencia(ActionEvent event) {
        hideAll();
        panelAsistencias.setVisible(true);
    }

    @FXML
    void mostrarConfiguracion(ActionEvent event) {
        hideAll();
        panelConfiguracion.setVisible(true);
    }

    @FXML
    void mostrarInicio(ActionEvent event) {
        hideAll();
        panelInicio.setVisible(true);
    }

    @FXML
    void mostrarPago(ActionEvent event) {
        hideAll();
        panelPagos.setVisible(true);
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
            // Si cancela, regresa el toggle a su estado original
            botonCerrarSesion.setSelected(false);
        }
    }

    private void hideAll() {
        panelInicio.setVisible(false);
        panelRutinas.setVisible(false);
        panelAsistencias.setVisible(false);
        panelPagos.setVisible(false);
        panelConfiguracion.setVisible(false);
    }


    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}




