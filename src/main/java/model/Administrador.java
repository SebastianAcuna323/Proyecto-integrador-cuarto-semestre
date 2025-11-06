package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Administrador extends Usuario {

    public Administrador() {}

    public Administrador(String cedula, String nombre, String apellido, String correo, String contrasena, String nombreRol, String nombreEstado) {
        super(cedula, nombre, apellido, correo, contrasena, nombreRol, nombreEstado);
    }

    // Eliminar usuario
    public static boolean eliminarUsuario(String cedula) {
        Connection conn = ConexionDatabase.getConnection();
        try {
            String query = "DELETE FROM usuario WHERE cedula = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, cedula);
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

