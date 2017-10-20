package com.godofburguer.app.godofburguer;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.godofburguer.app.godofburguer.entidades.Indicador;

import java.util.ArrayList;

/**
 * Rafael Silva
 */

public class AvaliacaoActivity extends Activity {

    private RecyclerView recyclerView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avaliacao);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        carregarCard();

    }

    public void carregarCard(){
        AdapterCardIndicadores card = new AdapterCardIndicadores();

        card.itemSet(getResources().getString(R.string.satisfacao), getResources().getString(R.string.atendimento_cliente));
        card.itemSet(getResources().getString(R.string.qualidade), getResources().getString(R.string.qualidade_lanche));
        card.itemSet(getResources().getString(R.string.agilidade), getResources().getString(R.string.tempo_entrega));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(card);
    }
}
