package com.godofburguer.app.godofburguer.entidades;



public class Indicador {

    private Integer satisfacao, qualidade, agilidade;
    private String titulo, descricao;

    public  Indicador(){
    }

    public void setAgilidade(Integer agilidade) {
        this.agilidade = agilidade;
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