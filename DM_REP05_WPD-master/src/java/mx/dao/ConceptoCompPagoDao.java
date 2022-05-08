package mx.dao;

import java.util.List;
import mx.model.ConceptoPagosComp;

public interface ConceptoCompPagoDao {

    public List<ConceptoPagosComp> listaConcepto();

    public void InsertConcepto(ConceptoPagosComp concepto);

    public void UpdateConcepto(ConceptoPagosComp concepto);

    public void DeleteConcepto(ConceptoPagosComp concepto);

}
