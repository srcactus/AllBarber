package com.example.allbarber;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.allbarber.modelo.Cliente;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class home_barbero_cambiar_contrasena extends AppCompatActivity {

    private EditText etContrasenaActual, etContrasenaNueva, etConfirmarContrasena;
    private Button btnModificarPass;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    private String contrasenaActualIgualar = "";

    private String contrasenaActual = "";
    private String contrasenaNueva = "";
    private String confirContrasena = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_barbero_cambiar_contrasena);

        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        etContrasenaActual = (EditText)findViewById(R.id.et_actualPass);
        etContrasenaNueva = (EditText)findViewById(R.id.et_newPass);
        etConfirmarContrasena = (EditText)findViewById(R.id.et_confirmarPass);

        btnModificarPass = (Button)findViewById(R.id.btn_cambiarPass);

        String id = mAuth.getCurrentUser().getUid();
        mDatabase.child("usuarioBarbero").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()){

                    String contrasena = snapshot.child("contrasena").getValue().toString();

                    contrasenaActualIgualar = contrasena;

                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });


        btnModificarPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*Map<String, Object> map = new HashMap<>();
                map.put("contrasena", contrasenaNueva);

                mDatabase.child("usuarioBarbero").child(id).updateChildren(map);*/



                String id = mAuth.getCurrentUser().getUid();

                contrasenaActual = etContrasenaActual.getText().toString();
                contrasenaNueva = etContrasenaNueva.getText().toString();
                confirContrasena = etConfirmarContrasena.getText().toString();

                if (contrasenaActual.equals(contrasenaActualIgualar)) {

                    if (contrasenaNueva.length() >= 6) {

                        if (contrasenaNueva.equals(confirContrasena)) {

                            if (!contrasenaNueva.isEmpty() && !contrasenaActual.isEmpty() && !confirContrasena.isEmpty()) {


                                Map<String, Object> map = new HashMap<>();
                                map.put("contrasena", etContrasenaNueva.getText().toString());



                                mDatabase.child("usuarioBarbero").child(id).updateChildren(map);


                                String nuevaPass = etContrasenaNueva.getText().toString();



                                mUser.updatePassword(nuevaPass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull @NotNull Task<Void> task) {

                                    }
                                });


                                Toast.makeText(home_barbero_cambiar_contrasena.this, "Datos cambiados exitosamente", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(home_barbero_cambiar_contrasena.this, "Debe completar los campos vacios", Toast.LENGTH_SHORT).show();
                            }
                        }else{

                            Toast.makeText(home_barbero_cambiar_contrasena.this, "La contraseña nueva debe ser igual a la contraseña a confirmar", Toast.LENGTH_SHORT).show();

                        }

                    } else {
                        Toast.makeText(home_barbero_cambiar_contrasena.this, "La contraseña nueva debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(home_barbero_cambiar_contrasena.this, "La contraseña actual no es correcta", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }
}