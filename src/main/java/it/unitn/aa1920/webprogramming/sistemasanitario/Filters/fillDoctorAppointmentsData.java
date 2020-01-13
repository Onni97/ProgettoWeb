package it.unitn.aa1920.webprogramming.sistemasanitario.Filters;

import it.unitn.aa1920.webprogramming.sistemasanitario.Beans.ExamBean;
import it.unitn.aa1920.webprogramming.sistemasanitario.Beans.UserBean;
import it.unitn.aa1920.webprogramming.sistemasanitario.DAO.ExamDAO;
import it.unitn.aa1920.webprogramming.sistemasanitario.DAO.UserDAO;
import it.unitn.aa1920.webprogramming.sistemasanitario.Exceptions.DAOException;
import it.unitn.aa1920.webprogramming.sistemasanitario.Exceptions.DAOFactoryException;
import it.unitn.aa1920.webprogramming.sistemasanitario.Factory.DAOFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class fillDoctorAppointmentsData implements Filter {

    private ExamDAO examDAO;
    private UserDAO userDAO;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        DAOFactory daoFactory = (DAOFactory) filterConfig.getServletContext().getAttribute("daoFactory");
        if (daoFactory == null) {
            throw new ServletException("Impossible to get dao factory for user storage system");
        }

        try {
            examDAO = daoFactory.getDAO(ExamDAO.class);
            userDAO = daoFactory.getDAO(UserDAO.class);
        } catch (DAOFactoryException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            HttpSession session = ((HttpServletRequest) servletRequest).getSession();
            String codiceFiscale = (String) session.getAttribute("codiceFiscale");
            UserBean user = userDAO.getByPrimaryKey(codiceFiscale);

            List<ExamBean> examList = examDAO.getExamsOfDoctor(user.getCodiceMedico());
            servletRequest.setAttribute("examList", examList);

            filterChain.doFilter(servletRequest, servletResponse);
        } catch (DAOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void destroy() {

    }
}
