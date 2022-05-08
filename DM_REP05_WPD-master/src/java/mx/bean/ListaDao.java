package mx.bean;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import mx.dao.DAO;
import mx.model.Factura;

public class ListaDao extends DAO {

    public List<Factura> listaDaoRFC(String RFC) throws SQLException {
//        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
//        String nombre = request.getParameter("frmRFC:codigo");
        List<Factura> lista;
        try {
            this.Conectarprov();
            PreparedStatement ps = this.getCnprov().prepareStatement("SELECT RFC_E, FACTURA, FECHA, TOTAL, REFERENCIA, FOLIOWCXP, FECHA_RECEPCION, FECHA_PAGO, ESTATUS, WCXP FROM FACTURA WHERE RFC_E ='" + RFC + "' ORDER BY ID DESC");
            ResultSet rs = ps.executeQuery();
            lista = new ArrayList();
            while (rs.next()) {
                Factura fDao = new Factura();
                fDao.setRfcE(rs.getString("RFC_E"));
                fDao.setFactura(rs.getString("FACTURA"));
                fDao.setFecha(rs.getString("FECHA"));
                fDao.setTotal(rs.getBigDecimal("TOTAL"));
                fDao.setReferencia(rs.getString("REFERENCIA"));
                fDao.setFoliowcxp(rs.getInt("FOLIOWCXP"));
                fDao.setFechaRecepcion(rs.getString("FECHA_RECEPCION"));
                fDao.setFechaPago(rs.getString("FECHA_PAGO"));
                fDao.setEstatus(rs.getString("ESTATUS"));
                fDao.setWcxp(rs.getString("WCXP"));
                lista.add(fDao);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrarprov();
        }
        return lista;

    }
}
