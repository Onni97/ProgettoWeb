package it.unitn.aa1920.webprogramming.sistemasanitario.Beans;

import java.util.Date;

public class UserBean {
    private String codiceFiscale;
    private String nome;
    private String cognome;
    private Date dataNascita;
    private String luogoNascita;
    private String foto;
    private int sesso;
    private int medicoDiBase;
    private String email;
    private String provincia;
    private boolean isDoctor;
    private int codiceMedico;

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public Date getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(Date dataNascita) {
        this.dataNascita = dataNascita;
    }

    public String getLuogoNascita() {
        return luogoNascita;
    }

    public void setLuogoNascita(String luogoNascita) {
        this.luogoNascita = luogoNascita;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public int getSesso() {
        return sesso;
    }

    public void setSesso(int sesso) {
        this.sesso = sesso;
    }

    public int getMedicoDiBase() {
        return medicoDiBase;
    }

    public void setMedicoDiBase(int medicoDiBase) {
        this.medicoDiBase = medicoDiBase;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public boolean getIsDoctor() {
        return isDoctor;
    }

    public void setIsDoctor(boolean isDoctor) {
        this.isDoctor = isDoctor;
    }

    public int getCodiceMedico() {
        return codiceMedico;
    }

    public void setCodiceMedico(int codiceMedico) {
        this.codiceMedico = codiceMedico;
    }
}
