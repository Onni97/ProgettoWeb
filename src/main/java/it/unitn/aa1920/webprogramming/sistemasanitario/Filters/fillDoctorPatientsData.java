package it.unitn.aa1920.webprogramming.sistemasanitario.Filters;

import it.unitn.aa1920.webprogramming.sistemasanitario.Beans.UserBean;
import it.unitn.aa1920.webprogramming.sistemasanitario.DAO.DoctorDAO;
import it.unitn.aa1920.webprogramming.sistemasanitario.DAO.UserDAO;
import it.unitn.aa1920.webprogramming.sistemasanitario.Exceptions.DAOException;
import it.unitn.aa1920.webprogramming.sistemasanitario.Exceptions.DAOFactoryException;
import it.unitn.aa1920.webprogramming.sistemasanitario.Factory.DAOFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class fillDoctorPatientsData implements Filter {

    private DoctorDAO doctorDAO;
    private UserDAO userDAO;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        DAOFactory daoFactory = (DAOFactory) filterConfig.getServletContext().getAttribute("daoFactory");
        if (daoFactory == null) {
            throw new ServletException("Impossible to get dao factory for user storage system");
        }
        try {
            userDAO = daoFactory.getDAO(UserDAO.class);
            doctorDAO = daoFactory.getDAO(DoctorDAO.class);
        } catch (DAOFactoryException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest) servletRequest).getSession();
        String codiceFiscale = (String) session.getAttribute("codiceFiscale");

        try {
            UserBean user = userDAO.getByPrimaryKey(codiceFiscale);
            List<UserBean> patients = doctorDAO.getPatientsOfDoctor(user.getCodiceMedico());
            servletRequest.setAttribute("patients", patients);

            filterChain.doFilter(servletRequest, servletResponse);
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {

    }
}
