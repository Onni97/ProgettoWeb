package it.unitn.aa1920.webprogramming.sistemasanitario.Servlets;

import it.unitn.aa1920.webprogramming.sistemasanitario.Beans.UserBean;
import it.unitn.aa1920.webprogramming.sistemasanitario.DAO.ExamDAO;
import it.unitn.aa1920.webprogramming.sistemasanitario.DAO.RecipeDAO;
import it.unitn.aa1920.webprogramming.sistemasanitario.DAO.UserDAO;
import it.unitn.aa1920.webprogramming.sistemasanitario.DAO.VisitDAO;
import it.unitn.aa1920.webprogramming.sistemasanitario.Exceptions.DAOException;
import it.unitn.aa1920.webprogramming.sistemasanitario.Exceptions.DAOFactoryException;
import it.unitn.aa1920.webprogramming.sistemasanitario.Factory.DAOFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

public class enterVisitDataServlet extends HttpServlet {

    private VisitDAO visitDAO;
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
            visitDAO = daoFactory.getDAO(VisitDAO.class);
            userDAO = daoFactory.getDAO(UserDAO.class);
            examDAO = daoFactory.getDAO(ExamDAO.class);
            recipeDAO = daoFactory.getDAO(RecipeDAO.class);
        } catch (DAOFactoryException e) {
            e.printStackTrace();
        }
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String contextPath = getServletContext().getContextPath();
        if (!contextPath.endsWith("/")) {
            contextPath += "/";
        }
        try {
            String cfDoctor = request.getParameter("cfDoctor");
            String cfPatient = request.getParameter("cfPatient");
            UserBean doctor = userDAO.getByPrimaryKey(cfDoctor);
            String description = request.getParameter("description");
            String visitDateString = request.getParameter("dateTime");

            int codiceVisita = visitDAO.addVisit(visitDateString, description, cfPatient, doctor.getCodiceMedico());

            Enumeration<String> parameterNames = request.getParameterNames();
            List<Integer> examIndexes = new LinkedList<>();
            List<Integer> recipeIndexes = new LinkedList<>();
            while (parameterNames.hasMoreElements()) {
                String paramName = parameterNames.nextElement();
                if (paramName.contains("esameData")) {
                    examIndexes.add(Integer.parseInt(paramName.substring(9)));
                }
                if (paramName.contains("ricettaFarmaco")) {
                    recipeIndexes.add(Integer.parseInt(paramName.substring(14)));
                }
            }

            for (int esame : examIndexes) {
                String dataOraFissata = request.getParameter("esameData" + esame) + " " + request.getParameter("esameOra" + esame);
                int esameMedico = Integer.parseInt(request.getParameter("esameMedico" + esame).split(" ")[0]);
                String esameTipo = request.getParameter("esameTipo"+ esame);
                examDAO.addExam(dataOraFissata, codiceVisita, esameMedico, esameTipo);
            }

            for (int ricetta : recipeIndexes) {
                String farmaco = request.getParameter("ricettaFarmaco" + ricetta);
                int quantita = Integer.parseInt(request.getParameter("ricettaQuantita" + ricetta));
                String descrizioneFarmaco = request.getParameter("ricettaDescrizione" + ricetta);
                recipeDAO.addRecipe(farmaco, quantita, descrizioneFarmaco, codiceVisita, null);
            }


        } catch (DAOException e) {
            e.printStackTrace();
        }


        response.sendRedirect(response.encodeRedirectURL(contextPath + "doctorPage"));
    }
}
