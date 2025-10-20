package model;

//Atributos del usuario

public class Usuario {
    private String nombre;
    private String apellido;
    private String correo;
    private String contrasena;
    private String cedula;
    private int idRol;
    private int idEstado;

    //Constructor
    public Usuario(String nombre, String apellido, String correo, String contrasena, String cedula, int idRol, int idEstado) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.contrasena = contrasena;
        this.cedula = cedula;
        this.idRol = 3; //Usuario será cliente por defecto
        this.idEstado = 1; //Usuario estará activo por defecto
    }
}
