package mx.dao;

import java.util.List;
import mx.model.Factura;
import mx.model.FacturaGastos;

public interface FacturaGastosDao {

    public List<FacturaGastos> listaFactura();

    public List<FacturaGastos> listaFacRFC(String rfc);

    public List<FacturaGastos> listarFact(String factura);

    public List<FacturaGastos> listarAdministrador();

    public List<FacturaGastos> listarFechaRecep(String f1, String f2);

    public List<FacturaGastos> listarNoRecep(String recepcion);

    public List<FacturaGastos> listarFolioWCXP(String folio);

    public List<FacturaGastos> listarEstatus(String estatus);

    public List<FacturaGastos> listaAdminFactura();

    public void InsertFactura(FacturaGastos factura);

    public void UpdateFactura(FacturaGastos factura);

    public void DeleteFactura(FacturaGastos factura);

    public void actualizarFacturaConcepto(String concepto, String uuid);

    public List<FacturaGastos> listaFaturaActualizarConcepto();

    public List<FacturaGastos> listarFechaPago(String f3, String f4);

    public void actualizarImpuestos(String uuid, String isr, String ret4, String ret6, String t0);

}
