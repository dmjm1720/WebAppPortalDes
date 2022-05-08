package mx.bean;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import mx.dao.DAO;
import mx.dao.FacturaGastosDao;
import mx.dao.FacturaGastosDaoImpl;
import mx.facturaJasper.reporteCFDI;
import mx.model.FacturaGastos;

@Named(value = "gastos")
@ViewScoped
public class GastosBeanLista extends DAO implements Serializable {

    List<String> listarTodo = new ArrayList();
    private List<FacturaGastos> listaCompleta;

    private String filterRFC;
    private String filterFactura;
    private Date filterFec1;
    private Date filterFec2;
    private String filterNoRec;
    private String filterFolio;
    private String filterEstatus;
    private String resultadoRFC;
    private String resultadoFactura;
    private String resultadoRecepcion;
    private String resultadoFolio;
    private String resultadoEstatus;
    private String f1;
    private String f2;
    private String uuid;

    private FacturaGastos factura;

    public GastosBeanLista() {
        factura = new FacturaGastos();
    }

    public String getFilterRFC() {
        return filterRFC;
    }

    public void setFilterRFC(String filterRFC) {
        this.filterRFC = filterRFC;
    }

    public String getFilterFactura() {
        return filterFactura;
    }

    public void setFilterFactura(String filterFactura) {
        this.filterFactura = filterFactura;
    }

    public Date getFilterFec1() {
        return filterFec1;
    }

    public void setFilterFec1(Date filterFec1) {
        this.filterFec1 = filterFec1;
    }

    public Date getFilterFec2() {
        return filterFec2;
    }

    public void setFilterFec2(Date filterFec2) {
        this.filterFec2 = filterFec2;
    }

    public String getFilterNoRec() {
        return filterNoRec;
    }

    public void setFilterNoRec(String filterNoRec) {
        this.filterNoRec = filterNoRec;
    }

    public String getFilterFolio() {
        return filterFolio;
    }

    public void setFilterFolio(String filterFolio) {
        this.filterFolio = filterFolio;
    }

    public String getFilterEstatus() {
        return filterEstatus;
    }

    public void setFilterEstatus(String filterEstatus) {
        this.filterEstatus = filterEstatus;
    }

    public String getResultadoRFC() {
        return resultadoRFC;
    }

    public void setResultadoRFC(String resultadoRFC) {
        this.resultadoRFC = resultadoRFC;
    }

    public FacturaGastos getFactura() {
        return factura;
    }

    public void setFactura(FacturaGastos factura) {
        this.factura = factura;
    }

    public List<FacturaGastos> getListaCompleta() {
        return listaCompleta;
    }

    public void setListaCompleta(List<FacturaGastos> listaCompleta) {
        this.listaCompleta = listaCompleta;
    }

    public String getResultadoFactura() {
        return resultadoFactura;
    }

    public void setResultadoFactura(String resultadoFactura) {
        this.resultadoFactura = resultadoFactura;
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

    public String getResultadoRecepcion() {
        return resultadoRecepcion;
    }

    public void setResultadoRecepcion(String resultadoRecepcion) {
        this.resultadoRecepcion = resultadoRecepcion;
    }

    public String getResultadoFolio() {
        return resultadoFolio;
    }

    public void setResultadoFolio(String resultadoFolio) {
        this.resultadoFolio = resultadoFolio;
    }

    public String getResultadoEstatus() {
        return resultadoEstatus;
    }

    public void setResultadoEstatus(String resultadoEstatus) {
        this.resultadoEstatus = resultadoEstatus;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

//**LISTAS POR RFC **//
    public List<String> completeRFC(String rfc) throws SQLException {
        List<String> resultRFC = new ArrayList<>();
        this.Conectarprov();
        PreparedStatement st = this.getCnprov().prepareStatement("SELECT DISTINCT (RFC_E) FROM FACTURA_GASTOS WHERE  RFC_E LIKE '" + rfc + "%'");
        ResultSet rs = st.executeQuery();
        listarTodo = new ArrayList<>();
        if (!rs.isBeforeFirst()) {
            listarTodo.add("No hay resultados para tu búsqueda");
        } else {
            while (rs.next()) {
                listarTodo.add(this.resultadoRFC = rs.getString("RFC_E"));
            }
        }
        for (int i = 0; i < listarTodo.size(); i++) {
            resultRFC.add(listarTodo.get(i));
        }

        this.Cerrarprov();
        return resultRFC;
    }

    public List<String> completeFactura(String factura) throws SQLException {
        List<String> resultFactura = new ArrayList<>();
        this.Conectarprov();
        PreparedStatement st = this.getCnprov().prepareStatement("SELECT DISTINCT (FACTURA) FROM FACTURA_GASTOS WHERE FACTURA LIKE '" + factura + "%'");
        ResultSet rs = st.executeQuery();
        listarTodo = new ArrayList<>();
        if (!rs.isBeforeFirst()) {
            listarTodo.add("No hay resultados para tu búsqueda");
        } else {
            while (rs.next()) {
                listarTodo.add(this.resultadoFactura = rs.getString("FACTURA"));
            }
        }
        for (int i = 0; i < listarTodo.size(); i++) {
            resultFactura.add(listarTodo.get(i));
        }

        this.Cerrarprov();
        return resultFactura;
    }

    public List<String> completeRecepcion(String recepcion) throws SQLException {
        List<String> resultRecepcion = new ArrayList<>();
        this.Conectarprov();
        PreparedStatement ps = this.getCnprov().prepareStatement("SELECT DISTINCT (REFERENCIA) FROM FACTURA_GASTOS WHERE REFERENCIA LIKE '" + recepcion + "%'");
        ResultSet rs = ps.executeQuery();
        listarTodo = new ArrayList<>();
        if (!rs.isBeforeFirst()) {
            listarTodo.add("No hay resultados para tu búsqueda");
        } else {
            while (rs.next()) {
                listarTodo.add(this.resultadoRecepcion = rs.getString("REFERENCIA"));
            }
        }
        for (int i = 0; i < listarTodo.size(); i++) {
            resultRecepcion.add(listarTodo.get(i));
        }
        this.Cerrarprov();
        return resultRecepcion;
    }

    public List<String> completeFolio(String folio) throws SQLException {
        List<String> resultFolio = new ArrayList<>();
        this.Conectarprov();
        PreparedStatement ps = this.getCnprov().prepareStatement("SELECT DISTINCT (FOLIOWCXP) FROM FACTURA_GASTOS WHERE FOLIOWCXP LIKE '" + folio + "%'");
        ResultSet rs = ps.executeQuery();
        listarTodo = new ArrayList<>();
        if (!rs.isBeforeFirst()) {
            listarTodo.add("No hay resultados para tu búesqueda");
        } else {
            while (rs.next()) {
                listarTodo.add(this.resultadoFolio = rs.getString("FOLIOWCXP"));
            }
        }
        for (int i = 0; i < listarTodo.size(); i++) {
            resultFolio.add(listarTodo.get(i));
        }
        this.Cerrarprov();
        return resultFolio;
    }

    public List<String> completeEstatus(String estatus) throws SQLException {
        List<String> resultEstatus = new ArrayList<>();
        this.Conectarprov();
        PreparedStatement ps = this.getCnprov().prepareStatement("SELECT DISTINCT (ESTATUS) FROM FACTURA_GASTOS WHERE ESTATUS LIKE '" + estatus + "%'");
        ResultSet rs = ps.executeQuery();
        listarTodo = new ArrayList<>();
        if (!rs.isBeforeFirst()) {
            listarTodo.add("No har resultados para tu búsqueda");
        } else {
            while (rs.next()) {
                listarTodo.add(this.resultadoEstatus = rs.getString("ESTATUS"));
            }

        }
        for (int i = 0; i < listarTodo.size(); i++) {
            resultEstatus.add(listarTodo.get(i));
        }
        this.Cerrarprov();
        return resultEstatus;
    }

    public List<FacturaGastos> listarAll() {
        FacturaGastosDao fDao = new FacturaGastosDaoImpl();
        if (filterRFC != null) {
            listaCompleta = fDao.listaFacRFC(filterRFC);
        } else if (filterFactura != null) {
            listaCompleta = fDao.listarFact(filterFactura);
        } else if (filterFec1 != null && filterFec2 != null) {
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            f1 = formato.format(filterFec1);
            f2 = formato.format(filterFec2);
            listaCompleta = fDao.listarFechaRecep(f1, f2);
        } else if (filterNoRec != null) {
            listaCompleta = fDao.listarNoRecep(filterNoRec);
        } else if (filterFolio != null) {
            listaCompleta = fDao.listarFolioWCXP(filterFolio);
        } else if (filterEstatus != null) {
            listaCompleta = fDao.listarEstatus(filterEstatus);
        }
        filterRFC = null;
        filterFactura = null;
        filterFec1 = null;
        filterFec2 = null;
        filterNoRec = null;
        filterFolio = null;
        filterEstatus = null;
        f1 = null;
        f2 = null;
        return listaCompleta;

    }

    public List<FacturaGastos> listarUltimasRecep() {
        FacturaGastosDao fDao = new FacturaGastosDaoImpl();
        listaCompleta = fDao.listarAdministrador();
        return listaCompleta;
    }

    //**LISTAS POR RFC **//
    public void visualizarCFDI_PDF() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        uuid = request.getParameter("frmVerXML:uuid");
        reporteCFDI rCliente = new reporteCFDI();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
        String ruta = servletContext.getRealPath("/CFDI/facturaGastosPDF.jasper");
        //rCliente.getReporte(ruta, this.viaje.getNombreViajero(), this.viaje.getFolioUsuario());
        rCliente.getReporte(ruta, uuid);
        //rCliente.getReporte(ruta, this.codigo, this.folio);
        FacesContext.getCurrentInstance().responseComplete();

    }

    //**LISTAS POR RFC **//
    public void visualizarCFDI_COMP() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        uuid = request.getParameter("frmVerComp:uuidrel");
        reporteCFDI rCliente = new reporteCFDI();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
        String ruta = servletContext.getRealPath("/CFDI/facturaComp.jasper");
        //rCliente.getReporte(ruta, this.viaje.getNombreViajero(), this.viaje.getFolioUsuario());
        rCliente.getReporte(ruta, uuid);
        //rCliente.getReporte(ruta, this.codigo, this.folio);
        FacesContext.getCurrentInstance().responseComplete();

    }

}
