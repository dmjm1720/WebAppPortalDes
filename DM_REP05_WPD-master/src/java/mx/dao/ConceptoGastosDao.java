package mx.dao;

import java.util.List;
import mx.model.ConceptoGastos;

public interface ConceptoGastosDao {

    public List<ConceptoGastos> listaConcepto();

    public void InsertConcepto(ConceptoGastos concepto);

    public void UpdateConcepto(ConceptoGastos concepto);

    public void DeleteConcepto(ConceptoGastos concepto);

    public List<String> listaConceptoUUID(String uuid);

}
