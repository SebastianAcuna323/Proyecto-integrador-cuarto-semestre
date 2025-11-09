package model;

public class PagoModuloCliente {
    private String fecha;
    private String plan;
    private double monto;
    private String estado;

    public PagoModuloCliente(String fecha, String plan, double monto, String estado) {
        this.fecha = fecha;
        this.plan = plan;
        this.monto = monto;
        this.estado = estado;
    }


    // GETTERS Y SETTERS

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "PagoHistorial{" +
                "fecha='" + fecha + '\'' +
                ", plan='" + plan + '\'' +
                ", monto=" + monto +
                ", estado='" + estado + '\'' +
                '}';
    }
}
