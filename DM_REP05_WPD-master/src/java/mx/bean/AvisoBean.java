package mx.bean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.mail.MessagingException;
import javax.servlet.ServletContext;
import mx.dao.ComunicadoDao;
import mx.dao.ComunicadoDaoImpl;
import mx.model.Comunicado;
import mx.model.Usuario;
import org.primefaces.context.RequestContext;
import org.primefaces.model.UploadedFile;

@Named(value = "AvisoBean")
@ViewScoped
public class AvisoBean implements Serializable {

    private List<Comunicado> listaComunicado;
    private Comunicado comunicado;
    private UploadedFile file;
    Usuario us = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("nombre");
    private Date hoy;
    private Date fechaSolicitud;
    private Calendar hoyFecha;
    private String fecSol;

    public AvisoBean() {
        comunicado = new Comunicado();
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public Date getHoy() {
        return hoy;
    }

    public void setHoy(Date hoy) {
        this.hoy = hoy;
    }

    public Date getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(Date fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public Calendar getHoyFecha() {
        return hoyFecha;
    }

    public void setHoyFecha(Calendar hoyFecha) {
        this.hoyFecha = hoyFecha;
    }

    public String getFecSol() {
        return fecSol;
    }

    public void setFecSol(String fecSol) {
        this.fecSol = fecSol;
    }

    public List<Comunicado> getListaComunicado() {
        ComunicadoDao cDao = new ComunicadoDaoImpl();
        listaComunicado = cDao.listaComunicado();
        return listaComunicado;
    }

    public Comunicado getComunicado() {
        return comunicado;
    }

    public void setComunicado(Comunicado comunicado) {
        this.comunicado = comunicado;
    }

    public void deleteComunicado() {
        ComunicadoDao cDao = new ComunicadoDaoImpl();
        cDao.DeleteComunicado(comunicado);
        comunicado = new Comunicado();
    }

    public void subirPDF() throws IOException, MessagingException {
        if (file != null) {
            FacesMessage message = new FacesMessage("DECORADOS Y SATINADOS MEXICANOS S.A DE C.V.", file.getFileName() + " El aviso se ha subido correctamente.");
            FacesContext.getCurrentInstance().addMessage(null, message);
            copyFile(file.getFileName(), file.getInputstream());
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "DECORADOS Y SATINADOS MEXICANOS S.A DE C.V.", "Favor de seleccionar su archivo."));
        }
    }

    public static String getPath() {
        try {
            ServletContext sc = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            return sc.getRealPath("/");

        } catch (Exception e) {
            e.getMessage();
        }
        return null;
    }

    public void copyFile(String fileName, InputStream in) throws MessagingException {
        String path = getPath() + "/Avisos/";
        try {
            File ruta = new File(path);
            if (!ruta.exists()) {
                ruta.mkdirs();
            }

            try (OutputStream dir = new FileOutputStream(new File(ruta + "//" + fileName))) {

                //System.out.println(f.getAbsolutePath());
                // try (OutputStream dir = new FileOutputStream(new File(ruta + "\\" + fileName))) {
                @SuppressWarnings("UnusedAssignment")
                int read = 0;
                byte[] bytes = new byte[5120];
                while ((read = in.read(bytes)) != -1) {
                    dir.write(bytes, 0, read);
                }
                ComunicadoDao cDao = new ComunicadoDaoImpl();
                comunicado.setRuta("http://desamexproveedores.dyndns.info:9088/proveedores/Avisos/" + fileName);
                comunicado.setFecha(hoy);
                cDao.InsertComunicado(comunicado);
                comunicado = new Comunicado();
                in.close();
                dir.flush();
                RequestContext.getCurrentInstance().execute("PF('dlgPDF').hide()");
                RequestContext.getCurrentInstance().execute("PF('dlgInfo').hide()");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
