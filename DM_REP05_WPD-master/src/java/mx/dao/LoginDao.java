package mx.dao;

import mx.model.Usuario;

public interface LoginDao {

    public Usuario obternerDatosUsuario(Usuario usuario);

    public Usuario login(Usuario usuario);

}
