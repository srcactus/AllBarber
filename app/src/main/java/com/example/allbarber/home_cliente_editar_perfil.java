package com.example.allbarber;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.allbarber.modelo.Cliente;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class home_cliente_editar_perfil extends AppCompatActivity {

    private EditText etNombre, etContrasena, etTelefono, etCorreo;

    private String oldEmail,oldPass;

    private Button btnModificar;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    private String nombre = "";
    private String mail = "";
    private String contrasena = "";
    private String telefono = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_cliente_editar_perfil);

        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        etNombre = (EditText)findViewById(R.id.et_modificarNombre);
        etContrasena = (EditText)findViewById(R.id.et_modificarContrasena);
        etTelefono = (EditText)findViewById(R.id.et_modificarTelefono);
        etCorreo = (EditText)findViewById(R.id.et_modificarCorreo);

        btnModificar= (Button)findViewById(R.id.btn_modificarDatos);

        oldEmail = etCorreo.getText().toString();
        oldPass = etContrasena.getText().toString();

        obtenerDatosModificar();

        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id = mAuth.getCurrentUser().getUid();

                nombre = etNombre.getText().toString();
                contrasena = etContrasena.getText().toString();
                mail = etCorreo.getText().toString();
                telefono = etTelefono.getText().toString();

                if(etTelefono.length() == 9){
                    if (etContrasena.length() >= 6){
                        if (!nombre.isEmpty() && !contrasena.isEmpty() && !mail.isEmpty() && !telefono.isEmpty()){

                            Cliente c = new Cliente();

                            c.setNombre(etNombre.getText().toString().trim());
                            c.setEmail(etCorreo.getText().toString().trim());
                            c.setTelefono(etTelefono.getText().toString().trim());
                            c.setContrasena(etContrasena.getText().toString().trim());

                            mDatabase.child("Cliente").child(id).setValue(c);

                            String nuevoEmail = etCorreo.getText().toString();
                            String nuevaPass = etContrasena.getText().toString();



                            mUser.updateEmail(nuevoEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<Void> task) {


                                }
                            });


                            mUser.updatePassword(nuevaPass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<Void> task) {

                                }
                            });


                            Toast.makeText(home_cliente_editar_perfil.this,"Datos cambiados exitosamente",Toast.LENGTH_SHORT).show();

                        }else {
                            Toast.makeText(home_cliente_editar_perfil.this, "Debe completar los campos vacios", Toast.LENGTH_SHORT).show();
                        }

                    }else {
                        Toast.makeText(home_cliente_editar_perfil.this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(home_cliente_editar_perfil.this, "El numero telefonico debe tener 9 digitos", Toast.LENGTH_SHORT).show();
                }



            }
        });

    }

    private void obtenerDatosModificar() {

        String id = mAuth.getCurrentUser().getUid();
        mDatabase.child("Cliente").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String nombre = snapshot.child("nombre").getValue().toString();
                    String email = snapshot.child("email").getValue().toString();
                    String telefono = snapshot.child("telefono").getValue().toString();
                    String contrasena = snapshot.child("contrasena").getValue().toString();

                    etNombre.setText(nombre);
                    etCorreo.setText(email);
                    etTelefono.setText(telefono);
                    etContrasena.setText(contrasena);

                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });


    }




}