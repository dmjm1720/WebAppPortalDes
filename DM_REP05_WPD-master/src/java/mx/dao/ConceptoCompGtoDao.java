package mx.dao;

import java.util.List;
import mx.model.ConceptoComplementoGto;

public interface ConceptoCompGtoDao {

    public List<ConceptoComplementoGto> listaConcepto();

    public void InsertConcepto(ConceptoComplementoGto concepto);

    public void UpdateConcepto(ConceptoComplementoGto concepto);

    public void DeleteConcepto(ConceptoComplementoGto concepto);
}
