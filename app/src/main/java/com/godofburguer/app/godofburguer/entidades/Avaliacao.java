package com.godofburguer.app.godofburguer.entidades;

/*  Rafael
    Nome da classe alterada de Indicador para Avaliacao por causa do WS
    Os pontos devem ser inseridos em cada item agilidade, independente do tipo de indicador
    Exemplo: 3 estrelas no card agilidade, agilidade = 3
             2 estrelas no card qualidade, qualidade = 2
*/

public class Avaliacao {

    private Integer agilidade, satisfacao, qualidade;
    private String titulo, descricao;

    public Avaliacao(){
    }

    public Integer getSatisfacao() {
        return satisfacao;
    }

    public void setSatisfacao(Integer satisfacao) {
        this.satisfacao = satisfacao;
    }

    public Integer getQualidade() {
        return qualidade;
    }

    public void setQualidade(Integer qualidade) {
        this.qualidade = qualidade;
    }

    public void setAgilidade(Integer agilidade) {
        this.agilidade = agilidade;
    }

    public Integer getAgilidade() {
        return agilidade;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}