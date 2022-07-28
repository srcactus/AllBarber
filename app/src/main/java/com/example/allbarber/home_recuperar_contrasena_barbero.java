package com.example.allbarber;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class home_recuperar_contrasena_barbero extends AppCompatActivity {

    private FirebaseAuth auth;

    private EditText etemailCliente;
    private Button btnrecuperarContrasena;

    private String emailRecuperar = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_recuperar_contrasena_barbero);

        auth = FirebaseAuth.getInstance();

        etemailCliente = (EditText)findViewById(R.id.et_emailCliente);

        btnrecuperarContrasena = (Button)findViewById(R.id.btn_recuperarContrasena);



        btnrecuperarContrasena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarCorreoRecuperacion();
            }
        });

    }

    private void enviarCorreoRecuperacion() {

        emailRecuperar = etemailCliente.getText().toString();

        auth.sendPasswordResetEmail(emailRecuperar)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(home_recuperar_contrasena_barbero.this, "Correo de recuperacion de contraseña enviado", Toast.LENGTH_SHORT).show();

                        }else{
                            Toast.makeText(home_recuperar_contrasena_barbero.this, "Este correo no existe", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}