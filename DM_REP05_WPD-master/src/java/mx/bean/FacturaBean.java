package mx.bean;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import mx.dao.DAO;
import mx.dao.FacturaDao;
import mx.dao.FacturaDaoImpl;
import mx.facturaJasper.reporteCFDI;
import mx.model.Factura;
import mx.model.Usuario;
import org.primefaces.context.RequestContext;

@Named(value = "facturaBean")
@ViewScoped
public class FacturaBean extends DAO implements Serializable {

    private Factura factura;
    private List<Factura> listaFactura;
    private List<Factura> listaAdminFactura;
    private String aviso;
    private String manual;
    private int contador = 0;
    private String nombre;
    private String claveProv;
    private String uuid;

    public FacturaBean() {
        factura = new Factura();
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public List<Factura> getListaFactura() throws SQLException {
        //mostrarManual();
        //mostrarAviso();
        FacturaDao fDao = new FacturaDaoImpl();
        listaFactura = fDao.listaFactura();
        return listaFactura;
    }

    public void setListaFactura(List<Factura> listaFactura) {
        this.listaFactura = listaFactura;
    }

    public List<Factura> getListaAdminFactura() {
        FacturaDao fDao = new FacturaDaoImpl();
        listaAdminFactura = fDao.listaAdminFactura();
        return listaAdminFactura;
    }

    public void setListaAdminFactura(List<Factura> listaAdminFactura) {
        this.listaAdminFactura = listaAdminFactura;
    }

    public String getAviso() {
        return aviso;
    }

    public void setAviso(String aviso) {
        this.aviso = aviso;
    }

    public int getContador() {
        return contador;
    }

    public void setContador(int contador) {
        this.contador = contador;
    }

    public String getManual() {
        return manual;
    }

    public void setManual(String manual) {
        this.manual = manual;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getClaveProv() {
        return claveProv;
    }

    public void setClaveProv(String claveProv) {
        this.claveProv = claveProv;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public void mostrarManual() throws SQLException {
        this.Conectarprov();
        Statement st = this.getCnprov().createStatement();
        ResultSet rs = st.executeQuery("SELECT TOP(1) RUTA FROM COMUNICADO WHERE ID=3");
        if (!rs.isBeforeFirst()) {
        } else {
            while (rs.next()) {
                this.manual = rs.getString("RUTA");
            }
        }
        this.Cerrarprov();
    }

    public void cancelarFactura() throws SQLException, MessagingException {
        FacturaDao fDao = new FacturaDaoImpl();
        fDao.UpdateFactura(factura);
        actualizarPagaM01();
        correoCancelar();
        factura = new Factura();
        RequestContext.getCurrentInstance().execute("PF('dlgEdit').hide()");
        RequestContext.getCurrentInstance().update("frmPrincipal");
    }

    public void actualizarPagaM01() throws SQLException {
        this.Conectar();
        PreparedStatement ps = this.getCn().prepareStatement("UPDATE PAGA_M01 SET REFER='WCXP" + factura.getFoliowcxp() + "-CANCELADO', DOCTO='WCXP" + factura.getFoliowcxp() + "-CANCELADO', IMPORTE=0, IMPMON_EXT=0 WHERE REFER='WCXP" + factura.getFoliowcxp() + "'");
        ps.executeUpdate();
        this.Cerrar();
    }

    public void buscarNombre() throws SQLException {
        this.Conectar();
        Usuario us = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("nombre");
        Statement st = this.getCn().createStatement();
        ResultSet rs = st.executeQuery("SELECT CLAVE, NOMBRE FROM PROV01 WHERE RFC='" + us.getRfc() + "' AND STATUS='A'");
        while (rs.next()) {
            this.claveProv = rs.getString("CLAVE");
            this.nombre = rs.getString("NOMBRE");
        }
        this.Cerrar();
    }

    public void correoCancelar() throws AddressException, MessagingException, SQLException {
        buscarNombre();
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
                + "<td colspan='3' bordercolor='#FFFFFF'><p align='left' style='font-family:calibri; font-size:17px'><font color='#086A87'>El proveedor ha cancelado la siguiente factura/Folio Fiscal: <i><font color='#17202a'>" + factura.getFactura() + "</font></i><br></font>"
                + "<font color='#086A87'>No. de recepci&oacute;n: </font><i><font color='#17202a'>" + factura.getReferencia() + "</font></i> <br>"
                + "<font color='#086A87'>Clave y RFC proveedor: </font><i><font color='#17202a'>" + this.claveProv + "/" + this.nombre + "</font></i><br>"
                + "<font color='#086A87'>Estatus factura: </font><i><font color='#E60013'><b>CANCELADA</b></font></i><br>"
                + "<font color='#086A87'>Comentario proveedor: </font><i><font color='#17202a'>" + factura.getComentarioProveedor() + "</font></i><br>"
                + "<font color='#086A87'>Cuenta por pagar No.: </font><i><font color='#17202a'>WXCP" + factura.getFoliowcxp() + "</font></i><br>"
                + "<font color='#17202a'>Favor de darle seguimiento</font> <font color='#086A87'><i>  </i></font><br><font color='black'><b></b></font><br></td>"
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

//        BodyPart adjunto = new MimeBodyPart();
//        adjunto.setDataHandler(new DataHandler(new FileDataSource("C:\\PROVEEDORES\\JasperReports - poliza.pdf")));
//        adjunto.setFileName("Test.pdf");
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
        //message.addRecipients(Message.RecipientType.TO, this.correo);
        message.addRecipient(Message.RecipientType.TO, new InternetAddress("cuentasporpagartoluca@duche.com"));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress("cuentasporpagarmexico@duche.com"));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress("amendoza@duche.com"));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress("bcarrillo@duche.com"));
        message.addRecipient(Message.RecipientType.CC, new InternetAddress("ggutierrez@duche.com"));

// Se rellena el subject
        message.setSubject("Notificación de Cancelación de Factura");

// Se mete el texto y la foto adjunta.
        message.setContent(multiParte);

        Transport t = session.getTransport("smtp");
        t.connect("proveedor@duche.com", "turtle");
        t.sendMessage(message, message.getAllRecipients());
        t.close();

    }

    public void visualizarCFDI_PDF() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        uuid = request.getParameter("frmVerXML:uuid");
        reporteCFDI rCliente = new reporteCFDI();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
        String ruta = servletContext.getRealPath("/CFDI/facturaPDF.jasper");
        //rCliente.getReporte(ruta, this.viaje.getNombreViajero(), this.viaje.getFolioUsuario());
        rCliente.getReporte(ruta, uuid);
        //rCliente.getReporte(ruta, this.codigo, this.folio);
        FacesContext.getCurrentInstance().responseComplete();

    }
    public void visualizarCFDI_COMP() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        uuid = request.getParameter("frmVerComp:uuidrel");
        reporteCFDI rCliente = new reporteCFDI();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
        String ruta = servletContext.getRealPath("/CFDI/com_pago.jasper");
        //rCliente.getReporte(ruta, this.viaje.getNombreViajero(), this.viaje.getFolioUsuario());
        rCliente.getReporte(ruta, uuid);
        //rCliente.getReporte(ruta, this.codigo, this.folio);
        FacesContext.getCurrentInstance().responseComplete();

    }

}
