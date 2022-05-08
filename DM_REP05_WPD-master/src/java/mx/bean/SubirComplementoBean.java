package mx.bean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import java.net.URL;
import java.util.Date;
import mx.dao.ConceptoCompDao;
import mx.dao.ConceptoCompDaoImpl;
import mx.dao.ConceptoCompPagoDao;
import mx.dao.ConceptoCompPagoDaoImpl;
import mx.dao.FacturaCompDao;
import mx.dao.FacturaDaoCompDaoImpl;
import mx.dao.PagoComDao;
import mx.dao.PagoComDaoImpl;
import mx.model.ConceptoComplemento;
import mx.model.ConceptoPagosComp;
import mx.model.FacturaComplemento;
import mx.model.Pago;

//Web Service SAT 
import mx.sat.Acuse;
import mx.sat.ConsultaCFDIService;
import mx.sat.IConsultaCFDIService;

@Named(value = "subirComplementoBean")
@ViewScoped
public class SubirComplementoBean extends DAO implements Serializable {

    private FacturaComplemento f;
    private ConceptoComplemento part;
    private ConceptoPagosComp pag;
    private List<FacturaComplemento> listaFactura;
    private List<String> listaPagoComp;
    private String referencia;
    private String validarReferencia;
    private String validarFactura;
    private String validarUUID;
    private String cveprov;
    private String CVE_DOC;
    private String SU_REFER;
    private float CAN_TOT;
    private int NUM_MONED;
    private float TIPCAMB;
    private float IMPORTE;
    private String avisoCorreo;
    private String uuid;
    private String mes;
    private String año;
    private String miFecha;

    private String serie;
    private String folio;
    private String fecha;
    private String sello;
    private String formaDePago;
    private String noCertificado;
    private String certificado;
    private String subTotal;
    private String TipoCambio;
    private String moneda;
    private String total;
    private String tipoDeComprobante;
    private String metodoDePago;
    private String LugarExpedicion;
    private String rfcE;
    private String nombreE;
    private String rfcR;
    private String nombreR;
    private String cantidad;
    private String unidad;
    private String descripcion;
    private String valorUnitario;
    private String importe;
    private String RegimenFiscal;
    private String Regimen;
    private String UsoCFDI;
    private String BaseTraslado;
    private String Impuesto;
    private String TipoFactor;
    private String TasaOCuota;
    private String ImporteTraslado;
    private String Version;
    private String FechaTimbrado;
    private String RfcProvCertif;
    private String SelloCFD;
    private String NoCertificadoSAT;
    private String SelloSAT;
    private String UUIDTF;
    private String VersionSAT;
    private String nombreCFDI;
    private String calleDF;
    private String noExteriorDF;
    private String noInteriorDF;
    private String coloniaDF;
    private String municipioDF;
    private String estadoDF;
    private String paisDF;
    private String codigoPostalDF;
    private String calle;
    private String noExterior;
    private String noInterior;
    private String colonia;
    private String municipio;
    private String estado;
    private String pais;
    private String codigoPostal;
    private String impuestoRet;
    private String importeRet;
    private String TotalCargos;
    private String CodigoCargoOC;
    private String importeOC;
    private int validarMoneda;
    private String mostrarMoneda;
    private int diasCredito;
    private Calendar hoy;
    private String pago;
    private int dia;
    private int folioWcxp;
    private float miTotal;
    private String miPago;
    private String miReferencia;
    private String ClaveProdServ;
    private String ClaveUnidad;
    private String facturaSAE;
    private int tamcadena;
    private String condPago;
    //variables para el CFDI
//    private final String ruta = "/home/dmsistemas/Escritorio/public/dir1/";
//    private final String rutaIp = "/home/dmsistemas/Escritorio/public/dir2/";
//    private final String ruta = "Z:\\public\\comprobantes\\";
//    private final String rutaIp = "Z:\\newPublic\\comprobantes\\";
    private final String ruta = "C:\\public\\comprobantes\\";
    private final String rutaIp = "C:\\newPublic\\comprobantes\\";
    private List<String> lista;
    private List<String> listaDoctoRel;
    private String miUUID;
    private Pago pagoComp;
    private String validadUUID;
    private String validadUUIDVacio = "SI";
    private String idDocUUID;

    //Web Service SAT
    private ConsultaCFDIService consulta;
    private IConsultaCFDIService respuesta;
    private Acuse acuse;

    public SubirComplementoBean() {
        this.lista = new ArrayList<>();
        this.listaDoctoRel = new ArrayList<>();
        this.listaPagoComp = new ArrayList<>();
        f = new FacturaComplemento();
        part = new ConceptoComplemento();
        pag = new ConceptoPagosComp();
        pagoComp = new Pago();
    }

    //variables para el CFDI
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getMiUUID() {
        return miUUID;
    }

    public void setMiUUID(String miUUID) {
        this.miUUID = miUUID;
    }

    public String getMiFecha() {
        return miFecha;
    }

    public void setMiFecha(String miFecha) {
        this.miFecha = miFecha;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getAño() {
        return año;
    }

    public void setAño(String año) {
        this.año = año;
    }

    public String getAvisoCorreo() {
        return avisoCorreo;
    }

    public void setAvisoCorreo(String avisoCorreo) {
        this.avisoCorreo = avisoCorreo;
    }

    public FacturaComplemento getF() {
        return f;
    }

    public void setF(FacturaComplemento f) {
        this.f = f;
    }

    public ConceptoComplemento getPart() {
        return part;
    }

    public void setPart(ConceptoComplemento part) {
        this.part = part;
    }

    public List<FacturaComplemento> getListaFacturaComplementos() {
        FacturaCompDao fDao = new FacturaDaoCompDaoImpl();
        listaFactura = fDao.listaFactura();
        return listaFactura;
    }

    public void setListaFactura(List<FacturaComplemento> listaFactura) {
        this.listaFactura = listaFactura;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getValidarReferencia() {
        return validarReferencia;
    }

    public void setValidarReferencia(String validarReferencia) {
        this.validarReferencia = validarReferencia;
    }

    public String getValidarFactura() {
        return validarFactura;
    }

    public void setValidarFactura(String validarFactura) {
        this.validarFactura = validarFactura;
    }

    public String getValidarUUID() {
        return validarUUID;
    }

    public void setValidarUUID(String validarUUID) {
        this.validarUUID = validarUUID;
    }

    public String getCveprov() {
        return cveprov;
    }

    public void setCveprov(String cveprov) {
        this.cveprov = cveprov;
    }

    public String getCVE_DOC() {
        return CVE_DOC;
    }

    public void setCVE_DOC(String CVE_DOC) {
        this.CVE_DOC = CVE_DOC;
    }

    public String getSU_REFER() {
        return SU_REFER;
    }

    public void setSU_REFER(String SU_REFER) {
        this.SU_REFER = SU_REFER;
    }

    public float getCAN_TOT() {
        return CAN_TOT;
    }

    public void setCAN_TOT(float CAN_TOT) {
        this.CAN_TOT = CAN_TOT;
    }

    public int getNUM_MONED() {
        return NUM_MONED;
    }

    public void setNUM_MONED(int NUM_MONED) {
        this.NUM_MONED = NUM_MONED;
    }

    public float getTIPCAMB() {
        return TIPCAMB;
    }

    public void setTIPCAMB(float TIPCAMB) {
        this.TIPCAMB = TIPCAMB;
    }

    public float getIMPORTE() {
        return IMPORTE;
    }

    public void setIMPORTE(float IMPORTE) {
        this.IMPORTE = IMPORTE;
    }

    //Get y Set varibales CFDI
    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getSello() {
        return sello;
    }

    public void setSello(String sello) {
        this.sello = sello;
    }

    public String getFormaDePago() {
        return formaDePago;
    }

    public void setFormaDePago(String formaDePago) {
        this.formaDePago = formaDePago;
    }

    public String getNoCertificado() {
        return noCertificado;
    }

    public void setNoCertificado(String noCertificado) {
        this.noCertificado = noCertificado;
    }

    public String getCertificado() {
        return certificado;
    }

    public void setCertificado(String certificado) {
        this.certificado = certificado;
    }

    public String getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(String subTotal) {
        this.subTotal = subTotal;
    }

    public String getTipoCambio() {
        return TipoCambio;
    }

    public void setTipoCambio(String TipoCambio) {
        this.TipoCambio = TipoCambio;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getTipoDeComprobante() {
        return tipoDeComprobante;
    }

    public void setTipoDeComprobante(String tipoDeComprobante) {
        this.tipoDeComprobante = tipoDeComprobante;
    }

    public String getMetodoDePago() {
        return metodoDePago;
    }

    public void setMetodoDePago(String metodoDePago) {
        this.metodoDePago = metodoDePago;
    }

    public String getLugarExpedicion() {
        return LugarExpedicion;
    }

    public void setLugarExpedicion(String LugarExpedicion) {
        this.LugarExpedicion = LugarExpedicion;
    }

    public String getRfcE() {
        return rfcE;
    }

    public void setRfcE(String rfcE) {
        this.rfcE = rfcE;
    }

    public String getNombreE() {
        return nombreE;
    }

    public void setNombreE(String nombreE) {
        this.nombreE = nombreE;
    }

    public String getRfcR() {
        return rfcR;
    }

    public void setRfcR(String rfcR) {
        this.rfcR = rfcR;
    }

    public String getNombreR() {
        return nombreR;
    }

    public void setNombreR(String nombreR) {
        this.nombreR = nombreR;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(String valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public String getImporte() {
        return importe;
    }

    public void setImporte(String importe) {
        this.importe = importe;
    }

    public String getRegimenFiscal() {
        return RegimenFiscal;
    }

    public void setRegimenFiscal(String RegimenFiscal) {
        this.RegimenFiscal = RegimenFiscal;
    }

    public String getRegimen() {
        return Regimen;
    }

    public void setRegimen(String Regimen) {
        this.Regimen = Regimen;
    }

    public String getUsoCFDI() {
        return UsoCFDI;
    }

    public void setUsoCFDI(String UsoCFDI) {
        this.UsoCFDI = UsoCFDI;
    }

    public String getBaseTraslado() {
        return BaseTraslado;
    }

    public void setBaseTraslado(String BaseTraslado) {
        this.BaseTraslado = BaseTraslado;
    }

    public String getImpuesto() {
        return Impuesto;
    }

    public void setImpuesto(String Impuesto) {
        this.Impuesto = Impuesto;
    }

    public String getTipoFactor() {
        return TipoFactor;
    }

    public void setTipoFactor(String TipoFactor) {
        this.TipoFactor = TipoFactor;
    }

    public String getTasaOCuota() {
        return TasaOCuota;
    }

    public void setTasaOCuota(String TasaOCuota) {
        this.TasaOCuota = TasaOCuota;
    }

    public String getImporteTraslado() {
        return ImporteTraslado;
    }

    public void setImporteTraslado(String ImporteTraslado) {
        this.ImporteTraslado = ImporteTraslado;
    }

    public String getVersion() {
        return Version;
    }

    public void setVersion(String Version) {
        this.Version = Version;
    }

    public String getFechaTimbrado() {
        return FechaTimbrado;
    }

    public void setFechaTimbrado(String FechaTimbrado) {
        this.FechaTimbrado = FechaTimbrado;
    }

    public String getRfcProvCertif() {
        return RfcProvCertif;
    }

    public void setRfcProvCertif(String RfcProvCertif) {
        this.RfcProvCertif = RfcProvCertif;
    }

    public String getSelloCFD() {
        return SelloCFD;
    }

    public void setSelloCFD(String SelloCFD) {
        this.SelloCFD = SelloCFD;
    }

    public String getNoCertificadoSAT() {
        return NoCertificadoSAT;
    }

    public void setNoCertificadoSAT(String NoCertificadoSAT) {
        this.NoCertificadoSAT = NoCertificadoSAT;
    }

    public String getSelloSAT() {
        return SelloSAT;
    }

    public void setSelloSAT(String SelloSAT) {
        this.SelloSAT = SelloSAT;
    }

    public String getUUIDTF() {
        return UUIDTF;
    }

    public void setUUIDTF(String UUIDTF) {
        this.UUIDTF = UUIDTF;
    }

    public String getVersionSAT() {
        return VersionSAT;
    }

    public void setVersionSAT(String VersionSAT) {
        this.VersionSAT = VersionSAT;
    }

    public String getCalleDF() {
        return calleDF;
    }

    public void setCalleDF(String calleDF) {
        this.calleDF = calleDF;
    }

    public String getNoExteriorDF() {
        return noExteriorDF;
    }

    public void setNoExteriorDF(String noExteriorDF) {
        this.noExteriorDF = noExteriorDF;
    }

    public String getNoInteriorDF() {
        return noInteriorDF;
    }

    public void setNoInteriorDF(String noInteriorDF) {
        this.noInteriorDF = noInteriorDF;
    }

    public String getColoniaDF() {
        return coloniaDF;
    }

    public void setColoniaDF(String coloniaDF) {
        this.coloniaDF = coloniaDF;
    }

    public String getMunicipioDF() {
        return municipioDF;
    }

    public void setMunicipioDF(String municipioDF) {
        this.municipioDF = municipioDF;
    }

    public String getEstadoDF() {
        return estadoDF;
    }

    public void setEstadoDF(String estadoDF) {
        this.estadoDF = estadoDF;
    }

    public String getPaisDF() {
        return paisDF;
    }

    public void setPaisDF(String paisDF) {
        this.paisDF = paisDF;
    }

    public String getCodigoPostalDF() {
        return codigoPostalDF;
    }

    public void setCodigoPostalDF(String codigoPostalDF) {
        this.codigoPostalDF = codigoPostalDF;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNoExterior() {
        return noExterior;
    }

    public void setNoExterior(String noExterior) {
        this.noExterior = noExterior;
    }

    public String getNoInterior() {
        return noInterior;
    }

    public void setNoInterior(String noInterior) {
        this.noInterior = noInterior;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getImpuestoRet() {
        return impuestoRet;
    }

    public void setImpuestoRet(String impuestoRet) {
        this.impuestoRet = impuestoRet;
    }

    public String getImporteRet() {
        return importeRet;
    }

    public void setImporteRet(String importeRet) {
        this.importeRet = importeRet;
    }

    public String getTotalCargos() {
        return TotalCargos;
    }

    public void setTotalCargos(String TotalCargos) {
        this.TotalCargos = TotalCargos;
    }

    public String getCodigoCargoOC() {
        return CodigoCargoOC;
    }

    public void setCodigoCargoOC(String CodigoCargoOC) {
        this.CodigoCargoOC = CodigoCargoOC;
    }

    public String getImporteOC() {
        return importeOC;
    }

    public void setImporteOC(String importeOC) {
        this.importeOC = importeOC;
    }

    //Termina Get y Set varibales CFDI
    public String getNombreCFDI() {
        return nombreCFDI;
    }

    public void setNombreCFDI(String nombreCFDI) {
        this.nombreCFDI = nombreCFDI;
    }

    public int getValidarMoneda() {
        return validarMoneda;
    }

    public void setValidarMoneda(int validarMoneda) {
        this.validarMoneda = validarMoneda;
    }

    public String getMostrarMoneda() {
        return mostrarMoneda;
    }

    public void setMostrarMoneda(String mostrarMoneda) {
        this.mostrarMoneda = mostrarMoneda;
    }

    public int getDiasCredito() {
        return diasCredito;
    }

    public void setDiasCredito(int diasCredito) {
        this.diasCredito = diasCredito;
    }

    public Calendar getHoy() {
        return hoy;
    }

    public void setHoy(Calendar hoy) {
        this.hoy = hoy;
    }

    public String getPago() {
        return pago;
    }

    public void setPago(String pago) {
        this.pago = pago;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getFolioWcxp() {
        return folioWcxp;
    }

    public void setFolioWcxp(int folioWcxp) {
        this.folioWcxp = folioWcxp;
    }

    public float getMiTotal() {
        return miTotal;
    }

    public void setMiTotal(float miTotal) {
        this.miTotal = miTotal;
    }

    public String getMiPago() {
        return miPago;
    }

    public void setMiPago(String miPago) {
        this.miPago = miPago;
    }

    public String getMiReferencia() {
        return miReferencia;
    }

    public void setMiReferencia(String miReferencia) {
        this.miReferencia = miReferencia;
    }

    public String getClaveProdServ() {
        return ClaveProdServ;
    }

    public void setClaveProdServ(String ClaveProdServ) {
        this.ClaveProdServ = ClaveProdServ;
    }

    public String getClaveUnidad() {
        return ClaveUnidad;
    }

    public void setClaveUnidad(String ClaveUnidad) {
        this.ClaveUnidad = ClaveUnidad;
    }

    public String getFacturaSAE() {
        return facturaSAE;
    }

    public void setFacturaSAE(String facturaSAE) {
        this.facturaSAE = facturaSAE;
    }

    public int getTamcadena() {
        return tamcadena;
    }

    public void setTamcadena(int tamcadena) {
        this.tamcadena = tamcadena;
    }

    public String getCondPago() {
        return condPago;
    }

    public void setCondPago(String condPago) {
        this.condPago = condPago;
    }

    public Pago getPagoComp() {
        return pagoComp;
    }

    public void setPagoComp(Pago pagoComp) {
        this.pagoComp = pagoComp;
    }

    public String getValidadUUID() {
        return validadUUID;
    }

    public void setValidadUUID(String validadUUID) {
        this.validadUUID = validadUUID;
    }

    public String getIdDocUUID() {
        return idDocUUID;
    }

    public void setIdDocUUID(String idDocUUID) {
        this.idDocUUID = idDocUUID;
    }

    public String getValidadUUIDVacio() {
        return validadUUIDVacio;
    }

    public void setValidadUUIDVacio(String validadUUIDVacio) {
        this.validadUUIDVacio = validadUUIDVacio;
    }

    public ConsultaCFDIService getConsulta() {
        return consulta;
    }

    public void setConsulta(ConsultaCFDIService consulta) {
        this.consulta = consulta;
    }

    public IConsultaCFDIService getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(IConsultaCFDIService respuesta) {
        this.respuesta = respuesta;
    }

    public Acuse getAcuse() {
        return acuse;
    }

    public void setAcuse(Acuse acuse) {
        this.acuse = acuse;
    }

    public void upload(FileUploadEvent event) throws SQLException, MessagingException, JDOMException, ParseException, InterruptedException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        try {

            copyFile(event.getFile().getFileName(), event.getFile().getInputstream());
            //FacesMessage msg = new FacesMessage("DECORADOS Y SATINADOS MEXICANOS S.A DE C.V. ", event.getFile().getFileName() + " Archivo subido correctamente");
            Usuario us = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("nombre");
            //FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (IOException e) {
        }

    }

    public void copyFile(String fileName, InputStream in) throws MessagingException, SQLException, ParseException, InterruptedException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Usuario us = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("nombre");
        //TOMAR LA FECHA ACTUAL
        Date ahora = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
        miFecha = formato.format(ahora);
        String[] nuevaFecha = miFecha.split("-");
        mes = nuevaFecha[1];
        año = nuevaFecha[2];
        //TOMAR LA FECHA ACTUAL
        File folder = new File(ruta + "\\" + us.getRfc().replace(" ", "") + "\\" + año + "\\" + mes);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        File folderip = new File(rutaIp + "\\" + us.getRfc().replace(" ", "") + "\\" + año + "\\" + mes);
        if (!folderip.exists()) {
            folderip.mkdirs();
        }
        try {
            OutputStream dirIp;
            try (OutputStream dir = new FileOutputStream(new File(ruta + "\\" + us.getRfc() + "\\" + año + "\\" + mes + "\\" + fileName))) {
                dirIp = new FileOutputStream(new File(rutaIp + "\\" + us.getRfc() + "\\" + año + "\\" + mes + "\\" + fileName));
                int read = 0;
                byte[] bytes = new byte[51200];
                while ((read = in.read(bytes)) != -1) {
                    dir.write(bytes, 0, read);
                    dirIp.write(bytes, 0, read);
                }
                this.nombreCFDI = fileName;
                leerCFDI();
                in.close();
                dir.flush();
                dirIp.flush();
            } catch (JDOMException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "DECORADOS Y SATINADOS MEXICANOS S.A DE C.V.", "El XML tiene problemas que impiden procesarlo. Error: " + ex.getMessage().replace("file:/E:/public/gastos/" + us.getRfc() + "/", "") + " Por favor revisa tu XML"));
                RequestContext.getCurrentInstance().execute("PF('dlgXML').hide()");
                in.close();
//Logger.getLogger(BuscarRecepcionBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "DECORADOS Y SATINADOS MEXICANOS S.A DE C.V.", "El XML tiene problemas que impiden procesarlo. Error: " + e.getMessage() + " Por favor revisa tu XML"));
            RequestContext.getCurrentInstance().execute("PF('dlgXML').hide()");
            try {
                in.close();
//System.out.println(e.getMessage());
            } catch (IOException ex) {
                Logger.getLogger(SubirComplementoBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void leerCFDI() throws JDOMException, IOException, SQLException, ParseException, InterruptedException, MessagingException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Usuario us = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("nombre");
        SAXBuilder builder = new SAXBuilder();
        File xmlFile = new File(ruta + "\\" + us.getRfc() + "\\" + año + "\\" + mes + "\\" + this.nombreCFDI);
        Document document = (Document) builder.build(xmlFile);
        Element rootNode = document.getRootElement();
        serie = rootNode.getAttributeValue("serie");
        if (this.serie == null) {
            serie = rootNode.getAttributeValue("Serie");
        }
        folio = rootNode.getAttributeValue("folio");
        if (this.folio == null) {
            folio = rootNode.getAttributeValue("Folio");
        }
        fecha = rootNode.getAttributeValue("fecha");
        if (this.fecha == null) {
            fecha = rootNode.getAttributeValue("Fecha");
        }
        sello = rootNode.getAttributeValue("sello");
        if (this.sello == null) {
            sello = rootNode.getAttributeValue("Sello");
        }
        formaDePago = rootNode.getAttributeValue("formaDePago");
        if (this.formaDePago == null) {
            formaDePago = rootNode.getAttributeValue("FormaPago");
        }
        noCertificado = rootNode.getAttributeValue("noCertificado");
        if (this.noCertificado == null) {
            noCertificado = rootNode.getAttributeValue("NoCertificado");
        }
        certificado = rootNode.getAttributeValue("certificado");
        if (this.certificado == null) {
            certificado = rootNode.getAttributeValue("Certificado");
        }

        condPago = rootNode.getAttributeValue("CondicionesDePago");
        subTotal = rootNode.getAttributeValue("subTotal");
        if (this.subTotal == null) {
            subTotal = rootNode.getAttributeValue("SubTotal");
        }
        TipoCambio = rootNode.getAttributeValue("tipoCambio");
        if (this.TipoCambio == null) {
            TipoCambio = rootNode.getAttributeValue("TipoCambio");
        }
        moneda = rootNode.getAttributeValue("moneda");
        if (this.moneda == null) {
            moneda = rootNode.getAttributeValue("Moneda");
        }
        total = rootNode.getAttributeValue("total");
        if (total == null) {
            total = rootNode.getAttributeValue("Total");
        }
        tipoDeComprobante = rootNode.getAttributeValue("tipoDeComprobante");
        if (this.tipoDeComprobante == null) {
            tipoDeComprobante = rootNode.getAttributeValue("TipoDeComprobante");
        }
        metodoDePago = rootNode.getAttributeValue("metodoDePago");
        if (this.metodoDePago == null) {
            metodoDePago = rootNode.getAttributeValue("MetodoPago");
        }
        LugarExpedicion = rootNode.getAttributeValue("LugarExpedicion");
        if (this.LugarExpedicion == null) {
            LugarExpedicion = rootNode.getAttributeValue("LugarExpedicion");
        }
        VersionSAT = rootNode.getAttributeValue("version");
        if (VersionSAT == null) {
            VersionSAT = rootNode.getAttributeValue("Version");
        }
        List list = rootNode.getChildren();
        for (int i = 0; i < list.size(); i++) {
            Element elementoCFDI = (Element) list.get(i);
            String valor = elementoCFDI.getName();
            if (valor.equals("Emisor")) {
                rfcE = elementoCFDI.getAttributeValue("rfc");
                if (rfcE == null) {
                    rfcE = elementoCFDI.getAttributeValue("Rfc");
                }
                nombreE = elementoCFDI.getAttributeValue("nombre");
                if (nombreE == null) {
                    nombreE = elementoCFDI.getAttributeValue("Nombre");
                }
                RegimenFiscal = elementoCFDI.getAttributeValue("RegimenFiscal");

            }
            if (valor.equals("Receptor")) {
                rfcR = elementoCFDI.getAttributeValue("rfc");
                if (rfcR == null) {
                    rfcR = elementoCFDI.getAttributeValue("Rfc");
                }
                nombreR = elementoCFDI.getAttributeValue("nombre");
                if (nombreR == null) {
                    nombreR = elementoCFDI.getAttributeValue("Nombre");
                }
                UsoCFDI = elementoCFDI.getAttributeValue("UsoCFDI");
            }
            List listaCampos = elementoCFDI.getChildren();
            for (int j = 0; j < listaCampos.size(); j++) {
                Element campo = (Element) listaCampos.get(j);
                String valor2 = campo.getName();

                if (valor2.equals("DomicilioFiscal")) {
                    this.calleDF = campo.getAttributeValue("calle");
                    this.noExteriorDF = campo.getAttributeValue("noExterior");
                    this.noInteriorDF = campo.getAttributeValue("noInterior");
                    this.coloniaDF = campo.getAttributeValue("colonia");
                    this.municipioDF = campo.getAttributeValue("municipio");
                    this.estadoDF = campo.getAttributeValue("estado");
                    this.paisDF = campo.getAttributeValue("pais");
                    this.codigoPostalDF = campo.getAttributeValue("codigoPostal");
                }
                if (valor2.equals("RegimenFiscal")) {
                    RegimenFiscal = campo.getAttributeValue("Regimen");
                }
                if (valor2.equals("Domicilio")) {

                    this.calle = campo.getAttributeValue("calle");
                    this.noExterior = campo.getAttributeValue("noExterior");
                    this.noInterior = campo.getAttributeValue("noInterior");
                    this.colonia = campo.getAttributeValue("colonia");
                    this.municipio = campo.getAttributeValue("municipio");
                    this.estado = campo.getAttributeValue("estado");
                    this.pais = campo.getAttributeValue("pais");
                    this.codigoPostal = campo.getAttributeValue("codigoPostal");
                }

                if (valor2.equals("TimbreFiscalDigital")) {
                    RfcProvCertif = campo.getAttributeValue("RfcProvCertif");
                    Version = campo.getAttributeValue("version");
                    if (Version == null) {
                        Version = campo.getAttributeValue("Version");
                    }
                    UUIDTF = campo.getAttributeValue("uuid");
                    if (UUIDTF == null) {
                        UUIDTF = campo.getAttributeValue("UUID");
                    }
                    this.miUUID = this.UUIDTF;
                    FechaTimbrado = campo.getAttributeValue("fechaTimbrado");
                    if (FechaTimbrado == null) {
                        FechaTimbrado = campo.getAttributeValue("FechaTimbrado");
                    }
                    SelloCFD = campo.getAttributeValue("selloCFD");
                    if (SelloCFD == null) {
                        SelloCFD = campo.getAttributeValue("SelloCFD");
                    }
                    NoCertificadoSAT = campo.getAttributeValue("noCertificadoSAT");
                    if (NoCertificadoSAT == null) {
                        NoCertificadoSAT = campo.getAttributeValue("NoCertificadoSAT");
                    }
                    SelloSAT = campo.getAttributeValue("selloSAT");
                    if (SelloSAT == null) {
                        SelloSAT = campo.getAttributeValue("SelloSAT");
                    }
                }
                if (valor2.equals("Concepto")) {
                    cantidad = campo.getAttributeValue("cantidad");
                    if (cantidad == null) {
                        cantidad = campo.getAttributeValue("Cantidad");//
                    }
                    unidad = campo.getAttributeValue("unidad");
                    if (unidad == null) {
                        unidad = campo.getAttributeValue("Unidad");
                    }

                    ClaveUnidad = campo.getAttributeValue("ClaveUnidad");

                    descripcion = campo.getAttributeValue("descripcion");
                    if (descripcion == null) {
                        descripcion = campo.getAttributeValue("Descripcion");//
                    }
                    valorUnitario = campo.getAttributeValue("valorUnitario");//
                    if (valorUnitario == null) {
                        valorUnitario = campo.getAttributeValue("ValorUnitario");
                    }
                    importe = campo.getAttributeValue("importe");
                    if (importe == null) {
                        importe = campo.getAttributeValue("Importe");
                    }
                    ClaveProdServ = campo.getAttributeValue("ClaveProdServ");
                    lista.add(cantidad);
                    lista.add(unidad);
                    lista.add(ClaveUnidad);
                    lista.add(descripcion);
                    lista.add(valorUnitario);
                    lista.add(importe);
                    lista.add(ClaveProdServ);
                    //Para almacenar los datos del concepto en una lista

                }

                List otros = campo.getChildren();
                for (int k = 0; k < otros.size(); k++) {
                    Element campo2 = (Element) otros.get(k);
                    String valor3 = campo.getName();

                    if (valor3.equals("Traslados")) {
                        Impuesto = campo2.getAttributeValue("impuesto");
                        if (Impuesto == null) {
                            Impuesto = campo2.getAttributeValue("Impuesto");
                        }
                        TasaOCuota = campo2.getAttributeValue("tasa");
                        if (TasaOCuota == null) {
                            TasaOCuota = campo2.getAttributeValue("TasaOCuota");
                        }
                        ImporteTraslado = campo2.getAttributeValue("importe");
                        if (ImporteTraslado == null) {
                            ImporteTraslado = campo2.getAttributeValue("Importe");
                        }
                        BaseTraslado = campo2.getAttributeValue("Base");
                    }
                    if (valor3.equals("Retenciones")) {
                        this.impuestoRet = campo2.getAttributeValue("impuesto");
                        this.importeRet = campo2.getAttributeValue("importe");
                    }

                    listaPagoComp.add(campo2.getAttributeValue("FechaPago"));
                    listaPagoComp.add(campo2.getAttributeValue("FormaDePagoP"));
                    listaPagoComp.add(campo2.getAttributeValue("MonedaP"));
                    listaPagoComp.add(campo2.getAttributeValue("Monto"));
                    listaPagoComp.add(campo2.getAttributeValue("NumOperacion"));
                    listaPagoComp.add(campo2.getAttributeValue("RfcEmisorCtaBen"));
                    listaPagoComp.add(campo2.getAttributeValue("CtaBeneficiario"));

                    if (valor3.equals("Pagos")) {

                        List comPagos = campo2.getChildren();
                        for (int c = 0; c < comPagos.size(); c++) {
                            Element camPa = (Element) comPagos.get(c);
                            String valorComple = camPa.getName();
                            if (valorComple.equals("DoctoRelacionado")) {

                                listaDoctoRel.add(camPa.getAttributeValue("IdDocumento"));
                                listaDoctoRel.add(camPa.getAttributeValue("Serie"));
                                listaDoctoRel.add(camPa.getAttributeValue("Folio"));
                                listaDoctoRel.add(camPa.getAttributeValue("MonedaDR"));
                                listaDoctoRel.add(camPa.getAttributeValue("MetodoDePagoDR"));
                                listaDoctoRel.add(camPa.getAttributeValue("NumParcialidad"));
                                listaDoctoRel.add(camPa.getAttributeValue("ImpSaldoAnt"));
                                listaDoctoRel.add(camPa.getAttributeValue("ImpPagado"));
                                listaDoctoRel.add(camPa.getAttributeValue("ImpSaldoInsoluto"));

                            }
                        }
                    }

                    if (valor3.equals("Aerolineas")) {
                        this.TotalCargos = campo2.getAttributeValue("TotalCargos");
                        List otros2 = campo2.getChildren();
                        for (int l = 0; l < otros2.size(); l++) {
                            Element campo3 = (Element) otros2.get(l);
                            String valor4 = campo2.getName();
                            if (valor4.equals("OtrosCargos")) {
                                this.CodigoCargoOC = campo3.getAttributeValue("CodigoCargo");
                                this.importeOC = campo3.getAttributeValue("Importe");
                            }

                        }

                    }
                }

            }

        }

        buscarFolioFactura();
        if (this.serie == null) {
            this.serie = "0";
        }
        if (this.folio == null) {
            this.folio = "0";
        }
        if (this.validarUUID == null) {
            this.validarUUID = "0";
        }
        if (this.validarFactura == null) {
            this.validarFactura = "1";
        }
        if (this.moneda == null) {
            this.moneda = "MXN";
        }
        consulta = new ConsultaCFDIService();
        respuesta = consulta.getBasicHttpBindingIConsultaCFDIService();
        acuse = respuesta.consulta("?re=" + this.rfcE + "&rr=" + this.rfcR + "&tt=" + this.total + "&id=" + this.UUIDTF);

        validarXML();///VALIDAMOS QUE EXISTA EL DOCUMENTO RELACIONADO CON LA FACTURA

        if (this.validarFactura.equals(this.serie + this.folio) || this.validarUUID.equals(this.UUIDTF)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "DECORADOS Y SATINADOS MEXICANOS S.A DE C.V.", "Comprobante de pago ingresado anteriormente"));
            RequestContext.getCurrentInstance().execute("PF('dlgXML').hide()");
            lista.clear();
            listaDoctoRel.clear();
            listaPagoComp.clear();
        } else if (this.rfcR.equals("CDU590909BQ3") && this.validadUUIDVacio.equals("SI") && this.rfcE.equals(us.getRfc().replace(" ", "")) && acuse.getEstado().getValue().equals("Vigente")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "DECORADOS Y SATINADOS MEXICANOS S.A DE C.V.", ""));
            RequestContext.getCurrentInstance().execute("PF('dlgXML').hide()");
            insertarFactura();
            actualizarFolio();
            //buscarWCXP();
            insertarConcepto();
            insertarConceptoPago();
            insertarCamposPago();
            generarPDF();
            enviarAviso();
        } else {
            if (!this.rfcR.equals("CDU590909BQ3")) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "DECORADOS Y SATINADOS MEXICANOS S.A DE C.V.", "El RFC en el XML no corresponde a DECORADOS Y SATINADOS MEXICANOS S.A DE C.V."));
            } else if (!this.rfcE.equals(us.getRfc().replace(" ", ""))) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "DECORADOS Y SATINADOS MEXICANOS S.A DE C.V.", "El RFC en tu XML es diferente al dado de alta en nuestro sistema, favor de verificar."));
            } else if (this.validadUUIDVacio.equals("NO EXISTE")) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "DECORADOS Y SATINADOS MEXICANOS S.A DE C.V.", "No existe UUID en nuestro sistema que relacione al UUID del comprobante que intentas subir."));
            } else if (!acuse.getEstado().getValue().equals("Vigente")) {
                lista.clear();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ESTATUS VALIDACIÓN CFDI SAT", "Estimado proveedor, tu XML no superó las validaciones del SAT: " + acuse.getEstado().getValue()));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "DECORADOS Y SATINADOS MEXICANOS S.A DE C.V.", "Hay diferencia en precio, Precio en XML:" + this.total + " Precio Sistema: " + this.IMPORTE));
                lista.clear();
                listaDoctoRel.clear();
                listaPagoComp.clear();
            }
            RequestContext.getCurrentInstance().execute("PF('dlgXML').hide()");
            limpiarVariables();
            lista.clear();
            listaDoctoRel.clear();
            listaPagoComp.clear();
        }
    }

    public void validarXML() {

        ConceptoCompPagoDao cDao = new ConceptoCompPagoDaoImpl();
        int a = 0;//idDocumento

        int tamaño = listaDoctoRel.size() / 9;
        for (String ap : listaDoctoRel) {
            while (tamaño > 0) {
                try {
                    this.Conectarprov();
                    Statement st = this.getCnprov().createStatement();
                    ResultSet rs = st.executeQuery("SELECT UUID FROM FACTURA WHERE UUID='" + this.listaDoctoRel.get(a) + "' ");
                    if (!rs.isBeforeFirst()) {
                        this.validadUUIDVacio = "NO EXISTE";
                    } else {
                        while (rs.next()) {
                            this.validadUUID = rs.getString("UUID");
                        }
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(SubirComplementoBean.class.getName()).log(Level.SEVERE, null, ex);
                }

                a = a + 9;
                tamaño = tamaño - 1;
            }
        }

    }

    public void buscarFolioFactura() throws SQLException {
        this.Conectarprov();
        Usuario us = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("nombre");
        Statement st = this.getCnprov().createStatement();
        ResultSet rs = st.executeQuery("SELECT FACTURA, UUID FROM FACTURA_COMPLEMENTO WHERE UUID='" + this.UUIDTF + "'");
        if (!rs.isBeforeFirst()) {
        } else {
            while (rs.next()) {
                this.validarFactura = rs.getString("FACTURA");
                this.validarUUID = rs.getString("UUID");
            }
        }
    }

//    public void buscarWCXP() throws SQLException {
//        this.Conectarprov();
//        Statement st = this.getCnprov().createStatement();
//        ResultSet rs = st.executeQuery("SELECT FOLIOWCXP, FECHA_PAGO, REFERENCIA FROM FACTURA_COMPLEMENTO WHERE UUID='" + this.UUIDTF + "'");
//        while (rs.next()) {
//            this.folioWcxp = rs.getInt("FOLIOWCXP");
//            this.miPago = rs.getString("FECHA_PAGO");
//            this.miReferencia = rs.getString("REFERENCIA");
//        }
//    }
    public void insertarFactura() throws SQLException, ParseException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Usuario us = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("nombre");
        this.hoy = Calendar.getInstance();
        this.dia = this.hoy.get(Calendar.DAY_OF_WEEK);
        this.hoy.add(Calendar.DATE, diasCredito);
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        this.pago = formatoFecha.format(this.hoy.getTime());
        FacturaCompDao fDao = new FacturaDaoCompDaoImpl();
        if (this.serie == null && this.folio == null || this.serie.equals("0") && this.folio.equals("0")) {
            f.setFactura(UUIDTF);
        } else {
            if (this.serie.equals("0")) {
                this.serie = "";
            }
            if (this.folio.equals("o")) {
                this.folio = "";
            }
            f.setFactura(serie + " " + folio);
        }
        f.setFecha(fecha);
        f.setFolio(folio);
        f.setSerie(serie);
        f.setVersioncfdi(VersionSAT);
        BigDecimal bdImporte = new BigDecimal(this.subTotal);
        f.setImporte(bdImporte);
        BigDecimal bdTotal = new BigDecimal(this.total);
        f.setTotal(bdTotal);
        if (this.TipoCambio == null) {
            this.TipoCambio = "0";
        }
        BigDecimal bdTC = new BigDecimal(this.TipoCambio);
        f.setTipoCambio(bdTC);
        f.setMoneda(moneda);
        f.setMetodoPago(metodoDePago);
        f.setTipoComprobante(tipoDeComprobante);
        f.setLugarExpedicion(LugarExpedicion);
        f.setCertificado(certificado);
        f.setNoCertificado(noCertificado);
        f.setCondicionesPago(condPago);
        f.setFormaPago(formaDePago);
        f.setSello(sello);
        f.setNombreE(nombreE);
        f.setRfcE(rfcE);
        f.setRegimenFiscal(RegimenFiscal);
        f.setNombreR(nombreR);
        f.setRfcR(rfcR);
        f.setUsoCfdi(UsoCFDI);
        f.setImpuesto(Impuesto);
        f.setTipoFactor(TipoFactor);
        f.setTasaCouta(TasaOCuota);
        if (this.ImporteTraslado == null) {
            this.ImporteTraslado = "0";
        }
        BigDecimal bdIC = new BigDecimal(this.ImporteTraslado);
        f.setImporteCouta(bdIC);
        f.setReferencia(referencia);
        //FechaRecepción
        f.setFechaRecepcion(pago);
        //f.setFechaPago(pago);
        f.setEstatus("RECIBIDA");
        //Estatus SAT Validación CFDI
        f.setEstatusSat(acuse.getEstado().getValue());
        f.setVersioncfd(Version);
        f.setUuid(UUIDTF);

        f.setFechaTimbrado(FechaTimbrado);
        f.setRfcProvCert(RfcProvCertif);
        f.setSelloCfd(SelloCFD);
        f.setSelloSat(SelloSAT);
        f.setClaveProv(cveprov);
        f.setNombreArchivo(nombreCFDI);
        f.setFoliowcxp(0);
        //f.setUsuario(us.getCorreo());
        f.setNoCertificadoSat(NoCertificadoSAT);
        fDao.InsertFactura(f);
        //Limpiamos las variables
        //limpiarVariables();
    }

    public void buscarMensaje() throws SQLException {
        this.Conectarprov();
        Statement st = this.getCnprov().createStatement();
        ResultSet rs = st.executeQuery("SELECT TOP(1) avisoCorreo FROM MENSAJE ORDER BY ID DESC");
        if (!rs.isBeforeFirst()) {
            this.avisoCorreo = "";
        } else {
            while (rs.next()) {
                this.avisoCorreo = rs.getString("avisoCorreo");
            }
        }
    }

    public void actualizarEstadoComplemento(String estado, String idDoc) throws SQLException {
        this.Conectarprov();
        PreparedStatement ps = this.getCnprov().prepareStatement("UPDATE FACTURA SET ESTATUS_COM='" + estado + "', UUIDREL='" + UUIDTF + "', FCOMP='" + folio + "' WHERE UUID='" + idDoc + "'");
        ps.executeUpdate();
        //this.Cerrarprov();
    }

    public void actualizarFolio() throws SQLException {
        this.Conectarprov();
        PreparedStatement ps = this.getCnprov().prepareStatement("UPDATE FACTURA_COMPLEMENTO SET FACTURA_COMPLEMENTO. FOLIOWCXP=(SELECT MAX(FACTURA_COMPLEMENTO.FOLIOWCXP)+1 FROM FACTURA_COMPLEMENTO), FECHA_RECEPCION=(SELECT CONVERT(VARCHAR(19), GETDATE(), 126)) FROM FACTURA_COMPLEMENTO WHERE FACTURA_COMPLEMENTO.FOLIOWCXP=0");
        ps.executeUpdate();
    }

    public void insertarConcepto() {
        ConceptoCompDao cDao = new ConceptoCompDaoImpl();
        int a = 0;//cantidad
        int b = 1;//unidad
        int c = 2;//ClaveUnidad
        int d = 3;//descripcion
        int e = 4;//valorUnitario
        int g = 5;//importe
        int f = 6;//clave prod

        int tamaño = lista.size() / 7;
        for (String ap : lista) {
            while (tamaño > 0) {
                part.setCantidad(lista.get(a));
                part.setUnidad(lista.get(b));
                part.setClaveUnidad(lista.get(c));
                part.setDescripcion(lista.get(d));
                part.setPrecioUnitario(new BigDecimal(lista.get(e)));
                part.setUuid(UUIDTF);
                part.setImporte(new BigDecimal(lista.get(g)));
                part.setClaveProd(lista.get(f));
                cDao.InsertConcepto(part);
                part = new ConceptoComplemento();
                a = a + 7;
                b = b + 7;
                c = c + 7;
                d = d + 7;
                e = e + 7;
                g = g + 7;
                f = f + 7;
                tamaño = tamaño - 1;
            }
        }
        lista.clear();
    }

    public void insertarCamposPago() throws SQLException {
        PagoComDao pDao = new PagoComDaoImpl();
        int a = 0;//FechaPago
        int b = 1;//FormaDePagoP
        int c = 2;//MonedaP
        int d = 3;//Monto
        int e = 4;//NumOperacion
        int f = 5;//RfcEmisorCtaBen
        int g = 6;//CtaBeneficiario

        int tamaño = listaPagoComp.size() / 7;
        for (String ap : listaPagoComp) {
            while (tamaño > 0) {
                pagoComp.setFechapago(listaPagoComp.get(a));
                pagoComp.setFormadepago(listaPagoComp.get(b));
                pagoComp.setMonedapago(listaPagoComp.get(c));
                pagoComp.setMonto(new BigDecimal(listaPagoComp.get(d)));
                pagoComp.setNumoperacion(listaPagoComp.get(e));
                pagoComp.setRfcemisorctaben(listaPagoComp.get(f));
                pagoComp.setCtabeneficiario(listaPagoComp.get(g));
                pagoComp.setUuid(UUIDTF);
                pDao.InsertPago(pagoComp);
                pagoComp = new Pago();
                a = a + 7;
                b = b + 7;
                c = c + 7;
                d = d + 7;
                e = e + 7;
                g = g + 7;
                f = f + 7;
                tamaño = tamaño - 1;
            }
        }
        listaPagoComp.clear();

    }

    public void insertarConceptoPago() throws SQLException {
        ConceptoCompPagoDao cDao = new ConceptoCompPagoDaoImpl();
        int a = 0;//idDocumento
        int b = 1;//Serie
        int c = 2;//Folio
        int d = 3;//Monedadr
        int e = 4;//Metododepagodr
        int g = 5;//NumParcialidad
        int f = 6;//ImpSaldoAnt
        int h = 7;//ImpPagado
        int i = 8;//ImpSaldoInsoluto

        int tamaño = listaDoctoRel.size() / 9;
        for (String ap : listaDoctoRel) {
            while (tamaño > 0) {
                pag.setIddocumento(listaDoctoRel.get(a));
                pag.setSerie(listaDoctoRel.get(b));
                pag.setFolio(listaDoctoRel.get(c));
                pag.setMonedadr(listaDoctoRel.get(d));
                pag.setMetododepagodr(listaDoctoRel.get(e));
                pag.setNumparcialidad(listaDoctoRel.get(g));
                pag.setImpsaldoant(listaDoctoRel.get(f));
                pag.setImppagado(listaDoctoRel.get(h));
                pag.setImpsaldoinsoluto(listaDoctoRel.get(i));
                pag.setFactura(UUIDTF);
                cDao.InsertConcepto(pag);
                actualizarEstadoComplemento("RECIBIDO", pag.getIddocumento());
                pag = new ConceptoPagosComp();

                a = a + 9;
                b = b + 9;
                c = c + 9;
                d = d + 9;
                e = e + 9;
                g = g + 9;
                f = f + 9;
                h = h + 9;
                i = i + 9;
                tamaño = tamaño - 1;
            }
        }
        listaDoctoRel.clear();
    }

    public void enviarAviso() throws MessagingException, SQLException {
        Usuario us = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("nombre");
        if (this.TIPCAMB == 0.0) {
            this.TIPCAMB = 1;
        }
        this.miTotal = Float.parseFloat(this.total) * this.TIPCAMB;
        if (this.NUM_MONED == 0) {
            this.NUM_MONED = 1;
        }
        if (this.NUM_MONED == 1) {
            this.total = "0";
        }
        buscarMensaje();
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
                + "<td colspan='3' bordercolor='#FFFFFF'><p align='left' style='font-family:calibri; font-size:20px'><font color='#34495E'><b>ESTIMADO PROVEEDOR:</b></font><br><br>"
                + "<font color='#17202a'></font><i><font color='#004177'> " + this.nombreE + "</font></i><br><br>"
                + "<font color='#17202a'></font><i><font color='#004177'> " + this.avisoCorreo + "</font></i> <br>"
                + "<font color='#17202a'>Nuestro sistema ha recibido el siguiente complemento de pago:</font> <font color='#086A87'><i></i></font><br>"
                + "<font color='#17202a'>Factura/Folio fiscal:</font> <font color='#004177'><i> " + this.serie + this.folio + " </i></font><br>"
                + "<font color='#17202a'></font> <font color='#086A87'></font>"
                + "<font color='#17202a'></font> <font color='#086A87'></font>"
                + "<font color='#17202a'></font> <font color='#086A87'></font>"
                + "<font color='#17202a'></font> <font color='#086A87'><i></i></font><br>"
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

//        BodyPart adjunto = new MimeBodyPart();
//        adjunto.setDataHandler(new DataHandler(new FileDataSource("C:\\PROVEEDORES\\JasperReports - poliza.pdf")));
//        adjunto.setFileName("Test.pdf");
        MimeMultipart multiParte = new MimeMultipart();
        BodyPart imagen = new MimeBodyPart();
        //DataSource fds = new FileDataSource("/home/dmsistemas/Escritorio/logo2.png");
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
        message.addRecipients(Message.RecipientType.TO, us.getCorreo());
        message.addRecipient(Message.RecipientType.CC, new InternetAddress("duche.proveedores@gmail.com"));

// Se rellena el subject
        message.setSubject("AVISO DE RECEPCIÓN DE COMPLEMENTO DE PAGO");

// Se mete el texto y la foto adjunta.
        message.setContent(multiParte);

        Transport t = session.getTransport("smtp");
        t.connect("portalproveedores@duche.com", "ML310gen11");
        t.sendMessage(message, message.getAllRecipients());
        t.close();
        limpiarVariables();
    }

    public void generarPDF() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        Usuario us = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("nombre");
        this.Conectarprov();
        JasperReport jasperReport;
        JasperPrint jasperPrint;
        try {
            //se carga el reporte
            this.uuid = this.UUIDTF;
            Map parameter = new HashMap();
            parameter.put("uuid", uuid);
            URL in = this.getClass().getResource("/mx/facturaJasper/com_pago.jasper");
            jasperReport = (JasperReport) JRLoader.loadObject(in);
            //se procesa el archivo jasper
            jasperPrint = JasperFillManager.fillReport(jasperReport, parameter, this.getCnprov());
            //se crea el archivo PDF

            JasperExportManager.exportReportToPdfFile(jasperPrint, "C:\\public\\comprobantes\\" + us.getRfc() + "\\" + año + "\\" + mes + "\\" + nombreCFDI.substring(0, this.nombreCFDI.length() - 4) + ".pdf");
            JasperExportManager.exportReportToPdfFile(jasperPrint, "C:\\newPublic\\comprobantes\\" + us.getRfc() + "\\" + año + "\\" + mes + "\\" + nombreCFDI.substring(0, this.nombreCFDI.length() - 4) + ".pdf");
        } catch (JRException ex) {
            System.err.println("Error iReport: " + ex.getMessage());
        }

    }

    public void limpiarVariables() throws SQLException {
        this.referencia = null;
        this.validarReferencia = null;
        this.cveprov = null;
        this.CVE_DOC = null;
        this.SU_REFER = null;
        this.NUM_MONED = 0;
        this.TIPCAMB = 0;
        this.IMPORTE = 0;
        this.validarFactura = null;
        this.validarUUID = null;

//variables para el CFDI
        this.serie = null;
        this.folio = null;
        this.fecha = null;
        this.sello = null;
        this.formaDePago = null;
        this.noCertificado = null;
        this.certificado = null;
        this.subTotal = null;
        this.TipoCambio = null;
        this.moneda = null;
        this.total = null;
        this.tipoDeComprobante = null;
        this.metodoDePago = null;
        this.LugarExpedicion = null;
        this.rfcE = null;
        this.nombreE = null;
        this.rfcR = null;
        this.nombreR = null;
        this.cantidad = null;
        this.unidad = null;
        this.descripcion = null;
        this.valorUnitario = null;
        this.importe = null;
        this.RegimenFiscal = null;
        this.Regimen = null;
        this.UsoCFDI = null;
        this.BaseTraslado = null;
        this.Impuesto = null;
        this.TipoFactor = null;
        this.TasaOCuota = null;
        this.ImporteTraslado = null;
        this.Version = null;
        this.FechaTimbrado = null;
        this.RfcProvCertif = null;
        this.SelloCFD = null;
        this.NoCertificadoSAT = null;
        this.SelloSAT = null;
        this.UUIDTF = null;
        this.VersionSAT = null;
        this.nombreCFDI = null;
        this.calleDF = null;
        this.noExteriorDF = null;
        this.noInteriorDF = null;
        this.coloniaDF = null;
        this.municipioDF = null;
        this.estadoDF = null;
        this.paisDF = null;
        this.codigoPostalDF = null;
        this.calle = null;
        this.noExterior = null;
        this.noInterior = null;
        this.colonia = null;
        this.municipio = null;
        this.estado = null;
        this.pais = null;
        this.codigoPostal = null;
        this.impuestoRet = null;
        this.importeRet = null;
        this.TotalCargos = null;
        this.CodigoCargoOC = null;
        this.importeOC = null;
        this.validarMoneda = 0;
        this.mostrarMoneda = null;
        this.diasCredito = 0;
        this.pago = null;
        this.folioWcxp = 0;
        this.miTotal = 0;
        this.miPago = "";
        this.miReferencia = "";
        this.referencia = null;
        this.ClaveProdServ = null;
        this.ClaveUnidad = null;
        this.facturaSAE = null;
        this.condPago = null;
        this.ClaveProdServ = null;
        this.Cerrar();
        this.Cerrarprov();
        //variables para el CFDI
    }

}
