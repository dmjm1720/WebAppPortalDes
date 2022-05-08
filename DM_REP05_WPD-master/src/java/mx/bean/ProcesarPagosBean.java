package mx.bean;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
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

@Named(value = "pagos")
@ViewScoped
public class ProcesarPagosBean extends DAO implements Serializable {

    private String CVE_PROV;
    private String NO_FACTURA;
    private String REFER;
    private String DOCTO;
    private String FECHA_APLI;
    private String IMPORTE;
    private String FACTURA;
    private String UUID;
    private String REFERENCIA;
    private String NOMBRE_E;
    private String RFC_E;
    private String CORREO;
    private String FOLIO;
    private String FECHA;
    private String NUM_CHEQUE;
    private int ID;
    private int NUM_CPTO;
    private int CONC_SAE;
    private String REF1;
    private String fecPag;

    public String getCVE_PROV() {
        return CVE_PROV;
    }

    public void setCVE_PROV(String CVE_PROV) {
        this.CVE_PROV = CVE_PROV;
    }

    public String getNO_FACTURA() {
        return NO_FACTURA;
    }

    public void setNO_FACTURA(String NO_FACTURA) {
        this.NO_FACTURA = NO_FACTURA;
    }

    public String getREFER() {
        return REFER;
    }

    public void setREFER(String REFER) {
        this.REFER = REFER;
    }

    public String getDOCTO() {
        return DOCTO;
    }

    public void setDOCTO(String DOCTO) {
        this.DOCTO = DOCTO;
    }

    public String getFECHA_APLI() {
        return FECHA_APLI;
    }

    public void setFECHA_APLI(String FECHA_APLI) {
        this.FECHA_APLI = FECHA_APLI;
    }

    public String getIMPORTE() {
        return IMPORTE;
    }

    public void setIMPORTE(String IMPORTE) {
        this.IMPORTE = IMPORTE;
    }

    public String getFACTURA() {
        return FACTURA;
    }

    public void setFACTURA(String FACTURA) {
        this.FACTURA = FACTURA;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getREFERENCIA() {
        return REFERENCIA;
    }

    public void setREFERENCIA(String REFERENCIA) {
        this.REFERENCIA = REFERENCIA;
    }

    public String getNOMBRE_E() {
        return NOMBRE_E;
    }

    public void setNOMBRE_E(String NOMBRE_E) {
        this.NOMBRE_E = NOMBRE_E;
    }

    public String getRFC_E() {
        return RFC_E;
    }

    public void setRFC_E(String RFC_E) {
        this.RFC_E = RFC_E;
    }

    public String getCORREO() {
        return CORREO;
    }

    public void setCORREO(String CORREO) {
        this.CORREO = CORREO;
    }

    public String getFOLIO() {
        return FOLIO;
    }

    public void setFOLIO(String FOLIO) {
        this.FOLIO = FOLIO;
    }

    public String getFECHA() {
        return FECHA;
    }

    public void setFECHA(String FECHA) {
        this.FECHA = FECHA;
    }

    public String getNUM_CHEQUE() {
        return NUM_CHEQUE;
    }

    public void setNUM_CHEQUE(String NUM_CHEQUE) {
        this.NUM_CHEQUE = NUM_CHEQUE;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getNUM_CPTO() {
        return NUM_CPTO;
    }

    public void setNUM_CPTO(int NUM_CPTO) {
        this.NUM_CPTO = NUM_CPTO;
    }

    public int getCONC_SAE() {
        return CONC_SAE;
    }

    public void setCONC_SAE(int CONC_SAE) {
        this.CONC_SAE = CONC_SAE;
    }

    public String getREF1() {
        return REF1;
    }

    public void setREF1(String REF1) {
        this.REF1 = REF1;
    }

    public String getFecPag() {
        return fecPag;
    }

    public void setFecPag(String fecPag) {
        this.fecPag = fecPag;
    }

    public ProcesarPagosBean() {

    }

    public void procesarPagos() throws MessagingException {
        pagoProveedores();
        pagoGastos();
    }

    public void pagoProveedores() throws MessagingException {
        try {
            this.Conectar();//Conexión SAE
            this.Conectarprov();//Conexión Portal
            this.ConectarBan();//Conexión Bancos
            Statement st = this.getCn().createStatement();
            ResultSet rs = st.executeQuery("SELECT CVE_PROV, REFER, FECHA_APLI, DOCTO, NUM_CPTO, NO_FACTURA FROM INSOFTEC_AVISOS_PAGO WHERE PROCESADO=0 AND REFER LIKE '%WCXP%' AND NUM_CPTO=27 OR NUM_CPTO=11");
            if (!rs.isBeforeFirst()) {
                System.out.println("Ningún pago detectado en sistema...");
            } else {
                while (rs.next()) {
                    System.out.println("Pago detectado en sistema...");
                    this.CVE_PROV = rs.getString("CVE_PROV");
                    this.REFER = rs.getString("REFER");
                    this.FECHA_APLI = rs.getString("FECHA_APLI");
                    this.DOCTO = rs.getString("DOCTO");
                    this.NUM_CPTO = rs.getInt("NUM_CPTO");
                    this.NO_FACTURA = rs.getString("NO_FACTURA");
                    System.out.println(this.CVE_PROV + "| " + this.REFER + "| " + this.FECHA_APLI);

                    System.out.println("Buscando información del importe pagado...");
                    Statement st1 = this.getCn().createStatement();
                    ResultSet rs1 = st1.executeQuery("SELECT IMPORTE FROM PAGA_M01 WHERE REFER='" + this.REFER + "' AND CVE_PROV='" + this.CVE_PROV + "'");
                    if (!rs1.isBeforeFirst()) {
                        System.out.println("____________________________________");
                    } else {
                        while (rs1.next()) {
                            this.IMPORTE = rs1.getString("IMPORTE");
                            System.out.println(this.IMPORTE);
                        }
                    }

                    System.out.println("Buscando datos de la factura pagada...");
                    Statement st2 = this.getCnprov().createStatement();
                    ResultSet rs2 = st2.executeQuery("SELECT FACTURA, UUID, REFERENCIA, NOMBRE_E, RFC_E  FROM FACTURA WHERE FOLIOWCXP='" + this.REFER.replace("WCXP", "") + "'");
                    if (!rs2.isBeforeFirst()) {
                        System.out.println("____________________________________");
                    } else {
                        while (rs2.next()) {
                            this.FACTURA = rs2.getString("FACTURA");
                            this.UUID = rs2.getString("UUID");
                            this.REFERENCIA = rs2.getString("REFERENCIA");
                            this.NOMBRE_E = rs2.getString("NOMBRE_E");
                            this.RFC_E = rs2.getString("RFC_E");
                            System.out.println(this.FACTURA + "| " + this.UUID + "| " + this.REFERENCIA + "| " + this.NOMBRE_E + "| " + this.RFC_E);
                        }
                    }

                    System.out.println("Buscando correo de la factura pagada...");
                    Statement st3 = this.getCnprov().createStatement();
                    ResultSet rs3 = st3.executeQuery("SELECT TOP(1) CORREO FROM USUARIO WHERE RFC='" + this.RFC_E + "'");
                    if (!rs3.isBeforeFirst()) {
                        System.out.println("____________________________________");
                    } else {
                        while (rs3.next()) {
                            this.CORREO = rs3.getString("CORREO");
                            System.out.println(this.CORREO);
                        }
                    }
                    System.out.println("Enviando aviso de pago a proveedor...");
                    //EnviarCorreo();

//                    //BUSCAR FECHA DE PAGO EN BANCOS
//                    Statement stfecban = this.getCnban().createStatement();
//                    ResultSet rsfecban = stfecban.executeQuery("SELECT TOP(1) FECHA FROM MOVS04 WHERE RFC='" + this.RFC_E + "' AND REF1 LIKE '%" + this.DOCTO.trim() + "%' ORDER BY NUM_REG DESC");
//                    if (!rsfecban.isBeforeFirst()) {
//                        System.out.println("Dato no encontrado");
//                    } else {
//                        while (rsfecban.next()) {
//                            this.fecPag = rsfecban.getString("FECHA");
//                        }
//                    }

                    //VALIDAMOS SI ES TRANSFERENCIA (27) O CHEQUE(11)
                    if (NUM_CPTO == 11) {
                        //PreparedStatement ps = this.getCnprov().prepareStatement("UPDATE FACTURA SET ESTATUS='PAGADA', CHEQUE='" + DOCTO + "', FECHA_PAGO='" + this.fecPag + "', TIPO_PAGO='CHEQUE' WHERE FOLIOWCXP='" + this.REFER.replace("WCXP", "") + "'");
                        PreparedStatement ps = this.getCnprov().prepareStatement("UPDATE FACTURA SET ESTATUS='PAGADA', CHEQUE='" + DOCTO + "', FECHA_PAGO='" + this.FECHA_APLI + "', TIPO_PAGO='CHEQUE' WHERE FOLIOWCXP='" + this.REFER.replace("WCXP", "") + "'");
                        ps.executeUpdate();

                    } else {
                        PreparedStatement ps = this.getCnprov().prepareStatement("UPDATE FACTURA SET ESTATUS='PAGADA', NO_TRANSFERENCIA='" + DOCTO + "', FECHA_PAGO='" + this.FECHA_APLI + "', TIPO_PAGO='TRANSFERENCIA' WHERE FOLIOWCXP='" + this.REFER.replace("WCXP", "") + "'");
                        //PreparedStatement ps = this.getCnprov().prepareStatement("UPDATE FACTURA SET ESTATUS='PAGADA', NO_TRANSFERENCIA='" + DOCTO + "', FECHA_PAGO='" + this.fecPag + "', TIPO_PAGO='TRANSFERENCIA' WHERE FOLIOWCXP='" + this.REFER.replace("WCXP", "") + "'");
                        ps.executeUpdate();
                    }

                    PreparedStatement ps1 = this.getCn().prepareStatement("UPDATE INSOFTEC_AVISOS_PAGO SET PROCESADO=1  WHERE CVE_PROV='" + this.CVE_PROV + "' AND REFER ='" + this.REFER + "'");
                    ps1.executeUpdate();
                    System.out.println("Proceso actualizado correctamente...");
                    Limpiar();
                }
            }
            this.CerrarBan();
            this.Cerrar();
            this.Cerrarprov();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void pagoGastos() {
        try {
            this.Conectarprov();//Conexión Portal
            this.ConectarBan();//Conexión Bancos
            //**BUSCAMOS FACTURAS EN LA BASE DE DATOS DEL PORTAL FACTURA_GASTOS **//
            Statement stPortal = this.getCnprov().createStatement();
            ResultSet rsPortal = stPortal.executeQuery("SELECT ID, FACTURA, FOLIO, RFC_E, TOTAL FROM FACTURA_GASTOS WHERE PROCESADO IS NULL OR PROCESADO=0");

            if (!rsPortal.isBeforeFirst()) {
                System.out.println("____________________________________");
            } else {
                while (rsPortal.next()) {
                    this.ID = rsPortal.getInt("ID");
                    this.FACTURA = rsPortal.getString("FACTURA").replaceAll("[^0-9.]", "");
                    this.FOLIO = rsPortal.getString("FOLIO");
                    this.RFC_E = rsPortal.getString("RFC_E");
                    //**BUSCAMOS DATOS DEL CHEQUE EN LA BASE DE DATOS BANCOS MOVS04**//
                    Statement stBan = this.getCnban().createStatement();
                    ResultSet rsBan = stBan.executeQuery("SELECT NUM_CHEQUE, FECHA, CONC_SAE, REF1 FROM MOVS04 WHERE RFC='" + RFC_E + "' AND REF2 LIKE'%" + FACTURA + "%' AND FECHA>'2020'");
                    if (!rsBan.isBeforeFirst()) {
                        System.out.println("____________________________________");
                    } else {
                        while (rsBan.next()) {
                            this.NUM_CHEQUE = rsBan.getString("NUM_CHEQUE");
                            this.FECHA = rsBan.getString("FECHA");
                            this.CONC_SAE = rsBan.getInt("CONC_SAE");
                            this.REF1 = rsBan.getString("REF1");
                            /*
                            SI EXISTE ACTUALIZAMOS EL #CHEQUE, FECHA DE PAGO EN LA BD DE FACTURA_GASTOS, CAMBI
                            ACTUALIZAMOS EL ESTADO 1 EN EL CAMPO PROCESADO
                             */
                            //VALIDAMOS SI ES TRANSFERENCIA (27) O CHEQUE(11)
                            if (CONC_SAE == 0) {
                                PreparedStatement ps = this.getCnprov().prepareStatement("UPDATE FACTURA_GASTOS SET FECHA_PAGO='" + FECHA + "', CHEQUE='" + NUM_CHEQUE + "', PROCESADO=1, ESTATUS='PAGADA' WHERE ID=" + ID + "");
                                ps.executeUpdate();
                            } else {
                                PreparedStatement ps = this.getCnprov().prepareStatement("UPDATE FACTURA_GASTOS SET FECHA_PAGO='" + FECHA + "', NO_TRANSFERENCIA='" + REF1 + "', PROCESADO=1, ESTATUS='PAGADA' WHERE ID=" + ID + "");
                                ps.executeUpdate();
                            }

                        }
                    }
                }
            }

            this.CerrarBan();
            this.Cerrarprov();
        } catch (SQLException ex) {

        }

    }

    public void EnviarCorreo() throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.alestraune.net.mx");
        props.setProperty("mail.smtp.starttls.enable", "true");
        props.setProperty("mail.smtp.port", "587");
        props.setProperty("mail.smtp.user", "portalproveedores@duche.com");
        props.setProperty("mail.smtp.auth", "true");
        Session session = Session.getDefaultInstance(props, null);
        session.setDebug(true);

        BodyPart texto = new MimeBodyPart();
        texto.setContent("<html><head><title></title></head>"
                + "<body>"
                + "<table width='878' height='315' border='0' bordercolor='#0000FF' bgcolor='#FFFFFF'>"
                + "<tr>"
                + "<td height='50' colspan='3' bordercolor='#FFFFFF'><br><br></td>"
                + "</tr>"
                + "<tr>"
                + "<td colspan='3' bordercolor='#FFFFFF'><p align='left' style='font-family:calibri; font-size:17px'><font color='#004177'>ESTIMADO PROVEEDOR:  </font><br>"
                + "<font color='#17202a'></font><i><font color='#004177'> " + this.NOMBRE_E + " | " + this.RFC_E + "</font></i><br><br>"
                + "<font color='#17202a'></font><i><font color='#17202a'><b>Nuestro sistema ha pagado el siguiente documento:</b></font></i> <br>"
                + "<font color='#17202a'></font> <font color='#086A87'><i></i></font><br>"
                + "<font color='#17202a'>Factura/Folio fiscal:</font> <font color='#004177'><i> " + this.FACTURA + " | " + this.UUID + " </i></font><br>"
                + "<font color='#17202a'>Recepci&oacute;n:</font> <font color='#004177'><i> " + this.REFERENCIA + " </i></font><br>"
                + "<font color='#641e16'><b>NO TRANSFERENCIA/CHEQUE:</font></b> <b><font color='#641e16'><i> " + this.DOCTO + " </i></b></font><br>"
                + "<font color='#17202a'>Cuenta por pagar no:</font> <font color='#004177'><i> " + this.REFER + " </i></font><br>"
                + "<font color='#17202a'>Monto pagado:</font> <font color='#004177'><i> $" + this.IMPORTE + " </i></font><br>"
                + "<font color='#17202a'></font> <font color='#004177'><i> </i></font><br>"
                + "<font color='#17202a'></font> <font color='#004177'><i></i></font><br><br>"
                + "<font color='#17202a'><p></p></font><br>"
                + "<font color='#004177'><b>PORTAL PROVEEDORES | </font><font color='#004177'>COLOIDALES DUCH&Eacute;, S.A. DE C.V.</b></font></td>"
                + "</tr>"
                + "<tr>"
                + "<td width='725' bordercolor='#FFFFFF'><p align='left' style='font-family:calibri; font-size:17px'><br><br>"
                + "<a href='http://desamexproveedores.dyndns.info:9088/proveedores/' target='_blank'><img src='cid:image' width='20%'/></a></td>"
                + "<td width='422' bordercolor='#FFFFFF'></td>"
                + "<td width='422' rowspan='2' bordercolor='#FFFFFF'></td>"
                + "</tr>"
                + "<tr>"
                + "<td colspan='2' bordercolor='#17202a'><br><br><p align='justify' style='font-family:calibri; font-size:16px'>"
                + "<font color='#004177'><br> Favor de no responder a este correo ya que es un aviso del sistema, "
                + "si tiene alguna duda favor de contactar al &aacute;rea de Atenci&oacute;n a proveedores:<br>cuentasporpagartoluca@duche.com<br> "
                + "cuentasporpagarmexico@duche.com<br> amendoza@duche.com<br> bcarrillo@duche.com<br></font></p></td>"
                + "</tr>"
                + "</table>"
                + "</body></html>", "text/html");

        MimeMultipart multiParte = new MimeMultipart();
        BodyPart imagen = new MimeBodyPart();
        DataSource fds = new FileDataSource("C:\\img\\duche.png");
//        DataSource fds = new FileDataSource("/home/dmsistemas/Escritorio/logo2.png");
        imagen.setDataHandler(new DataHandler(fds));
        imagen.setHeader("Content-ID", "<image>");

        multiParte.addBodyPart(texto);
        // multiParte.addBodyPart(adjunto);
        multiParte.addBodyPart(imagen);

        MimeMessage message = new MimeMessage(session);

// Se rellena el From
        message.setFrom(new InternetAddress("portalproveedores@duche.com"));

// Se rellenan los destinatarios
        //message.addRecipients(Message.RecipientType.TO, us.getCorreo());
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(this.CORREO));
        message.addRecipient(Message.RecipientType.CC, new InternetAddress("duche.proveedores@gmail.com"));
//        message.addRecipient(Message.RecipientType.CC, new InternetAddress("cuentasporpagartoluca@duche.com"));
//        message.addRecipient(Message.RecipientType.CC, new InternetAddress("cuentasporpagarmexico@duche.com"));
//        message.addRecipient(Message.RecipientType.CC, new InternetAddress("amendoza@duche.com"));
//        message.addRecipient(Message.RecipientType.CC, new InternetAddress("bcarrillo@duche.com"));

// Se rellena el subject
        message.setSubject("AVISO DE PAGO DE FACTURA");

// Se mete el texto y la foto adjunta.
        message.setContent(multiParte);

        Transport t = session.getTransport("smtp");
        t.connect("portalproveedores@duche.com", "ML310gen11");
        t.sendMessage(message, message.getAllRecipients());
        t.close();
    }

    public void Limpiar() {
        CVE_PROV = "";
        NO_FACTURA = "";
        REFER = "";
        DOCTO = "";
        FECHA_APLI = "";
        IMPORTE = "";
        FACTURA = "";
        UUID = "";
        REFERENCIA = "";
        NOMBRE_E = "";
        RFC_E = "";
        CORREO = "";
        FOLIO = "";
        fecPag = "";
    }

}
