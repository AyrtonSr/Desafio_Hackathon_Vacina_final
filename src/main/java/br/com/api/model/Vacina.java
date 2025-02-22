package br.com.api.model;

import java.util.List;

public class Vacina {
    private int id;
    private String nome;
    private String descricao;
    private Integer limiteIdadeMeses;
    private List<PublicoAlvo> publicoAlvo;

    public Vacina() {
    }

    // Enumeração para o público-alvo
    public enum PublicoAlvo {
        CRIANCA, ADOLESCENTE, ADULTO, GESTANTE
    }

    // Construtor
    public Vacina(int id, String nome, String descricao, Integer limiteIdadeMeses, List<PublicoAlvo> publicoAlvo) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.limiteIdadeMeses = limiteIdadeMeses;
        this.publicoAlvo = publicoAlvo;
    }

    // Getters e Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getLimiteIdadeMeses() {
        return limiteIdadeMeses;
    }

    public void setLimiteIdadeMeses(Integer limiteIdadeMeses) {
        this.limiteIdadeMeses = limiteIdadeMeses;
    }

    public List<PublicoAlvo> getPublicoAlvo() {
        return publicoAlvo;
    }

    public void setPublicoAlvo(List<PublicoAlvo> publicoAlvo) {
        this.publicoAlvo = publicoAlvo;
    }
}
