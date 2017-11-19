package com.godofburguer.app.godofburguer;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by gustavo on 16/11/17.
 */

public class SobreActivity extends Activity {
    private ImageButton btnEmail;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre);

        btnEmail = (ImageButton) findViewById(R.id.email);

        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:godofburger@contato.com"));
                startActivity(emailIntent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}
