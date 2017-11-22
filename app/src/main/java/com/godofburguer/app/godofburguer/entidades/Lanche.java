package com.godofburguer.app.godofburguer.entidades;

import java.io.Serializable;

public class Lanche implements Serializable{

    private String nome, id, tipoLanche, insumos;
    private float valor;


    @Override
    public String toString() {
        return nome ;
    }

    public Lanche(String nome, String id, float valor, String tipoLanche, String insumos) {
        this.nome = nome;
        this.id = id;
        this.valor = valor;
        this.tipoLanche = tipoLanche;
        this.insumos = insumos;
    }

    public String getInsumos() {
        return insumos;
    }

    public void setInsumos(String insumos) {
        this.insumos = insumos;
    }

    public String getTipoLanche() {
        return tipoLanche;
    }

    public void setTipoLanche(String tipoLanche) {
        this.tipoLanche = tipoLanche;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }
}
