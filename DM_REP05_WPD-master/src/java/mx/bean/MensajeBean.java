package mx.bean;

import java.io.Serializable;
import java.util.List;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import mx.dao.MensajeDao;
import mx.dao.MensajeDaoImpl;
import mx.model.Mensaje;

@Named(value = "mensajeBean")
@ViewScoped
public class MensajeBean implements Serializable{

    private Mensaje mensaje;
    private List<Mensaje> listaMensaje;

    public MensajeBean() {
        mensaje = new Mensaje();
    }

    public Mensaje getMensaje() {
        return mensaje;
    }

    public void setMensaje(Mensaje mensaje) {
        this.mensaje = mensaje;
    }

    public List<Mensaje> getListaMensaje() {
        MensajeDao mDao = new MensajeDaoImpl();
        listaMensaje = mDao.listaMensaje();
        return listaMensaje;
    }

    public void setListaMensaje(List<Mensaje> listaMensaje) {
        this.listaMensaje = listaMensaje;
    }

    public void insertarMensaje() {
        MensajeDao mDao = new MensajeDaoImpl();
        mDao.InsertMesaje(mensaje);
        mensaje = new Mensaje();
    }

    public void actiualizarMensaje() {
        MensajeDao mDao = new MensajeDaoImpl();
        mDao.UpdateMensaje(mensaje);
        mensaje = new Mensaje();
    }

    public void borrarMensaje() {
        MensajeDao mDao = new MensajeDaoImpl();
        mDao.DeleteMensaje(mensaje);
        mensaje = new Mensaje();
    }

}
