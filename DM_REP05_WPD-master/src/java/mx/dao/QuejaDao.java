package mx.dao;

import java.util.List;
import mx.model.Queja;

public interface QuejaDao {

    List<Queja> listaQueja();

    public void InsertQueja(Queja queja);

    public void UpdateQueja(Queja queja);

    public void DeleteQueja(Queja queja);

}
