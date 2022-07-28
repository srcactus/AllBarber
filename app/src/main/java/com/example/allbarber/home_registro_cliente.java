package com.example.allbarber;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.allbarber.modelo.Cliente;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class home_registro_cliente extends AppCompatActivity {

    private EditText et_nombre;
    private EditText et_correo;
    private EditText et_contrasena;
    private EditText et_telefono;
    private EditText et_confirmarContrasena;


    private Button botonRegistrar;

    private String nombre = "";
    private String mail = "";
    private String contrasena = "";
    private String telefono = "";
    private String confirmarContrasena = "";


    FirebaseAuth registroAuth;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_registro_cliente);

        registroAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        et_nombre = (EditText) findViewById(R.id.nombreRegistroCliente);
        et_correo = (EditText) findViewById(R.id.mailRegistroCliente);
        et_contrasena = (EditText) findViewById(R.id.passRegistroCliente);
        et_telefono = (EditText)findViewById(R.id.telefonoRegistroCliente);
        et_confirmarContrasena=(EditText)findViewById(R.id.passConfirmarRegistroCliente);


        botonRegistrar = (Button)findViewById(R.id.btn_registroCliente);

        botonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nombre = et_nombre.getText().toString();
                contrasena = et_contrasena.getText().toString();
                mail = et_correo.getText().toString();
                telefono = et_telefono.getText().toString();
                confirmarContrasena = et_confirmarContrasena.getText().toString();

                if (!nombre.isEmpty() && !mail.isEmpty() && !contrasena.isEmpty() && !telefono.isEmpty() && !confirmarContrasena.isEmpty()){

                    if (contrasena.length() >= 6){
                        if (telefono.length() == 9){
                            if (et_contrasena.getText().toString().equals(et_confirmarContrasena.getText().toString())){
                                registrarCliente();

                            }else {
                                Toast.makeText(home_registro_cliente.this, "El campo contraseña debe ser igual al campo confirmar contraseña", Toast.LENGTH_SHORT).show();

                            }


                        }else{
                            Toast.makeText(home_registro_cliente.this, "El numero telefonico debe tener 9 digitos", Toast.LENGTH_SHORT).show();

                        }


                    }else{
                        Toast.makeText(home_registro_cliente.this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
                    }



                }else {
                    Toast.makeText(home_registro_cliente.this, "Debe completar los campos vacios", Toast.LENGTH_SHORT).show();

                }

            }
        });


    }



    private void registrarCliente(){
        registroAuth.createUserWithEmailAndPassword(mail, contrasena).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    Map<String, Object> map = new HashMap<>();
                    map.put("nombre",nombre);
                    map.put("email",mail);
                    map.put("contrasena",contrasena);
                    map.put("telefono",telefono);


                    String id = registroAuth.getCurrentUser().getUid();

                    mDatabase.child("Cliente").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<Void> task2) {
                            if (task2.isSuccessful()){
                                startActivity(new Intent(home_registro_cliente.this, inicio_sesion_cliente.class));
                                Toast.makeText(home_registro_cliente.this, "TE HAS REGISTRADO EXITOSAMENTE", Toast.LENGTH_SHORT).show();

                            }else{

                                Toast.makeText(home_registro_cliente.this, "No se pudieron crear los datos correctamente", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });

                }else{
                    Toast.makeText(home_registro_cliente.this, "No se pudo registrar este usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}