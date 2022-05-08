package mx.dao;

import java.util.List;
import mx.model.DiasPago;

public interface PagoDao {

    List<DiasPago> lista();

    public void InsertPago(DiasPago pago);

    public void UpdatePago(DiasPago pago);

    public void DeletePago(DiasPago pago);

}
