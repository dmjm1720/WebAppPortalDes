package mx.dao;

import java.util.List;
import mx.model.FacturaComplementoGto;

public interface FacturaCompGtoDao {

    public List<FacturaComplementoGto> listaFactura();

    public List<FacturaComplementoGto> listaFacRFC(String rfc);

    public List<FacturaComplementoGto> listarFact(String factura);

    public List<FacturaComplementoGto> listarAdministrador();

    public List<FacturaComplementoGto> listarFechaRecep(String f1, String f2);

    public List<FacturaComplementoGto> listarNoRecep(String recepcion);

    public List<FacturaComplementoGto> listarFolioWCXP(String folio);

    public List<FacturaComplementoGto> listarEstatus(String estatus);

    public List<FacturaComplementoGto> listaAdminFactura();

    public void InsertFactura(FacturaComplementoGto factura);

    public void UpdateFactura(FacturaComplementoGto factura);

    public void DeleteFactura(FacturaComplementoGto factura);
}
