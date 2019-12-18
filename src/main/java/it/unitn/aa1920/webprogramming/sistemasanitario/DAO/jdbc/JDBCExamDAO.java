package it.unitn.aa1920.webprogramming.sistemasanitario.DAO.jdbc;

import it.unitn.aa1920.webprogramming.sistemasanitario.Beans.ExamBean;
import it.unitn.aa1920.webprogramming.sistemasanitario.DAO.ExamDAO;
import it.unitn.aa1920.webprogramming.sistemasanitario.Exceptions.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class JDBCExamDAO extends JDBCDAO<ExamBean, Integer> implements ExamDAO {
    public JDBCExamDAO(Connection con) { super(con); }

    @Override
    public List<ExamBean> getExamsOfUser(String codiceFiscale) throws DAOException {
        String query = "select * " +
                "from esami, visite " +
                "where esami.codiceVisita = visite.codice and utente = '" + codiceFiscale + "'";
        try (PreparedStatement statement = CON.prepareStatement(query)){
            List<ExamBean> listaEsami = new LinkedList<>();
            ResultSet result = statement.executeQuery(query);
            while ( result.next() ) {
                ExamBean esame = new ExamBean();
                esame.setCodice(result.getInt("codice"));
                esame.setCodiceVisita(result.getInt("codiceVisita"));
                esame.setData(result.getDate("data"));
                esame.setFatto(result.getBoolean("fatto"));
                esame.setReferto(result.getString("referto"));
                esame.setTicket(result.getDouble("ticket"));
                listaEsami.add(esame);
            }
            return listaEsami;
        } catch (SQLException ex) {
            throw new DAOException("Error", ex);
        }
    }

    @Override
    public ExamBean getByPrimaryKey(Integer codiceEsame) throws DAOException {
        String query = "select * from " +
                "esami " +
                "where codice =" + codiceEsame;
        try (PreparedStatement stmt = CON.prepareStatement(query)){
            ResultSet result = stmt.executeQuery();
            ExamBean esame = new ExamBean();
            if (!result.next()){
                esame.setCodice(-1);
            } else {
                esame.setCodice(result.getInt("codice"));
                esame.setCodiceVisita(result.getInt("codiceVisita"));
                esame.setData(result.getDate("data"));
                esame.setFatto(result.getBoolean("fatto"));
                esame.setReferto(result.getString("referto"));
                esame.setTicket(result.getDouble("ticket"));
            }
            return esame;
        } catch (SQLException ex) {
            throw new DAOException("Error", ex);
        }
    }
}
