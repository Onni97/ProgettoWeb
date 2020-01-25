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
    public JDBCRecipeDAO(Connection con) {
        super(con);
    }

    @Override
    public List<RecipeBean> getRecipesOfUser(String codiceFiscale) throws DAOException {
        String query = "select r.codice, r.farmaco, r.quantita, r.codiceVisita, r.codiceEsame, r.dataOraEvasa, r.provinciaPrescrizione, r.descrizioneFarmaco, IF(v.dataOra is null, e.dataOraFissata, v.dataOra) as data\n" +
                "       from ricette r\n" +
                "       left join visite v on r.codiceVisita = v.codice\n" +
                "       left join (select esami.*, visite.utente\n" +
                "                  from esami, visite\n" +
                "                  where esami.codiceVisita = visite.codice) e on r.codiceEsame = e.codice\n" +
                "       where e.utente = '" + codiceFiscale + "' or v.utente = '" + codiceFiscale + "'" +
                "       order by data DESC ";
        try (PreparedStatement statement = CON.prepareStatement(query)) {
            List<RecipeBean> listaRicette = new LinkedList<>();
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                RecipeBean ricetta = new RecipeBean();
                ricetta.setCodice(result.getInt("codice"));
                ricetta.setFarmaco(result.getString("farmaco"));

                JDBCExamDAO examDAO = new JDBCExamDAO(CON);
                ricetta.setEsame(examDAO.getByPrimaryKey(result.getInt("codiceEsame")));

                JDBCVisitDAO visitDAO = new JDBCVisitDAO(CON);
                ricetta.setVisita(visitDAO.getByPrimaryKey(result.getInt("codiceVisita")));

                ricetta.setDataOraEvasa(result.getTimestamp("dataOraEvasa"));
                ricetta.setQuantita(result.getInt("quantita"));
                ricetta.setDescrizioneFarmaco(result.getString("descrizioneFarmaco"));
                ricetta.setData(result.getTimestamp("data"));
                ricetta.setProvinciaPrescrizione(result.getString("provinciaPrescrizione"));
                listaRicette.add(ricetta);
            }
            return listaRicette;
        } catch (SQLException ex) {
            throw new DAOException("Error", ex);
        }
    }

    @Override
    public List<RecipeBean> getRecipesNotTakenOfUser(String codiceFiscale) throws DAOException {
        String query = "select r.codice, r.farmaco, r.quantita, r.codiceVisita, r.codiceEsame, r.provinciaPrescrizione, r.dataOraEvasa, r.descrizioneFarmaco, IF(v.dataOra is null, e.dataOraFissata, v.dataOra) as data\n" +
                "       from ricette r\n" +
                "       left join visite v on r.codiceVisita = v.codice\n" +
                "       left join (select esami.*, visite.utente\n" +
                "                  from esami, visite\n" +
                "                  where esami.codiceVisita = visite.codice) e on r.codiceEsame = e.codice\n" +
                "       where (e.utente = '" + codiceFiscale + "' or v.utente = '" + codiceFiscale + "') and dataOraEvasa is null " +
                "       order by data DESC ";
        try (PreparedStatement statement = CON.prepareStatement(query)) {
            List<RecipeBean> recipeListNotTaken = new LinkedList<>();
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                RecipeBean ricetta = new RecipeBean();
                ricetta.setCodice(result.getInt("codice"));
                ricetta.setFarmaco(result.getString("farmaco"));

                JDBCExamDAO examDAO = new JDBCExamDAO(CON);
                ricetta.setEsame(examDAO.getByPrimaryKey(result.getInt("codiceEsame")));

                JDBCVisitDAO visitDAO = new JDBCVisitDAO(CON);
                ricetta.setVisita(visitDAO.getByPrimaryKey(result.getInt("codiceVisita")));

                ricetta.setDataOraEvasa(result.getTimestamp("dataOraEvasa"));
                ricetta.setQuantita(result.getInt("quantita"));
                ricetta.setDescrizioneFarmaco(result.getString("descrizioneFarmaco"));
                ricetta.setData(result.getTimestamp("data"));
                ricetta.setProvinciaPrescrizione(result.getString("provinciaPrescrizione"));
                recipeListNotTaken.add(ricetta);
            }
            return recipeListNotTaken;
        } catch (SQLException ex) {
            throw new DAOException("Error", ex);
        }
    }

    @Override
    public List<RecipeBean> getRecipesTakenOfUser(String codiceFiscale) throws DAOException {
        String query = "select r.codice, r.farmaco, r.quantita, r.codiceVisita, r.codiceEsame, r.provinciaPrescrizione, r.dataOraEvasa, r.descrizioneFarmaco, IF(v.dataOra is null, e.dataOraFissata, v.dataOra) as data\n" +
                "       from ricette r\n" +
                "       left join visite v on r.codiceVisita = v.codice\n" +
                "       left join (select esami.*, visite.utente\n" +
                "                  from esami, visite\n" +
                "                  where esami.codiceVisita = visite.codice) e on r.codiceEsame = e.codice\n" +
                "       where (e.utente = '" + codiceFiscale + "' or v.utente = '" + codiceFiscale + "') and dataOraEvasa is not null" +
                "       order by data DESC ";
        try (PreparedStatement statement = CON.prepareStatement(query)) {
            List<RecipeBean> recipeListTaken = new LinkedList<>();
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                RecipeBean ricetta = new RecipeBean();
                ricetta.setCodice(result.getInt("codice"));
                ricetta.setFarmaco(result.getString("farmaco"));

                JDBCExamDAO examDAO = new JDBCExamDAO(CON);
                ricetta.setEsame(examDAO.getByPrimaryKey(result.getInt("codiceEsame")));

                JDBCVisitDAO visitDAO = new JDBCVisitDAO(CON);
                ricetta.setVisita(visitDAO.getByPrimaryKey(result.getInt("codiceVisita")));

                ricetta.setDataOraEvasa(result.getTimestamp("dataOraEvasa"));
                ricetta.setQuantita(result.getInt("quantita"));
                ricetta.setDescrizioneFarmaco(result.getString("descrizioneFarmaco"));
                ricetta.setData(result.getTimestamp("data"));
                ricetta.setProvinciaPrescrizione(result.getString("provinciaPrescrizione"));
                recipeListTaken.add(ricetta);
            }
            return recipeListTaken;
        } catch (SQLException ex) {
            throw new DAOException("Error", ex);
        }
    }

    @Override
    public RecipeBean getLastRecipeOfUser(String codiceFiscale) throws DAOException {
        List<RecipeBean> recipesOfUser = getRecipesOfUser(codiceFiscale);
        if(recipesOfUser.size() == 0) {
            return null;
        } else {
            return recipesOfUser.get(0);
        }
    }

    @Override
    public void addRecipe(String farmaco, int quantita, String descrizioneFarmaco, String provinciaPrescrizione, Integer codiceVisita, Integer codiceEsame) throws DAOException {
        String query = "";
        if (codiceEsame == null)
            query = "insert into ricette (farmaco, quantita, descrizioneFarmaco, provinciaPrescrizione, codiceVisita) " +
                    "values ('" + farmaco + "', " + quantita + ", '" + descrizioneFarmaco + "', '" + provinciaPrescrizione + "', " + codiceVisita + ");";
        else if (codiceVisita == null)
            query = "insert into ricette (farmaco, quantita, descrizioneFarmaco, provinciaPrescrizione, codiceEsame) " +
                    "values ('" + farmaco + "', " + quantita + ", '" + descrizioneFarmaco + "', '" + provinciaPrescrizione + "', " + codiceEsame + ");";
        try (PreparedStatement stmt = CON.prepareStatement(query)) {
            stmt.executeUpdate(query);
        } catch (SQLException ex) {
            throw new DAOException("Error", ex);
        }
    }

    @Override
    public List<RecipeBean> getRecipesPerProvince(String province) throws DAOException {
        String query = "select r.codice, r.farmaco, r.quantita, r.codiceVisita, r.codiceEsame, r.provinciaPrescrizione, r.dataOraEvasa, r.descrizioneFarmaco, IF(v.dataOra is null, e.dataOraFissata, v.dataOra) as data\n" +
                "       from ricette r\n" +
                "       left join visite v on r.codiceVisita = v.codice\n" +
                "       left join (select esami.*, visite.utente\n" +
                "                  from esami, visite\n" +
                "                  where esami.codiceVisita = visite.codice) e on r.codiceEsame = e.codice\n" +
                "       where r.provinciaPrescrizione = '" + province + "' " +
                "       order by data DESC ";
        List<RecipeBean> listRecipes = new LinkedList<>();
        try (PreparedStatement stmt = CON.prepareStatement(query)) {
            ResultSet result = stmt.executeQuery();
            while(result.next()) {
                RecipeBean recipe = new RecipeBean();

                recipe.setCodice(result.getInt("codice"));
                recipe.setFarmaco(result.getString("farmaco"));

                JDBCExamDAO examDAO = new JDBCExamDAO(CON);
                recipe.setEsame(examDAO.getByPrimaryKey(result.getInt("codiceEsame")));

                JDBCVisitDAO visitDAO = new JDBCVisitDAO(CON);
                recipe.setVisita(visitDAO.getByPrimaryKey(result.getInt("codiceVisita")));

                recipe.setDataOraEvasa(result.getTimestamp("dataOraEvasa"));
                recipe.setQuantita(result.getInt("quantita"));
                recipe.setDescrizioneFarmaco(result.getString("descrizioneFarmaco"));
                recipe.setData(result.getTimestamp("data"));
                recipe.setProvinciaPrescrizione(result.getString("provinciaPrescrizione"));

                listRecipes.add(recipe);
            }
            return listRecipes;
        } catch (SQLException ex) {
            throw new DAOException("Error", ex);
        }
    }

    @Override
    public RecipeBean getByPrimaryKey(Integer codiceRicetta) throws DAOException {
        String query = "select r.codice, r.farmaco, r.quantita, r.codiceVisita, r.provinciaPrescrizione r.codiceEsame, r.dataOraEvasa, r.descrizioneFarmaco, IF(v.dataOra is null, e.dataOraFissata, v.dataOra) as data\n" +
                "       from ricette r\n" +
                "       left join visite v on r.codiceVisita = v.codice\n" +
                "       left join (select esami.*, visite.utente\n" +
                "                  from esami, visite\n" +
                "                  where esami.codiceVisita = visite.codice) e on r.codiceEsame = e.codice\n" +
                "       where r.codice = '" + codiceRicetta + "'";
        try (PreparedStatement stmt = CON.prepareStatement(query)) {
            ResultSet result = stmt.executeQuery();
            RecipeBean ricetta = new RecipeBean();
            if (!result.next()) {
                ricetta.setCodice(-1);
            } else {
                ricetta.setCodice(result.getInt("codice"));
                ricetta.setFarmaco(result.getString("farmaco"));

                JDBCExamDAO examDAO = new JDBCExamDAO(CON);
                ricetta.setEsame(examDAO.getByPrimaryKey(result.getInt("codiceEsame")));

                JDBCVisitDAO visitDAO = new JDBCVisitDAO(CON);
                ricetta.setVisita(visitDAO.getByPrimaryKey(result.getInt("codiceVisita")));

                ricetta.setDataOraEvasa(result.getTimestamp("dataOraEvasa"));
                ricetta.setQuantita(result.getInt("quantita"));
                ricetta.setData(result.getTimestamp("data"));
                ricetta.setDescrizioneFarmaco(result.getString("descrizioneFarmaco"));
                ricetta.setProvinciaPrescrizione(result.getString("provinciaPrescrizione"));
            }
            return ricetta;
        } catch (SQLException ex) {
            throw new DAOException("Error", ex);
        }
    }
}
