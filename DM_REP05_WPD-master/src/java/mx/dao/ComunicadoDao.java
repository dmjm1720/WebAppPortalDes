package mx.dao;

import java.util.List;
import mx.model.Comunicado;

public interface ComunicadoDao {

    public List<Comunicado> listaComunicado();

    public void InsertComunicado(Comunicado comunicado);

    public void UpdateComunicado(Comunicado comunicado);

    public void DeleteComunicado(Comunicado comunicado);

}
