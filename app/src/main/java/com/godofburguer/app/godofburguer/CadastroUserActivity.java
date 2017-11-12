package com.godofburguer.app.godofburguer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.godofburguer.app.godofburguer.controller.RootController;
import com.godofburguer.app.godofburguer.controller.SincronizaBancoWs;
import com.godofburguer.app.godofburguer.controller.UsuariosController;
import com.godofburguer.app.godofburguer.entidades.Usuarios;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Rafael Silva
 */

public class CadastroUserActivity extends AppCompatActivity {
    private Button btnCancelar, btnGravar;
    private EditText edtDescricao, edtEmail, edtTelefone, edtEndereco, edtSenha, edtLogin;
    private Intent intentCadastroUsuario;
    private String idUusario;

    private Usuarios usuarioLogado, usuarioOrigem;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_usuarios);

        inicialise();
        botoes();

        atualizar();

    }


    @Override
    public void onBackPressed(){
        Intent myIntent;

        if(idUusario == null){
            myIntent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivityForResult(myIntent, 0);
        }

        finish();
    }


    public void atualizar(){
        if(intentCadastroUsuario.getSerializableExtra("usuario") != null){
            usuarioOrigem = (Usuarios) intentCadastroUsuario.getSerializableExtra("usuario");
            final String id = usuarioOrigem.getId();
            SincronizaBancoWs.atualizarUsuarios(new SincronizaBancoWs.CallBack<List<Usuarios>>(){
                @Override
                public void call(List<Usuarios> objeto){

                    for(Usuarios u : objeto){
                        if(u.getId().equals(id)) {
                            usuarioLogado = u;
                            preencheUsuario(usuarioLogado);
                        }
                    }
                }
            }, CadastroUserActivity.this);

            edtLogin.setEnabled(false);

        }else{
            idUusario=null;
            edtLogin.setEnabled(true);
        }
    }



    public void inicialise(){
        btnGravar = (Button)findViewById(R.id.btnGravarUsuario);
        btnCancelar = (Button) findViewById(R.id.btnCancelarCadUsuario);
        edtDescricao = (EditText)findViewById(R.id.edtNome);
        edtEndereco = (EditText)findViewById(R.id.edtEndereco);
        edtEmail = (EditText)findViewById(R.id.edtEmail);
        edtTelefone = (EditText)findViewById(R.id.edtTelefone);
        edtSenha = (EditText)findViewById(R.id.edtSenha);
        edtLogin = (EditText)findViewById(R.id.edtLogin);
        intentCadastroUsuario = getIntent();
    }


    public void botoes(){
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(idUusario==null){
                    Intent i = new Intent(CadastroUserActivity.this, LoginActivity.class);
                    startActivity(i);

                }

                finish();

            }
        });

        btnGravar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HideSoftkeyBoard.hideSoftKeyboard(CadastroUserActivity.this);

                if(validaDados(edtDescricao.getText().toString(), edtSenha.getText().toString(), edtLogin.getText().toString())){
                    inserir();
                }

            }
        });

        edtLogin.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus && edtLogin.getText()!=null && !edtLogin.getText().toString().trim().isEmpty()){

                    SincronizaBancoWs.atualizarUsuarios(new SincronizaBancoWs.CallBack<List<Usuarios>>(){
                        @Override
                        public void call(List<Usuarios> objeto){
                            List<Usuarios>list = new ArrayList<>();

                            Boolean possuiLogin=false;

                            for(Usuarios u : objeto){
                                if(u.getTipo().equals("E") && u.getLogin().trim().toUpperCase().equals(edtLogin.getText().toString().trim().toUpperCase()))
                                    possuiLogin=true;
                            }

                            if(possuiLogin){

                                new SweetAlertDialog(CadastroUserActivity.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Oops...")
                                        .setContentText("O usuário "+edtLogin.getText().toString()+" já existe!")
                                        .show();

                                edtLogin.setText("");
                                edtLogin.requestFocus();

                            }

                        }
                    }, CadastroUserActivity.this);

                }
            }
        });

    }

    public boolean validaDados(String descricao, String senha, String login) {
        Boolean isValid = true;

        if(descricao.isEmpty()) {
            edtDescricao.setError(getString(R.string.informe_nome));
            isValid = false;
        }

        if(senha.isEmpty()) {
            edtSenha.setError(getString(R.string.informe_senha));
            isValid = false;
        }

        if(login.isEmpty()) {
            edtLogin.setError(getString(R.string.informe_login));
            isValid = false;
        }

        return isValid;
    }

    public interface CallBack{
        void call();
    }

    public void inserir(){
        inserir(new CadastroUserActivity.CallBack(){
            @Override
            public void call() {
            }
        });
    }

    public void inserir(final CadastroUserActivity.CallBack callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RootController.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UsuariosController controler = retrofit.create(UsuariosController.class);

        HashMap<String, String> param = obterHashUsuario();

        final SweetAlertDialog progressDoalog = new SweetAlertDialog(CadastroUserActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        progressDoalog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        progressDoalog.setTitleText("Carregando...");
        progressDoalog.setCancelable(false);
        progressDoalog.show();

        if(idUusario != null){
            param.put("id",idUusario);
            idUusario=null;

            Call<Boolean> request = controler.alterar(param);

            request.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    progressDoalog.dismiss();
                    if (!response.isSuccessful()) {
                        Toast.makeText(CadastroUserActivity.this, "Erro: "+response.code(), Toast.LENGTH_SHORT).show();
                    }else{
                        callback.call();
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    progressDoalog.dismiss();
                    Toast.makeText(CadastroUserActivity.this, "Erro: "+t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }else{
            Call<Boolean> request = controler.inserir(param);

            request.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    progressDoalog.dismiss();
                    if (!response.isSuccessful()) {
                        Toast.makeText(CadastroUserActivity.this, "Erro: "+response.code(), Toast.LENGTH_SHORT).show();
                    } else {
                        callback.call();
                        Intent it = new Intent(CadastroUserActivity.this, LoginActivity.class);
                        startActivity(it);
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    progressDoalog.dismiss();
                    Toast.makeText(CadastroUserActivity.this, "Erro: "+t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }


    public void preencheUsuario(Usuarios u){
        if(u.getEmail() != null)
            edtEmail.setText(u.getEmail());

        if(u.getId() != null)
            idUusario = u.getId();

        if(u.getNome() != null)
            edtDescricao.setText(u.getNome());

        if(u.getSenha() != null)
            edtSenha.setText(u.getSenha());

        if(u.getTelefone() != null)
            edtTelefone.setText(u.getTelefone());

        if(u.getEndereco() != null)
            edtEndereco.setText(u.getEndereco());

        if(u.getLogin() != null)
            edtLogin.setText(u.getLogin());

    }

    public HashMap<String, String> obterHashUsuario(){

        HashMap<String, String> hashMap = new HashMap<String, String>();

        hashMap.put("nome", edtDescricao.getText().toString());
        hashMap.put("endereco", edtEndereco.getText().toString());
        hashMap.put("email", edtEmail.getText().toString());
        hashMap.put("telefone", edtTelefone.getText().toString());
        hashMap.put("login", edtLogin.getText().toString());
        hashMap.put("senha", edtSenha.getText().toString());
        hashMap.put("tipo", "E");
        return hashMap;

    }


}
