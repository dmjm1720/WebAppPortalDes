package mx.bean;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
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
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import mx.dao.DAO;
import org.primefaces.context.RequestContext;

@Named(value = "resetearPassBean")
@ViewScoped
public class resetearPassBean extends DAO implements Serializable {

    private String correo;
    private String correoPred;
    private String rfc;
    private String clave;

    public resetearPassBean() {
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
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

    public String getCorreoPred() {
        return correoPred;
    }

    public void setCorreoPred(String correoPred) {
        this.correoPred = correoPred;
    }

    public void enviarPass() throws SQLException, MessagingException {
        try {
            this.Conectarprov();
            Statement st = this.getCnprov().createStatement();
            ResultSet rs = st.executeQuery("SELECT TOP(1) CLAVE, CORREO FROM USUARIO WHERE RFC='" + this.rfc + "'");
            if (!rs.isBeforeFirst()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "DECORADOS Y SATINADOS MEXICANOS S.A DE C.V.", "Tu RFC no está registrado en el sistema, favor de verificarlo."));
                this.rfc = null;
                this.correo = null;
            } else {
                while (rs.next()) {
                    this.clave = rs.getString("CLAVE");
                    this.correoPred = rs.getString("CORREO");
                    correo();
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "DECORADOS Y SATINADOS MEXICANOS S.A DE C.V.", "Te hemos enviado un correo con la información solicitada."));
                    RequestContext.getCurrentInstance().execute("PF('dlgPass').hide()");
                    this.correo = null;
                    this.correoPred = null;
                    this.rfc = null;
                    this.clave = null;
                }
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

    }

    public void correo() throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.alestraune.net.mx");
        props.setProperty("mail.smtp.starttls.enable", "true");
        props.setProperty("mail.smtp.port", "587");
        props.setProperty("mail.smtp.user", "portalproveedores@duche.com");
        props.setProperty("mail.smtp.auth", "true");
        Session session = Session.getDefaultInstance(props, null);
        session.setDebug(false);

        BodyPart texto = new MimeBodyPart();
        texto.setContent("<html><head><title></title></head>"
                + "<body>"
                + "<table width='878' height='315' border='0' bordercolor='#0000FF' bgcolor='#FFFFFF'>"
                + "<tr>"
                + "<td height='1' colspan='3' bordercolor='#FFFFFF'></td>"
                + "</tr>"
                + "<tr>"
                + "<td colspan='3' bordercolor='#FFFFFF'><p align='left' style='font-family:calibri; font-size:17px'><font color='#086A87'>Estimado proveedor,<br>Su contrase&ntilde;a es: </font><br><br>"
                + "<font color='#17202a'></font><i><font color='#E60013'> " + this.clave + "</font></i> <br>"
                + "<font color='#086A87'>Le recordamos guardarla en un lugar seguro.</font> <font color='#086A87'><i></i></font><br>"
                + "<font color='#17202a'></font> <font color='#086A87'><i>  </i></font><br><font color='black'><b></b></font><br></td>"
                + "</tr>"
                + "<tr>"
                + "<td width='725' bordercolor='#FFFFFF'><p align='left' style='font-family:calibri; font-size:17px'><br><font color='#17202a'>Portal Proveedores | </font><font color='#E60013'>Coloidales Duch&eacute;, S.A. de C.V.</font><br>"
                + "<a href='http://desamexproveedores.dyndns.info:9088/proveedores/' target='_blank'><img src='cid:image' width='20%'/></a></td>"
                + "<td width='422' bordercolor='#FFFFFF'></td>"
                + "<td width='422' rowspan='2' bordercolor='#FFFFFF'></td>"
                + "</tr>"
                + "<tr>"
                + "<td colspan='2' bordercolor='#17202a'><br><br><p align='center' style='font-family:calibri; font-size:15px'><font color='#086A87'><br> Favor de no responder a este correo ya que es un aviso autom&aacute;tico, si usted tiene alguna duda favor de contactar al &aacute;rea de Atenci&oacute;n a proveedores ggutierrez@duche-com</font></p></td>"
                + "</tr>"
                + "</table>"
                + "</body></html>", "text/html");

        MimeMultipart multiParte = new MimeMultipart();
        BodyPart imagen = new MimeBodyPart();
        DataSource fds = new FileDataSource("C:\\img\\duche.png");

        imagen.setDataHandler(new DataHandler(fds));
        imagen.setHeader("Content-ID", "<image>");
        multiParte.addBodyPart(texto);
        // multiParte.addBodyPart(adjunto);
        multiParte.addBodyPart(imagen);

        MimeMessage message = new MimeMessage(session);

// Se rellena el From
        message.setFrom(new InternetAddress("portalproveedores@duche.com"));

// Se rellenan los destinatarios
        message.addRecipients(Message.RecipientType.TO, this.correo);
        message.addRecipients(Message.RecipientType.TO, this.correoPred);
        message.addRecipient(Message.RecipientType.BCC, new InternetAddress("ggutierrez@duche.com"));

// Se rellena el subject
        message.setSubject("Recuperación de contraseña");

// Se mete el texto y la foto adjunta.
        message.setContent(multiParte);

        Transport t = session.getTransport("smtp");
        t.connect("portalproveedores@duche.com", "ML310gen11");
        t.sendMessage(message, message.getAllRecipients());
        t.close();
    }

}
