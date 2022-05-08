package mx.dao;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import mx.model.Correo;
import mx.util.HibernateUtil;

public class CorreoDaoImpl implements ICorreoDao {

    @Override
    public Correo listaPrincipal() {
        Correo correo = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        String hql = "FROM Correo WHERE tipo='principal'";
        try {
            correo = (Correo) session.createQuery(hql).uniqueResult();
            t.commit();
            session.close();
        } catch (HibernateException e) {
            t.rollback();
        }
        return correo;
    }

    @Override
    public List<Correo> lista() {
        List<Correo> lista = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        String hql = "SELECT correo FROM Correo WHERE tipo='contabilidad'";
        try {
            lista = session.createQuery(hql).list();
            t.commit();
            session.close();
        } catch (HibernateException e) {
            t.rollback();
        }
        return lista;
    }

}
