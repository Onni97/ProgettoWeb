package it.unitn.aa1920.webprogramming.sistemasanitario.Servlets;

import it.unitn.aa1920.webprogramming.sistemasanitario.Beans.UserBean;
import it.unitn.aa1920.webprogramming.sistemasanitario.Beans.ListaMediciBean;
import it.unitn.aa1920.webprogramming.sistemasanitario.Classes.dbUtils;
import it.unitn.aa1920.webprogramming.sistemasanitario.DAO.UserDAO;
import it.unitn.aa1920.webprogramming.sistemasanitario.Exceptions.DAOException;
import it.unitn.aa1920.webprogramming.sistemasanitario.Exceptions.DAOFactoryException;
import it.unitn.aa1920.webprogramming.sistemasanitario.Factory.DAOFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;


public class loginServlet extends javax.servlet.http.HttpServlet {

    enum LoginStatus {
        LOGIN_OK_BUT_NOT_DOCTOR,
        LOGIN_OK_AS_DOCTOR,
        LOGIN_OK_AS_USER,
        LOGIN_WRONG
    }

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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        HttpSession session = req.getSession(true);
        String codiceFiscale = req.getParameter("codiceFiscale");
        String password = req.getParameter("password");
        String userLogin = req.getParameter("user");

        String contextPath = getServletContext().getContextPath();
        if (!contextPath.endsWith("/")) {
            contextPath += "/";
        }
        try {
            boolean status = userDAO.checkUserPassword(codiceFiscale, password);
            LoginStatus loginOk;

            if (status) {
                UserBean user = userDAO.getByPrimaryKey(codiceFiscale);

                //login ok
                if (userLogin == null) {
                    //cerco di fare il login come medico
                    if (user.getIsDoctor()) {
                        //login ok come medico
                        loginOk = LoginStatus.LOGIN_OK_AS_DOCTOR;
                    } else {
                        //login ok ma non è un medico
                        loginOk = LoginStatus.LOGIN_OK_BUT_NOT_DOCTOR;
                    }
                } else {
                    //login ok come utente
                    loginOk = LoginStatus.LOGIN_OK_AS_USER;
                }
            } else {
                //login errato
                loginOk = LoginStatus.LOGIN_WRONG;
            }


            if (loginOk == LoginStatus.LOGIN_OK_BUT_NOT_DOCTOR) {
                //login ok ma non è un dotore
                System.out.println("NON SEI UN DOTTORE");
                resp.sendRedirect(resp.encodeRedirectURL(contextPath + "login?error=-2"));

            } else if (loginOk == LoginStatus.LOGIN_OK_AS_DOCTOR) {
                //login ok come medico
                System.out.println("LOGIN OK COME MEDICO");

                UserBean user = new UserBean();
                dbUtils.getUserData(codiceFiscale, user);
                session.setAttribute("user", user);

                UserBean doctorbean = new UserBean();
                dbUtils.getDoctorData(user.getMedicoDiBase(), doctorbean);
                session.setAttribute("usersDoctor", doctorbean);

                ListaMediciBean listaMediciDellaProvincia = new ListaMediciBean();
                List<Integer> listaCodiciMediciDellaProvincia = new LinkedList<>();
                dbUtils.getListaMediciPerProfincia(user.getProvincia(), listaCodiciMediciDellaProvincia);
                for (int codiceMedico : listaCodiciMediciDellaProvincia) {
                    UserBean medico = new UserBean();
                    dbUtils.getDoctorData(codiceMedico, medico);
                    listaMediciDellaProvincia.addMedico(medico);
                }
                session.setAttribute("listaMediciDellaProvincia", listaMediciDellaProvincia);

                //TODO
                resp.sendRedirect(resp.encodeRedirectURL(contextPath + "doctorPage"));

            } else if (loginOk == LoginStatus.LOGIN_OK_AS_USER) {
                //login ok come utente
                System.out.println("LOGIN OK COME UTENTE");
                session.setAttribute("codiceFiscale", codiceFiscale);

                resp.sendRedirect(resp.encodeRedirectURL(contextPath + "userPage"));

            } else {
                //login errato
                System.out.println("PASSWORD SBAGLIATA");
                resp.sendRedirect(resp.encodeRedirectURL(contextPath + "login?error=-1"));

            }
        } catch (DAOException e) {
            System.out.println("500, non riesco a comunicare con il db");
            resp.sendRedirect(resp.encodeRedirectURL(contextPath + "login?error=-3"));
        }
    }
}
