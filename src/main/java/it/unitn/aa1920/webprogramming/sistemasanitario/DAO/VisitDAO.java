package it.unitn.aa1920.webprogramming.sistemasanitario.DAO;

import it.unitn.aa1920.webprogramming.sistemasanitario.Beans.VisitBean;
import it.unitn.aa1920.webprogramming.sistemasanitario.Exceptions.DAOException;

import java.util.List;

public interface VisitDAO extends DAO<VisitBean, Integer> {
    List<VisitBean> getVisitsOfUser(String codiceFiscale) throws DAOException;
    VisitBean getLastVisitOfUser(String codiceFiscale) throws DAOException;
    int addVisit(String dataOra, String resoconto, String utente, int codiceMedico) throws DAOException;
}
