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
import mx.dao.ConceptoGastosDao;
import mx.dao.ConceptoGastosDaoImpl;
import mx.dao.DAO;
import mx.dao.FacturaGastosDao;
import mx.dao.FacturaGastosDaoImpl;
import mx.model.ConceptoGastos;
import mx.model.FacturaGastos;
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

//Web Service SAT 
import mx.sat.Acuse;
import mx.sat.ConsultaCFDIService;
import mx.sat.IConsultaCFDIService;

@Named(value = "subirGastosBean")
@ViewScoped
public class SubirGastosBean extends DAO implements Serializable {

    private FacturaGastos f;
    private ConceptoGastos part;
    private List<FacturaGastos> listaFactura;
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
    private List<Double> imp04 = new ArrayList<>();
    private List<Double> imp06 = new ArrayList<>();
    private List<Double> imp10isr = new ArrayList<>();

    //variables para el CFDI
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

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
    private final String ruta = "C:\\public\\gastos\\";
    private final String rutaIp = "C:\\newPublic\\gastos\\";
    private List<String> lista;

    //Web Service SAT
    private ConsultaCFDIService consulta;
    private IConsultaCFDIService respuesta;
    private Acuse acuse;

    //IMPUESTOS ISR
    private String impuestoIsr;
    private String tipoFactorIsr;
    private String tasaCoutaIsr;
    private String importeCuotaIsr;
    private String descuento;

    public SubirGastosBean() {
        this.lista = new ArrayList<>();
        f = new FacturaGastos();
        part = new ConceptoGastos();
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

    public FacturaGastos getF() {
        return f;
    }

    public void setF(FacturaGastos f) {
        this.f = f;
    }

    public ConceptoGastos getPart() {
        return part;
    }

    public void setPart(ConceptoGastos part) {
        this.part = part;
    }

    public List<FacturaGastos> getListaFactura() {
        FacturaGastosDao fDao = new FacturaGastosDaoImpl();
        listaFactura = fDao.listaFactura();
        return listaFactura;
    }

    public void setListaFactura(List<FacturaGastos> listaFactura) {
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

    public String getImpuestoIsr() {
        return impuestoIsr;
    }

    public void setImpuestoIsr(String impuestoIsr) {
        this.impuestoIsr = impuestoIsr;
    }

    public String getTipoFactorIsr() {
        return tipoFactorIsr;
    }

    public void setTipoFactorIsr(String tipoFactorIsr) {
        this.tipoFactorIsr = tipoFactorIsr;
    }

    public String getTasaCoutaIsr() {
        return tasaCoutaIsr;
    }

    public void setTasaCoutaIsr(String tasaCoutaIsr) {
        this.tasaCoutaIsr = tasaCoutaIsr;
    }

    public String getImporteCuotaIsr() {
        return importeCuotaIsr;
    }

    public void setImporteCuotaIsr(String importeCuotaIsr) {
        this.importeCuotaIsr = importeCuotaIsr;
    }

    public List<Double> getImp04() {
        return imp04;
    }

    public void setImp04(List<Double> imp04) {
        this.imp04 = imp04;
    }

    public List<Double> getImp06() {
        return imp06;
    }

    public void setImp06(List<Double> imp06) {
        this.imp06 = imp06;
    }

    public List<Double> getImp10isr() {
        return imp10isr;
    }

    public void setImp10isr(List<Double> imp10isr) {
        this.imp10isr = imp10isr;
    }

    public String getDescuento() {
        return descuento;
    }

    public void setDescuento(String descuento) {
        this.descuento = descuento;
    }

    public void buscarRecepcion() throws SQLException {
        this.Conectar();
        this.Conectarprov();
        Usuario us = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("nombre");
        Statement st = this.getCn().createStatement();
        ResultSet rs = st.executeQuery("SELECT CLAVE, DIASCRED FROM PROV01 WHERE RFC LIKE'%" + us.getRfc() + "%' AND STATUS='A'");
        while (rs.next()) {
            this.cveprov = rs.getString("CLAVE");
            this.diasCredito = rs.getInt("DIASCRED");
            Statement sta = this.getCnprov().createStatement();
            ResultSet rsa = sta.executeQuery("SELECT REFERENCIA FROM FACTURA WHERE RFC_E='" + us.getRfc() + "' AND REFERENCIA='" + this.referencia + "'");
            if (!rsa.isBeforeFirst()) {
                //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "DECORADOS Y SATINADOS MEXICANOS S.A DE C.V.", "¡El número de recepción: " + this.referencia + " no existe!"));
            } else {
                while (rsa.next()) {
                    this.validarReferencia = rsa.getString("REFERENCIA");
                }
            }
            if (this.referencia.equals(this.validarReferencia)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "DECORADOS Y SATINADOS MEXICANOS S.A DE C.V.", "¡El número de recepción: " + this.referencia + " ya ha sido ingresado anteriormente!"));

            } else {
                Statement stb = this.getCn().createStatement();
                ResultSet rsb = stb.executeQuery("SELECT CVE_DOC, SU_REFER, CAN_TOT, NUM_MONED, TIPCAMB, IMPORTE FROM COMPR01 WHERE CVE_DOC='" + this.referencia + "' AND CVE_CLPV='" + this.cveprov + "' AND STATUS<>'C'");
                if (!rsb.isBeforeFirst()) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "DECORADOS Y SATINADOS MEXICANOS S.A DE C.V.", "¡El número de recepción: " + this.referencia + " no existe en el sistema, favor de verificarlo!"));
                } else {
                    while (rsb.next()) {
                        this.CVE_DOC = rsb.getString("CVE_DOC");
                        this.SU_REFER = rsb.getString("SU_REFER");
                        this.CAN_TOT = rsb.getFloat("CAN_TOT");
                        this.NUM_MONED = rsb.getInt("NUM_MONED");
                        this.TIPCAMB = rsb.getFloat("TIPCAMB");
                        this.IMPORTE = rsb.getFloat("IMPORTE");
                        //subimos el xml
                        RequestContext.getCurrentInstance().execute("PF('dlgXML').show()");
                    }
                }
            }

        }
        //this.Cerrar();
        //this.Cerrarprov();
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
                Logger.getLogger(SubirGastosBean.class.getName()).log(Level.SEVERE, null, ex);
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
        descuento = rootNode.getAttributeValue("Descuento");
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

                List impuestoRet1 = campo.getChildren();

                for (int r = 0; r < impuestoRet1.size(); r++) {

                    Element campoRet = (Element) impuestoRet1.get(r);
                    List impRet = campoRet.getChildren();
                    for (int p = 0; p < impRet.size(); p++) {
                        Element campoImpRet = (Element) impRet.get(p);
                        String valorRet = campoImpRet.getName();
                        if (valorRet.equals("Retenciones")) {
                            List valorRe = campoImpRet.getChildren();
                            for (int d = 0; d < valorRe.size(); d++) {
                                Element v = (Element) valorRe.get(d);
                                String TasaOCuota1 = v.getAttributeValue("TasaOCuota");
                                String Impuest = v.getAttributeValue("Impuesto");
                                if (TasaOCuota1.contains("0.04") && Impuest.equals("002")) {
                                    this.imp04.add(Double.valueOf(v.getAttributeValue("Importe")));
                                } else if (TasaOCuota1.contains("0.06") && Impuest.equals("002")) {
                                    this.imp06.add(Double.valueOf(v.getAttributeValue("Importe")));
                                } else if (TasaOCuota1.contains("0.10") && Impuest.equals("001")) {
                                    this.imp10isr.add(Double.valueOf(v.getAttributeValue("Importe")));
                                }
                            }
                        }

                    }
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
                        if (campo2.getAttributeValue("Impuesto").equals("001")) {
                            impuestoIsr = campo2.getAttributeValue("impuesto");
                            if (impuestoIsr == null) {
                                impuestoIsr = campo2.getAttributeValue("Impuesto");
                            }
                            tasaCoutaIsr = campo2.getAttributeValue("tasa");
                            if (tasaCoutaIsr == null) {
                                tasaCoutaIsr = campo2.getAttributeValue("TasaOCuota");
                            }
                            importeCuotaIsr = campo2.getAttributeValue("importe");
                            if (importeCuotaIsr == null) {
                                importeCuotaIsr = campo2.getAttributeValue("Importe");
                            }
                            BaseTraslado = campo2.getAttributeValue("Base");
                        }
                    }
                    if (valor3.equals("Retenciones")) {
                        this.impuestoRet = campo2.getAttributeValue("Impuesto");
                        this.importeRet = campo2.getAttributeValue("Importe");
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
        if (this.serie
                == null) {
            this.serie = "0";
        }
        if (this.folio
                == null) {
            this.folio = "0";
        }
        if (this.validarUUID
                == null) {
            this.validarUUID = "0";
        }
        if (this.validarFactura
                == null) {
            this.validarFactura = "1";
        }
        if (this.moneda
                == null) {
            this.moneda = "MXN";
        }

        consulta = new ConsultaCFDIService();
        respuesta = consulta.getBasicHttpBindingIConsultaCFDIService();
        acuse = respuesta.consulta("?re=" + this.rfcE + "&rr=" + this.rfcR + "&tt=" + this.total + "&id=" + this.UUIDTF);

        if (this.validarFactura.equals(
                this.serie + this.folio) || this.validarUUID.equals(this.UUIDTF) && acuse.getEstado().getValue().equals("Vigente")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "DECORADOS Y SATINADOS MEXICANOS S.A DE C.V.", "Factura ingresada anteriormente"));
            RequestContext.getCurrentInstance().execute("PF('dlgXML').hide()");
            lista.clear();
        } else if (this.rfcR.equals(
                "CDU590909BQ3")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "DECORADOS Y SATINADOS MEXICANOS S.A DE C.V.", "Se ha procesado su factura para pago"));
            RequestContext.getCurrentInstance().execute("PF('dlgXML').hide()");
            insertarFactura();
            insertarConcepto();
            actualizarFolio();
            buscarWCXP();
            generarPDF();
            // enviarAviso();
        } else if (!acuse.getEstado()
                .getValue().equals("Vigente")) {
            lista.clear();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ESTATUS VALIDACIÓN CFDI SAT", "Estimado proveedor, tu XML no superó las validaciones del SAT: " + acuse.getEstado().getValue()));
            //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ESTATUS VALIDACIÓN CFDI SAT", "Estatus XML: " + acuse.getEstado().getValue()));
        } else {
            if (!this.rfcR.equals("CDU590909BQ3")) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "DECORADOS Y SATINADOS MEXICANOS S.A DE C.V.", "El RFC en el XML no corresponde a DECORADOS Y SATINADOS MEXICANOS S.A DE C.V."));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "DECORADOS Y SATINADOS MEXICANOS S.A DE C.V.", "Hay diferencia en precio, Precio en XML:" + this.total + " Precio Sistema: " + this.IMPORTE));
                lista.clear();
            }
            RequestContext.getCurrentInstance().execute("PF('dlgXML').hide()");
            limpiarVariables();
            lista.clear();
        }
    }

    public void buscarFolioFactura() throws SQLException {
        this.Conectarprov();
        Usuario us = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("nombre");
        Statement st = this.getCnprov().createStatement();
        ResultSet rs = st.executeQuery("SELECT FACTURA, UUID FROM FACTURA_GASTOS WHERE UUID='" + this.UUIDTF + "'");
        if (!rs.isBeforeFirst()) {
        } else {
            while (rs.next()) {
                this.validarFactura = rs.getString("FACTURA");
                this.validarUUID = rs.getString("UUID");
            }
        }
    }

    public void buscarWCXP() throws SQLException {
        this.Conectarprov();
        Statement st = this.getCnprov().createStatement();
        ResultSet rs = st.executeQuery("SELECT FOLIOWCXP, FECHA_PAGO, REFERENCIA FROM FACTURA_GASTOS WHERE UUID='" + this.UUIDTF + "'");
        while (rs.next()) {
            this.folioWcxp = rs.getInt("FOLIOWCXP");
            this.miPago = rs.getString("FECHA_PAGO");
            this.miReferencia = rs.getString("REFERENCIA");
        }
    }

    public void insertarFactura() throws SQLException, ParseException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Usuario us = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("nombre");
        this.hoy = Calendar.getInstance();
        this.dia = this.hoy.get(Calendar.DAY_OF_WEEK);
        this.hoy.add(Calendar.DATE, diasCredito);
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        this.pago = formatoFecha.format(this.hoy.getTime());
        FacturaGastosDao fDao = new FacturaGastosDaoImpl();
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

        if (descuento != null) {
            f.setDescuento("0.0");
        } else {
            f.setDescuento(descuento);
        }

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
        try {
            if (this.TasaOCuota.contains("0.00")) {
                f.setBase0(this.importe);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        try {
            if (this.TasaOCuota.contains("0.16")) {
                f.setBase16(this.importe);
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        try {
            if (this.ImporteTraslado == null) {
                this.ImporteTraslado = "0";
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        BigDecimal bdIC = new BigDecimal(this.ImporteTraslado);
        f.setImporteCouta(bdIC);
        f.setReferencia(referencia);
        //FechaRecepción
        f.setFechaPago(pago);
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
        f.setUsuario(us.getCorreo());
        f.setNoCertificadoSat(NoCertificadoSAT);
        f.setImpuestoIsr(impuestoIsr);
        f.setTasaCuotaIsr(tasaCoutaIsr);
        f.setConceptos(lista.toString());
//        if (importeCuotaIsr != null) {
//            f.setImporteCuotaIsr(new BigDecimal(importeCuotaIsr));
//            this.importeRet = null;
//        }

        Double i04 = 0.0;
        Double i06 = 0.0;
        Double i10isr = 0.0;
        //System.out.println("Importe04: " + imp04.size());
        if (imp04.size() > 0) {
            for (int q = 0; q < imp04.size(); q++) {
                i04 += imp04.get(q);
            }
        }
        //System.out.println("Importe06: " + imp06.size());
        if (imp06.size() > 0) {
            for (int w = 0; w < imp06.size(); w++) {
                i06 += imp06.get(w);
            }
        }

        if (imp10isr.size() > 0) {
            for (int w = 0; w < imp10isr.size(); w++) {
                i10isr += imp10isr.get(w);
            }
        }

        // f.setIvaRet0(new BigDecimal("0"));
        if (!i04.toString().equals("null")) {
            f.setIvaRet04(i04.toString());
        }

        if (!i04.toString().equals("null")) {
            f.setIvaRet06(i06.toString());
        }
        if (!i10isr.toString().equals("null")) {
            f.setImporteCuotaIsr(i10isr.toString());
        }

//        if (impuestoIsr != "001") {
//            if (importeRet != null) {
//                f.setIvaRet16(new BigDecimal(importeRet));
//            }
//        }
        fDao.InsertFactura(f);
        //Limpiamos las variables
        //limpiarVariables();
        imp04.clear();
        imp06.clear();
        imp10isr.clear();
    }

    public void insertaPAGA_M01() throws SQLException {
        //this.miTotal = Float.parseFloat(this.total) * this.TIPCAMB;
        this.Conectar();
        this.facturaSAE = this.serie + this.folio;
        if (this.facturaSAE.length() > 20) {
            this.tamcadena = this.facturaSAE.length();
            this.tamcadena = this.tamcadena - 20;
            this.facturaSAE = this.facturaSAE.substring(this.tamcadena, this.facturaSAE.length());
        }
        PreparedStatement ps = this.getCn().prepareStatement("INSERT INTO PAGA_M01 VALUES ('" + this.cveprov + "', '" + "WCXP" + this.folioWcxp + "', 1, 1, NULL,0, '" + this.facturaSAE + "', 'WCXP" + this.folioWcxp + "', '" + this.miTotal + "', GETDATE(), '" + this.miPago + "', 'A', '" + this.NUM_MONED + "', '" + this.TIPCAMB + "','" + this.total + "', GETDATE(), NULL, 'C', NULL, 1, NULL, 0, NULL, NULL, NULL, 'A')");
        ps.executeUpdate();
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

    public void insertarCOMPR01() throws SQLException {
        this.Conectar();
        PreparedStatement ps = this.getCn().prepareStatement("UPDATE COMPR01 SET ENLAZADO='T', TIP_DOC_E='c',TIP_DOC_SIG='c', DOC_SIG='WCXP" + this.folioWcxp + "' WHERE CVE_CLPV='" + this.cveprov + "' AND CVE_DOC='" + this.referencia + "'");
        ps.executeUpdate();
    }

    public void actualizarFolio() throws SQLException {
        this.Conectarprov();
        PreparedStatement ps = this.getCnprov().prepareStatement("UPDATE FACTURA_GASTOS SET FACTURA_GASTOS. FOLIOWCXP=(SELECT MAX(FACTURA_GASTOS.FOLIOWCXP)+1 FROM FACTURA_GASTOS), FECHA_RECEPCION=(SELECT CONVERT(VARCHAR(19), GETDATE(), 126)) FROM FACTURA_GASTOS WHERE FACTURA_GASTOS.FOLIOWCXP=0");
        ps.executeUpdate();
    }

    public void insertarConcepto() {
        ConceptoGastosDao cDao = new ConceptoGastosDaoImpl();
        int a = 0;//cantidad
        int b = 1;//unidad
        int c = 2;//ClaveUnidad
        int d = 3;//descripcion
        int e = 4;//valorUnitario
        int g = 5;//importe
        int ff = 6;//clave prod

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
                part.setClaveProd(lista.get(ff));
                cDao.InsertConcepto(part);
                part = new ConceptoGastos();
                a = a + 7;
                b = b + 7;
                c = c + 7;
                d = d + 7;
                e = e + 7;
                g = g + 7;
                ff = ff + 7;
                tamaño = tamaño - 1;
            }
        }
        lista.clear();
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
        session.setDebug(false);

        BodyPart texto = new MimeBodyPart();
        texto.setContent("<html><head><title></title></head>"
                + "<body>"
                + "<table width='878' height='315' border='0' bordercolor='#0000FF' bgcolor='#FFFFFF'>"
                + "<tr>"
                + "<td height='50' colspan='3' bordercolor='#FFFFFF'><br><br></td>"
                + "</tr>"
                + "<tr>"
                + "<td colspan='3' bordercolor='#FFFFFF'><p align='left' style='font-family:calibri; font-size:17px'><font color='#086A87'>Estimado proveedor:  </font><br>"
                + "<font color='#17202a'></font><i><font color='#086A87'> " + this.nombreE + "</font></i><br><br>"
                + "<font color='#17202a'></font><i><font color='#086A87'> " + this.avisoCorreo + "</font></i> <br>"
                + "<font color='#17202a'>Le informo que nuestro sistema ha programado el pago del siguiente documento:</font> <font color='#086A87'><i></i></font><br>"
                + "<font color='#17202a'>Factura/Folio fiscal:</font> <font color='#086A87'><i> " + this.serie + this.folio + " </i></font><br>"
                + "<font color='#17202a'>Recepci&oacute;n:</font> <font color='#086A87'><i> " + this.referencia + " </i></font><br>"
                + "<font color='#17202a'>Cuenta por pagar no:</font> <font color='#086A87'><i> GWCXP" + this.folioWcxp + " </i></font><br>"
                + "<font color='#17202a'>Monto MXN:</font> <font color='#086A87'><i> $" + this.miTotal + " </i></font><br>"
                + "<font color='#17202a'>Monto USD/EUROS: :</font> <font color='#086A87'><i> $" + this.total + " </i></font><br>"
                + "<font color='#17202a'></font> <font color='#086A87'><i>  </i></font><br><font color='black'><b></b></font><br></td>"
                + "</tr>"
                + "<tr>"
                + "<td width='725' bordercolor='#FFFFFF'><p align='left' style='font-family:calibri; font-size:17px'><br><font color='#17202a'>Portal Proveedores | </font><font color='#E60013'>Coloidales Duch&eacute;, S.A. de C.V.</font><br>"
                + "<a href='http://desamexproveedores.dyndns.info:9088/proveedores/' target='_blank'><img src='cid:image' width='20%'/></a></td>"
                + "<td width='422' bordercolor='#FFFFFF'></td>"
                + "<td width='422' rowspan='2' bordercolor='#FFFFFF'></td>"
                + "</tr>"
                + "<tr>"
                + "<td colspan='2' bordercolor='#17202a'><br><br><p align='center' style='font-family:calibri; font-size:15px'><font color='#086A87'><br> Favor de no responder a este correo ya que es un aviso autom&aacute;tico, si usted tiene alguna duda favor de contactar al &aacute;rea de Atenci&oacute;n a proveedores cuentasporpagartoluca@duche.com cuentasporpagarmexico@duche.com amendoza@duche.com bcarrillo@duche.com</font></p></td>"
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
        message.setFrom(new InternetAddress("portalproveedores@duche.com"));

// Se rellenan los destinatarios
        message.addRecipients(Message.RecipientType.TO, us.getCorreo());
        message.addRecipient(Message.RecipientType.CC, new InternetAddress("duche.proveedores@gmail.com"));

// Se rellena el subject
        message.setSubject("Notificación de factura aceptada");

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
            URL in = this.getClass().getResource("/mx/facturaJasper/facturaGastosPDF.jasper");
            jasperReport = (JasperReport) JRLoader.loadObject(in);
            //se procesa el archivo jasper
            jasperPrint = JasperFillManager.fillReport(jasperReport, parameter, this.getCnprov());
            //se crea el archivo PDF

            JasperExportManager.exportReportToPdfFile(jasperPrint, "C:\\public\\gastos\\" + us.getRfc() + "\\" + año + "\\" + mes + "\\" + nombreCFDI.substring(0, this.nombreCFDI.length() - 4) + ".pdf");
            JasperExportManager.exportReportToPdfFile(jasperPrint, "C:\\newPublic\\gastos\\" + us.getRfc() + "\\" + año + "\\" + mes + "\\" + nombreCFDI.substring(0, this.nombreCFDI.length() - 4) + ".pdf");
        } catch (JRException ex) {
            System.err.println("Error iReport: " + ex.getMessage());
        }

        //RequestContext.getCurrentInstance().update("@all");
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
        this.descuento = null;

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
        this.impuestoIsr = null;
        this.tipoFactorIsr = null;
        this.tasaCoutaIsr = null;
        this.importeCuotaIsr = null;
        this.importeRet = null;
        this.Cerrar();
        this.Cerrarprov();
        //variables para el CFDI
    }

}
