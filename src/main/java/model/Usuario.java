package model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//Atributos del usuario

public class Usuario {
    private String nombre;
    private String apellido;
    private String correo;
    private String contrasena;
    private String cedula;
    private int idRol;
    private int idEstado;


    // Constructor con datos del registro
    public Usuario(String nombre, String apellido, String correo, String contrasena, String cedula) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.contrasena = contrasena;
        this.cedula = cedula;
        this.idRol = 3;    // Rol por defecto: cliente
        this.idEstado = 1; // Estado por defecto: activo
    }

    // Metodo para registrar un nuevo usuario
    public String registrar() {
        String sql = "INSERT INTO usuario (nombre, apellido, correo, contraseña, cedula, id_rol, id_estado) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            Connection conn = ConexionDatabase.getConnection();

            // Verificar correo y cédula antes de registrar
            if (correoExiste(this.correo)) {
                return "⚠️ El correo ya está registrado.";
            }

            if (cedulaExiste(this.cedula)) {
                return "⚠️ La cédula ya está registrada.";
            }

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, this.nombre);
            stmt.setString(2, this.apellido);
            stmt.setString(3, this.correo);
            stmt.setString(4, this.contrasena);
            stmt.setString(5, this.cedula);
            stmt.setInt(6, this.idRol);
            stmt.setInt(7, this.idEstado);

            stmt.executeUpdate();
            stmt.close();

            return "✅ Usuario registrado correctamente.";

        } catch (SQLException e) {
            return "❌ Error al registrar usuario: " + e.getMessage();
        }
    }

    // Verifica si un correo ya existe en la BD
    private boolean correoExiste(String correo) {
        String sql = "SELECT COUNT(*) FROM usuario WHERE correo = ?";
        try {
            Connection conn = ConexionDatabase.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, correo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                rs.close();
                stmt.close();
                return true;
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Verifica si una cédula ya existe en la BD
    private boolean cedulaExiste(String cedula) {
        String sql = "SELECT COUNT(*) FROM usuario WHERE cedula = ?";
        try {
            Connection conn = ConexionDatabase.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, cedula);
            ResultSet rs = stmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                rs.close();
                stmt.close();
                return true;
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
