package it.unitn.aa1920.webprogramming.sistemasanitario.DAO;

import it.unitn.aa1920.webprogramming.sistemasanitario.Beans.SspBean;
import it.unitn.aa1920.webprogramming.sistemasanitario.Exceptions.DAOException;

public interface SspDAO extends DAO<SspBean, String> {
    boolean checkSspPassword(String provincia, String password) throws DAOException;
}
