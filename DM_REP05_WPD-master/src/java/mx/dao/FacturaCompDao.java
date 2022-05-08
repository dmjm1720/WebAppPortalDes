package mx.dao;

import java.util.List;
import mx.model.FacturaComplemento;

public interface FacturaCompDao {

    public List<FacturaComplemento> listaFactura();

    public List<FacturaComplemento> listaFacRFC(String rfc);

    public List<FacturaComplemento> listarFact(String factura);

    public List<FacturaComplemento> listarAdministrador();

    public List<FacturaComplemento> listarFechaRecep(String f1, String f2);

    public List<FacturaComplemento> listarNoRecep(String recepcion);

    public List<FacturaComplemento> listarFolioWCXP(String folio);

    public List<FacturaComplemento> listarEstatus(String estatus);

    public List<FacturaComplemento> listaAdminFactura();

    public void InsertFactura(FacturaComplemento factura);

    public void UpdateFactura(FacturaComplemento factura);

    public void DeleteFactura(FacturaComplemento factura);

    public List<FacturaComplemento> listaFolioComprobante(String uuid);
}
