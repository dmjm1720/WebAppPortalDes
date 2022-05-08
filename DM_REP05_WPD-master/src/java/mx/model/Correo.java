package mx.model;

public class Correo implements java.io.Serializable {

    private int id;
    private String correo;
    private String tipo;
    private String pass;

    public Correo() {
    }

    public Correo(int id, String correo) {
        this.id = id;
        this.correo = correo;
    }

    public Correo(int id, String correo, String tipo, String pass) {
        this.id = id;
        this.correo = correo;
        this.tipo = tipo;
        this.pass = pass;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCorreo() {
        return this.correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTipo() {
        return this.tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getPass() {
        return this.pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

}
