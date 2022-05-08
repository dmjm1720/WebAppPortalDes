package mx.dao;

import java.util.List;
import mx.model.PagoGto;

public interface PagoComGtoDao {

    public List<PagoGto> listaPago();

    public void InsertPago(PagoGto pago);

    public void UpdatePago(PagoGto pago);

    public void DeletePago(PagoGto pago);
}
