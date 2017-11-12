package com.godofburguer.app.godofburguer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class PedidosActivity extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos);
    }

    @Override
    public void onBackPressed(){
        finish();
    }

}
