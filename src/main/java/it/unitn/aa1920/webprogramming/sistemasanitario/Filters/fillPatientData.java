package it.unitn.aa1920.webprogramming.sistemasanitario.Filters;

import it.unitn.aa1920.webprogramming.sistemasanitario.Beans.ExamBean;
import it.unitn.aa1920.webprogramming.sistemasanitario.Beans.RecipeBean;
import it.unitn.aa1920.webprogramming.sistemasanitario.Beans.UserBean;
import it.unitn.aa1920.webprogramming.sistemasanitario.Beans.VisitBean;
import it.unitn.aa1920.webprogramming.sistemasanitario.DAO.ExamDAO;
import it.unitn.aa1920.webprogramming.sistemasanitario.DAO.RecipeDAO;
import it.unitn.aa1920.webprogramming.sistemasanitario.DAO.UserDAO;
import it.unitn.aa1920.webprogramming.sistemasanitario.DAO.VisitDAO;
import it.unitn.aa1920.webprogramming.sistemasanitario.Exceptions.DAOException;
import it.unitn.aa1920.webprogramming.sistemasanitario.Exceptions.DAOFactoryException;
import it.unitn.aa1920.webprogramming.sistemasanitario.Factory.DAOFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class fillPatientData implements Filter {

    private static final int VISIT = 1;
    private static final int EXAM = 2;
    private static final int RECIPE = 3;

    private UserDAO userDAO;
    private VisitDAO visitDAO;
    private ExamDAO examDAO;
    private RecipeDAO recipeDAO;

    public void init(FilterConfig config) throws ServletException {
        DAOFactory daoFactory = (DAOFactory) config.getServletContext().getAttribute("daoFactory");
        if (daoFactory == null) {
            throw new ServletException("Impossible to get dao factory for user storage system");
        }

        try {
            examDAO = daoFactory.getDAO(ExamDAO.class);
            userDAO = daoFactory.getDAO(UserDAO.class);
            visitDAO = daoFactory.getDAO(VisitDAO.class);
            recipeDAO = daoFactory.getDAO(RecipeDAO.class);
        } catch (DAOFactoryException e) {
            e.printStackTrace();
        }
    }


    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        try {
            HttpSession session = ((HttpServletRequest) req).getSession();
            String codiceFiscalePaziente = req.getParameter("codiceFiscale").toUpperCase();

            UserBean paziente = userDAO.getByPrimaryKey(codiceFiscalePaziente);
            UserBean medico = userDAO.getByPrimaryKey((String) session.getAttribute("codiceFiscale"));

            if (paziente.getMedicoDiBase() != medico.getCodiceMedico()) {
                //non è un paziente del medico loggato
                ((HttpServletResponse) resp).sendError(HttpServletResponse.SC_UNAUTHORIZED);
            } else {
                List<VisitBean> listaVisite = visitDAO.getVisitsOfUser(codiceFiscalePaziente);
                List<ExamBean> listaEsamiFatti = examDAO.getExamsDoneOfUser(codiceFiscalePaziente);
                List<ExamBean> listaEsamiDaFare = examDAO.getExamsNotDoneOfUser(codiceFiscalePaziente);
                Collections.reverse(listaEsamiDaFare);
                List<RecipeBean> listaRicettePrese = recipeDAO.getRecipesTakenOfUser(codiceFiscalePaziente);
                List<RecipeBean> listaRicetteNonPrese = recipeDAO.getRecipesNotTakenOfUser(codiceFiscalePaziente);

                //creo questa lista per aiutarmi nel jsp a stampare le visite, gli esami fatti e le ricette evase in ordine cronologico
                List<Integer> listaPerStampa = new LinkedList<>();
                int counterVisit = 0, counterExam = 0, counterRecipes = 0;
                for (int i = 0; i < (listaVisite.size() + listaEsamiFatti.size() + listaRicettePrese.size()); i++) {
                    if (counterVisit == listaVisite.size() && counterExam == listaEsamiFatti.size()) {
                        //mi restano solo ricette da stampare
                        listaPerStampa.add(RECIPE);
                        counterRecipes++;
                    } else if (counterVisit == listaVisite.size() && counterRecipes == listaRicettePrese.size()) {
                        //mi restano solo esami da stampare
                        listaPerStampa.add(EXAM);
                        counterExam++;
                    } else if (counterExam == listaEsamiFatti.size() && counterRecipes == listaRicettePrese.size()) {
                        //mi restano solo visite da stampare
                        listaPerStampa.add(VISIT);
                        counterVisit++;
                    } else if (counterVisit == listaVisite.size()) {
                        //ho stampato tutte le visite, stampo il più recente tra esami e ricette
                        if (listaEsamiFatti.get(counterExam).getDataOraFissata().compareTo(listaRicettePrese.get(counterRecipes).getDataOraEvasa())>=0) {
                            listaPerStampa.add(EXAM);
                            counterExam++;
                        } else {
                            listaPerStampa.add(RECIPE);
                            counterRecipes++;
                        }
                    } else if (counterExam == listaEsamiFatti.size()) {
                        //ho stampato tutti gli esami, stampo il più recente tra visite e ricette
                        if (listaVisite.get(counterVisit).getDataOra().compareTo(listaRicettePrese.get(counterRecipes).getDataOraEvasa())>=0) {
                            listaPerStampa.add(VISIT);
                            counterVisit++;
                        } else {
                            listaPerStampa.add(RECIPE);
                            counterRecipes++;
                        }
                    } else if (counterRecipes == listaRicettePrese.size()) {
                        //ho stampato tutte le ricette, stampo il più recente tra esami e visite
                        if (listaVisite.get(counterVisit).getDataOra().compareTo(listaEsamiFatti.get(counterExam).getDataOraFissata())>=0) {
                            listaPerStampa.add(VISIT);
                            counterVisit++;
                        } else {
                            listaPerStampa.add(EXAM);
                            counterExam++;
                        }
                    } else {
                        //nessuna lista è finita, stampo il più recente tra visite, esami e ricette
                        if (listaVisite.get(counterVisit).getDataOra().compareTo(listaEsamiFatti.get(counterExam).getDataOraFissata())>=0 &&
                                listaVisite.get(counterVisit).getDataOra().compareTo(listaRicettePrese.get(counterRecipes).getDataOraEvasa())>=0) {
                            listaPerStampa.add(VISIT);
                            counterVisit++;
                            continue;
                        }
                        if (listaEsamiFatti.get(counterExam).getDataOraFissata().compareTo(listaVisite.get(counterVisit).getDataOra())>=0 &&
                                listaEsamiFatti.get(counterExam).getDataOraFissata().compareTo(listaRicettePrese.get(counterRecipes).getDataOraEvasa())>=0) {
                            listaPerStampa.add(EXAM);
                            counterExam++;
                            continue;
                        }
                        if (listaRicettePrese.get(counterRecipes).getData().compareTo(listaVisite.get(counterVisit).getDataOra())>=0 &&
                                listaRicettePrese.get(counterRecipes).getData().compareTo(listaEsamiFatti.get(counterExam).getDataOraFissata())>=0) {
                            listaPerStampa.add(RECIPE);
                            counterRecipes++;
                        }
                    }
                }

                req.setAttribute("patient", paziente);
                req.setAttribute("visitsList", listaVisite);
                req.setAttribute("examsDoneList", listaEsamiFatti);
                req.setAttribute("examsNotDoneList", listaEsamiDaFare);
                req.setAttribute("recipesTakenList", listaRicettePrese);
                req.setAttribute("recipesNotTakenList", listaRicetteNonPrese);
                req.setAttribute("stampList", listaPerStampa);

                chain.doFilter(req, resp);
            }
        } catch (
                DAOException e) {
            e.printStackTrace();
        }

    }

    public void destroy() {
    }
}
