package mx.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import mx.dao.FacturaDao;
import mx.dao.FacturaDaoImpl;
import mx.dao.PagoDao;
import mx.dao.PagoDaoImpl;
import mx.model.DiasPago;
import mx.model.Factura;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;

@Named(value = "pagoBean")
@ViewScoped
public class PagoBean implements Serializable {

    private DiasPago pago;
    private List<DiasPago> listaPago;
    private String fecha;
    private Date fecPago;
    private List<Factura> listaPagosPendientes;
    private String tipoOC;
    private String estatus;
    private List<Factura> listaCompleta;
    private String f1;
    private String f2;
    private Date fecha1;
    private Date fecha2;
    private String localidad;

    RequestContext facesContext = RequestContext.getCurrentInstance();

    public PagoBean() {
        pago = new DiasPago();
        this.listaCompleta = new ArrayList<>();
        this.listaPago = new ArrayList<>();
        this.listaPagosPendientes = new ArrayList<>();
    }

    public DiasPago getPago() {
        return pago;
    }

    public void setPago(DiasPago pago) {
        this.pago = pago;
    }

    public List<DiasPago> getListaPago() {
        PagoDao pDao = new PagoDaoImpl();
        listaPago = pDao.lista();
        return listaPago;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Date getFecPago() {
        return fecPago;
    }

    public void setFecPago(Date fecPago) {
        this.fecPago = fecPago;
    }

    public void setListaPago(List<DiasPago> listaPago) {
        this.listaPago = listaPago;
    }

    public String getTipoOC() {
        return tipoOC;
    }

    public void setTipoOC(String tipoOC) {
        this.tipoOC = tipoOC;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public List<Factura> getListaCompleta() {
        return listaCompleta;
    }

    public void setListaCompleta(List<Factura> listaCompleta) {
        this.listaCompleta = listaCompleta;
    }

    public String getF1() {
        return f1;
    }

    public void setF1(String f1) {
        this.f1 = f1;
    }

    public String getF2() {
        return f2;
    }

    public void setF2(String f2) {
        this.f2 = f2;
    }

    public Date getFecha1() {
        return fecha1;
    }

    public void setFecha1(Date fecha1) {
        this.fecha1 = fecha1;
    }

    public Date getFecha2() {
        return fecha2;
    }

    public void setFecha2(Date fecha2) {
        this.fecha2 = fecha2;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public void insertarPago() {

        PagoDao pDao = new PagoDaoImpl();
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        this.fecha = formato.format(this.fecPago);
        this.pago.setFechaPago(fecha);
        this.pago.setLocalidad(localidad);
        pDao.InsertPago(pago);
        pago = new DiasPago();
        this.fecPago = null;
        this.fecha = null;
        RequestContext.getCurrentInstance().update("frmPrincipal:tablaPrincipal");
        RequestContext.getCurrentInstance().update("frmReporte:mensaje");
    }

    public void updatePago() {
        PagoDao pDao = new PagoDaoImpl();
        pDao.UpdatePago(pago);
        pago = new DiasPago();
    }

    public void deletePago() {
        PagoDao pDao = new PagoDaoImpl();
        pDao.DeletePago(pago);
        pago = new DiasPago();
    }

    public void validar() {
        if (tipoOC != null && estatus != null) {
            RequestContext.getCurrentInstance().execute("PF('dlgRepPago').show()");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "¡AVISO!", "Selecciona OC México o Toluca"));
        }
    }

    public List<Factura> getListaPagosPendientes() {
        FacturaDao fDao = new FacturaDaoImpl();
        listaPagosPendientes = fDao.listaPagosPendientes(pago.getFechaPago(), estatus, tipoOC);
        return listaPagosPendientes;
    }

    public List<Factura> listarAll() {
        FacturaDao fDao = new FacturaDaoImpl();
        if (listaCompleta.size() > 0) {
            listaCompleta.clear();
            RequestContext.getCurrentInstance().update("fmrFecRep:tablaPagos");
            // RequestContext.getCurrentInstance().execute("PF('tblPagosRep').clearFilters()");
        }
        if (fecha1 != null && fecha2 != null) {
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            f1 = formato.format(fecha1);
            f2 = formato.format(fecha2);
            listaCompleta = fDao.listarFechaRecep(f1, f2);
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "DECORADOS Y SATINADOS MEXICANOS S.A DE C.V.", "Selecciona fecha inicial y fecha final"));
        }
        fecha1 = null;
        fecha2 = null;
        f1 = null;
        f2 = null;

        return listaCompleta;
    }

    public void mensaje() {
        System.out.println("Mensaje");
    }

}
