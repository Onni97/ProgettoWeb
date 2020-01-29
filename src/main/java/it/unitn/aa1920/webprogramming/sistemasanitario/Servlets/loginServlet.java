package it.unitn.aa1920.webprogramming.sistemasanitario.Servlets;

import it.unitn.aa1920.webprogramming.sistemasanitario.Beans.UserBean;
import it.unitn.aa1920.webprogramming.sistemasanitario.DAO.SspDAO;
import it.unitn.aa1920.webprogramming.sistemasanitario.DAO.UserDAO;
import it.unitn.aa1920.webprogramming.sistemasanitario.Exceptions.DAOException;
import it.unitn.aa1920.webprogramming.sistemasanitario.Exceptions.DAOFactoryException;
import it.unitn.aa1920.webprogramming.sistemasanitario.Factory.DAOFactory;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class loginServlet extends javax.servlet.http.HttpServlet {

    private enum LoginStatus {
        LOGIN_OK_BUT_NOT_DOCTOR,
        LOGIN_OK_AS_DOCTOR,
        LOGIN_OK_AS_USER,
        LOGIN_WRONG,
        LOGIN_OK_AS_SSP,
        LOGIN_OK_BUT_NOT_SSP,
        LOGIN_OK_BUT_NOT_USER
    }

    private UserDAO userDAO;
    private SspDAO sspDAO;

    @Override
    public void init() throws ServletException {
        DAOFactory daoFactory = (DAOFactory) super.getServletContext().getAttribute("daoFactory");
        if (daoFactory == null) {
            throw new ServletException("Impossible to get dao factory for user storage system");
        }

        try {
            userDAO = daoFactory.getDAO(UserDAO.class);
            sspDAO = daoFactory.getDAO(SspDAO.class);
        } catch (DAOFactoryException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(true);
        String utente = req.getParameter("utente");
        String password = req.getParameter("password");
        String rememberMe = req.getParameter("rememberMe");

        boolean userLogin = false, sspLogin = false, doctorLogin = false;
        if (req.getParameter("user") != null ) userLogin = true;
        if (req.getParameter("doctor") != null ) doctorLogin = true;
        if (req.getParameter("ssp") != null ) sspLogin = true;

        String contextPath = getServletContext().getContextPath();
        if (!contextPath.endsWith("/")) {
            contextPath += "/";
        }
        try {
            boolean statusUser = userDAO.checkUserPassword(utente.toUpperCase(), password);
            boolean statusSsp = sspDAO.checkSspPassword(utente, password);
            LoginStatus loginAs = LoginStatus.LOGIN_WRONG;

            if (userLogin) {
                //cerco di fare il login come utente
                if (statusUser)
                    loginAs = LoginStatus.LOGIN_OK_AS_USER;
                else if (statusSsp)
                    loginAs = LoginStatus.LOGIN_OK_BUT_NOT_USER;
            } else if (doctorLogin) {
                //cerco di fare il login come medico
                if (statusUser) {
                    UserBean user = userDAO.getByPrimaryKey(utente);
                    boolean isDoctor = user.getIsDoctor();
                    if (isDoctor)
                        loginAs = LoginStatus.LOGIN_OK_AS_DOCTOR;
                    else
                        loginAs = LoginStatus.LOGIN_OK_BUT_NOT_DOCTOR;
                } else if (statusSsp)
                    loginAs = LoginStatus.LOGIN_OK_BUT_NOT_DOCTOR;
            } else if (sspLogin) {
                if (statusSsp)
                    loginAs = LoginStatus.LOGIN_OK_AS_SSP;
                else if (statusUser)
                    loginAs = LoginStatus.LOGIN_OK_BUT_NOT_SSP;
            }


            if (loginAs == LoginStatus.LOGIN_OK_BUT_NOT_DOCTOR) {
                //login ok ma non è un dotore
                System.out.println("NON SEI UN DOTTORE");
                resp.sendRedirect(resp.encodeRedirectURL(contextPath + "login?error=-2"));

            } else if (loginAs == LoginStatus.LOGIN_OK_AS_DOCTOR) {
                //login ok come medico
                System.out.println("LOGIN OK COME MEDICO");
                session.setAttribute("codiceFiscale", utente.toUpperCase());

                if (rememberMe != null) {
                    //invalido il cookie ssp e mando quello utente
                    Cookie sspCookie = new Cookie("ssp", null);
                    sspCookie.setMaxAge(0);
                    resp.addCookie(sspCookie);

                    Cookie codiceFiscaleCoockie = new Cookie("user", utente);
                    codiceFiscaleCoockie.setMaxAge(60 * 24 * 3600);
                    resp.addCookie(codiceFiscaleCoockie);
                }

                resp.sendRedirect(resp.encodeRedirectURL(contextPath + "doctorPage"));

            } else if (loginAs == LoginStatus.LOGIN_OK_AS_USER) {
                //login ok come utente
                System.out.println("LOGIN OK COME UTENTE");
                session.setAttribute("codiceFiscale", utente.toUpperCase());

                if (rememberMe != null) {
                    //invalido il cookie ssp e mando quello utente
                    Cookie sspCookie = new Cookie("ssp", null);
                    sspCookie.setMaxAge(0);
                    resp.addCookie(sspCookie);

                    Cookie codiceFiscaleCoockie = new Cookie("user", utente);
                    codiceFiscaleCoockie.setMaxAge(60 * 24 * 3600);
                    resp.addCookie(codiceFiscaleCoockie);
                }

                resp.sendRedirect(resp.encodeRedirectURL(contextPath + "userPage"));

            } else if (loginAs == LoginStatus.LOGIN_WRONG) {
                //login errato
                System.out.println("PASSWORD SBAGLIATA");
                resp.sendRedirect(resp.encodeRedirectURL(contextPath + "login?error=-1"));

            } else if (loginAs == LoginStatus.LOGIN_OK_AS_SSP){
                //login ok come ssp
                System.out.println("LOGIN OK COME SSP");
                session.setAttribute("ssp", utente.toUpperCase());

                if (rememberMe != null) {
                    Cookie codiceFiscaleCoockie = new Cookie("user", null);
                    codiceFiscaleCoockie.setMaxAge(0);
                    resp.addCookie(codiceFiscaleCoockie);

                    Cookie sspCookie = new Cookie("ssp", utente);
                    sspCookie.setMaxAge(60 * 24 * 3600);
                    resp.addCookie(sspCookie);
                }

                resp.sendRedirect(resp.encodeRedirectURL(contextPath + "sspPage"));
            } else if (loginAs == LoginStatus.LOGIN_OK_BUT_NOT_SSP) {
                //login ok ma non sei un ssp
                System.out.println("NON SEI UN SSP");
                resp.sendRedirect(resp.encodeRedirectURL(contextPath + "login?error=-4"));
            } else {
                //login ok ma non sei un utente
                System.out.println("NON SEI UN UTENTE");
                resp.sendRedirect(resp.encodeRedirectURL(contextPath + "login?error=-5"));
            }
        } catch (DAOException e) {
            System.out.println("500, non riesco a comunicare con il db");
            resp.sendRedirect(resp.encodeRedirectURL(contextPath + "login?error=-3"));
        }
    }
}
