package mx.bean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.SQLException;
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
import mx.model.Usuario;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

@Named(value = "pdfDepositoBean")
@ViewScoped
public class PdfDepositoBean extends DAO implements Serializable {

    private final String ruta = "C:\\xampp\\htdocs\\PortalNac\\FichaDeposito\\";
    private String nomnbrePDF;
    private UploadedFile file;

    public String getNomnbrePDF() {
        return nomnbrePDF;
    }

    public void setNomnbrePDF(String nomnbrePDF) {
        this.nomnbrePDF = nomnbrePDF;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public void subirArchivoPDF() throws MessagingException, IOException {
        if (file != null) {
            FacesMessage message = new FacesMessage("DECORADOS Y SATINADOS MEXICANOS S.A DE C.V.", file.getFileName() + " La actualización de tus datos de depósito ha sido enviada correctamente.");
            FacesContext.getCurrentInstance().addMessage(null, message);
            copyFile(file.getFileName(), file.getInputstream());
        }
    }

    public void upload(FileUploadEvent event) throws SQLException, MessagingException {
        try {

            copyFile(event.getFile().getFileName(), event.getFile().getInputstream());
            FacesMessage msg = new FacesMessage("DECORADOS Y SATINADOS MEXICANOS S.A DE C.V. ", event.getFile().getFileName() + " Archivo enviado correctamente");

            Usuario us = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("nombre");

            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (IOException e) {
        }

    }

    public void copyFile(String fileName, InputStream in) throws MessagingException {
        Usuario us = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("nombre");
        File folder = new File(ruta + "\\" + us.getRfc());
        if (!folder.exists()) {
            folder.mkdirs();
        }
        try {
            try (OutputStream dir = new FileOutputStream(new File(ruta + "\\" + us.getRfc() + "\\" + fileName))) {
                int read = 0;
                byte[] bytes = new byte[5120];
                while ((read = in.read(bytes)) != -1) {
                    dir.write(bytes, 0, read);
                }
                this.nomnbrePDF = fileName;
                enviarCorreo();
                in.close();
                dir.flush();
                RequestContext.getCurrentInstance().execute("PF('dlgPDF').hide()");
                this.nomnbrePDF = null;
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void enviarCorreo() throws MessagingException {
        Usuario us = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("nombre");
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
                + "<td height='50' colspan='3' bordercolor='#FFFFFF'><br><br></td>"
                + "</tr>"
                + "<tr>"
                + "<td colspan='3' bordercolor='#FFFFFF'><p align='left' style='font-family:calibri; font-size:17px'><font color='#086A87'>El siguiente proveedor<br>Ha cambiado sus datos de depósito:  </font><br><br>"
                + "<font color='#17202a'></font><i><font color='#E60013'> " + this.nomnbrePDF + "</font></i> <br>"
                + "<font color='#086A87'>Favor de actualizarlos sus datos, se adjunta arhivo en PDF</font> <font color='#086A87'><i></i></font><br>"
                + "<font color='#17202a'></font> <font color='#086A87'><i>  </i></font><br><font color='black'><b></b></font><br></td>"
                + "</tr>"
                + "<tr>"
                + "<td width='725' bordercolor='#FFFFFF'><p align='left' style='font-family:calibri; font-size:17px'><br><font color='#17202a'>Portal Proveedores | </font><font color='#E60013'>Coloidales Duch&eacute;, S.A. de C.V.</font><br>"
                + "<a href='http://desamexproveedores.dyndns.info/proveedores/' target='_blank'><img src='cid:image' width='20%'/></a></td>"
                + "<td width='422' bordercolor='#FFFFFF'></td>"
                + "<td width='422' rowspan='2' bordercolor='#FFFFFF'></td>"
                + "</tr>"
                + "<tr>"
                + "<td colspan='2' bordercolor='#17202a'><br><br><p align='center' style='font-family:calibri; font-size:15px'><font color='#086A87'><br> Favor de no responder a este correo ya que es un aviso autom&aacute;tico, si usted tiene alguna duda favor de contactar al &aacute;rea de Atenci&oacute;n a proveedores cuentasporpagartoluca@duche.com cuentasporpagarmexico@duche.com amendoza@duche.com bcarrillo@duche.com</font></p></td>"
                + "</tr>"
                + "</table>"
                + "</body></html>", "text/html");

        BodyPart adjunto = new MimeBodyPart();
        adjunto.setDataHandler(new DataHandler(new FileDataSource(ruta + "\\" + us.getRfc() + "\\" + nomnbrePDF)));
        adjunto.setFileName(this.nomnbrePDF);
        MimeMultipart multiParte = new MimeMultipart();
        BodyPart imagen = new MimeBodyPart();
        DataSource fds = new FileDataSource("C:\\img\\duche.jpg");
        imagen.setDataHandler(new DataHandler(fds));
        imagen.setHeader("Content-ID", "<image>");
        multiParte.addBodyPart(texto);
        multiParte.addBodyPart(adjunto);
        multiParte.addBodyPart(imagen);

        MimeMessage message = new MimeMessage(session);

// Se rellena el From
        message.setFrom(new InternetAddress("proveedor@duche.com"));

// Se rellenan los destinatarios
        message.addRecipients(Message.RecipientType.BCC, us.getCorreo());
        message.addRecipient(Message.RecipientType.TO, new InternetAddress("cuentasporpagartoluca@duche.com"));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress("cuentasporpagarmexico@duche.com"));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress("amendoza@duche.com"));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress("bcarrillo@duche.com"));
        message.addRecipient(Message.RecipientType.CC, new InternetAddress("ggutierrez@duche.com"));

// Se rellena el subject
        message.setSubject("Notificación de actualización de datos de depósito");

// Se mete el texto y la foto adjunta.
        message.setContent(multiParte);

        Transport t = session.getTransport("smtp");
        t.connect("proveedor@duche.com", "turtle");
        t.sendMessage(message, message.getAllRecipients());
        t.close();

    }

}
