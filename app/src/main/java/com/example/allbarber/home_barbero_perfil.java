package com.example.allbarber;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.allbarber.modelo.Cita;
import com.example.allbarber.modelo.CitaBarbero;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class home_barbero_perfil extends AppCompatActivity {

    private ListView lvCitasRealizadas;

    private Button btncambiarContrasena;

    private TextView tvNombrebarbero;
    private String nombreBarberoIgualar;

    private List<CitaBarbero> listCita = new ArrayList<CitaBarbero>();
    ArrayAdapter<CitaBarbero> arrayAdapterCita;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_barbero_perfil);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        btncambiarContrasena = (Button)findViewById(R.id.btn_editContrasena);

        lvCitasRealizadas = (ListView)findViewById(R.id.lv_historialHorasRealizadas);

        tvNombrebarbero = (TextView)findViewById(R.id.tv_nombreBarbero);

        obtenerDatosPerfil();

        obtenerDatosListView();

        btncambiarContrasena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home_barbero_perfil.this, home_barbero_cambiar_contrasena.class);
                startActivity(intent);
            }
        });

    }

    private void obtenerDatosPerfil() {

        String id = mAuth.getCurrentUser().getUid();
        mDatabase.child("usuarioBarbero").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String nombre = snapshot.child("nombre").getValue().toString();

                    nombreBarberoIgualar = nombre;

                    tvNombrebarbero.setText(nombre);


                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }

    private void obtenerDatosListView() {

        mDatabase.child("Cita").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                listCita.clear();
                for(final DataSnapshot s : snapshot.getChildren()){

                    mDatabase.child("Cita").child(s.getKey()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                            CitaBarbero c = snapshot.getValue(CitaBarbero.class);
                            String nombreBarbero = c.getNombreBarbero();

                            String id = mAuth.getCurrentUser().getUid();
                            if(nombreBarbero.equals(nombreBarberoIgualar)){

                                CitaBarbero cita = snapshot.getValue(CitaBarbero.class);
                                String fecha = cita.getFecha();
                                String hora = cita.getHora();
                                String servicio = cita.getServicio();

                                listCita.add(cita);

                                arrayAdapterCita = new ArrayAdapter<CitaBarbero>(home_barbero_perfil.this, android.R.layout.simple_list_item_1, listCita);
                                lvCitasRealizadas.setAdapter(arrayAdapterCita);

                                Log.e("fecha: ", fecha);
                                Log.e("hora: ", hora);
                                Log.e("nombre barbero: ", nombreBarbero);
                                Log.e("servicio: ", servicio);


                            }

                        }

                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                        }
                    });


                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }
}