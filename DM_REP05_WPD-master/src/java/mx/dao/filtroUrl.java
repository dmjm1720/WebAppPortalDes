
package mx.dao;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpSession;


public class filtroUrl implements PhaseListener{

    @Override
    public void afterPhase(PhaseEvent event) {
        FacesContext facesContext = event.getFacesContext();
        String currrentPage= facesContext.getViewRoot().getViewId();
        boolean isPageLogin= currrentPage.lastIndexOf("/index.xhtml") > -1 ? true : false;
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        Object nombre = session.getAttribute("nombre");
        if (!isPageLogin && nombre==null) {
            NavigationHandler nHandler = facesContext.getApplication().getNavigationHandler();
            nHandler.handleNavigation(facesContext, null, "/index.xhtml");
            
        } else {
        }
    }

    @Override
    public void beforePhase(PhaseEvent event) {
        
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }
    
}
