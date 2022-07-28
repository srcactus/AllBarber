package com.example.allbarber;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class home_barbero_scan_qr extends AppCompatActivity {

    private FirebaseAuth autentificacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_barbero_scan_qr);

        autentificacion = FirebaseAuth.getInstance();

        new IntentIntegrator(this).initiateScan();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);

        String datos = result.getContents();

    }

    public boolean onCreateOptionsMenu(Menu clientes){
        getMenuInflater().inflate(R.menu.barberos, clientes);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.btnInicio:{
                onRestart();
            }
            break;
            case R.id.btnPerfil:{
                Intent intent2 = new Intent(home_barbero_scan_qr.this, home_barbero_perfil.class);
                startActivity(intent2);
            }

            break;
            case R.id.btnEscaneaQr:{
                Intent intent4 = new Intent(home_barbero_scan_qr.this, home_barbero_scan_qr.class);
                startActivity(intent4);
            }
            break;
            case R.id.btnCerrarSesion:{

                autentificacion.signOut();
                Intent intent6 = new Intent(home_barbero_scan_qr.this, inicio_sesion_barbero.class);
                startActivity(intent6);
                finish();


            }
            break;
        }
        return true;

    }
}