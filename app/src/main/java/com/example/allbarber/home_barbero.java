package com.example.allbarber;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

public class home_barbero extends AppCompatActivity {

    private FirebaseAuth autentificacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_barbero);

        autentificacion = FirebaseAuth.getInstance();

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
                Intent intent2 = new Intent(home_barbero.this, home_barbero_perfil.class);
                startActivity(intent2);
            }
            break;
            case R.id.btnEscaneaQr:{
                Intent intent4 = new Intent(home_barbero.this, home_barbero_scan_qr.class);
                startActivity(intent4);
            }
            break;
            case R.id.btnCerrarSesion:{

                autentificacion.signOut();
                Intent intent6 = new Intent(home_barbero.this, inicio_sesion_barbero.class);
                startActivity(intent6);
                finish();


            }
            break;
        }
        return true;

    }

}