package mx.model;

public class DiasPago implements java.io.Serializable {

    private int id;
    private String fechaPago;
    private String localidad;

    public DiasPago() {
    }

    public DiasPago(int id) {
        this.id = id;
    }

    public DiasPago(int id, String fechaPago, String localidad) {
        this.id = id;
        this.fechaPago = fechaPago;
        this.localidad = localidad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(String fechaPago) {
        this.fechaPago = fechaPago;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

}
