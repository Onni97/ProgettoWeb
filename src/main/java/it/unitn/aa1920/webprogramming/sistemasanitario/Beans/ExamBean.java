package it.unitn.aa1920.webprogramming.sistemasanitario.Beans;

import java.sql.Timestamp;
import java.util.Date;

public class ExamBean {
    private int codice;
    private double ticket;
    private String referto;
    private Date dataOraFissata;
    private boolean fatto;
    private VisitBean visita;
    private UserBean medico;

    public int getCodice() { return codice; }

    public void setCodice(int codice) { this.codice = codice; }

    public double getTicket() { return ticket; }

    public void setTicket(double ticket) { this.ticket = ticket; }

    public String getReferto() { return referto; }

    public void setReferto(String referto) { this.referto = referto; }

    public Date getDataOraFissata() { return dataOraFissata; }

    public void setDataOraFissata(Date dataOraFissata) { this.dataOraFissata = dataOraFissata; }

    public boolean getFatto() { return fatto; }

    public void setFatto(boolean fatto) { this.fatto = fatto; }

    public VisitBean getVisita() { return visita; }

    public void setVisita(VisitBean visita) { this.visita = visita; }

    public UserBean getMedico() { return medico; }

    public void setMedico(UserBean medico) { this.medico = medico; }
}
