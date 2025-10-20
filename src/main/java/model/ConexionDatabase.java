package model;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class ConexionDatabase {
    private static Connection conn = null;

    public static Connection getConnection() {
        if (conn != null) return conn;

        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream("config.properties")) {
            props.load(fis);

            String url = props.getProperty("URL");
            String user = props.getProperty("USER");
            String password = props.getProperty("PASSWORD");

            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Conexión exitosa a la base de datos.");

        } catch (IOException e) {
            System.out.println("⚠ Error al leer el archivo config.properties: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println(" Error de conexión a la base de datos: " + e.getMessage());
        }

        return conn;
    }
}


