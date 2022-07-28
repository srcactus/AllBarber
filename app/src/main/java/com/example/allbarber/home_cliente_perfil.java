package com.example.allbarber;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.allbarber.modelo.Cita;
import com.example.allbarber.modelo.Cliente;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class home_cliente_perfil extends AppCompatActivity {

    private TextView tvNombre;
    private TextView tvTelefono;
    private TextView tvCorreo;

    private ListView lvInformacionCita;

    private List<Cita> listCita = new ArrayList<Cita>();
    ArrayAdapter<Cita> arrayAdapterCita;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_cliente_perfil);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        tvNombre = (TextView)findViewById(R.id.tv_Nombre);
        tvCorreo = (TextView)findViewById(R.id.tv_Email);
        tvTelefono = (TextView)findViewById(R.id.tv_Telefono);

        lvInformacionCita = (ListView)findViewById(R.id.lv_historialReservas);

        obtenerDatosPerfil();

        obtenerDatosListView();

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

                            Cita c = snapshot.getValue(Cita.class);
                            String idCliente = c.getIdCliente();

                            String id = mAuth.getCurrentUser().getUid();
                            if(idCliente.equals(id)){

                                Cita citaB = snapshot.getValue(Cita.class);
                                String fecha = citaB.getFecha();
                                String hora = citaB.getHora();
                                String nombreBarberia = citaB.getNombreBarberia();
                                String nombreBarbero = citaB.getNombreBarbero();
                                String servicio = citaB.getServicio();

                                listCita.add(citaB);

                                arrayAdapterCita = new ArrayAdapter<Cita>(home_cliente_perfil.this, android.R.layout.simple_list_item_1, listCita);
                                lvInformacionCita.setAdapter(arrayAdapterCita);

                                Log.e("fecha: ", fecha);
                                Log.e("hora: ", hora);
                                Log.e("nombre barberia: ", nombreBarberia);
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

    public void obtenerDatosPerfil(){
        String id = mAuth.getCurrentUser().getUid();
        mDatabase.child("Cliente").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String nombre = snapshot.child("nombre").getValue().toString();
                    String email = snapshot.child("email").getValue().toString();
                    String telefono = snapshot.child("telefono").getValue().toString();

                    tvNombre.setText(nombre);
                    tvCorreo.setText(email);
                    tvTelefono.setText(telefono);


                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editar_perfil, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(home_cliente_perfil.this, home_cliente_editar_perfil.class);
        startActivity(intent);
        return true;
    }
}