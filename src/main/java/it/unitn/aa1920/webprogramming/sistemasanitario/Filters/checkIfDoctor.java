package it.unitn.aa1920.webprogramming.sistemasanitario.Filters;

import it.unitn.aa1920.webprogramming.sistemasanitario.Beans.UserBean;
import it.unitn.aa1920.webprogramming.sistemasanitario.DAO.UserDAO;
import it.unitn.aa1920.webprogramming.sistemasanitario.Exceptions.DAOException;

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
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpSession session = ((HttpServletRequest)req).getSession();
        String codiceFiscale = (String) session.getAttribute("codiceFiscale");

        try {
            System.out.println(userDAO.getByPrimaryKey(codiceFiscale));
            if(userDAO.getByPrimaryKey(codiceFiscale).getIsDoctor()) {
                System.out.println("LOGIN COME DOTTORE");
                chain.doFilter(req, resp);
            } else {
                System.out.println("FILTERED: not doctor");
                ServletContext sc = req.getServletContext();
                String contextPath = sc.getContextPath();
                if (!contextPath.endsWith("/")) {
                    contextPath += "/";
                }
                ((HttpServletResponse)resp).sendRedirect(((HttpServletResponse)resp).encodeRedirectURL(contextPath));
            }
        } catch (DAOException e) {
            e.printStackTrace();
        }


    }

    public void init(FilterConfig config) throws ServletException {

    }

}
