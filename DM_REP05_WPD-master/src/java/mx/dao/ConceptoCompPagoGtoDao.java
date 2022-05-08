package mx.dao;

import java.util.List;
import mx.model.ConceptoPagosCompGto;

public interface ConceptoCompPagoGtoDao {

    public List<ConceptoPagosCompGto> listaConcepto();

    public void InsertConcepto(ConceptoPagosCompGto concepto);

    public void UpdateConcepto(ConceptoPagosCompGto concepto);

    public void DeleteConcepto(ConceptoPagosCompGto concepto);

}
