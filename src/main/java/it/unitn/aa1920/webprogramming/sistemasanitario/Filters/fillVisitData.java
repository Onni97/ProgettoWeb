package it.unitn.aa1920.webprogramming.sistemasanitario.Filters;

import it.unitn.aa1920.webprogramming.sistemasanitario.Beans.UserBean;
import it.unitn.aa1920.webprogramming.sistemasanitario.Beans.VisitBean;
import it.unitn.aa1920.webprogramming.sistemasanitario.DAO.UserDAO;
import it.unitn.aa1920.webprogramming.sistemasanitario.DAO.VisitDAO;
import it.unitn.aa1920.webprogramming.sistemasanitario.Exceptions.DAOException;
import it.unitn.aa1920.webprogramming.sistemasanitario.Exceptions.DAOFactoryException;
import it.unitn.aa1920.webprogramming.sistemasanitario.Factory.DAOFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class fillVisitData implements Filter {

    private VisitDAO visitDAO;
    private UserDAO userDAO;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        DAOFactory daoFactory = (DAOFactory) filterConfig.getServletContext().getAttribute("daoFactory");
        if (daoFactory == null) {
            throw new ServletException("Impossible to get dao factory for user storage system");
        }

        try {
            visitDAO = daoFactory.getDAO(VisitDAO.class);
            userDAO = daoFactory.getDAO(UserDAO.class);
        } catch (DAOFactoryException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            HttpSession session = ((HttpServletRequest) servletRequest).getSession();
            int id = Integer.parseInt(servletRequest.getParameter("id"));
            VisitBean visit = visitDAO.getByPrimaryKey(id);

            UserBean utente = userDAO.getByPrimaryKey((String) session.getAttribute("codiceFiscale"));

            if (utente.getCodiceFiscale().toUpperCase().equals(visit.getUtente().getCodiceFiscale()) || utente.getIsDoctor()) {
                servletRequest.setAttribute("visit", visit);
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                ((HttpServletResponse)servletResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {

    }
}
