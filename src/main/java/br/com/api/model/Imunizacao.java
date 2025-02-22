package br.com.api.model;

import java.util.Date;

public class Imunizacao {

    private int id;
    private Date dataAplicacao;
    private String fabricante;
    private String lote;
    private String localAplicacao;
    private String profissionalAplicador;
    private int idPaciente;
    private int idDose;

    public Imunizacao() {
    }

    public Imunizacao(int id, Date dataAplicacao, String fabricante, String lote, String localAplicacao,
                      String profissionalAplicador, int idPaciente, int idDose) {
        this.id = id;
        this.dataAplicacao = dataAplicacao;
        this.fabricante = fabricante;
        this.lote = lote;
        this.localAplicacao = localAplicacao;
        this.profissionalAplicador = profissionalAplicador;
        this.idPaciente = idPaciente;
        this.idDose = idDose;
    }

    public Imunizacao(Date dataAplicacao, String fabricante, String lote, String localAplicacao,
                      String profissionalAplicador, int idPaciente, int idDose) {
        this.dataAplicacao = dataAplicacao;
        this.fabricante = fabricante;
        this.lote = lote;
        this.localAplicacao = localAplicacao;
        this.profissionalAplicador = profissionalAplicador;
        this.idPaciente = idPaciente;
        this.idDose = idDose;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDataAplicacao() {
        return dataAplicacao;
    }

    public void setDataAplicacao(Date dataAplicacao) {
        this.dataAplicacao = dataAplicacao;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public String getLocalAplicacao() {
        return localAplicacao;
    }

    public void setLocalAplicacao(String localAplicacao) {
        this.localAplicacao = localAplicacao;
    }

    public String getProfissionalAplicador() {
        return profissionalAplicador;
    }

    public void setProfissionalAplicador(String profissionalAplicador) {
        this.profissionalAplicador = profissionalAplicador;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public int getIdDose() {
        return idDose;
    }

    public void setIdDose(int idDose) {
        this.idDose = idDose;
    }

    // @Override
    /*public String toString() {
        return "Imunizacao{" +
                "nomeVacina='" + nomeVacina + '\'' +
                ", numeroDoses=" + numeroDoses +
                ", dosesAplicadas=" + dosesAplicadas +
                ", dataUltimaDose=" + dataUiltmaDose +
                ", completa=" + completa +
                '}';
    } */
}
