package it.unitn.aa1920.webprogramming.sistemasanitario.Filters;

import it.unitn.aa1920.webprogramming.sistemasanitario.Beans.UserBean;
import it.unitn.aa1920.webprogramming.sistemasanitario.DAO.*;
import it.unitn.aa1920.webprogramming.sistemasanitario.Exceptions.DAOException;
import it.unitn.aa1920.webprogramming.sistemasanitario.Exceptions.DAOFactoryException;
import it.unitn.aa1920.webprogramming.sistemasanitario.Factory.DAOFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class checkIfDoctor implements Filter {
    public void destroy() {
    }

    private UserDAO userDAO;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        DAOFactory daoFactory = (DAOFactory) filterConfig.getServletContext().getAttribute("daoFactory");
        if (daoFactory == null) {
            throw new ServletException("Impossible to get dao factory for user storage system");
        }
        try {
            userDAO = daoFactory.getDAO(UserDAO.class);
        } catch (DAOFactoryException e) {
            e.printStackTrace();
        }
    }


    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpSession session = ((HttpServletRequest)req).getSession();
        String codiceFiscale = (String) session.getAttribute("codiceFiscale");

        try {
            System.out.println(userDAO.getByPrimaryKey(codiceFiscale));
            if(userDAO.getByPrimaryKey(codiceFiscale).getIsDoctor()) {
                chain.doFilter(req, resp);
            } else {
                System.out.println("FILTERED: not a doctor");
                ServletContext sc = req.getServletContext();
                String contextPath = sc.getContextPath();
                if (!contextPath.endsWith("/")) {
                    contextPath += "/";
                }
                ((HttpServletResponse)resp).sendRedirect(((HttpServletResponse)resp).encodeRedirectURL(contextPath + "login"));
            }
        } catch (DAOException e) {
            e.printStackTrace();
        }


    }

}
