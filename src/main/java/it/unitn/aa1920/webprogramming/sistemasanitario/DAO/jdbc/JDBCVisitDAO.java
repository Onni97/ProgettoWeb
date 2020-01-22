package it.unitn.aa1920.webprogramming.sistemasanitario.DAO.jdbc;

import it.unitn.aa1920.webprogramming.sistemasanitario.Beans.VisitBean;
import it.unitn.aa1920.webprogramming.sistemasanitario.DAO.VisitDAO;
import it.unitn.aa1920.webprogramming.sistemasanitario.Exceptions.DAOException;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class JDBCVisitDAO extends JDBCDAO<VisitBean, Integer> implements VisitDAO {
    public JDBCVisitDAO(Connection con) {
        super(con);
    }

    @Override
    public List<VisitBean> getVisitsOfUser(String codiceFiscale) throws DAOException {
        String query = "select * " +
                "from visite " +
                "where utente = '" + codiceFiscale + "' " +
                "order by dataOra DESC ";
        try (PreparedStatement statement = CON.prepareStatement(query)) {
            List<VisitBean> listaVisite = new LinkedList<>();
            ResultSet result = statement.executeQuery(query);
            while ( result.next() ) {
                VisitBean visita = new VisitBean();
                visita.setCodice(result.getInt("codice"));
                visita.setDataOra(result.getTimestamp("dataOra"));
                visita.setResoconto(result.getString("resoconto"));

                JDBCUserDAO userDAO = new JDBCUserDAO(CON);
                visita.setUtente(userDAO.getByPrimaryKey(result.getString("utente")));

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
    public VisitBean getLastVisitOfUser(String codiceFiscale) throws DAOException {
        List<VisitBean> visitsOfUser = getVisitsOfUser(codiceFiscale);
        if (visitsOfUser.size() == 0 ) {
            return null;
        } else {
            return visitsOfUser.get(0);
        }
    }

    @Override
    public int addVisit(String dataOra, String resoconto, String utente, int codiceMedico) throws DAOException {
        String query = "insert into visite (dataOra, resoconto, utente, codiceMedicoDiBase) " +
                "values ('" + dataOra + "', '" + resoconto + "', '" + utente + "', " + codiceMedico + ");";
        int toRtn = 0;
        try (PreparedStatement stmt = CON.prepareStatement(query)) {
            stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    toRtn = generatedKeys.getInt(1);
                }
                else {
                    throw new DAOException();
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Error", ex);
        }
        return toRtn;
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
                visita.setDataOra(result.getTimestamp("dataOra"));
                visita.setResoconto(result.getString("resoconto"));
                JDBCUserDAO userDAO = new JDBCUserDAO(CON);
                visita.setUtente(userDAO.getByPrimaryKey(result.getString("utente")));
                JDBCDoctorDAO doctorDAO = new JDBCDoctorDAO(CON);
                visita.setMedicoDiBase(doctorDAO.getByPrimaryKey(result.getInt("codiceMedicoDiBase")));
            }
            return visita;
        } catch (SQLException ex) {
            throw new DAOException("Error", ex);
        }
    }
}
