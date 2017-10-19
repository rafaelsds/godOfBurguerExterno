package com.godofburguer.app.godofburguer;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Rafael Silva
 */

public class CadastroUserActivity extends Activity {
    private Button btnCancelar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_usuarios);

        btnCancelar = (Button) findViewById(R.id.btnCancelarCadUsuario);

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
