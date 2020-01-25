package it.unitn.aa1920.webprogramming.sistemasanitario.Filters;

import it.unitn.aa1920.webprogramming.sistemasanitario.Beans.RecipeBean;
import it.unitn.aa1920.webprogramming.sistemasanitario.Beans.SspBean;
import it.unitn.aa1920.webprogramming.sistemasanitario.DAO.RecipeDAO;
import it.unitn.aa1920.webprogramming.sistemasanitario.Exceptions.DAOException;
import it.unitn.aa1920.webprogramming.sistemasanitario.Exceptions.DAOFactoryException;
import it.unitn.aa1920.webprogramming.sistemasanitario.Factory.DAOFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;


public class fillSspRecipesData implements Filter {

    private RecipeDAO recipeDAO;

    public void init(FilterConfig config) throws ServletException {
        DAOFactory daoFactory = (DAOFactory) config.getServletContext().getAttribute("daoFactory");
        if (daoFactory == null) {
            throw new ServletException("Impossible to get dao factory for user storage system");
        }

        try {
            recipeDAO = daoFactory.getDAO(RecipeDAO.class);
        } catch (DAOFactoryException e) {
            e.printStackTrace();
        }
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        try {
            HttpSession session = ((HttpServletRequest)req).getSession();

            String province = (String) session.getAttribute("ssp");
            List<RecipeBean> recipeList = recipeDAO.getRecipesPerProvince(province);

            req.setAttribute("recipeList", recipeList);
            chain.doFilter(req, resp);
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    public void destroy() {
    }
}
