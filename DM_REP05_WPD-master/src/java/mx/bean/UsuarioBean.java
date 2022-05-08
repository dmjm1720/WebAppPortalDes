package mx.bean;

import java.io.Serializable;
import java.util.List;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import mx.dao.UsuarioDao;
import mx.dao.UsuarioDaoImpl;
import mx.model.Usuario;

@Named(value = "usuarioBean")
@ViewScoped
public class UsuarioBean implements Serializable {

    private List<Usuario> listaUsuario;
    private Usuario usuario;

    public UsuarioBean() {
        usuario = new Usuario();
    }

    public List<Usuario> getListaUsuario() {
        UsuarioDao uDao = new UsuarioDaoImpl();
        listaUsuario = uDao.listaUsuario();
        return listaUsuario;
    }

    public void setListaUsuario(List<Usuario> listaUsuario) {
        this.listaUsuario = listaUsuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public void updateUsuario(){
        UsuarioDao uDao = new UsuarioDaoImpl();
        uDao.UpdateUsuario(usuario);
        usuario = new Usuario();
    }

}
