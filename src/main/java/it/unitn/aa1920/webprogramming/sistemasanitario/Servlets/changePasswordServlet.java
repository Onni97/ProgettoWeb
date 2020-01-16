package it.unitn.aa1920.webprogramming.sistemasanitario.Servlets;

import it.unitn.aa1920.webprogramming.sistemasanitario.DAO.UserDAO;
import it.unitn.aa1920.webprogramming.sistemasanitario.Exceptions.DAOException;
import it.unitn.aa1920.webprogramming.sistemasanitario.Exceptions.DAOFactoryException;
import it.unitn.aa1920.webprogramming.sistemasanitario.Factory.DAOFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class changePasswordServlet extends javax.servlet.http.HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        DAOFactory daoFactory = (DAOFactory) super.getServletContext().getAttribute("daoFactory");
        if (daoFactory == null) {
            throw new ServletException("Impossible to get dao factory for user storage system");
        }

        try {
            userDAO = daoFactory.getDAO(UserDAO.class);
        } catch (DAOFactoryException e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();

        String oldPassword = req.getParameter("oldPassword");
        String newPassword = req.getParameter("newPassword");
        String repeatPassword = req.getParameter("repeatPassword");
        String codiceFiscaleUtente = (String) session.getAttribute("codiceFiscale");

        String contextPath = getServletContext().getContextPath();
        if (!contextPath.endsWith("/")) {
            contextPath += "/";
        }

        if (oldPassword.equals("") || newPassword.equals("") || repeatPassword.equals("")) {
            resp.sendRedirect(resp.encodeRedirectURL(contextPath + "userPage?error=-1"));
        } else {
            try {
                if (userDAO.checkUserPassword(codiceFiscaleUtente, oldPassword)) {
                    if (newPassword.equals(repeatPassword)) {
                        userDAO.changeUserPassword(codiceFiscaleUtente, newPassword);

                        resp.sendRedirect(resp.encodeRedirectURL(contextPath + "userPage"));
                    } else {
                        resp.sendRedirect(resp.encodeRedirectURL(contextPath + "userPage?error=-3"));
                    }
                } else {
                    resp.sendRedirect(resp.encodeRedirectURL(contextPath + "userPage?error=-2"));
                }

            } catch (DAOException e) {
                e.printStackTrace();
            }
        }

    }
}
