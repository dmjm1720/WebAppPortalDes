package mx.bean;

import java.io.File;
import java.io.Serializable;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import mx.dao.DAO;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

@Named(value = "generarPDFBean")
@ViewScoped
public class GenerarPDFBean extends DAO implements Serializable {

    private String uuid;
    private String rfc_e;
    private String fecha_recepcion;
    private String nombreArchivo;
    private final String rutaGastos;
    private final String rutaProv;
    private String año;
    private String mes;
    private String usuario;

    public GenerarPDFBean() {

        this.rutaGastos = "C:\\public\\gastos\\";
        this.rutaProv = "C:\\public\\proveedores\\";

    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getRfc_e() {
        return rfc_e;
    }

    public void setRfc_e(String rfc_e) {
        this.rfc_e = rfc_e;
    }

    public String getFecha_recepcion() {
        return fecha_recepcion;
    }

    public void setFecha_recepcion(String fecha_recepcion) {
        this.fecha_recepcion = fecha_recepcion;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getAño() {
        return año;
    }

    public void setAño(String año) {
        this.año = año;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void pdfGastos() {

        try {
            this.Conectarprov();
            JasperReport jr;
            JasperPrint jp;
            Map mp = new HashMap();
            PreparedStatement ps = this.getCnprov().prepareStatement("SELECT RFC_E, UUID, FECHA_RECEPCION, NOMBRE_ARCHIVO, USUARIO FROM FACTURA_GASTOS WHERE NOMBRE_ARCHIVO IS NOT NULL");
            ResultSet rs = ps.executeQuery();
            if (!rs.isBeforeFirst()) {
                System.out.println("No hay datos...");
            } else {
                while (rs.next()) {
                    try {
                        this.rfc_e = rs.getString("RFC_E");
                        this.uuid = rs.getString("UUID");
                        this.fecha_recepcion = rs.getString("FECHA_RECEPCION");
                        this.nombreArchivo = rs.getString("NOMBRE_ARCHIVO");
                        this.usuario = rs.getString("USUARIO");
                        if (this.usuario.equals("cuentasporpagartoluca@duche.com")) {
                            this.usuario = "cxptoluca1";
                        } else {
                            this.usuario = "cxpdf1";
                        }
                        mp.put("uuid", this.uuid);
                        URL in = this.getClass().getResource("/mx/facturaJasper/facturaGastosPDF.jasper");
                        jr = (JasperReport) JRLoader.loadObject(in);
                        jp = JasperFillManager.fillReport(jr, mp, this.getCnprov());
                        String[] nuevaFecha = this.fecha_recepcion.split("-");
                        this.año = nuevaFecha[0];
                        this.mes = nuevaFecha[1];
                        File carpeta = new File(this.rutaGastos + "\\" + this.usuario + "\\" + this.año + "\\" + this.mes);
                        if (!carpeta.exists()) {
                            carpeta.mkdirs();
                        }
                        JasperExportManager.exportReportToPdfFile(jp, this.rutaGastos + "\\" + this.usuario + "\\" + this.año + "\\" + this.mes + "\\" + this.nombreArchivo.substring(0, this.nombreArchivo.length() - 4) + ".pdf");
                    } catch (JRException ex) {
                        Logger.getLogger(GenerarPDFBean.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            this.rfc_e = null;
            this.uuid = null;
            this.fecha_recepcion = null;
            this.nombreArchivo = null;
            this.usuario = null;
            this.Cerrarprov();
        } catch (SQLException ex) {
            Logger.getLogger(GenerarPDFBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void pdfProveedores() {
        try {
            this.Conectarprov();
            JasperReport jr;
            JasperPrint jp;
            Map mp = new HashMap();
            PreparedStatement ps = this.getCnprov().prepareStatement("SELECT RFC_E, UUID, FECHA_RECEPCION, NOMBRE_ARCHIVO FROM FACTURA WHERE NOMBRE_ARCHIVO IS NOT NULL");
            ResultSet rs = ps.executeQuery();
            if (!rs.isBeforeFirst()) {
                System.out.println("No hay datos...");
            } else {
                while (rs.next()) {
                    try {
                        this.rfc_e = rs.getString("RFC_E");
                        this.uuid = rs.getString("UUID");
                        this.fecha_recepcion = rs.getString("FECHA_RECEPCION");
                        this.nombreArchivo = rs.getString("NOMBRE_ARCHIVO");
                        mp.put("uuid", this.uuid);
                        URL in = this.getClass().getResource("/mx/facturaJasper/facturaPDF.jasper");
                        jr = (JasperReport) JRLoader.loadObject(in);
                        jp = JasperFillManager.fillReport(jr, mp, this.getCnprov());
                        String[] nuevaFecha = this.fecha_recepcion.split("-");
                        this.año = nuevaFecha[0];
                        this.mes = nuevaFecha[1];
                        File carpeta = new File(this.rutaProv + "\\" + this.rfc_e + "\\" + this.año + "\\" + this.mes);
                        if (!carpeta.exists()) {
                            carpeta.mkdirs();
                        }
                        JasperExportManager.exportReportToPdfFile(jp, this.rutaProv + "\\" + this.rfc_e + "\\" + this.año + "\\" + this.mes + "\\" + this.nombreArchivo.substring(0, this.nombreArchivo.length() - 4) + ".pdf");
                    } catch (JRException ex) {
                        Logger.getLogger(GenerarPDFBean.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            this.rfc_e = null;
            this.uuid = null;
            this.fecha_recepcion = null;
            this.nombreArchivo = null;
            this.Cerrarprov();
        } catch (SQLException ex) {
            Logger.getLogger(GenerarPDFBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
