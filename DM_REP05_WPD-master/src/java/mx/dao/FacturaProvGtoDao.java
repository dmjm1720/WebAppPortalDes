package mx.dao;

import java.util.List;
import mx.model.FacturaGastosProv;

public interface FacturaProvGtoDao {

    public List<FacturaGastosProv> listaFactura();

    public List<FacturaGastosProv> listaAdministrador();

    public List<FacturaGastosProv> listaFacRFC(String rfc);

    public List<FacturaGastosProv> listarFact(String factura);

    public List<FacturaGastosProv> listarFechaRecep(String f1, String f2);

    public List<FacturaGastosProv> listarFechaPago(String f3, String f4);

    public List<FacturaGastosProv> listarNoRecep(String recepcion);

    public List<FacturaGastosProv> listarFolioWCXP(String folio);

    public List<FacturaGastosProv> listarEstatus(String estatus);

    public List<FacturaGastosProv> listaAdminFactura();

    public void InsertFactura(FacturaGastosProv factura);

    public void UpdateFactura(FacturaGastosProv factura);

    public void DeleteFactura(FacturaGastosProv factura);
}
