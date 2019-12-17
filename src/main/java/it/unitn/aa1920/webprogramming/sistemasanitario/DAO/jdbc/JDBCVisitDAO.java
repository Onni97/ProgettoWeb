package it.unitn.aa1920.webprogramming.sistemasanitario.DAO.jdbc;

import it.unitn.aa1920.webprogramming.sistemasanitario.Beans.UserBean;
import it.unitn.aa1920.webprogramming.sistemasanitario.Beans.VisitBean;
import it.unitn.aa1920.webprogramming.sistemasanitario.DAO.VisitDAO;
import it.unitn.aa1920.webprogramming.sistemasanitario.Exceptions.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class JDBCVisitDAO extends JDBCDAO<VisitBean, Integer> implements VisitDAO {
    public JDBCVisitDAO(Connection con) {
        super(con);
    }

    @Override
    public List<VisitBean> getVisitOfUser(String codiceFiscale) throws DAOException {
        String query = "select * " +
                "from visite " +
                "where utente = '" + codiceFiscale + "'";
        try (PreparedStatement statement = CON.prepareStatement(query)) {
            List<VisitBean> listaVisite = new LinkedList<>();
            ResultSet result = statement.executeQuery(query);
            while ( result.next() ) {
                VisitBean visita = new VisitBean();
                visita.setCodice(result.getInt("codice"));
                visita.setData(result.getDate("data"));
                visita.setResoconto(result.getString("resoconto"));
                visita.setUtente(codiceFiscale);

                JDBCDoctorDAO doctorDAO = new JDBCDoctorDAO(CON);
                visita.setMedicoDiBase(doctorDAO.getByPrimaryKey(result.getInt("codiceMedicoDiBase")));
                listaVisite.add(visita);
            }
            return listaVisite;
        } catch (SQLException ex) {
            throw new DAOException("Error", ex);
        }
    }

    @Override
    public VisitBean getByPrimaryKey(Integer codiceVisita) throws DAOException {
        String query = "select * from " +
                "visite " +
                "where codice = " + codiceVisita;
        try (PreparedStatement stmt = CON.prepareStatement(query)) {
            ResultSet result = stmt.executeQuery();
            VisitBean visita = new VisitBean();
            if (!result.next()) {
                visita.setCodice(-1);
            } else {
                visita.setCodice(result.getInt("codice"));
                visita.setData(result.getDate("data"));
                visita.setResoconto(result.getString("resoconto"));
                visita.setUtente(result.getString("utente"));
                JDBCDoctorDAO doctorDAO = new JDBCDoctorDAO(CON);
                visita.setMedicoDiBase(doctorDAO.getByPrimaryKey(result.getInt("codiceMedicoDiBase")));
            }
            return visita;
        } catch (SQLException ex) {
            throw new DAOException("Error", ex);
        }
    }
}
