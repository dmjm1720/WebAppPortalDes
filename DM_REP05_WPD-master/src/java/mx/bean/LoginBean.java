package mx.bean;

import java.awt.event.ActionEvent;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import mx.dao.DAO;
import mx.dao.LoginDao;
import mx.dao.LoginDaoImpl;
import mx.model.Usuario;
import org.primefaces.context.RequestContext;

@Named(value = "loginBean")
@SessionScoped
public class LoginBean extends DAO implements Serializable {
    
    private Usuario usuario;
    private String rfc;
    private String clave;
    private String idioma;
    private String msgWelcome;
    private String msgError;
    private String msgError2;
    private String aviso;
    
    public LoginBean() {
        this.usuario = new Usuario();
    }
    
    public Usuario getUsuario() {
        return usuario;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public String getRfc() {
        return rfc;
    }
    
    public void setRfc(String rfc) {
        this.rfc = rfc;
    }
    
    public String getClave() {
        return clave;
    }
    
    public void setClave(String clave) {
        this.clave = clave;
    }
    
    public String getIdioma() {
        return idioma;
    }
    
    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }
    
    public String getMsgWelcome() {
        return msgWelcome;
    }
    
    public void setMsgWelcome(String msgWelcome) {
        this.msgWelcome = msgWelcome;
    }
    
    public String getMsgError() {
        return msgError;
    }
    
    public void setMsgError(String msgError) {
        this.msgError = msgError;
    }
    
    public String getMsgError2() {
        return msgError2;
    }
    
    public void setMsgError2(String msgError2) {
        this.msgError2 = msgError2;
    }

    public String getAviso() {
        return aviso;
    }

    public void setAviso(String aviso) {
        this.aviso = aviso;
    }
    
    
    
    public void login(ActionEvent event) throws InterruptedException {
        this.idioma = Locale.getDefault().getLanguage();
        if (this.idioma.contains("es")) {
            this.msgWelcome = "Bienvenido";
            this.msgError = "Usuario o contraseña incorrectos";
            this.msgError2 = "Error de sesión";
        } else {
            this.msgWelcome = "Welcome";
            this.msgError = "Username or password incorrect";
            this.msgError2 = "Session error";
        }
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage message = null;
        FacesMessage message2 = null;
        FacesMessage message3 = null;
        boolean loggedIn = false;
        String ruta = "";
        LoginDao uDao = new LoginDaoImpl();
        this.usuario = uDao.login(this.usuario);
        
        if (this.usuario != null) {
            loggedIn = true;
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("nombre", usuario);
            
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, this.msgWelcome, this.usuario.getRfc());
            
            if (this.usuario.getCodperfil().equals("Administrador")) {
                ruta = "/proveedores/Views/BuscarFacturas.desamex";
            } else {
                ruta = "/proveedores/Views/Aviso.desamex";
            }
            
        } else {
            loggedIn = false;
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, this.msgError2, this.msgError);
            this.usuario = new Usuario();
            
        }
        
        FacesContext.getCurrentInstance().addMessage(null, message);
        try {
            FacesContext.getCurrentInstance().addMessage(null, message2);
            FacesContext.getCurrentInstance().addMessage(null, message3);
        } catch (Exception e) {
        }
        context.addCallbackParam("loggedIn", loggedIn);
        context.addCallbackParam("ruta", ruta);
    }
    
    public void mostrarAviso() throws SQLException {
        this.Conectarprov();
        Statement st = this.getCnprov().createStatement();
        ResultSet rs = st.executeQuery("SELECT TOP(1) RUTA FROM COMUNICADO ORDER BY ID DESC");
        if (!rs.isBeforeFirst()) {
        } else {
            while (rs.next()) {
                this.aviso = rs.getString("RUTA");
                if (this.aviso != null) {
                    RequestContext.getCurrentInstance().execute("PF('dialogVerPDF').show()");
                }
            }
        }
        this.Cerrar();

    }
    
    public void cerrarSesion() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
    }
    
}
