package it.unitn.aa1920.webprogramming.sistemasanitario.Servlets;

import it.unitn.aa1920.webprogramming.sistemasanitario.Beans.ExamBean;
import it.unitn.aa1920.webprogramming.sistemasanitario.Beans.RecipeBean;
import it.unitn.aa1920.webprogramming.sistemasanitario.Beans.UserBean;
import it.unitn.aa1920.webprogramming.sistemasanitario.DAO.ExamDAO;
import it.unitn.aa1920.webprogramming.sistemasanitario.DAO.RecipeDAO;
import it.unitn.aa1920.webprogramming.sistemasanitario.DAO.UserDAO;
import it.unitn.aa1920.webprogramming.sistemasanitario.Exceptions.DAOException;
import it.unitn.aa1920.webprogramming.sistemasanitario.Exceptions.DAOFactoryException;
import it.unitn.aa1920.webprogramming.sistemasanitario.Factory.DAOFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;


public class loginServlet extends javax.servlet.http.HttpServlet {

    enum LoginStatus {
        LOGIN_OK_BUT_NOT_DOCTOR,
        LOGIN_OK_AS_DOCTOR,
        LOGIN_OK_AS_USER,
        LOGIN_WRONG
    }

    private UserDAO userDAO;
    private ExamDAO examDAO;
    private RecipeDAO recipeDAO;

    @Override
    public void init() throws ServletException {
        DAOFactory daoFactory = (DAOFactory) super.getServletContext().getAttribute("daoFactory");
        if (daoFactory == null) {
            throw new ServletException("Impossible to get dao factory for user storage system");
        }

        try {
            examDAO = daoFactory.getDAO(ExamDAO.class);
            recipeDAO = daoFactory.getDAO(RecipeDAO.class);
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

        //test
        System.out.println("INIZIO TEST");
        try {
            List<ExamBean> esami = examDAO.getExamsOfUser(codiceFiscale);
            for (ExamBean exam : esami) {
                System.out.println(exam.getCodice());
                System.out.println(exam.getCodiceVisita());
                System.out.println(exam.getData());
                System.out.println(exam.getFatto());
                System.out.println(exam.getReferto());
                System.out.println(exam.getTicket());
            }

            List<RecipeBean> ricette = recipeDAO.getRecipesOfUser(codiceFiscale);
            for (RecipeBean recipe : ricette) {
                System.out.println(recipe.getCodice());
                System.out.println(recipe.getCodiceEsame());
                System.out.println(recipe.getCodiceVisita());
                System.out.println(recipe.getEvasa());
                System.out.println(recipe.getFarmaco());
                System.out.println(recipe.getQuantita());
            }
        } catch (DAOException e) {
            e.printStackTrace();
        }
        System.out.println("FINE TEST");
        //fine test

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
