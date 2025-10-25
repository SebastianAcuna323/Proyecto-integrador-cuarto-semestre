package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToggleNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.ConexionDatabase;
import utils.paths;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class ClienteController {
    @FXML
    private JFXToggleNode botonAsistencia;

    @FXML
    private JFXToggleNode botonCerrarSesion;

    @FXML
    private JFXToggleNode botonConfiguracion;

    @FXML
    private JFXToggleNode botonInicio;

    @FXML
    private JFXButton botonPagarAhora;

    @FXML
    private JFXToggleNode botonPago;

    @FXML
    private JFXToggleNode botonRutina;

    @FXML
    private CategoryAxis ejeX;

    @FXML
    private NumberAxis ejeY;

    @FXML
    private BarChart<String, Number> graficoAsistencias;

    @FXML
    private Label lblAsistenciaMes;

    @FXML
    private Label lblAsistenciaTotal;

    @FXML
    private Label lblBienvenida;

    @FXML
    private Label lblEntrenador;

    @FXML
    private Label lblPagos;

    @FXML
    private Label lblPlan;

    @FXML
    private Label lblSesion;

    @FXML
    private ToggleGroup menuDash;

    @FXML
    private ToggleGroup menuDash1;

    @FXML
    private ToggleGroup menuDash11;

    @FXML
    private ToggleGroup menuDash111;

    @FXML
    private AnchorPane panelDashboard;

    @FXML
    private AnchorPane panelPlanes;

    @FXML
    private AnchorPane panelUsuarios;

    @FXML
    void mostrarAsistencia(ActionEvent event) {

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

    @FXML
    void mostrarConfiguracion(ActionEvent event) {

    }

    @FXML
    void mostrarInicio(ActionEvent event) {

    }

    @FXML
    void mostrarPago(ActionEvent event) {

    }

    @FXML
    void mostrarRutina(ActionEvent event) {

    }

    @FXML
    void pagarAhora(ActionEvent event) {

    }

}


