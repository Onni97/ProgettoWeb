package it.unitn.aa1920.webprogramming.sistemasanitario.DAO.jdbc;

import it.unitn.aa1920.webprogramming.sistemasanitario.Beans.RecipeBean;
import it.unitn.aa1920.webprogramming.sistemasanitario.DAO.RecipeDAO;
import it.unitn.aa1920.webprogramming.sistemasanitario.Exceptions.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class JDBCRecipeDAO extends JDBCDAO<RecipeBean, Integer> implements RecipeDAO {
    public JDBCRecipeDAO(Connection con) { super(con); }

    @Override
    public List<RecipeBean> getRecipesOfUser(String codiceFiscale) throws DAOException {
        String query = "select * " +
                "from ricetta " +
                "where utente = '" + codiceFiscale + "'";
        try (PreparedStatement statement = CON.prepareStatement(query)){
            List<RecipeBean> listaRicette = new LinkedList<>();
            ResultSet result = statement.executeQuery(query);
            while ( result.next() ) {
                RecipeBean ricetta = new RecipeBean();
                ricetta.setCodice(result.getInt("codice"));
                ricetta.setFarmaco(result.getString("farmaco"));
                ricetta.setCodiceEsame(result.getInt("codiceEsame"));
                ricetta.setCodiceVisita(result.getInt("codiceVisita"));
                ricetta.setEvasa(result.getBoolean("evasa"));
                ricetta.setQuantita(result.getInt("quantita"));
            }
            return listaRicette;
        } catch (SQLException ex) {
            throw new DAOException("Error", ex);
        }
    }

    @Override
    public RecipeBean getByPrimaryKey(Integer codiceRicetta) throws DAOException {
        String query = "select * from" +
                "ricetta " +
                "where codice = '" + codiceRicetta + "'";
        try (PreparedStatement stmt = CON.prepareStatement(query)){
            ResultSet result = stmt.executeQuery();
            RecipeBean ricetta = new RecipeBean();
            if (!result.next()){
                ricetta.setCodice(-1);
            } else {
                ricetta.setCodice(result.getInt("codice"));
                ricetta.setFarmaco(result.getString("farmaco"));
                ricetta.setCodiceEsame(result.getInt("codiceEsame"));
                ricetta.setCodiceVisita(result.getInt("codiceVisita"));
                ricetta.setEvasa(result.getBoolean("evasa"));
                ricetta.setQuantita(result.getInt("quantita"));
            }
            return ricetta;
        } catch (SQLException ex) {
            throw new DAOException("Error", ex);
        }
    }
}
