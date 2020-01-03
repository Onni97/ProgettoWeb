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
                "where esami.codiceVisita = visite.codice and utente = '" + codiceFiscale + "' " +
                "order by dataOraFissata DESC ";
        try (PreparedStatement statement = CON.prepareStatement(query)){
            List<ExamBean> examList = new LinkedList<>();
            ResultSet result = statement.executeQuery(query);
            while ( result.next() ) {
                ExamBean esame = new ExamBean();
                esame.setCodice(result.getInt("codice"));

                JDBCVisitDAO visitDAO = new JDBCVisitDAO(CON);
                esame.setVisita(visitDAO.getByPrimaryKey(result.getInt("codiceVisita")));

                esame.setDataOraFissata(result.getTimestamp("dataOraFissata"));
                esame.setFatto(result.getBoolean("fatto"));
                esame.setReferto(result.getString("referto"));
                esame.setTicket(result.getDouble("ticket"));
                esame.setTipo(result.getString("tipo"));

                JDBCDoctorDAO doctorDAO = new JDBCDoctorDAO(CON);
                esame.setMedico(doctorDAO.getByPrimaryKey(result.getInt("medico")));

                examList.add(esame);
            }
            return examList;
        } catch (SQLException ex) {
            throw new DAOException("Error", ex);
        }
    }

    @Override
    public List<ExamBean> getExamsNotDoneOfUser(String codiceFiscale) throws DAOException {
        String query = "select * " +
                "from esami, visite " +
                "where esami.codiceVisita = visite.codice and utente = '" + codiceFiscale + "' and fatto = false " +
                "order by dataOraFissata DESC ";
        try (PreparedStatement statement = CON.prepareStatement(query)){
            List<ExamBean> examListNotDone = new LinkedList<>();
            ResultSet result = statement.executeQuery(query);
            while ( result.next() ) {
                ExamBean esame = new ExamBean();
                esame.setCodice(result.getInt("codice"));

                JDBCVisitDAO visitDAO = new JDBCVisitDAO(CON);
                esame.setVisita(visitDAO.getByPrimaryKey(result.getInt("codiceVisita")));

                esame.setDataOraFissata(result.getTimestamp("dataOraFissata"));
                esame.setFatto(result.getBoolean("fatto"));
                esame.setReferto(result.getString("referto"));
                esame.setTicket(result.getDouble("ticket"));
                esame.setTipo(result.getString("tipo"));

                JDBCDoctorDAO doctorDAO = new JDBCDoctorDAO(CON);
                esame.setMedico(doctorDAO.getByPrimaryKey(result.getInt("medico")));

                examListNotDone.add(esame);
            }
            return examListNotDone;
        } catch (SQLException ex) {
            throw new DAOException("Error", ex);
        }
    }

    @Override
    public List<ExamBean> getExamsDoneOfUser(String codiceFiscale) throws DAOException {
        String query = "select * " +
                "from esami, visite " +
                "where esami.codiceVisita = visite.codice and utente = '" + codiceFiscale + "' and fatto = true " +
                "order by dataOraFissata DESC ";
        try (PreparedStatement statement = CON.prepareStatement(query)){
            List<ExamBean> examListDone = new LinkedList<>();
            ResultSet result = statement.executeQuery(query);
            while ( result.next() ) {
                ExamBean esame = new ExamBean();
                esame.setCodice(result.getInt("codice"));

                JDBCVisitDAO visitDAO = new JDBCVisitDAO(CON);
                esame.setVisita(visitDAO.getByPrimaryKey(result.getInt("codiceVisita")));

                esame.setDataOraFissata(result.getTimestamp("dataOraFissata"));
                esame.setFatto(result.getBoolean("fatto"));
                esame.setReferto(result.getString("referto"));
                esame.setTicket(result.getDouble("ticket"));
                esame.setTipo(result.getString("tipo"));

                JDBCDoctorDAO doctorDAO = new JDBCDoctorDAO(CON);
                esame.setMedico(doctorDAO.getByPrimaryKey(result.getInt("medico")));

                examListDone.add(esame);
            }
            return examListDone;
        } catch (SQLException ex) {
            throw new DAOException("Error", ex);
        }
    }

    @Override
    public ExamBean getByPrimaryKey(Integer codiceEsame) throws DAOException {
        String query = "select * from " +
                "esami " +
                "where codice = " + codiceEsame;
        try (PreparedStatement stmt = CON.prepareStatement(query)){
            ResultSet result = stmt.executeQuery();
            ExamBean esame = new ExamBean();
            if (!result.next()){
                esame.setCodice(-1);
            } else {
                esame.setCodice(result.getInt("codice"));

                JDBCVisitDAO visitDAO = new JDBCVisitDAO(CON);
                esame.setVisita(visitDAO.getByPrimaryKey(result.getInt("codiceVisita")));

                esame.setDataOraFissata(result.getTimestamp("dataOraFissata"));
                esame.setFatto(result.getBoolean("fatto"));
                esame.setReferto(result.getString("referto"));
                esame.setTicket(result.getDouble("ticket"));
                esame.setTipo(result.getString("tipo"));

                JDBCDoctorDAO doctorDAO = new JDBCDoctorDAO(CON);
                esame.setMedico(doctorDAO.getByPrimaryKey(result.getInt("medico")));
            }
            return esame;
        } catch (SQLException ex) {
            throw new DAOException("Error", ex);
        }
    }
}
