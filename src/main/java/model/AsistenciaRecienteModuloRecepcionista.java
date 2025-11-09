package model;

public class AsistenciaRecienteModuloRecepcionista {

    private String cliente;
    private String hora;
    private String fecha;
    private String plan;

    public AsistenciaRecienteModuloRecepcionista(String cliente, String hora, String fecha, String plan) {
        this.cliente = cliente;
        this.hora = hora;
        this.fecha = fecha;
        this.plan = plan;
    }

    public String getCliente() { return cliente; }
    public String getHora() { return hora; }
    public String getFecha() { return fecha; }
    public String getPlan() { return plan; }
}

