package it.unitn.aa1920.webprogramming.sistemasanitario.Filters;

import it.unitn.aa1920.webprogramming.sistemasanitario.Beans.RecipeBean;
import it.unitn.aa1920.webprogramming.sistemasanitario.Beans.UserBean;
import it.unitn.aa1920.webprogramming.sistemasanitario.DAO.RecipeDAO;
import it.unitn.aa1920.webprogramming.sistemasanitario.DAO.UserDAO;
import it.unitn.aa1920.webprogramming.sistemasanitario.Exceptions.DAOException;
import it.unitn.aa1920.webprogramming.sistemasanitario.Exceptions.DAOFactoryException;
import it.unitn.aa1920.webprogramming.sistemasanitario.Factory.DAOFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.Console;
import java.io.IOException;
import java.util.List;

public class fillRecipeData implements Filter {
    private RecipeDAO recipeDAO;
    private UserDAO userDAO;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        DAOFactory daoFactory = (DAOFactory) filterConfig.getServletContext().getAttribute("daoFactory");
        if (daoFactory == null) {
            throw new ServletException("Impossible to get dao factory for user storage system");
        }

        try {
            recipeDAO = daoFactory.getDAO(RecipeDAO.class);
            userDAO = daoFactory.getDAO(UserDAO.class);
        } catch (DAOFactoryException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            HttpSession session = ((HttpServletRequest) servletRequest).getSession();
            int id = Integer.parseInt(servletRequest.getParameter("id"));
            RecipeBean recipe = recipeDAO.getByPrimaryKey(id);

            UserBean utente = userDAO.getByPrimaryKey((String) session.getAttribute("codiceFiscale"));

            //controllo se la ricetta cercata è dell'utente in questione
            List<RecipeBean> recipeOfUser = recipeDAO.getRecipesOfUser((String)session.getAttribute("codiceFiscale"));
            boolean allow = false;
            boolean isThePatient = false;
            for (RecipeBean currRecipe: recipeOfUser) {
                if(currRecipe.getCodice() == recipe.getCodice()) {
                    allow = true;
                    isThePatient = true;
                    break;
                }
            }

            if (utente.getIsDoctor()) {
                allow = true;
            }
            
            if (allow) {
                servletRequest.setAttribute("recipe", recipe);
                servletRequest.setAttribute("isThePatient", isThePatient);
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                ((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {

    }
}
