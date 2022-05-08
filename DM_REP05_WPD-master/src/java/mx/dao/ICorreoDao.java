package mx.dao;

import java.util.List;
import mx.model.Correo;

public interface ICorreoDao {

    public Correo listaPrincipal();

    public List<Correo> lista();

}
