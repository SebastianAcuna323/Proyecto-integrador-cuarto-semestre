package model;

import java.sql.Date;

public class Cliente extends Usuario{

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
    public Date getFecha_nacimiento() { return fecha_nacimiento; }
    public void setFecha_nacimiento(Date fecha_nacimiento) { this.fecha_nacimiento = fecha_nacimiento; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public int getId_tipo_plan() { return id_tipo_plan; }
    public void setId_tipo_plan(int id_tipo_plan) { this.id_tipo_plan = id_tipo_plan; }
    public Date getFecha_matricula() { return fecha_matricula; }
    public void setFecha_matricula(Date fecha_matricula) { this.fecha_matricula = fecha_matricula; }
    
}
