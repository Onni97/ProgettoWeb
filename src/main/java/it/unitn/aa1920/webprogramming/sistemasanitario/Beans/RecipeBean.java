package it.unitn.aa1920.webprogramming.sistemasanitario.Beans;

public class RecipeBean {
    private int codice;
    private String farmaco;
    private int quantita;
    private int codiceVisita;
    private int codiceEsame;
    private boolean evasa;

    public int getCodice() { return codice; }

    public void setCodice(int codice) { this.codice = codice; }

    public String getFarmaco() { return farmaco; }

    public void setFarmaco(String farmaco) { this.farmaco = farmaco; }

    public int getQuantita() { return  quantita; }

    public void setQuantita(int quantita) { this.quantita = quantita; }

    public int getCodiceVisita() { return codiceVisita; }

    public void setCodiceVisita(int codiceVisita) { this.codiceVisita = codiceVisita; }

    public int getCodiceEsame() { return codiceEsame; }

    public void setCodiceEsame(int codiceEsame) { this.codiceEsame = codiceEsame; }

    public boolean getEvasa() { return evasa; }

    public void setEvasa(boolean evasa) { this.evasa = evasa; }
}
