package it.unitn.aa1920.webprogramming.sistemasanitario.DAO;

import it.unitn.aa1920.webprogramming.sistemasanitario.Beans.UserBean;
import it.unitn.aa1920.webprogramming.sistemasanitario.Exceptions.DAOException;

import java.util.List;

public interface UserDAO extends DAO<UserBean, String> {

    boolean checkUserPassword(String codiceFiscale, String password) throws DAOException;
    void setMedicoDiBase(String codiceFiscale, int codiceMedico) throws DAOException;
    String setNewPhoto(String codiceFiscale, String nuovaFoto) throws DAOException;
}
