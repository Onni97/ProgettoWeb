package it.unitn.aa1920.webprogramming.sistemasanitario.Filters;

import it.unitn.aa1920.webprogramming.sistemasanitario.Beans.ExamTypeBean;
import it.unitn.aa1920.webprogramming.sistemasanitario.Beans.UserBean;
import it.unitn.aa1920.webprogramming.sistemasanitario.DAO.DoctorDAO;
import it.unitn.aa1920.webprogramming.sistemasanitario.DAO.ExamDAO;
import it.unitn.aa1920.webprogramming.sistemasanitario.DAO.RecipeDAO;
import it.unitn.aa1920.webprogramming.sistemasanitario.DAO.UserDAO;
import it.unitn.aa1920.webprogramming.sistemasanitario.Exceptions.DAOException;
import it.unitn.aa1920.webprogramming.sistemasanitario.Exceptions.DAOFactoryException;
import it.unitn.aa1920.webprogramming.sistemasanitario.Factory.DAOFactory;

import javax.print.Doc;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class fillPatientForVisit implements Filter {

    private UserDAO userDAO;
    private DoctorDAO doctorDAO;
    private ExamDAO examDAO;

    public void init(FilterConfig config) throws ServletException {
        DAOFactory daoFactory = (DAOFactory) config.getServletContext().getAttribute("daoFactory");
        if (daoFactory == null) {
            throw new ServletException("Impossible to get dao factory for user storage system");
        }

        try {
            userDAO = daoFactory.getDAO(UserDAO.class);
            doctorDAO = daoFactory.getDAO(DoctorDAO.class);
            examDAO = daoFactory.getDAO(ExamDAO.class);
        } catch (DAOFactoryException e) {
            e.printStackTrace();
        }
    }


    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        try {
            HttpSession session = ((HttpServletRequest) req).getSession();
            String cfPatient = req.getParameter("cfPatient");
            UserBean patient = userDAO.getByPrimaryKey(cfPatient);
            UserBean doctor = userDAO.getByPrimaryKey((String) session.getAttribute("codiceFiscale"));
            List<UserBean> doctorsOfProvince = doctorDAO.getListaMediciPerProfincia(patient.getProvincia());
            List<ExamTypeBean> examTypes = examDAO.getExamTypes();

            if (patient.getMedicoDiBase() == doctor.getCodiceMedico()) {
                req.setAttribute("patient", patient);
                req.setAttribute("doctor", doctor);
                req.setAttribute("doctorsOfProvince", doctorsOfProvince);
                req.setAttribute("examTypes", examTypes);

                chain.doFilter(req, resp);
            } else {
                ((HttpServletResponse) resp).sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }


    public void destroy() {
    }

}
