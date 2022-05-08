package mx.bean;

import mx.dao.*;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Random;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import mx.dao.DAO;
import mx.dao.DAO;
import mx.model.Correo;
import org.primefaces.context.RequestContext;

@Named(value = "registroBean")
@ViewScoped
public class registroBean extends DAO implements Serializable {

    private String correo;
    private String perfil;
    private String rfc;
    private String rfc2;
    private String clave;
    private String clave2;
    private String codactivacion;
    private String cadena;
    private String telefono;
    private String code;
    private String activa;
    private boolean aceptar;
    private Correo c;

    public registroBean() {
        this.c = new Correo();
    }

    public Correo getC() {
        return c;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
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

    public String getCodactivacion() {
        return codactivacion;
    }

    public void setCodactivacion(String codactivacion) {
        this.codactivacion = codactivacion;
    }

    public String getClave2() {
        return clave2;
    }

    public void setClave2(String clave2) {
        this.clave2 = clave2;
    }

    public String getRfc2() {
        return rfc2;
    }

    public void setRfc2(String rfc2) {
        this.rfc2 = rfc2;
    }

    public String getCadena() {
        return cadena;
    }

    public void setCadena(String cadena) {
        this.cadena = cadena;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getActiva() {
        return activa;
    }

    public void setActiva(String activa) {
        this.activa = activa;
    }

    public boolean isAceptar() {
        return aceptar;
    }

    public void setAceptar(boolean aceptar) {
        this.aceptar = aceptar;
    }

    public void registrar() throws SQLException, MessagingException {
        if (aceptar == false) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "DECORADOS Y SATINADOS MEXICANOS S.A DE C.V.", "¡Para completar tu registro es necesario aceptar el aviso de privacidad, da clic en Aceptar aviso de privacidad!"));
        } else {
            this.Conectar();
            this.Conectarprov();
            Statement st = this.getCn().createStatement();
            ResultSet rs = st.executeQuery("SELECT RFC FROM PROV01 WHERE RFC='" + this.rfc + "'");
            if (!rs.isBeforeFirst()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "DECORADOS Y SATINADOS MEXICANOS S.A DE C.V.", "¡Tu RFC:  " + this.rfc + " no está dado de alta en nuestro sistema!"));
            } else {
                while (rs.next()) {
                    this.rfc2 = rs.getString("RFC");
                    Statement st1 = this.getCnprov().createStatement();
                    ResultSet rs1 = st1.executeQuery("SELECT RFC FROM USUARIO WHERE RFC='" + this.rfc2 + "'");
                    if (!rs1.isBeforeFirst()) {
                        this.cadena = "";
                        long milis = new java.util.GregorianCalendar().getTimeInMillis();
                        Random r = new Random(milis);
                        int i = 0;
                        while (i < 50) {
                            char c = (char) r.nextInt(255);
                            if ((c >= '0' && c <= '9') || (c >= 'A' && c <= 'Z')) {
                                this.cadena += c;
                                i++;
                            }
                        }
                        PreparedStatement ps = this.getCnprov().prepareStatement("INSERT INTO USUARIO VALUES('" + this.correo + "','Usuario','" + this.rfc + "','" + this.clave + "','" + this.telefono + "',0,GETDATE(),'','','" + this.cadena + "','')");
                        ps.executeUpdate();
                        correo();
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "DECORADOS Y SATINADOS MEXICANOS S.A DE C.V.", "¡Te hemos enviado un código de activación al correo:  " + this.correo + " por favor revisa tu bandeja de entrada!"));
                        RequestContext.getCurrentInstance().execute("PF('dlgActivar').show()");
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "DECORADOS Y SATINADOS MEXICANOS S.A DE C.V..", "¡Tu RFC:  " + this.rfc + " ya está registrado en el Portal!"));
                    }
                }
            }
            this.Cerrar();
            this.Cerrarprov();
            this.correo = null;
            this.rfc = null;
            this.telefono = null;
            this.aceptar = false;
        }
    }

    public void activar() throws SQLException {
        this.Conectarprov();
        Statement st = this.getCnprov().createStatement();
        ResultSet rs = st.executeQuery("SELECT CODACTIVACION, ACTIVACION FROM USUARIO WHERE CODACTIVACION='" + this.codactivacion + "' AND ACTIVACION=0");
        if (!rs.isBeforeFirst()) {
            if (this.code == null ? this.codactivacion != null : !this.code.equals(this.codactivacion)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "DECORADOS Y SATINADOS MEXICANOS S.A DE C.V.", "El código de activación es incorrecto, favor de verificar."));
            }
            Statement st2 = this.getCnprov().createStatement();
            ResultSet rs2 = st2.executeQuery("SELECT CODACTIVACION, ACTIVACION FROM USUARIO WHERE CODACTIVACION='" + this.codactivacion + "' AND ACTIVACION=1");
            while (rs2.next()) {
                this.code = rs2.getString("CODACTIVACION");
                this.activa = rs2.getString("ACTIVACION");
                if (this.activa.equals("1")) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "DECORADOS Y SATINADOS MEXICANOS S.A DE C.V.", "Tu cuenta ya ha sido activada previamente"));
                }
            }

        } else {
            PreparedStatement ps = this.getCnprov().prepareStatement("UPDATE USUARIO SET ACTIVACION=1 WHERE CODACTIVACION='" + this.codactivacion.trim() + "'");
            ps.executeUpdate();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "DECORADOS Y SATINADOS MEXICANOS S.A DE C.V.", "Tu cuenta se ha activado satisfactoriamente, por favor inicia sesión con tu RFC y contraseña"));
            RequestContext.getCurrentInstance().execute("PF('dlgActivar').hide();PF('dlgRegistro').hide()");
        }
        this.Cerrarprov();
        this.codactivacion = "";
    }

    public void correo() throws MessagingException {

        ICorreoDao cDao = new CorreoDaoImpl();
        c = cDao.listaPrincipal();
        //Get properties object    
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        //get Session   
        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
            @Override

            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(c.getCorreo(), c.getPass());
            }
        });
        session.setDebug(true);
        //compose message    
        try {

            MimeMessage message = new MimeMessage(session);

            message.addRecipient(Message.RecipientType.TO, new InternetAddress(this.correo));
            // message.addRecipients(Message.RecipientType.CC, recipientAddress);

            message.setSubject("CÓDIGO DE ACTIVACIÓN");
            BodyPart texto = new MimeBodyPart();
            texto.setContent("<html><head><title></title></head>"
                    + "<body>"
                    + "<table width='878' height='315' border='0' bordercolor='#0000FF' bgcolor='#FFFFFF'>"
                    + "<tr>"
                    + "<td height='50' colspan='3' bordercolor='#FFFFFF'><br><br></td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td colspan='3' bordercolor='#FFFFFF'><p align='left' style='font-family:calibri; font-size:17px'><font color='#086A87'>Estimado proveedor,<br>Te informamos que para completar tu registro en el Portal, te hemos enviado el siguiente c&oacute;digo de activaci&oacute;n:  </font><br><br>"
                    + "<font color='#17202a'></font><i><font color='#E60013'> " + this.cadena + "</font></i> <br>"
                    + "<font color='#086A87'>Favor de copiarlo y pegarlo en el campo correspondiente, y posteriormente da clic en bot&oacute;n Activar cuenta</font> <font color='#086A87'><i></i></font><br>"
                    + "<font color='#17202a'></font> <font color='#086A87'><i>  </i></font><br><font color='black'><b></b></font><br></td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td width='725' bordercolor='#FFFFFF'><img src='cid:activacion' width='100%'/><p align='left' style='font-family:calibri; font-size:17px'><br><font color='#17202a'>Portal Proveedores | </font><font color='#E60013'>DECORADOS Y SATINADOS MEXICANOS S.A DE C.V.</font><br>"
                    + "<a href='https://desamex.net/' target='_blank'><img src='cid:image' width='75%'/></a></td>"
                    + "<td width='422' bordercolor='#FFFFFF'></td>"
                    + "<td width='422' rowspan='2' bordercolor='#FFFFFF'></td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td colspan='2' bordercolor='#17202a'><br><br><p align='center' style='font-family:calibri; font-size:15px'><font color='#086A87'><br> Favor de no responder a este correo ya que es un aviso autom&aacute;tico, si tiene alguna duda favor de contactar al &aacute;rea de Atenci&oacute;n a proveedores:<br>amoreno@desamex.net<br>"
                    + "zdoran@desamex.net<br> omartinez@desamex.net<br> tesquivel@desamex.net  </font></p></td>"
                    + "</tr>"
                    + "</table>"
                    + "</body></html>", "text/html");

            MimeMultipart multiParte = new MimeMultipart();
            BodyPart imagen = new MimeBodyPart();
            BodyPart activacion = new MimeBodyPart();
            DataSource fds = new FileDataSource("C:\\img\\logo.png");
            DataSource act = new FileDataSource("C:\\img\\activacion.png");
            imagen.setDataHandler(new DataHandler(fds));
            imagen.setHeader("Content-ID", "<image>");
            activacion.setDataHandler(new DataHandler(act));
            activacion.setHeader("Content-ID", "<activacion>");
            multiParte.addBodyPart(texto);
            multiParte.addBodyPart(imagen);
            multiParte.addBodyPart(activacion);
            message.setContent(multiParte);
            //send message  
            Transport.send(message);
            System.out.println("message sent successfully");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

}
