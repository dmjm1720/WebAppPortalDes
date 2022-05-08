package mx.dao;

import java.util.List;
import mx.model.Usuario;

public interface UsuarioDao {

    public List<Usuario> listaUsuario();

    public void InsertUsuario(Usuario usuario);

    public void UpdateUsuario(Usuario usuario);

    public void DeleteUsuario(Usuario usuario);
}
