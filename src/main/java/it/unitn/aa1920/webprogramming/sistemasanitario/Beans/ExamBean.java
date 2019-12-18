package it.unitn.aa1920.webprogramming.sistemasanitario.Beans;

import java.util.Date;

public class ExamBean {
    private int codice;
    private double ticket;
    private String referto;
    private Date data;
    private boolean fatto;
    private int codiceVisita;

    public int getCodice() { return codice; }

    public void setCodice(int codice) { this.codice = codice; }

    public double getTicket() { return ticket; }

    public void setTicket(double ticket) { this.ticket = ticket; }

    public String getReferto() { return referto; }

    public void setReferto(String referto) { this.referto = referto; }

    public Date getData() { return data; }

    public void setData(Date data) { this.data = data; }

    public boolean getFatto() { return fatto; }

    public void setFatto(boolean fatto) { this.fatto = fatto; }

    public int getCodiceVisita() { return codiceVisita; }

    public void setCodiceVisita(int codiceVisita) { this.codiceVisita = codiceVisita; }
}
