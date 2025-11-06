package model;

public class AsistenciaModuloCliente {
    private String fecha;
    private String hora;
    private String duracion;
    private String estado;

    public AsistenciaModuloCliente(String fecha, String hora, String duracion, String estado) {
        this.fecha = fecha;
        this.hora = hora;
        this.duracion = duracion;
        this.estado = estado;
    }

    public String getFecha() { return fecha; }
    public String getHora() { return hora; }
    public String getDuracion() { return duracion; }
    public String getEstado() { return estado; }


}

