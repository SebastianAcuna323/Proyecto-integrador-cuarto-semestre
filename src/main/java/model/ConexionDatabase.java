package model;


import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;


public class ConexionDatabase {
    private static Connection conn = null;

    // Metodo principal de la conextio de la base de datos
    public static Connection getConnection() {
        try {
            if (conn == null || conn.isClosed()) {
                Properties props = new Properties();

                try (InputStream input = ConexionDatabase.class.getClassLoader().getResourceAsStream("config.properties")) {
                    if (input == null) {
                        System.out.println("No se encontró el archivo config.properties en resources/");
                        return null;
                    }

                    props.load(input);

                    String url = props.getProperty("URL");
                    String user = props.getProperty("USERNAME");
                    String password = props.getProperty("PASSWORD");

                    if (url == null || user == null || password == null) {
                        System.out.println("Faltan propiedades en config.properties (URL, USER o PASSWORD)");
                        return null;
                    }

                    conn = DriverManager.getConnection(url, user, password);
                    System.out.println("Conexión establecida con la base de datos.");
                }
            }
        } catch (Exception e) {
            System.out.println("Error al conectar con la base de datos: " + e.getMessage());
            e.printStackTrace();
        }
        return conn;
    }
}


