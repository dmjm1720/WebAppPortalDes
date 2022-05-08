package mx.dao;

import java.util.List;
import mx.model.Pago;

public interface PagoComDao {

    public List<Pago> listaPago();

    public void InsertPago(Pago pago);

    public void UpdatePago(Pago pago);

    public void DeletePago(Pago pago);

}
