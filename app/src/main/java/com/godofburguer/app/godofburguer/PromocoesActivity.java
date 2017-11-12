package com.godofburguer.app.godofburguer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.godofburguer.app.godofburguer.entidades.Indicador;

import java.util.ArrayList;

public class PromocoesActivity extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promocoes);
    }

    @Override
    public void onBackPressed(){
        finish();
    }


}
