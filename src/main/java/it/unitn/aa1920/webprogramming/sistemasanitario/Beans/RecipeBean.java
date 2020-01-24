package it.unitn.aa1920.webprogramming.sistemasanitario.Beans;

import java.util.Date;

public class RecipeBean {
    private int codice;
    private String farmaco;
    private int quantita;
    private VisitBean visita;
    private ExamBean esame;
    private Date dataOraEvasa;
    private String descrizioneFarmaco;
    private Date data;
    private String provinciaPrescrizione;

    public int getCodice() { return codice; }

    public void setCodice(int codice) { this.codice = codice; }

    public String getFarmaco() { return farmaco; }

    public void setFarmaco(String farmaco) { this.farmaco = farmaco; }

    public int getQuantita() { return  quantita; }

    public void setQuantita(int quantita) { this.quantita = quantita; }

    public VisitBean getVisita() { return visita; }

    public void setVisita(VisitBean visita) { this.visita = visita; }

    public ExamBean getEsame() { return esame; }

    public void setEsame(ExamBean esame) { this.esame = esame; }

    public void setDataOraEvasa(Date evasa) { this.dataOraEvasa = evasa; }

    public Date getDataOraEvasa() { return dataOraEvasa; }

    public String getDescrizioneFarmaco() { return descrizioneFarmaco; }

    public void setDescrizioneFarmaco(String descrizioneFarmaco) { this.descrizioneFarmaco = descrizioneFarmaco; }

    public Date getData() { return data; }

    public void setData(Date data) { this.data = data; }

    public String getProvinciaPrescrizione() {
        return provinciaPrescrizione;
    }

    public void setProvinciaPrescrizione(String provinciaPrescrizione) {
        this.provinciaPrescrizione = provinciaPrescrizione;
    }
}
