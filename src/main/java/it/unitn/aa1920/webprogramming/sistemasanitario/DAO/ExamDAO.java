package it.unitn.aa1920.webprogramming.sistemasanitario.DAO;

import it.unitn.aa1920.webprogramming.sistemasanitario.Beans.ExamBean;
import it.unitn.aa1920.webprogramming.sistemasanitario.Beans.ExamTypeBean;
import it.unitn.aa1920.webprogramming.sistemasanitario.Exceptions.DAOException;

import java.util.Date;
import java.util.List;

public interface ExamDAO extends DAO<ExamBean, Integer> {
    List<ExamBean> getExamsOfUser(String codiceFiscale) throws DAOException;

    List<ExamBean> getExamsNotDoneOfUser(String codiceFiscale) throws DAOException;

    List<ExamBean> getExamsDoneOfUser(String codiceFiscale) throws DAOException;

    List<ExamBean> getExamsOfDoctor(int codiceMedico) throws DAOException;

    void updateExam(int codice, Date dataOraFissata, String referto, float ticket, boolean chiudi) throws DAOException;

    void addExam(String dataOraFissata, int codiceVisita, int medico, String tipo) throws DAOException;

    List<ExamTypeBean> getExamTypes() throws DAOException;
}
