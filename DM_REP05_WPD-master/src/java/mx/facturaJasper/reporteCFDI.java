package mx.facturaJasper;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import mx.dao.DAO;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;

public class reporteCFDI extends DAO {

    public void getReporte(String ruta, String uuid) {
        this.Conectarprov();
        Map parameter = new HashMap();
        parameter.put("uuid", uuid);
        try {
            File file = new File(ruta);

            HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            httpServletResponse.setDateHeader("Expires", 0);
            httpServletResponse.setContentType("application/PDF");
            JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(file.getPath());
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameter, this.getCnprov());
            JRExporter jrExporter = null;
            jrExporter = new JRPdfExporter();
            jrExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            jrExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, httpServletResponse.getOutputStream());
            if (jrExporter != null) {
                try {
                    jrExporter.exportReport();
                } catch (JRException e) {
                }
            }

        } catch (JRException | IOException e) {
        } finally {
            if (this.getCnprov() != null) {
                try {
                    this.Cerrarprov();
                } catch (SQLException e) {
                }
            }
        }
    }

}
