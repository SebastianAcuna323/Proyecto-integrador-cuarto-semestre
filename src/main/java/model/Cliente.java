package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cliente extends Usuario {

    private Date fecha_nacimiento;
    private String telefono;
    private String direccion;
    private int id_tipo_plan;
    private Date fecha_matricula;

    public Cliente() {
        super();
    }

    public Cliente(int id_usuario, String nombre, String apellido, String correo,
                   String contrasena, String cedula, int id_rol, int id_estado,
                   Date fecha_nacimiento, String telefono, String direccion,
                   int id_tipo_plan, Date fecha_matricula) {
        // super(id_usuario, nombre, apellido, correo, contrasena, cedula, id_rol, id_estado);
        this.fecha_nacimiento = fecha_nacimiento;
        this.telefono = telefono;
        this.direccion = direccion;
        this.id_tipo_plan = id_tipo_plan;
        this.fecha_matricula = fecha_matricula;
    }

    // Getters y setters
    public Date getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(Date fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getId_tipo_plan() {
        return id_tipo_plan;
    }

    public void setId_tipo_plan(int id_tipo_plan) {
        this.id_tipo_plan = id_tipo_plan;
    }

    public Date getFecha_matricula() {
        return fecha_matricula;
    }

    public void setFecha_matricula(Date fecha_matricula) {
        this.fecha_matricula = fecha_matricula;
    }


    //----------------------------------------Metodos mis rutinas-------------------------------------------
    public class ClienteRutinas {
        private Connection conn;

        public ClienteRutinas() {
            conn = ConexionDatabase.getConnection();
        }

        // ---- CLASE INTERNA: PLAN DEL CLIENTE ----
        public static class PlanCliente {
            private int idPlan;
            private String nombrePlan;

            public PlanCliente(int idPlan, String nombrePlan) {
                this.idPlan = idPlan;
                this.nombrePlan = nombrePlan;
            }

            public int getIdPlan() {
                return idPlan;
            }

            public String getNombrePlan() {
                return nombrePlan;
            }

            @Override
            public String toString() {
                return nombrePlan; // Para que el ComboBox muestre el nombre
            }
        }

        // ---- CLASE INTERNA: EJERCICIO ----
        public static class Ejercicio {
            private String nombreEjercicio;
            private int series;
            private String repeticiones;
            private String pesoRecomendado;
            private String descanso;

            public Ejercicio(String nombreEjercicio, int series, String repeticiones,
                             String pesoRecomendado, String descanso) {
                this.nombreEjercicio = nombreEjercicio;
                this.series = series;
                this.repeticiones = repeticiones;
                this.pesoRecomendado = pesoRecomendado;
                this.descanso = descanso;
            }

            // Getters
            public String getNombreEjercicio() {
                return nombreEjercicio;
            }

            public int getSeries() {
                return series;
            }

            public String getRepeticiones() {
                return repeticiones;
            }

            public String getPesoRecomendado() {
                return pesoRecomendado;
            }

            public String getDescanso() {
                return descanso;
            }

            // Setters
            public void setNombreEjercicio(String nombreEjercicio) {
                this.nombreEjercicio = nombreEjercicio;
            }

            public void setSeries(int series) {
                this.series = series;
            }

            public void setRepeticiones(String repeticiones) {
                this.repeticiones = repeticiones;
            }

            public void setPesoRecomendado(String pesoRecomendado) {
                this.pesoRecomendado = pesoRecomendado;
            }

            public void setDescanso(String descanso) {
                this.descanso = descanso;
            }
        }

        public static class InfoRutina {
            private String planActual;
            private String entrenadorAsignado;
            private String duracion;

            public InfoRutina(String planActual, String entrenadorAsignado, String duracion) {
                this.planActual = planActual;
                this.entrenadorAsignado = entrenadorAsignado;
                this.duracion = duracion;
            }

            public String getPlanActual() {
                return planActual;
            }

            public String getEntrenadorAsignado() {
                return entrenadorAsignado;
            }

            public String getDuracion() {
                return duracion;
            }
        }

        //--------------------- MÉTODOS RUTINAS DEL CLIENTE ----------------------------

        public Map<String, String> obtenerPlanYEntrenador(int idCliente) {
            Map<String, String> datos = new HashMap<>();
            String sql = "SELECT p.nombre AS plan, p.duracion, CONCAT(u.nombre, ' ', u.apellido) AS entrenador " +
                    "FROM planentrenamiento p " +
                    "JOIN usuario u ON p.id_entrenador = u.id_usuario " +
                    "WHERE p.id_cliente = ?";

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, idCliente);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    datos.put("plan", rs.getString("plan"));
                    datos.put("duracion", rs.getString("duracion"));
                    datos.put("entrenador", rs.getString("entrenador"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return datos;
        }

        public List<String> obtenerRutinas(int idCliente) {
            List<String> rutinas = new ArrayList<>();
            String sql = "SELECT nombre FROM planentrenamiento WHERE id_cliente = ?";

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, idCliente);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    rutinas.add(rs.getString("nombre"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return rutinas;
        }

        public List<Map<String, Object>> obtenerEjerciciosPorRutina(String nombreRutina) {
            List<Map<String, Object>> lista = new ArrayList<>();
            String sql = "SELECT e.ejercicio, e.series, e.repeticiones, e.peso_recomendado, e.descanso " +
                    "FROM ejercicioplan e " +
                    "JOIN planentrenamiento p ON e.id_plan = p.id_plan " +
                    "WHERE p.nombre = ?";

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, nombreRutina);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    Map<String, Object> ejercicio = new HashMap<>();
                    ejercicio.put("ejercicio", rs.getString("ejercicio"));
                    ejercicio.put("series", rs.getInt("series"));
                    ejercicio.put("repeticiones", rs.getInt("repeticiones"));
                    ejercicio.put("peso_recomendado", rs.getString("peso_recomendado"));
                    ejercicio.put("descanso", rs.getString("descanso"));
                    lista.add(ejercicio);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return lista;
        }
    }
}