package com.godofburguer.app.godofburguer;

import android.app.Activity;
import android.os.Bundle;

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
