package com.example.allbarber;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class inicio_sesion_cliente extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    private Button btnInicioSesion;
    private EditText editTextEmail;
    private EditText editTextPass;
    private TextView tvCrearCuentaCliente;
    private TextView tvOlvideContrasena;

    private String email = "";
    private String pass = "";

    private FirebaseAuth autentificacion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion_cliente);



        autentificacion = FirebaseAuth.getInstance();

        Button volver = findViewById(R.id.volver);
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), MainActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        tvCrearCuentaCliente = (TextView)findViewById(R.id.tv_crearCuentaCliente);
        tvOlvideContrasena = (TextView)findViewById(R.id.tv_olvideContrasena);
        btnInicioSesion = (Button)findViewById(R.id.btn_iniciosesionCliente);
        editTextEmail = (EditText)findViewById(R.id.mailCliente);
        editTextPass = (EditText)findViewById(R.id.passCliente);

        tvOlvideContrasena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(inicio_sesion_cliente.this, home_recuperar_contrasena_barbero.class));
            }
        });

        tvCrearCuentaCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(inicio_sesion_cliente.this, home_registro_cliente.class));
            }
        });

        btnInicioSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = editTextEmail.getText().toString();
                pass = editTextPass.getText().toString();

                if (!email.isEmpty() && !pass.isEmpty()){
                    loginUser();

                }else{
                    Toast.makeText(inicio_sesion_cliente.this, "Complete los campos", Toast.LENGTH_SHORT).show();
                }

            }
        });



    }

    private  void loginUser(){

        autentificacion.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    startActivity(new Intent(inicio_sesion_cliente.this, home_cliente.class));
                    finish();

                }else{
                    Toast.makeText(inicio_sesion_cliente.this, "Contrasena o email no valido.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }




}