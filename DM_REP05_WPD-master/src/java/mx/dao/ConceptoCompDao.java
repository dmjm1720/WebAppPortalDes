package mx.dao;

import java.util.List;
import mx.model.ConceptoComplemento;

public interface ConceptoCompDao {

    public List<ConceptoComplemento> listaConcepto();

    public void InsertConcepto(ConceptoComplemento concepto);

    public void UpdateConcepto(ConceptoComplemento concepto);

    public void DeleteConcepto(ConceptoComplemento concepto);

}
