package it.unitn.aa1920.webprogramming.sistemasanitario.DAO;

import it.unitn.aa1920.webprogramming.sistemasanitario.Beans.ExamBean;
import it.unitn.aa1920.webprogramming.sistemasanitario.Exceptions.DAOException;

import java.util.List;

public interface ExamDAO extends DAO<ExamBean, Integer> {
    List<ExamBean> getExamsOfUser(String codiceFiscale) throws DAOException;
    List<ExamBean> getExamsNotDoneOfUser(String codiceFiscale) throws DAOException;
    List<ExamBean> getExamsDoneOfUser(String codiceFiscale) throws DAOException;
}
