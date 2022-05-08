package mx.dao;

import java.util.List;
import mx.model.Mensaje;

public interface MensajeDao {

    List<Mensaje> listaMensaje();

    public void InsertMesaje(Mensaje mensaje);

    public void UpdateMensaje(Mensaje mensaje);

    public void DeleteMensaje(Mensaje mensaje);
}
