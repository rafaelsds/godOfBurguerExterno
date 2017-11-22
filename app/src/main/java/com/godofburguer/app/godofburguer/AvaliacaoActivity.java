package com.godofburguer.app.godofburguer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.godofburguer.app.godofburguer.controller.AvaliacaoController;
import com.godofburguer.app.godofburguer.controller.RootController;
import com.godofburguer.app.godofburguer.entidades.Avaliacao;

import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Rafael Silva
 */

public class AvaliacaoActivity extends Activity {

    private RecyclerView recyclerView;
    private Button btnAvaliar;
    private AdapterCardIndicadores card;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avaliacao);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        btnAvaliar = (Button)findViewById(R.id.btnAvaliar);
        card = new AdapterCardIndicadores();

        carregarCard();

        btnAvaliar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(Avaliacao a : card.getList()){
                    inserirAvaliacao(new CallBack() {
                        @Override
                        public void call() {
                        }
                    },a);
                }
            }
        });

    }


    public interface CallBack{
        void call();
    }


    public void inserirAvaliacao(final CallBack callback, Avaliacao a) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RootController.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AvaliacaoController controler = retrofit.create(AvaliacaoController.class);

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("qualidade", a.getQualidade().toString());
        hashMap.put("satisfacao", a.getAgilidade().toString());
        hashMap.put("agilidade", a.getAgilidade().toString());

        final SweetAlertDialog progressDoalog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        progressDoalog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        progressDoalog.setTitleText("Carregando...");
        progressDoalog.setCancelable(false);
        progressDoalog.show();

        Call<Boolean> request = controler.insert(hashMap);

        request.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(AvaliacaoActivity.this, response.code(), Toast.LENGTH_SHORT).show();
                }else{
                    progressDoalog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                progressDoalog.dismiss();
                Toast.makeText(AvaliacaoActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public void onBackPressed(){
        finish();
    }


    public void carregarCard(){
        card.itemSet(getResources().getString(R.string.satisfacao), getResources().getString(R.string.atendimento_cliente));
        card.itemSet(getResources().getString(R.string.qualidade), getResources().getString(R.string.qualidade_lanche));
        card.itemSet(getResources().getString(R.string.agilidade), getResources().getString(R.string.tempo_entrega));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(card);
    }
}