package com.example.allbarber;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.allbarber.modelo.Cita;


import com.google.firebase.auth.FirebaseAuth;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class home_cliente_qr extends AppCompatActivity {

    ImageView imagenQr;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_cliente_qr);

        mAuth = FirebaseAuth.getInstance();

        imagenQr = findViewById(R.id.iv_codigoQr);

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            String id = mAuth.getCurrentUser().getUid();
            BitMatrix bitMatrix = multiFormatWriter.encode(id, BarcodeFormat.QR_CODE, 500,500);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            imagenQr.setImageBitmap(bitmap);

            Toast.makeText(home_cliente_qr.this, "¡Tu codigo QR se a generado!", Toast.LENGTH_LONG).show();

        }catch (Exception e){
            e.printStackTrace();


        }



    }
}