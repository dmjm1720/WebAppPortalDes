package mx.bean;

import java.io.Serializable;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import mx.dao.ComunicadoDao;
import mx.dao.ComunicadoDaoImpl;
import mx.model.Comunicado;

@Named(value = "comunicadoBean")
@ViewScoped
public class ComunicadoBean implements Serializable{

    private List<Comunicado> listaComunicado;
    private Comunicado comunicado;

    public ComunicadoBean() {
        comunicado = new Comunicado();
    }

    public List<Comunicado> getListaComunicado() {
        ComunicadoDao cDao = new ComunicadoDaoImpl();
        listaComunicado = cDao.listaComunicado();
        return listaComunicado;
    }

    public void setListaComunicado(List<Comunicado> listaComunicado) {
        this.listaComunicado = listaComunicado;
    }

    public Comunicado getComunicado() {
        return comunicado;
    }

    public void setComunicado(Comunicado comunicado) {
        this.comunicado = comunicado;
    }

    public void insertComunicado() {
        ComunicadoDao cDao = new ComunicadoDaoImpl();
        cDao.InsertComunicado(comunicado);
    }

    public void updateComunicado() {
        ComunicadoDao cDao = new ComunicadoDaoImpl();
        cDao.UpdateComunicado(comunicado);
        comunicado = new Comunicado();
    }

    public void deleteComunicado() {
        ComunicadoDao cDao = new ComunicadoDaoImpl();
        cDao.DeleteComunicado(comunicado);
        comunicado = new Comunicado();
    }

}
