package com.example.allbarber;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class inicio_sesion_barbero extends AppCompatActivity {

    private Button btnIniciarSesion;
    private EditText etCorreo, etContrasena;
    private TextView tvOlvideContrasena;

    private FirebaseAuth autentificacion;

    private String email = "";
    private String pass = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion_barbero);

        autentificacion = FirebaseAuth.getInstance();

        btnIniciarSesion = (Button)findViewById(R.id.sesionBarbero);

        etCorreo = (EditText)findViewById(R.id.et_correoBarbero);
        etContrasena = (EditText)findViewById(R.id.et_contrasenaBarbero);

        tvOlvideContrasena = (TextView)findViewById(R.id.tv_olvidasteContrasena);

        Button volver = findViewById(R.id.volver);
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), MainActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = etCorreo.getText().toString();
                pass = etContrasena.getText().toString();

                if (!email.isEmpty() && !pass.isEmpty()){
                    loginUser();

                }else{
                    Toast.makeText(inicio_sesion_barbero.this, "Complete los campos", Toast.LENGTH_SHORT).show();
                }

            }
        });

        tvOlvideContrasena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(inicio_sesion_barbero.this, home_recuperar_contrasena_barbero.class));

            }
        });

    }

    private  void loginUser(){

        autentificacion.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    startActivity(new Intent(inicio_sesion_barbero.this, home_barbero.class));
                    finish();

                }else{
                    Toast.makeText(inicio_sesion_barbero.this, "Contrasena o email no valido.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}