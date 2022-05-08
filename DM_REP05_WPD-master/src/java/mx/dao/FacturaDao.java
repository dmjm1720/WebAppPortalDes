package mx.dao;

import java.util.List;
import mx.model.Factura;

public interface FacturaDao {

    public List<Factura> listaFactura();

    public List<Factura> listaAdministrador();

    public List<Factura> listaFacRFC(String rfc);

    public List<Factura> listarFact(String factura);

    public List<Factura> listarFechaRecep(String f1, String f2);

    public List<Factura> listarFechaPago(String f3, String f4);

    public List<Factura> listarNoRecep(String recepcion);

    public List<Factura> listarFolioWCXP(String folio);

    public List<Factura> listarEstatus(String estatus);

    public List<Factura> listaAdminFactura();

    public void InsertFactura(Factura factura);

    public void UpdateFactura(Factura factura);

    public void DeleteFactura(Factura factura);

    public List<Factura> listaPagosPendientes(String fechaPago, String estatus, String oc);

    public List<Factura> listaFaturaActualizarConcepto();

    public void actualizarFacturaConcepto(String concepto, String uuid);

    public void actualizarFolioComprobante(String folio, String uuid);

    public List<Factura> listaFaturaFolioComprobante();

    public void actualizarImpuestos(String uuid, String isr, String ret4, String ret6);

}
