package com.godofburguer.app.godofburguer;

import android.app.Activity;
import android.os.Bundle;
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

        ArrayList<Indicador> list = new ArrayList<Indicador>();
        list.add(new Indicador(5,5,5));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new AdapterCardIndicadores(list));
    }
}
