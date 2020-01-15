package it.unitn.aa1920.webprogramming.sistemasanitario.DAO;

import it.unitn.aa1920.webprogramming.sistemasanitario.Beans.RecipeBean;
import it.unitn.aa1920.webprogramming.sistemasanitario.Exceptions.DAOException;

import java.util.List;

public interface RecipeDAO extends DAO<RecipeBean, Integer> {
    List<RecipeBean> getRecipesOfUser(String codiceFiscale) throws DAOException;
    List<RecipeBean> getRecipesNotTakenOfUser(String codiceFiscale) throws DAOException;
    List<RecipeBean> getRecipesTakenOfUser(String codiceFiscale) throws DAOException;
    RecipeBean getLastRecipeOfUser(String codiceFiscale) throws DAOException;
}
