package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

public class Entrenador {
    private Connection conn;


    public Entrenador() {
        conn = ConexionDatabase.getConnection();
    }

    public static class ClienteAsignado {
        private String nombreCompleto;
        private String plan;
        private String tipoPlan;
        private String estado;
        private String cedula;
        private String telefono;
        private String correo;

        public ClienteAsignado(String cedula, String nombreCompleto, String telefono, String correo,
                               String plan, String tipoPlan, String estado) {
            this.cedula = cedula;
            this.nombreCompleto = nombreCompleto;
            this.telefono = telefono;
            this.correo = correo;
            this.plan = plan;
            this.tipoPlan = tipoPlan;
            this.estado = estado;
        }

        public String getCedula() { return cedula; }
        public String getNombreCompleto() { return nombreCompleto; }
        public String getTelefono() { return telefono; }
        public String getCorreo() { return correo; }
        public String getPlan() { return plan; }
        public String getTipoPlan() { return tipoPlan; }
        public String getEstado() { return estado; }
    }


    public static class ResumenEntrenador {
        private int totalClientes;
        private int totalRutinas;
        private int totalSesiones;

        public ResumenEntrenador(int totalClientes, int totalRutinas, int totalSesiones) {
            this.totalClientes = totalClientes;
            this.totalRutinas = totalRutinas;
            this.totalSesiones = totalSesiones;
        }

        public int getTotalClientes() { return totalClientes; }
        public int getTotalRutinas() { return totalRutinas; }
        public int getTotalSesiones() { return totalSesiones; }
    }

    // Metodo cargar resumen
    public ResumenEntrenador obtenerResumen(int idEntrenador) {
        String sql = """
            SELECT COUNT(DISTINCT p.id_cliente) AS total_clientes,
                   COUNT(DISTINCT p.id_plan) AS total_rutinas,
                   COUNT(DISTINCT s.id_sesion) AS total_sesiones
            FROM PlanEntrenamiento p
            LEFT JOIN SesionEntrenador s ON p.id_entrenador = s.id_entrenador
            WHERE p.id_entrenador = ?;
        """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idEntrenador);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new ResumenEntrenador(
                        rs.getInt("total_clientes"),
                        rs.getInt("total_rutinas"),
                        rs.getInt("total_sesiones")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ResumenEntrenador(0, 0, 0);
    }

    // Metodo cargar clientes asignados
    public ObservableList<ClienteAsignado> obtenerClientesAsignados(int idEntrenador) {
        ObservableList<ClienteAsignado> lista = FXCollections.observableArrayList();
        String sql = """
        SELECT u.cedula, 
               CONCAT(u.nombre, ' ', u.apellido) AS nombre_completo,
               c.telefono, 
               u.correo,
               p.nombre AS plan, 
               t.nombre AS tipo_plan,
               e.descripcion AS estado
        FROM PlanEntrenamiento p
        JOIN Cliente c ON p.id_cliente = c.id_cliente
        JOIN Usuario u ON c.id_cliente = u.id_usuario
        JOIN TipoPlan t ON c.id_tipo_plan = t.id_tipo_plan
        JOIN Estado e ON u.id_estado = e.id_estado
        WHERE p.id_entrenador = ?;
    """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idEntrenador);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(new ClienteAsignado(
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

    //--------------------- METODOS MIS CLIENTES ----------------------------


     //Busca los clientes asignados al entrenador filtrando por nombre o cédula.

    public ObservableList<ClienteAsignado> buscarClientes(int idEntrenador, String filtro) {
        ObservableList<ClienteAsignado> lista = FXCollections.observableArrayList();

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
          AND (u.nombre LIKE ? OR u.apellido LIKE ? OR u.cedula LIKE ?);
    """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idEntrenador);
            String parametroBusqueda = "%" + filtro + "%";
            ps.setString(2, parametroBusqueda);
            ps.setString(3, parametroBusqueda);
            ps.setString(4, parametroBusqueda);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(new ClienteAsignado(
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

    public String obtenerRutinaPorCedula(String cedula) {
        String rutina = "Sin rutina asignada";

        String sql = """
            SELECT r.nombre 
            FROM Rutina r
            JOIN PlanEntrenamiento p ON r.id_plan = p.id_plan
            JOIN Cliente c ON p.id_cliente = c.id_cliente
            JOIN Usuario u ON u.id_usuario = c.id_cliente
            WHERE u.cedula = ? LIMIT 1;
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cedula);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                rutina = rs.getString("nombre");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rutina;
    }

    //--------------------- METODOS Rutinas ----------------------------

    public static class PlanEntrenamiento {
        private int idPlan;
        private String nombre;
        private String descripcion;
        private String cliente;
        private int idCliente;

        public PlanEntrenamiento(int idPlan, String nombre, String descripcion, String cliente, int idCliente) {
            this.idPlan = idPlan;
            this.nombre = nombre;
            this.descripcion = descripcion;
            this.cliente = cliente;
            this.idCliente = idCliente;
        }

        // Getters
        public int getIdPlan() { return idPlan; }
        public String getNombre() { return nombre; }
        public String getDescripcion() { return descripcion; }
        public String getCliente() { return cliente; }
        public int getIdCliente() { return idCliente; }

        // Setters
        public void setIdPlan(int idPlan) { this.idPlan = idPlan; }
        public void setNombre(String nombre) { this.nombre = nombre; }
        public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
        public void setCliente(String cliente) { this.cliente = cliente; }
        public void setIdCliente(int idCliente) { this.idCliente = idCliente; }
    }

    // Clase cliente simple (para ComboBox)
    public static class ClienteSimple {
        private int idCliente;
        private String nombreCompleto;

        public ClienteSimple(int idCliente, String nombreCompleto) {
            this.idCliente = idCliente;
            this.nombreCompleto = nombreCompleto;
        }

        public int getIdCliente() { return idCliente; }
        public String getNombreCompleto() { return nombreCompleto; }

        @Override
        public String toString() {
            return nombreCompleto; // Para que el ComboBox muestre el nombre
        }
    }

     //Obtiene los clientes asignados al entrenador para el ComboBox

    public ObservableList<ClienteSimple> obtenerClientesParaCombo(int idEntrenador) {
        ObservableList<ClienteSimple> lista = FXCollections.observableArrayList();

        String sql = """
        SELECT DISTINCT c.id_cliente, CONCAT(u.nombre, ' ', u.apellido) AS nombre_completo
        FROM Cliente c
        JOIN Usuario u ON c.id_cliente = u.id_usuario
        JOIN PlanEntrenamiento p ON c.id_cliente = p.id_cliente
        WHERE p.id_entrenador = ?
        ORDER BY nombre_completo;
    """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idEntrenador);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(new ClienteSimple(
                        rs.getInt("id_cliente"),
                        rs.getString("nombre_completo")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }


      //Registra un nuevo plan de entrenamiento

    public boolean registrarPlan(String nombre, String descripcion, int idCliente, int idEntrenador) {
        String sql = """
        INSERT INTO PlanEntrenamiento (nombre, descripcion, id_cliente, id_entrenador)
        VALUES (?, ?, ?, ?);
    """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setString(2, descripcion);
            ps.setInt(3, idCliente);
            ps.setInt(4, idEntrenador);

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


     //Obtiene todos los planes de entrenamiento del entrenador

    public ObservableList<PlanEntrenamiento> obtenerPlanesEntrenamiento(int idEntrenador) {
        ObservableList<PlanEntrenamiento> lista = FXCollections.observableArrayList();

        String sql = """
        SELECT p.id_plan, p.nombre, p.descripcion, p.id_cliente,
               CONCAT(u.nombre, ' ', u.apellido) AS cliente
        FROM PlanEntrenamiento p
        JOIN Cliente c ON p.id_cliente = c.id_cliente
        JOIN Usuario u ON c.id_cliente = u.id_usuario
        WHERE p.id_entrenador = ?
        ORDER BY p.id_plan DESC;
    """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idEntrenador);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(new PlanEntrenamiento(
                        rs.getInt("id_plan"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getString("cliente"),
                        rs.getInt("id_cliente")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }


     //Actualiza un plan de entrenamiento existente

    public boolean actualizarPlan(int idPlan, String nombre, String descripcion) {
        String sql = """
        UPDATE PlanEntrenamiento
        SET nombre = ?, descripcion = ?
        WHERE id_plan = ?;
    """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setString(2, descripcion);
            ps.setInt(3, idPlan);

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


     //Elimina un plan de entrenamiento

    public boolean eliminarPlan(int idPlan) {
        String sql = """
        DELETE FROM PlanEntrenamiento
        WHERE id_plan = ?;
    """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPlan);

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


}
