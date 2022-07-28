package com.example.allbarber;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;


public class home_cliente extends AppCompatActivity {

    private Button btnCerrarSesion;
    private FirebaseAuth autentificacion;


    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_cliente);

        //btnCerrarSesion = (Button)findViewById(R.id.btnCerrarSesion);
        autentificacion = FirebaseAuth.getInstance();

        //btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
           // @Override
            //public void onClick(View v) {
                //autentificacion.signOut();
               // startActivity(new Intent(home_cliente.this, MainActivity.class));
                //finish();
            //}
        //});



    }

    public boolean onCreateOptionsMenu(Menu clientes){
        getMenuInflater().inflate(R.menu.clientes, clientes);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.btnInicio:{
                onRestart();
            }
            break;
            case R.id.btnPerfil:{
                Intent intent2 = new Intent(home_cliente.this, home_cliente_perfil.class);
                startActivity(intent2);
            }
            break;
            case R.id.btnSolicitarHora:{
                Intent intent3 = new Intent(home_cliente.this, home_cliente_solicitar_hora.class);
                startActivity(intent3);

            }
            break;
            case R.id.btnMuestraQr:{
                Intent intent4 = new Intent(home_cliente.this, home_cliente_qr.class);
                startActivity(intent4);
            }
            break;
            case R.id.btnBuscaBarberia:{
                Intent intent5 = new Intent(home_cliente.this, MapsActivity.class);
                startActivity(intent5);
            }
            break;
            case R.id.btnCerrarSesion:{

                autentificacion.signOut();
                Intent intent6 = new Intent(home_cliente.this, inicio_sesion_cliente.class);
                startActivity(intent6);
                finish();


            }
            break;
        }
        return true;

    }



}