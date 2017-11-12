package com.godofburguer.app.godofburguer.controller;

import android.content.Context;
import android.graphics.Color;
import android.widget.Toast;
import com.godofburguer.app.godofburguer.entidades.Usuarios;

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


    public interface CallBack<T>{
        void call(T callList);
    }

}