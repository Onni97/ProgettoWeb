package it.unitn.aa1920.webprogramming.sistemasanitario.DAO.jdbc;

import it.unitn.aa1920.webprogramming.sistemasanitario.Beans.SspBean;
import it.unitn.aa1920.webprogramming.sistemasanitario.DAO.SspDAO;
import it.unitn.aa1920.webprogramming.sistemasanitario.Exceptions.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCSspDAO extends JDBCDAO<SspBean, String> implements SspDAO {
    public JDBCSspDAO(Connection con) {
        super(con);
    }

    @Override
    public boolean checkSspPassword(String provincia, String password) throws DAOException {
        String query = "select * from ssp where provincia = '" + provincia + "' and BINARY password = '" + password + "'";
        try (PreparedStatement stmt = CON.prepareStatement(query)) {
            ResultSet result = stmt.executeQuery();
            return result.next();
        } catch (SQLException ex) {
            throw new DAOException("Error", ex);
        }
    }

    @Override
    public SspBean getByPrimaryKey(String provincia) throws DAOException {
        String query = "select * from " +
                "ssp " +
                "where provincia = '" + provincia + "'";
        try (PreparedStatement stmt = CON.prepareStatement(query)) {
            ResultSet result = stmt.executeQuery();
            SspBean ssp = new SspBean();
            result.next();
            ssp.setProvincia(result.getString("provincia"));
            return ssp;
        } catch (SQLException ex) {
            throw new DAOException("Error", ex);
        }
    }
}
