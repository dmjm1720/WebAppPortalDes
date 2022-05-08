package mx.bean;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
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
import mx.dao.QuejaDao;
import mx.dao.QuejaDaoImpl;
import mx.model.Queja;
import mx.model.Usuario;
import org.primefaces.context.RequestContext;

@Named(value = "quejaBean")
@ViewScoped
public class QuejaBean extends DAO implements Serializable {

    private Queja queja;
    private List<Queja> listaQueja;
    private Calendar hoy;
    private String nombreE;

    public QuejaBean() {
        queja = new Queja();
    }

    public Queja getQueja() {
        return queja;
    }

    public void setQueja(Queja queja) {
        this.queja = queja;
    }

    public List<Queja> getListaQueja() {
        QuejaDao qDao = new QuejaDaoImpl();
        listaQueja = qDao.listaQueja();
        return listaQueja;
    }

    public void setListaQueja(List<Queja> listaQueja) {
        this.listaQueja = listaQueja;
    }

    public Calendar getHoy() {
        return hoy;
    }

    public void setHoy(Calendar hoy) {
        this.hoy = hoy;
    }

    public String getNombreE() {
        return nombreE;
    }

    public void setNombreE(String nombreE) {
        this.nombreE = nombreE;
    }

    Usuario us = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("nombre");

    public void insertQueja() throws SQLException, MessagingException {
        this.hoy = Calendar.getInstance();
        queja.setRfc(us.getRfc());
        queja.setFeccomentario(this.hoy.getTime());
        QuejaDao qDao = new QuejaDaoImpl();
        buscarProveedor();
        enviarQueja();
        qDao.InsertQueja(queja);
        queja = new Queja();
        RequestContext.getCurrentInstance().execute("PF('dlgInfo').hide()");
    }

    public void updateQueja() {
        QuejaDao qDao = new QuejaDaoImpl();
        qDao.UpdateQueja(queja);
        queja = new Queja();
    }

    public void deleteQueja() {
        QuejaDao qDao = new QuejaDaoImpl();
        qDao.DeleteQueja(queja);
        queja = new Queja();
    }

    public void buscarProveedor() throws SQLException {
        this.Conectar();
        Statement st = this.getCn().createStatement();
        ResultSet rs = st.executeQuery("SELECT NOMBRE FROM PROV01 WHERE RFC LIKE '%" + us.getRfc() + "%' AND STATUS='A'");
        if (!rs.isBeforeFirst()) {
            this.nombreE = "";
        } else {
            while (rs.next()) {
                this.nombreE = rs.getString("NOMBRE");
            }
        }
        this.Cerrar();
    }

    public void enviarQueja() throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.alestraune.net.mx");
        props.setProperty("mail.smtp.starttls.enable", "true");
        props.setProperty("mail.smtp.port", "587");
        props.setProperty("mail.smtp.user", "proveedor@duche.com");
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
                + "<td colspan='3' bordercolor='#FFFFFF'><p align='left' style='font-family:calibri; font-size:17px'><font color='#086A87'></font>"
                + "<font color='#17202a'>Nombre proveedor: </font><i><font color='#086A87'> " + this.nombreE + "</font></i> <br>"
                + "<font color='#17202a'>Correo del proveedor: </font> <font color='#086A87'><i> " + us.getCorreo() + " </i></font><br>"
                + "<font color='#17202a'>Tel&eacute;fono del proveedor: </font> <font color='#086A87'><i> " + us.getTelefono() + " </i></font><br>"
                + "<font color='#17202a'>RFC proveedor: </font> <font color='#086A87'><i> " + us.getRfc() + " </i></font><br>"
                + "<font color='#17202a'></font> <font color='#086A87'><i></i></font><br>"
                + "<font color='#17202a'>Mensaje: </font> <font color='#086A87'><i> " + queja.getComentarioprov() + " </i></font><br>"
                + "<font color='#17202a'></font> <font color='#086A87'><i></i></font>"
                + "<font color='#17202a'></font> <font color='#086A87'><i></i></font>"
                + "<font color='#17202a'></font> <font color='#086A87'><i></i></font>"
                + "<font color='#17202a'></font> <font color='#086A87'><i> </i></font><br><font color='black'><b></b></font><br></td>"
                + "</tr>"
                + "<tr>"
                + "<td width='725' bordercolor='#FFFFFF'><p align='left' style='font-family:calibri; font-size:17px'><br><font color='#17202a'>Portal Proveedores | </font><font color='#E60013'>Coloidales Duch&eacute;, S.A. de C.V.</font><br>"
                + "<a href='http://desamexproveedores.dyndns.info:9088/proveedores/' target='_blank'><img src='cid:image' width='20%'/></a></td>"
                + "<td width='422' bordercolor='#FFFFFF'></td>"
                + "<td width='422' rowspan='2' bordercolor='#FFFFFF'></td>"
                + "</tr>"
                + "<tr>"
                + "<td colspan='2' bordercolor='#17202a'><br><br><p align='center' style='font-family:calibri; font-size:15px'><font color='#086A87'><br> Favor de no responder a este correo ya que es un aviso autom&aacute;tico, si usted tiene alguna duda favor de contactar al &aacute;rea de Atenci&oacute;n a proveedores ggutierrez@duche.com</font></p></td>"
                + "</tr>"
                + "</table>"
                + "</body></html>", "text/html");

        MimeMultipart multiParte = new MimeMultipart();
        BodyPart imagen = new MimeBodyPart();
        DataSource fds = new FileDataSource("C:\\img\\duche.jpg");
        imagen.setDataHandler(new DataHandler(fds));
        imagen.setHeader("Content-ID", "<image>");

        multiParte.addBodyPart(texto);
        // multiParte.addBodyPart(adjunto);
        multiParte.addBodyPart(imagen);

        MimeMessage message = new MimeMessage(session);

// Se rellena el From
        message.setFrom(new InternetAddress("proveedor@duche.com"));

// Se rellenan los destinatarios
        message.addRecipients(Message.RecipientType.TO, us.getCorreo());
        message.addRecipient(Message.RecipientType.TO, new InternetAddress("cuentasporpagartoluca@duche.com"));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress("cuentasporpagarmexico@duche.com"));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress("amendoza@duche.com"));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress("bcarrillo@duche.com"));
        message.addRecipient(Message.RecipientType.CC, new InternetAddress("ggutierrez@duche.com"));

// Se rellena el subject
        message.setSubject("Notificación de quejas y sugerencias");

// Se mete el texto y la foto adjunta.
        message.setContent(multiParte);

        Transport t = session.getTransport("smtp");
        t.connect("proveedor@duche.com", "turtle");
        t.sendMessage(message, message.getAllRecipients());
        t.close();
    }

}
