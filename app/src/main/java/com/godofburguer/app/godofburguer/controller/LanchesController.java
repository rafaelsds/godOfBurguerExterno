package com.godofburguer.app.godofburguer.controller;


import com.godofburguer.app.godofburguer.entidades.Lanche;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LanchesController {

    @POST("lanches")
    Call<List<Lanche>> list();


    @POST("inserir_lanche")
    Call<Boolean>inserir(@Body HashMap<String, String> param);

    @POST("excluir_lanche")
    Call<Boolean> excluir(@Body HashMap<String, String> param);

    @POST("alterar_lanche")
    Call<Boolean> alterar(@Body HashMap<String, String> param);


}
