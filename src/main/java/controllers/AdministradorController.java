package controllers;

import com.jfoenix.controls.JFXToggleNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;

public class AdministradorController {
    @FXML
    private JFXToggleNode botonDashboard;

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