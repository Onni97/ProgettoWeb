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
        LOGIN_OK_AS_SSP
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
        String userLogin = req.getParameter("user");
        String sspLogin = req.getParameter("ssp");
        String rememberMe = req.getParameter("rememberMe");

        String contextPath = getServletContext().getContextPath();
        if (!contextPath.endsWith("/")) {
            contextPath += "/";
        }
        try {
            boolean status = userDAO.checkUserPassword(utente.toUpperCase(), password);
            LoginStatus loginOk;

            if (status) {
                UserBean user = userDAO.getByPrimaryKey(utente);

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
                //controllo se ha fatto il login come ssp
                if (sspLogin != null) {
                    if (sspDAO.checkSspPassword(utente, password)) {
                        loginOk = LoginStatus.LOGIN_OK_AS_SSP;
                    } else {
                        //login errato
                        loginOk = LoginStatus.LOGIN_WRONG;
                    }
                } else {
                    //login errato
                    loginOk = LoginStatus.LOGIN_WRONG;
                }

            }


            if (loginOk == LoginStatus.LOGIN_OK_BUT_NOT_DOCTOR) {
                //login ok ma non è un dotore
                System.out.println("NON SEI UN DOTTORE");
                resp.sendRedirect(resp.encodeRedirectURL(contextPath + "login?error=-2"));

            } else if (loginOk == LoginStatus.LOGIN_OK_AS_DOCTOR) {
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

            } else if (loginOk == LoginStatus.LOGIN_OK_AS_USER) {
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

            } else if (loginOk == LoginStatus.LOGIN_WRONG){
                //login errato
                System.out.println("PASSWORD SBAGLIATA");
                resp.sendRedirect(resp.encodeRedirectURL(contextPath + "login?error=-1"));

            } else {
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
            }
        } catch (DAOException e) {
            System.out.println("500, non riesco a comunicare con il db");
            resp.sendRedirect(resp.encodeRedirectURL(contextPath + "login?error=-3"));
        }
    }
}
