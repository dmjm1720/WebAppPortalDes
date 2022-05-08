package mx.dao;

import java.util.List;
import mx.model.Deposito;

public interface DepositoDao {

    public List<Deposito> listaDeposito();

    public void InsertDeposito(Deposito deposito);

    public void UpdateDeposito(Deposito deposito);

    public void DeleteDeposito(Deposito deposito);

}
