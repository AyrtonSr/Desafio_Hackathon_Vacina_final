package br.com.api.dto;

import java.util.List;

public class VacinaDTO {
    private int id;
    private String nome;
    private String descricao;
    private Integer limiteIdadeMeses;
    private List<String> publicoAlvo;

    public VacinaDTO() {
    }

    public VacinaDTO(int id, String nome, String descricao, Integer limiteIdadeMeses, List<String> publicoAlvo) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.limiteIdadeMeses = limiteIdadeMeses;
        this.publicoAlvo = publicoAlvo;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public Integer getLimiteIdadeMeses() { return limiteIdadeMeses; }
    public void setLimiteIdadeMeses(Integer limiteIdadeMeses) { this.limiteIdadeMeses = limiteIdadeMeses; }

    public List<String> getPublicoAlvo() { return publicoAlvo; }
    public void setPublicoAlvo(List<String> publicoAlvo) { this.publicoAlvo = publicoAlvo; }
}