package it.unitn.aa1920.webprogramming.sistemasanitario.Beans;

import java.util.Date;

public class VisitBean {
    private int codice;
    private Date data;
    private String resoconto;
    private String utente;
    private UserBean medicoDiBase;

    public int getCodice() {
        return codice;
    }

    public void setCodice(int codice) {
        this.codice = codice;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getResoconto() {
        return resoconto;
    }

    public void setResoconto(String resoconto) {
        this.resoconto = resoconto;
    }

    public String getUtente() {
        return utente;
    }

    public void setUtente(String utente) {
        this.utente = utente;
    }

    public UserBean getMedicoDiBase() {
        return medicoDiBase;
    }

    public void setMedicoDiBase(UserBean medicoDiBase) {
        this.medicoDiBase = medicoDiBase;
    }
}
