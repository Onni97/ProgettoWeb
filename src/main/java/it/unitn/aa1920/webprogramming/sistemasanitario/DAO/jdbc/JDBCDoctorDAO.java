package it.unitn.aa1920.webprogramming.sistemasanitario.DAO.jdbc;

import it.unitn.aa1920.webprogramming.sistemasanitario.Beans.UserBean;
import it.unitn.aa1920.webprogramming.sistemasanitario.DAO.DoctorDAO;
import it.unitn.aa1920.webprogramming.sistemasanitario.DAO.UserDAO;
import it.unitn.aa1920.webprogramming.sistemasanitario.Exceptions.DAOException;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class JDBCDoctorDAO extends JDBCDAO<UserBean, Integer> implements DoctorDAO {
    public JDBCDoctorDAO(Connection con) {
        super(con);
    }

    @Override
    public int getDoctorCode(String codiceFiscale) throws DAOException {
        String query = "select codiceMedico from medicidibase where codiceFiscale = '" + codiceFiscale + "'";
        try (PreparedStatement statement = CON.prepareStatement(query)) {
            ResultSet result = statement.executeQuery(query);
            if (result.next()) {
                return result.getInt("codiceMedico");
            } else {
                return -1;
            }
        } catch (SQLException ex) {
            throw new DAOException("Error", ex);
        }
    }

    @Override
    public List<UserBean> getListaMediciPerProfincia(String provincia) throws DAOException {
        String query = "select codiceMedico " +
                "from medicidibase, utenti " +
                "where medicidibase.codiceFiscale = utenti.codiceFiscale and provincia = '" + provincia + "'";
        List<Integer> listaCodiciMediciDellaProvincia = new LinkedList<>();
        List<UserBean> listaMediciDellaProvincia = new LinkedList<>();
        try (PreparedStatement statement = CON.prepareStatement(query)) {
            //prendo prima i codici dei medici della provincia
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                listaCodiciMediciDellaProvincia.add(result.getInt("codiceMedico"));
            }
            //ottengo tutti i dati di questi medici
            for (int codiceMedico : listaCodiciMediciDellaProvincia) {
                UserBean medico = getByPrimaryKey(codiceMedico);
                listaMediciDellaProvincia.add(medico);
            }
            return listaMediciDellaProvincia;
        } catch (SQLException ex) {
            throw new DAOException("Error", ex);
        }
    }

    @Override
    public List<UserBean> getPatientsOfDoctor(int codiceMedico) throws DAOException {
        String query = "select codiceFiscale " +
                "from utenti " +
                "where codiceMedicoDiBase = " + codiceMedico + " " +
                "order by nome, cognome";
        List<UserBean> listaPazienti = new LinkedList<>();
        UserDAO userDAO = new JDBCUserDAO(CON);

        try (PreparedStatement statement = CON.prepareStatement(query)) {
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                UserBean user = userDAO.getByPrimaryKey(result.getString("codiceFiscale"));
                listaPazienti.add(user);
            }
            return listaPazienti;
        } catch (SQLException ex) {
            throw new DAOException("Error", ex);
        }
    }

    @Override
    public UserBean getByPrimaryKey(Integer codiceMedico) throws DAOException {
        String query = "select utenti.codiceFiscale from " +
                "utenti, medicidibase " +
                "where utenti.codiceFiscale = medicidibase.codiceFiscale and medicidibase.codiceMedico = " + codiceMedico;
        try (PreparedStatement statement = CON.prepareStatement(query)) {
            ResultSet result = statement.executeQuery(query);
            if (!result.next()) {
                UserBean doctor = new UserBean();
                doctor.setCodiceFiscale("-1");
                return doctor;
            } else {
                JDBCUserDAO userDAO = new JDBCUserDAO(CON);
                return userDAO.getByPrimaryKey(result.getString("codiceFiscale"));
            }
        } catch (SQLException ex) {
            throw new DAOException("Error", ex);
        }

    }
}
