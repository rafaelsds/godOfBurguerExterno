package com.godofburguer.app.godofburguer.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.widget.Toast;

import com.godofburguer.app.godofburguer.entidades.Lanche;
import com.godofburguer.app.godofburguer.entidades.LancheInsumo;
import com.godofburguer.app.godofburguer.entidades.Usuarios;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SincronizaBancoWs {

    public static void atualizarUsuarios(final CallBack callback, final Context context){
        final SweetAlertDialog progressDoalog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        progressDoalog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        progressDoalog.setTitleText("Carregando...");
        progressDoalog.setCancelable(false);
        progressDoalog.show();

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RootController.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UsuariosController controlerUsuarios = retrofit.create(UsuariosController.class);
        Call<List<Usuarios>> requestUsuarios = controlerUsuarios.list();

        progressDoalog.show();

        requestUsuarios.enqueue(new Callback<List<Usuarios>>() {
            @Override
            public void onResponse(Call<List<Usuarios>> call, Response<List<Usuarios>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(context, response.code(), Toast.LENGTH_SHORT).show();
                } else {

                    //Intância uma nova lista que recebe os dados do response[WS]
                    List<Usuarios> objeto = response.body();

                    progressDoalog.dismiss();
                    callback.call(objeto);

                }
            }

            @Override
            public void onFailure(Call<List<Usuarios>> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public static void atualizarLanches(final CallBack callback, final Context context){

        final SweetAlertDialog progressDoalog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        progressDoalog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        progressDoalog.setTitleText("Carregando...");
        progressDoalog.setCancelable(false);

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RootController.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LanchesController controlerLanches = retrofit.create(LanchesController.class);
        Call<List<Lanche>> requestLanches = controlerLanches.list();

        progressDoalog.show();

        requestLanches.enqueue(new Callback<List<Lanche>>() {
            @Override
            public void onResponse(Call<List<Lanche>> call, Response<List<Lanche>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(context, response.code(), Toast.LENGTH_SHORT).show();
                } else {


                    //Intância uma nova lista que recebe os dados do response[WS]
                    final List<Lanche> listLanches = response.body();


                    SincronizaBancoWs.atualizarLancheInsumo(new SincronizaBancoWs.CallBack<List<LancheInsumo>>(){
                        @Override
                        public void call(List<LancheInsumo> listInsumos){

                            List<Lanche>listReturn = new ArrayList<>();

                            for(Lanche r : listLanches){

                                String insumos="";

                                for(LancheInsumo i : listInsumos){

                                    if(i.getIdLanche().equals(r.getId())){

                                        if(insumos.isEmpty()){
                                            insumos=insumos+i.getNome();
                                        }else{
                                            insumos=insumos+", "+i.getNome();
                                        }

                                    }

                                }

                                listReturn.add(new Lanche(r.getNome(), r.getId(), r.getValor(), r.getTipoLanche(), insumos));
                            }

                            callback.call(listReturn);

                        }
                    }, context);

                    progressDoalog.dismiss();

                }
            }

            @Override
            public void onFailure(Call<List<Lanche>> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    public static void atualizarLancheInsumo(final CallBack callback, final Context context){
        final SweetAlertDialog progressDoalog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        progressDoalog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        progressDoalog.setTitleText("Carregando...");
        progressDoalog.setCancelable(false);

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RootController.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        LancheInsumoController controler = retrofit.create(LancheInsumoController.class);
        Call<List<LancheInsumo>> request= controler.buscarInsumos();

        progressDoalog.show();

        request.enqueue(new Callback<List<LancheInsumo>>() {
            @Override
            public void onResponse(Call<List<LancheInsumo>> call, Response<List<LancheInsumo>> response) {
                progressDoalog.dismiss();
                if (!response.isSuccessful()) {
                    Toast.makeText(context, response.code(), Toast.LENGTH_SHORT).show();
                } else {

                    List<LancheInsumo> objeto = response.body();
                    progressDoalog.dismiss();
                    callback.call(objeto);
                }
            }

            @Override
            public void onFailure(Call<List<LancheInsumo>> call, Throwable t) {
                progressDoalog.dismiss();
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public interface CallBack<T>{
        void call(T callList);
    }

}