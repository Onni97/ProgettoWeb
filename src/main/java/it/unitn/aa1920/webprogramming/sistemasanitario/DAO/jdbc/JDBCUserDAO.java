package it.unitn.aa1920.webprogramming.sistemasanitario.DAO.jdbc;

import it.unitn.aa1920.webprogramming.sistemasanitario.Beans.UserBean;
import it.unitn.aa1920.webprogramming.sistemasanitario.DAO.UserDAO;
import it.unitn.aa1920.webprogramming.sistemasanitario.Exceptions.DAOException;

import java.sql.*;

public class JDBCUserDAO extends JDBCDAO<UserBean, String> implements UserDAO {
    public JDBCUserDAO(Connection con) {
        super(con);
    }

    @Override
    public UserBean getByPrimaryKey(String codiceFiscale) throws DAOException {
        String query = "select * from " +
                "utenti " +
                "where utenti.codiceFiscale = '" + codiceFiscale + "'";
        try (PreparedStatement stmt = CON.prepareStatement(query)) {
            ResultSet result = stmt.executeQuery();
            UserBean user = new UserBean();
            if (!result.next()) {
                user.setCodiceFiscale("-1");
            } else {
                user.setCodiceFiscale(result.getString("codiceFiscale"));
                user.setNome(result.getString("nome"));
                user.setCognome(result.getString("cognome"));
                user.setDataNascita(result.getDate("dataNascita"));
                user.setLuogoNascita(result.getString("luogoNascita"));
                user.setFoto(result.getString("foto"));
                user.setSesso(result.getInt("sesso"));
                user.setMedicoDiBase(result.getInt("codiceMedicoDiBase"));
                user.setEmail(result.getString("email"));
                user.setProvincia(result.getString("provincia"));

                JDBCDoctorDAO doctorDAO = new JDBCDoctorDAO(CON);
                int codiceMedico = doctorDAO.getDoctorCode(codiceFiscale);
                if (codiceMedico != -1) {
                    user.setIsDoctor(true);
                    user.setCodiceMedico(codiceMedico);
                } else {
                    user.setIsDoctor(false);
                    user.setCodiceMedico(-1);
                }
            }
            return user;
        } catch (SQLException ex) {
            throw new DAOException("Error", ex);
        }
    }


    @Override
    public boolean checkUserPassword(String codiceFiscale, String password) throws DAOException {
        String query = "select * from utenti where codiceFiscale = '" + codiceFiscale + "' and password = '" + password + "'";
        try (PreparedStatement stmt = CON.prepareStatement(query)) {
                ResultSet result = stmt.executeQuery();
            return result.next();
        } catch (SQLException ex) {
            throw new DAOException("Error", ex);
        }
    }


    @Override
    public void setMedicoDiBase(String codiceFiscale, int codiceMedico) throws DAOException {
        String query = "update utenti " +
                "set codiceMedicoDiBase = " + codiceMedico + " " +
                "where codiceFiscale = '" + codiceFiscale + "'";
        try (PreparedStatement stmt = CON.prepareStatement(query)) {
            stmt.executeUpdate(query);
        } catch (SQLException ex) {
            throw new DAOException("Error", ex);
        }
    }


    @Override
    public String setNewPhoto(String codiceFiscale, String nuovaFoto) throws DAOException {
        String toRtn;
        //prendo il nome vecchio
        String query = "select foto " +
                "from utenti " +
                "where codiceFiscale = '" + codiceFiscale + "'";
        try (PreparedStatement stmt = CON.prepareStatement(query)) {
            ResultSet result = stmt.executeQuery(query);
            result.next();
            toRtn = result.getString("foto");
        } catch (SQLException ex) {
            throw new DAOException("Error", ex);
        }

        //aggiorno con quello nuovo
        query = "update utenti " +
                "set foto = '" + nuovaFoto + "' " +
                "where codiceFiscale = '" + codiceFiscale + "'";
        try (PreparedStatement stmt = CON.prepareStatement(query)) {
            stmt.executeUpdate(query);
        } catch (SQLException ex) {
            throw new DAOException("Error", ex);
        }
        return toRtn;
    }
}
