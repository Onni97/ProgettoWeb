package it.unitn.aa1920.webprogramming.sistemasanitario.Servlets;

import it.unitn.aa1920.webprogramming.sistemasanitario.Beans.ExamBean;
import it.unitn.aa1920.webprogramming.sistemasanitario.Beans.UserBean;
import it.unitn.aa1920.webprogramming.sistemasanitario.DAO.UserDAO;
import it.unitn.aa1920.webprogramming.sistemasanitario.Utils.SendMail;
import it.unitn.aa1920.webprogramming.sistemasanitario.DAO.ExamDAO;
import it.unitn.aa1920.webprogramming.sistemasanitario.DAO.RecipeDAO;
import it.unitn.aa1920.webprogramming.sistemasanitario.Exceptions.DAOException;
import it.unitn.aa1920.webprogramming.sistemasanitario.Exceptions.DAOFactoryException;
import it.unitn.aa1920.webprogramming.sistemasanitario.Factory.DAOFactory;
import net.glxn.qrgen.core.scheme.EMail;
import org.w3c.dom.UserDataHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class updateExamServlet extends HttpServlet {

    private ExamDAO examDAO;
    private RecipeDAO recipeDAO;
    private UserDAO userDAO;

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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        String contextPath = getServletContext().getContextPath();
        int codice = -1;
        if (!contextPath.endsWith("/")) {
            contextPath += "/";
        }
        try {
            codice = Integer.parseInt(request.getParameter("codice"));
            String data = request.getParameter("data");
            String ora = request.getParameter("ora");
            String stringDataOra = data + " " + ora;
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

            Date dataOra = formatter.parse(stringDataOra);
            String referto = request.getParameter("referto");
            float ticket = Float.parseFloat(request.getParameter("ticket"));
            String chiudi = request.getParameter("chiudi");

            ExamBean exam = examDAO.getByPrimaryKey(codice);
            String userMail = "";
            userMail = exam.getVisita().getUtente().getEmail();

            //aggiungo le ricette
            UserBean doctor = userDAO.getByPrimaryKey((String) session.getAttribute("codiceFiscale"));

            Enumeration<String> parameterNames = request.getParameterNames();
            List<Integer> recipeIndexes = new LinkedList<>();
            while (parameterNames.hasMoreElements()) {
                String paramName = parameterNames.nextElement();
                if (paramName.contains("ricettaFarmaco")) {
                    recipeIndexes.add(Integer.parseInt(paramName.substring(14)));
                }
            }
            for (int ricetta : recipeIndexes) {
                String farmaco = request.getParameter("ricettaFarmaco" + ricetta).replace("'", "\\'");
                int quantita = Integer.parseInt(request.getParameter("ricettaQuantita" + ricetta));
                String descrizioneFarmaco = request.getParameter("ricettaDescrizione" + ricetta).replace("'", "\\'");
                recipeDAO.addRecipe(farmaco, quantita, descrizioneFarmaco, doctor.getProvincia() ,null, codice);
                SendMail mail = new SendMail(SendMail.SendMailType.RICETTA, userMail);
                mail.start();
            }

            if (chiudi == null) {
                //lascio l'esame in sospeso
                examDAO.updateExam(codice, dataOra, referto, ticket, false);
                response.sendRedirect(response.encodeRedirectURL(contextPath + "doctorPage?error=2"));
            } else {
                //chiudo l'esame
                examDAO.updateExam(codice, dataOra, referto, ticket, true);
                response.sendRedirect(response.encodeRedirectURL(contextPath + "doctorPage?error=1"));
            }
        } catch (ParseException | DAOException e) {
            //e.printStackTrace();
            response.sendRedirect(response.encodeRedirectURL(contextPath + "doctorPage?error=-1&id=" + codice));
        }

    }
}
