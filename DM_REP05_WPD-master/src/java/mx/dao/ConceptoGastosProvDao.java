package mx.dao;

import java.util.List;
import mx.model.ConceptoGastosProv;

public interface ConceptoGastosProvDao {

    public List<ConceptoGastosProv> listaConcepto();

    public void InsertConcepto(ConceptoGastosProv concepto);

    public void UpdateConcepto(ConceptoGastosProv concepto);

    public void DeleteConcepto(ConceptoGastosProv concepto);

}
