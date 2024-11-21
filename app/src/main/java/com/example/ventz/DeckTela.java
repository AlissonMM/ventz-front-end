package com.example.ventz;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ventz.model.Dados;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class DeckTela extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_deck_tela);

        TextView labelNomeEvento = findViewById(R.id.labelNomeEvento);

        int idEventoAtual = Dados.getInstance().getIdEventoAtual();

        TextView idIngresso = findViewById(R.id.txtIdIngresso);

        ImageView imageViewQrCode = findViewById(R.id.imageViewQrCode);

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

            try {
            BitMatrix bitMatrix = multiFormatWriter.encode(idIngresso.getText().toString(), BarcodeFormat.QR_CODE, 300, 300);

            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);

            imageViewQrCode.setImageBitmap(bitmap);

            }catch (WriterException e){
            throw new RuntimeException(e);

//         labelNomeEvento.setText("Id do evento: " + idEventoAtual);


    }



}
