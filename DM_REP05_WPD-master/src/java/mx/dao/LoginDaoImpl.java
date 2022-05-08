package mx.dao;

import mx.model.Usuario;
import mx.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;

public class LoginDaoImpl implements LoginDao {

    @Override
    public Usuario obternerDatosUsuario(Usuario usuario) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "FROM Usuario WHERE RFC=:RFC and CLAVE=:CLAVE";
        Query q = session.createQuery(hql).setMaxResults(1);
        q.setParameter("RFC", usuario.getRfc());
        q.setParameter("CLAVE", usuario.getClave());
        return (Usuario) q.uniqueResult();
    }

    @Override
    public Usuario login(Usuario usuario) {
        Usuario user = this.obternerDatosUsuario(usuario);
        if (user != null) {
            if (!user.getClave().equals(user.getClave())) {
                user = null;
            }
        }
        return user;
    }

}
