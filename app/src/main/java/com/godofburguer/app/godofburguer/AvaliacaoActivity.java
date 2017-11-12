package com.godofburguer.app.godofburguer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.godofburguer.app.godofburguer.entidades.Indicador;

import java.util.ArrayList;

/**
 * Rafael Silva
 */

public class AvaliacaoActivity extends Activity {

    private Button btnAvaliar;
    private AdapterCardIndicadores card;
    private RecyclerView recyclerView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avaliacao);

        card = new AdapterCardIndicadores();
        btnAvaliar = (Button) findViewById(R.id.avaliar);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        carregarCard();

        btnAvaliar.setOnClickListener(new View.OnClickListener() {

            @Override
        public void onClick(View v) {
                card.getValoresAvaliacao();
            }
        });

    }


    @Override
    public void onBackPressed(){
        finish();
    }


    public void carregarCard(){
        card.itemSet(getString(R.string.satisfacao), getString(R.string.atendimento_cliente));
        card.itemSet(getString(R.string.qualidade), getString(R.string.qualidade_lanche));
        card.itemSet(getString(R.string.agilidade), getString(R.string.tempo_entrega));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(card);
    }
}
