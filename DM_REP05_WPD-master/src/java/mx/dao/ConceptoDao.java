package mx.dao;

import java.util.List;
import mx.model.Concepto;

public interface ConceptoDao {

    public List<Concepto> listaConcepto();

    public void InsertConcepto(Concepto concepto);

    public void UpdateConcepto(Concepto concepto);

    public void DeleteConcepto(Concepto concepto);
    
    public List<String> listaConceptoUUID(String uuid);
}
