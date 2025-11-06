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
import model.AsistenciaModuloCliente;
import model.ConexionDatabase;
import model.UsuarioSesion;
import utils.paths;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.Optional;

public class ClienteController {

    @FXML
    private JFXComboBox<?> ComboRutina;

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
    private TableColumn<?, ?> colDescanso;

    @FXML
    private TableColumn<?, ?> colEjercicio;

    @FXML
    private TableColumn<?, ?> colMonto;

    @FXML
    private TableColumn<?, ?> colPesoRecomendado;

    @FXML
    private TableColumn<?, ?> colPlan;

    @FXML
    private TableColumn<?, ?> colRepeticiones;

    @FXML
    private TableColumn<?, ?> colSerie;

    @FXML
    private ComboBox<?> comboMetodoPago;

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
    private TableView<?> tablaEjercicios;

    @FXML
    private TableView<?> tablaPagos;

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
    void cambiarContraseña(ActionEvent event) {

    }

    @FXML
    void cancelar(ActionEvent event) {

    }

    @FXML
    void guardarCambios(ActionEvent event) {

    }

    @FXML
    void pagarAhoraPlan(ActionEvent event) {

    }

    @FXML
    void seleccionarBasico(ActionEvent event) {

    }

    @FXML
    void seleccionarPremium(ActionEvent event) {

    }

    @FXML
    void pagarAhora(ActionEvent event) {

    }

    private Connection conn;
    private int idCliente;

    //INICIALIZADOR DE METODOS
    @FXML
    public void initialize() {
        conn = ConexionDatabase.getConnection();
        idCliente = UsuarioSesion.getIdUsuario(); // el cliente que inició sesión


        cargarPlan();
        cargarEntrenador();
        cargarSesion();
        cargarAsistencias();
        cargarPagos();
        cargarGrafico();


        configurarTablaAsistencias();
        cargarResumenAsistencias();
        cargarGraficaSemanal();
    }




    //METODOS DEL MODULO DE ASISTENCIA DEL CLIENTE

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



    //METODOS DEL MODULO DE INICIO DEL CLIENTE
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

    //Barra lateral inicio del cliente
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




