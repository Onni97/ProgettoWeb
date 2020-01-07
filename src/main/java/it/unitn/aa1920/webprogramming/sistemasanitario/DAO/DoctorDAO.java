package it.unitn.aa1920.webprogramming.sistemasanitario.DAO;

import it.unitn.aa1920.webprogramming.sistemasanitario.Beans.UserBean;
import it.unitn.aa1920.webprogramming.sistemasanitario.Exceptions.DAOException;

import java.util.List;

public interface DoctorDAO extends DAO<UserBean, Integer> {

    int getDoctorCode(String codiceFiscale) throws DAOException;
    List<UserBean> getListaMediciPerProfincia(String provincia) throws DAOException;
    List<UserBean> getPatientsOfDoctor(int codiceMedico) throws  DAOException;
}
