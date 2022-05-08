package mx.model;
// Generated 29/01/2021 12:11:55 PM by Hibernate Tools 4.3.1


import java.util.Date;

/**
 * PagaM01 generated by hbm2java
 */
public class PagaM01  implements java.io.Serializable {


     private PagaM01Id id;
     private String cveFolio;
     private Integer cveObs;
     private String noFactura;
     private String docto;
     private Double importe;
     private Date fechaApli;
     private Date fechaVenc;
     private String afecCoi;
     private Integer numMoned;
     private Double tcambio;
     private Double impmonExt;
     private Date fechaelab;
     private Integer ctlpol;
     private String tipoMov;
     private Integer cveBita;
     private Integer signo;
     private Integer cveAut;
     private Short usuario;
     private String entregada;
     private Date fechaEntrega;
     private String refSist;
     private String status;
     private Integer estado;

    public PagaM01() {
    }

	
    public PagaM01(PagaM01Id id) {
        this.id = id;
    }
    public PagaM01(PagaM01Id id, String cveFolio, Integer cveObs, String noFactura, String docto, Double importe, Date fechaApli, Date fechaVenc, String afecCoi, Integer numMoned, Double tcambio, Double impmonExt, Date fechaelab, Integer ctlpol, String tipoMov, Integer cveBita, Integer signo, Integer cveAut, Short usuario, String entregada, Date fechaEntrega, String refSist, String status, Integer estado) {
       this.id = id;
       this.cveFolio = cveFolio;
       this.cveObs = cveObs;
       this.noFactura = noFactura;
       this.docto = docto;
       this.importe = importe;
       this.fechaApli = fechaApli;
       this.fechaVenc = fechaVenc;
       this.afecCoi = afecCoi;
       this.numMoned = numMoned;
       this.tcambio = tcambio;
       this.impmonExt = impmonExt;
       this.fechaelab = fechaelab;
       this.ctlpol = ctlpol;
       this.tipoMov = tipoMov;
       this.cveBita = cveBita;
       this.signo = signo;
       this.cveAut = cveAut;
       this.usuario = usuario;
       this.entregada = entregada;
       this.fechaEntrega = fechaEntrega;
       this.refSist = refSist;
       this.status = status;
       this.estado = estado;
    }
   
    public PagaM01Id getId() {
        return this.id;
    }
    
    public void setId(PagaM01Id id) {
        this.id = id;
    }
    public String getCveFolio() {
        return this.cveFolio;
    }
    
    public void setCveFolio(String cveFolio) {
        this.cveFolio = cveFolio;
    }
    public Integer getCveObs() {
        return this.cveObs;
    }
    
    public void setCveObs(Integer cveObs) {
        this.cveObs = cveObs;
    }
    public String getNoFactura() {
        return this.noFactura;
    }
    
    public void setNoFactura(String noFactura) {
        this.noFactura = noFactura;
    }
    public String getDocto() {
        return this.docto;
    }
    
    public void setDocto(String docto) {
        this.docto = docto;
    }
    public Double getImporte() {
        return this.importe;
    }
    
    public void setImporte(Double importe) {
        this.importe = importe;
    }
    public Date getFechaApli() {
        return this.fechaApli;
    }
    
    public void setFechaApli(Date fechaApli) {
        this.fechaApli = fechaApli;
    }
    public Date getFechaVenc() {
        return this.fechaVenc;
    }
    
    public void setFechaVenc(Date fechaVenc) {
        this.fechaVenc = fechaVenc;
    }
    public String getAfecCoi() {
        return this.afecCoi;
    }
    
    public void setAfecCoi(String afecCoi) {
        this.afecCoi = afecCoi;
    }
    public Integer getNumMoned() {
        return this.numMoned;
    }
    
    public void setNumMoned(Integer numMoned) {
        this.numMoned = numMoned;
    }
    public Double getTcambio() {
        return this.tcambio;
    }
    
    public void setTcambio(Double tcambio) {
        this.tcambio = tcambio;
    }
    public Double getImpmonExt() {
        return this.impmonExt;
    }
    
    public void setImpmonExt(Double impmonExt) {
        this.impmonExt = impmonExt;
    }
    public Date getFechaelab() {
        return this.fechaelab;
    }
    
    public void setFechaelab(Date fechaelab) {
        this.fechaelab = fechaelab;
    }
    public Integer getCtlpol() {
        return this.ctlpol;
    }
    
    public void setCtlpol(Integer ctlpol) {
        this.ctlpol = ctlpol;
    }
    public String getTipoMov() {
        return this.tipoMov;
    }
    
    public void setTipoMov(String tipoMov) {
        this.tipoMov = tipoMov;
    }
    public Integer getCveBita() {
        return this.cveBita;
    }
    
    public void setCveBita(Integer cveBita) {
        this.cveBita = cveBita;
    }
    public Integer getSigno() {
        return this.signo;
    }
    
    public void setSigno(Integer signo) {
        this.signo = signo;
    }
    public Integer getCveAut() {
        return this.cveAut;
    }
    
    public void setCveAut(Integer cveAut) {
        this.cveAut = cveAut;
    }
    public Short getUsuario() {
        return this.usuario;
    }
    
    public void setUsuario(Short usuario) {
        this.usuario = usuario;
    }
    public String getEntregada() {
        return this.entregada;
    }
    
    public void setEntregada(String entregada) {
        this.entregada = entregada;
    }
    public Date getFechaEntrega() {
        return this.fechaEntrega;
    }
    
    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }
    public String getRefSist() {
        return this.refSist;
    }
    
    public void setRefSist(String refSist) {
        this.refSist = refSist;
    }
    public String getStatus() {
        return this.status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    public Integer getEstado() {
        return this.estado;
    }
    
    public void setEstado(Integer estado) {
        this.estado = estado;
    }




}


