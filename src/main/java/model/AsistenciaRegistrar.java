package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class AsistenciaRegistrar {
    private String cliente;
    private LocalDate fecha;
    private LocalTime hora;
    private String plan;
    private String estado;

    public AsistenciaRegistrar(String cliente, LocalDate fecha, LocalTime hora, String plan, String estado) {
        this.cliente = cliente;
        this.fecha = fecha;
        this.hora = hora;
        this.plan = plan;
        this.estado = estado;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
