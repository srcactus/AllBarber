package com.example.allbarber;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.allbarber.modelo.Barberias;
import com.example.allbarber.modelo.Barberos;
import com.example.allbarber.modelo.Cita;
import com.example.allbarber.modelo.Horas;
import com.example.allbarber.modelo.Servicios;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class home_cliente_solicitar_hora extends AppCompatActivity {



    Spinner spnBarberias, spnServicios, spnBarberos, spnHorasDisponibles;

    Button btnAgendarHora;

    CalendarView cvCalendario;

    private String barberiaSeleccionada;
    private String servicioSeleccionado;
    private String fechaSeleccionada;
    public String nombreCliente;


    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mDatabase;
    FirebaseAuth mAuth;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_cliente_solicitar_hora);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabase = mFirebaseDatabase.getReference();
        mAuth = FirebaseAuth.getInstance();

        spnBarberias = findViewById(R.id.spn_Barberias);
        spnServicios = findViewById(R.id.spn_Servicios);
        spnBarberos = findViewById(R.id.spn_Barberos);
        spnHorasDisponibles = findViewById(R.id.spn_HorasDisponibles);

        btnAgendarHora = findViewById(R.id.btn_agendarHora);

        cvCalendario =  findViewById(R.id.cv_Calendario);

        String id = mAuth.getCurrentUser().getUid();
        mDatabase.child("Cliente").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String nombre = snapshot.child("nombre").getValue().toString();

                    nombreCliente = nombre;


                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });



        cvCalendario.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String fecha = dayOfMonth + "/" + month + "/" + year;
                int anio = year;
                int mes = month;
                int dia = dayOfMonth;
                fechaSeleccionada = fecha;

                Toast.makeText(home_cliente_solicitar_hora.this, "Fecha Seleccionada: "+fecha, Toast.LENGTH_SHORT).show();
            }
        });


        cargarBarberias();
        cargarServicios();
        cargarBarberos();
        cargarHorasDisponibles();

        btnAgendarHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String IdCliente = mAuth.getCurrentUser().getUid();

                String nombreBarberia = spnBarberias.getSelectedItem().toString();
                String servicio = spnServicios.getSelectedItem().toString();
                String nombreBarberos = spnBarberos.getSelectedItem().toString();
                String hora = spnHorasDisponibles.getSelectedItem().toString();




                Cita c = new Cita();
                c.setId(UUID.randomUUID().toString());
                c.setIdCliente(IdCliente);
                c.setNombreBarberia(nombreBarberia);
                c.setServicio(servicio);
                c.setNombreBarbero(nombreBarberos);
                c.setHora(hora);
                c.setNombreCliente(nombreCliente);
                c.setFecha(fechaSeleccionada);
                mDatabase.child("Cita").child(c.getId()).setValue(c);
                Toast.makeText(home_cliente_solicitar_hora.this, "Cita Agendada", Toast.LENGTH_LONG).show();


                startActivity(new Intent(home_cliente_solicitar_hora.this, home_cliente_qr.class));
                finish();

            }
        });

        /*btnAgendarHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombreBarberia = spnBarberias.getSelectedItem().toString();
                String servicio = spnServicios.getSelectedItem().toString();


                cvCalendario.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                        String fechaActual = year + "/" + month + "/" + dayOfMonth;


                    }
                });

                String nombreBarbero = spnBarberos.getSelectedItem().toString();
                String hora = spnHorasDisponibles.getSelectedItem().toString();

                mDatabase.child("Cita").child()

            }
        });*/


    }



    public void cargarHorasDisponibles() {
        final List<Horas> horas = new ArrayList<>();
        mDatabase.child("Horas").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot ds : snapshot.getChildren()){
                        String id = ds.getKey();
                        String hora = ds.child("hora").getValue().toString();
                        horas.add(new Horas(id, hora));
                    }

                    ArrayAdapter<Horas> arrayAdapter = new ArrayAdapter<>(home_cliente_solicitar_hora.this, android.R.layout.simple_dropdown_item_1line, horas);
                    spnHorasDisponibles.setAdapter(arrayAdapter);

                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    public void cargarBarberos() {
        final List<Barberos> barberos = new ArrayList<>();
        mDatabase.child("usuarioBarbero").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot ds : snapshot.getChildren()){
                        String id = ds.getKey();
                        String nombre = ds.child("nombre").getValue().toString();
                        barberos.add(new Barberos(id, nombre));

                    }



                    ArrayAdapter<Barberos> arrayAdapter = new ArrayAdapter<>(home_cliente_solicitar_hora.this, android.R.layout.simple_dropdown_item_1line, barberos);
                    spnBarberos.setAdapter(arrayAdapter);

                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
     }


    public void cargarBarberias(){
        final List<Barberias> barberias = new ArrayList<>();
        mDatabase.child("Barberias").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot ds : snapshot.getChildren()){
                        String id = ds.getKey();
                        String nombre = ds.child("nombre").getValue().toString();
                        barberias.add(new Barberias(id, nombre));
                    }

                    ArrayAdapter<Barberias> arrayAdapter = new ArrayAdapter<>(home_cliente_solicitar_hora.this, android.R.layout.simple_dropdown_item_1line, barberias);
                    spnBarberias.setAdapter(arrayAdapter);

                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    public void cargarServicios(){

        final List<Servicios> servicios = new ArrayList<>();
        mDatabase.child("Servicio").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot ds : snapshot.getChildren()){
                        String id = ds.getKey();
                        String nombre = ds.child("nombre").getValue().toString();
                        String precio = ds.child("precio").getValue().toString();
                        servicios.add(new Servicios(id, nombre, precio));
                    }

                    ArrayAdapter<Servicios> arrayAdapter = new ArrayAdapter<>(home_cliente_solicitar_hora.this, android.R.layout.simple_dropdown_item_1line, servicios);
                    spnServicios.setAdapter(arrayAdapter);

                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }



}