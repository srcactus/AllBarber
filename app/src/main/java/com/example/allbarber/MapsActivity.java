package com.example.allbarber;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.example.allbarber.modelo.AllFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.allbarber.databinding.ActivityMapsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    private DatabaseReference mDatabase;
    private ArrayList<Marker> tmpRealTimeMarkers = new ArrayList<>();
    private ArrayList<Marker> realTimeMarkers = new ArrayList<>();

    private Marker InfoWindow;
    private Marker InfoWindow2;
    private Marker InfoWindow3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mDatabase = FirebaseDatabase.getInstance().getReference();


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        mMap.setMyLocationEnabled(true);



        googleMap.setOnInfoWindowClickListener(this);



        /*mDatabase.child("usuarios").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot) {

                for (Marker marker:realTimeMarkers){
                    marker.remove();
                }


                for (DataSnapshot snapshot : dataSnapshot.getChildren()){

                    MapsPojo mp = snapshot.getValue(MapsPojo.class);
                    Double latitud = mp.getLatitud();
                    Double longitud = mp.getLongitud();
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(new LatLng(latitud,longitud));

                    tmpRealTimeMarkers.add(mMap.addMarker(markerOptions));

                }

                realTimeMarkers.clear();
                realTimeMarkers.addAll(tmpRealTimeMarkers);

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });*/

        // Add a marker in Sydney and move the camera




        LatLng CaballerosBarberia = new LatLng(-33.5173212, -70.6061714);
        InfoWindow = googleMap.addMarker(new MarkerOptions().position(CaballerosBarberia).title("Caballeros Barberia").snippet("Ver mas informacion").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        LatLng Shishigang = new LatLng(-33.5209796, -70.6008249);
        InfoWindow2 = googleMap.addMarker(new MarkerOptions().position(Shishigang).title("Barberia Shishigang").snippet("Ver mas informacion").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        LatLng BarbaNegra = new LatLng(-33.5224955, -70.5746615);
        InfoWindow3 = googleMap.addMarker(new MarkerOptions().position(BarbaNegra).title("Barberia BarbaNegra").snippet("Ver mas informacion").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));


        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    public void onInfoWindowClick(@NonNull @NotNull Marker marker) {

        if(marker.equals(InfoWindow)){
            AllFragment.newInstance(marker.getTitle(),getString(R.string.CaballerosBarberiaInfo)).show(getSupportFragmentManager(),null);
        }

        if (marker.equals(InfoWindow2)){

            AllFragment.newInstance(marker.getTitle(),getString(R.string.ShishigangInfo)).show(getSupportFragmentManager(),null);

        }

        if (marker.equals(InfoWindow3)){
            AllFragment.newInstance(marker.getTitle(),getString(R.string.BarbaNegraInfo)).show(getSupportFragmentManager(),null);
        }

    }
}