package it.unitn.aa1920.webprogramming.sistemasanitario.Filters;

import it.unitn.aa1920.webprogramming.sistemasanitario.Beans.RecipeBean;
import it.unitn.aa1920.webprogramming.sistemasanitario.DAO.RecipeDAO;
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

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        DAOFactory daoFactory = (DAOFactory) filterConfig.getServletContext().getAttribute("daoFactory");
        if (daoFactory == null) {
            throw new ServletException("Impossible to get dao factory for user storage system");
        }

        try {
            recipeDAO = daoFactory.getDAO(RecipeDAO.class);
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

            //controllo se la ricetta cercata è dell'utente in questione
            List<RecipeBean> recipeOfUser = recipeDAO.getRecipesOfUser((String)session.getAttribute("codiceFiscale"));
            boolean isRecipeOfUser = false;
            for (RecipeBean currRecipe: recipeOfUser) {
                if(currRecipe.getCodice() == recipe.getCodice()) {
                    isRecipeOfUser = true;
                    break;
                }
            }
            
            if (isRecipeOfUser) {
                servletRequest.setAttribute("recipe", recipe);
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                ((HttpServletResponse)servletResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {

    }
}
