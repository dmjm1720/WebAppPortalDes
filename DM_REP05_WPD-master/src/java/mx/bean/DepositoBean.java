package mx.bean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
import mx.dao.DepositoDao;
import mx.dao.DepositoDaoImpl;
import mx.model.Deposito;
import mx.model.Usuario;
import org.primefaces.context.RequestContext;
import org.primefaces.model.UploadedFile;

@Named(value = "depositoBean")
@ViewScoped
public class DepositoBean implements Serializable {

    private List<Deposito> listaDeposito;
    private Deposito deposito;
    private UploadedFile file;
    private final String ruta = "C:\\public\\fichaDeposito\\";
    private String nomnbrePDF;
    Usuario us = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("nombre");
    private Date hoy;
    private Date fechaSolicitud;
    private Calendar hoyFecha;
    private String fecSol;

    public DepositoBean() {
        deposito = new Deposito();
    }

    public List<Deposito> getListaDeposito() {
        DepositoDao dDao = new DepositoDaoImpl();
        listaDeposito = dDao.listaDeposito();
        return listaDeposito;
    }

    public void setListaDeposito(List<Deposito> listaDeposito) {
        this.listaDeposito = listaDeposito;
    }

    public Deposito getDeposito() {
        return deposito;
    }

    public void setDeposito(Deposito deposito) {
        this.deposito = deposito;
    }

    public void insertarDeposito() {
        DepositoDao dDao = new DepositoDaoImpl();
        this.hoy = Date.from(Instant.now());
        deposito.setFecha(this.hoy);
        deposito.setRfc(us.getRfc());
        deposito.setArchivo(file.getFileName());
        dDao.InsertDeposito(deposito);
    }

    public void updateDeposito() {
        DepositoDao dDao = new DepositoDaoImpl();
        dDao.UpdateDeposito(deposito);
        deposito = new Deposito();
    }

    public void deleteDeposito() {
        DepositoDao dDao = new DepositoDaoImpl();
        dDao.DeleteDeposito(deposito);
        deposito = new Deposito();
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public String getNomnbrePDF() {
        return nomnbrePDF;
    }

    public void setNomnbrePDF(String nomnbrePDF) {
        this.nomnbrePDF = nomnbrePDF;
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

    public void subirPDF() throws IOException, MessagingException {
        if (file != null) {
            FacesMessage message = new FacesMessage("DECORADOS Y SATINADOS MEXICANOS S.A DE C.V.", file.getFileName() + " La actualización de tus datos de depósito ha sido enviada correctamente.");
            FacesContext.getCurrentInstance().addMessage(null, message);
            copyFile(file.getFileName(), file.getInputstream());
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "DECORADOS Y SATINADOS MEXICANOS S.A DE C.V.", "Favor de seleccionar su archivo de actualización de depósito."));
        }
    }

    public void copyFile(String fileName, InputStream in) throws MessagingException {
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
                insertarDeposito();
                in.close();
                dir.flush();
                RequestContext.getCurrentInstance().execute("PF('dlgPDF').hide()");
                RequestContext.getCurrentInstance().execute("PF('dlgInfo').hide()");
                this.nomnbrePDF = null;
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void enviarCorreo() throws MessagingException {
        this.hoyFecha = Calendar.getInstance();
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.fecSol = formatoFecha.format(this.hoyFecha.getTime());
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
                + "<td colspan='3' bordercolor='#FFFFFF'><p align='left' style='font-family:calibri; font-size:17px'><font color='#086A87'>Solicitud de Actualización en los datos de Dep&oacute;sito.<br></font><br>"
                + "<font color='#086A87'>Nombre del proveedor:</font><i><font color='#17202a'> " + deposito.getTitular() + "</font></i> <br>"
                + "<font color='#086A87'>Nombre del Banco: </font><i><font color='#17202a'> " + deposito.getBanco() + "</font></i> <br>"
                + "<font color='#086A87'>No. de Cuenta: </font><i><font color='#17202a'> " + deposito.getNocuenta() + "</font></i> <br>"
                + "<font color='#086A87'>Cuenta CLABE: </font><i><font color='#17202a'> " + deposito.getClabe() + "</font></i> <br>"
                + "<font color='#086A87'>Nombre del Titular: </font><i><font color='#17202a'> " + deposito.getTitular() + "</font></i> <br>"
                + "<font color='#086A87'>RFC del Proveedor: </font><i><font color='#17202a'> " + us.getRfc() + "</font></i> <br>"
                + "<font color='#086A87'>Correo predeterminado: </font><i><font color='#17202a'> " + us.getCorreo() + "</font></i> <br>"
                + "<font color='#086A87'>Correo Alternativo: </font><i><font color='#17202a'> " + deposito.getCorreo() + "</font></i> <br>"
                + "<font color='#086A87'>Fecha de Solicitud: </font><i><font color='#17202a'> " + this.fecSol + "</font></i> <br>"
                + "<font color='#17202a'></font> <font color='#086A87'><i>  </i></font><br><font color='black'><b></b></font><br></td>"
                + "</tr>"
                + "<tr>"
                + "<td width='725' bordercolor='#FFFFFF'><p align='left' style='font-family:calibri; font-size:17px'><br><font color='#17202a'>Portal Proveedores | </font><font color='#E60013'>Coloidales Duch&eacute;, S.A. de C.V.</font><br>"
                + "<a href='http://desamexproveedores.dyndns.info:9088/proveedores/' target='_blank'><img src='cid:image' width='20%'/></a></td>"
                + "<td width='422' bordercolor='#FFFFFF'></td>"
                + "<td width='422' rowspan='2' bordercolor='#FFFFFF'></td>"
                + "</tr>"
                + "<tr>"
                + "<td colspan='2' bordercolor='#17202a'><br><br><p align='center' style='font-family:calibri; font-size:15px'><font color='#086A87'><br>Atentamente, Mensajería Portal Proveedores Coloidales Duch&eacute;.</font></p></td>"
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
        message.addRecipient(Message.RecipientType.TO, new InternetAddress("cuentasporpagartoluca@duche.com"));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress("cuentasporpagarmexico@duche.com"));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress("amendoza@duche.com"));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress("bcarrillo@duche.com"));
        message.addRecipient(Message.RecipientType.CC, new InternetAddress("ggutierrez@duche.com"));
        message.addRecipient(Message.RecipientType.CC, new InternetAddress(us.getCorreo()));
//        message.addRecipient(Message.RecipientType.CC, new InternetAddress(deposito.getCorreo()));

// Se rellena el subject
        message.setSubject("Notificación de Actualización de Datos de Depósito");

// Se mete el texto y la foto adjunta.
        message.setContent(multiParte);

        Transport t = session.getTransport("smtp");
        t.connect("proveedor@duche.com", "turtle");
        t.sendMessage(message, message.getAllRecipients());
        t.close();

    }

}
