package mx.bean;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import mx.dao.DAO;
import org.primefaces.context.RequestContext;

@Named(value = "avisosBean")
@ViewScoped
public class AvisosBean extends DAO implements Serializable {

    private String aviso;

    public String getAviso() {
        return aviso;
    }

    public void setAviso(String aviso) {
        this.aviso = aviso;
    }

    public AvisosBean() throws SQLException {

    }

    public void onload(AjaxBehaviorEvent event) {
        try {
            mostrarAviso();
        } catch (SQLException ex) {
            Logger.getLogger(AvisosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @PostConstruct
    public void init() {
        try {
            mostrarAviso();
        } catch (SQLException ex) {
            Logger.getLogger(AvisosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
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
}
