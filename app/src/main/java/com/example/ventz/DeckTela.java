package com.example.ventz;

import static java.security.AccessController.getContext;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ventz.model.Dados;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.json.JSONException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class DeckTela extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_deck_tela);

        TextView labelNomeEvento = findViewById(R.id.labelNomeEvento);

        TextView dataInicio = findViewById(R.id.txtDataInicioIngresso);
        TextView dataTermino = findViewById(R.id.txtDataTerminoIngresso);

        TextView labelEndereco = findViewById(R.id.labelEndereco);

        TextView multilineDescricao = findViewById(R.id.multilineDescricao);

        int idEventoAtual = Dados.getInstance().getIdEventoAtual();

        TextView idIngresso = findViewById(R.id.txtIngressoAtual);

        ImageView imageViewQrCode = findViewById(R.id.imageViewQrCode);

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();



             buscarEventosPorId();
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                 labelNomeEvento.setText(Dados.getInstance().getTituloEventoAtual());
//                 idIngresso.setText(Dados.getInstance().getIdIngressoAtual());
                 dataInicio.setText(Dados.getInstance().getDataInicioAtual().toString());
                 dataTermino.setText(Dados.getInstance().getDataTerminoAtual().toString());
                 labelEndereco.setText(Dados.getInstance().getEnderecoAtual());
                 multilineDescricao.setText(Dados.getInstance().getDescricaoAtual());
//
                try {
                    BitMatrix bitMatrix = multiFormatWriter.encode(Dados.getInstance().getUrl() + "/ingressos/utilizarIngresso/" + Dados.getInstance().getIdIngressoAtual(), BarcodeFormat.QR_CODE, 300, 300);

                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);

                    imageViewQrCode.setImageBitmap(bitmap);

                }catch (WriterException e) {
                    throw new RuntimeException(e);
                }

            }, 3000); // 3000 milliseconds = 3 seconds
    }


    private void buscarEventosPorId() {



// URL para buscar todos os eventos
            String urlBuscarEvento = Dados.getInstance().getUrl() + "/eventos/buscarPorId/" + Dados.getInstance().getIdEventoAtual();

            // Faz a requisição GET
            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.GET,
                    urlBuscarEvento,
                    null,
                    response -> {
                        try {
                            // Extrai apenas os dados do evento

                            Dados.getInstance().setTituloEventoAtual(response.getString("tituloEvento"));
                            Dados.getInstance().setDescricaoAtual(response.getString("descricao"));

                            // Formata e define as datas
                            SimpleDateFormat formatoEntrada = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.getDefault());
                            try {
                                Dados.getInstance().setDataInicioAtual(formatoEntrada.parse(response.getString("dataInicio")));
                                Dados.getInstance().setDataTerminoAtual(formatoEntrada.parse(response.getString("dataTermino")));
                            } catch (ParseException e) {
                                e.printStackTrace();
                                Dados.getInstance().setDataInicioAtual(null);
                                Dados.getInstance().setDataTerminoAtual(null);
                            }

                            Dados.getInstance().setEnderecoAtual(response.getString("endereco"));

                            // Notifica sucesso
                            if (getContext() != null) {

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    },
                    error -> {
                        // Trata erros
                        if (error.networkResponse != null) {
                            String errorMessage = new String(error.networkResponse.data);
                            Log.e("ERROR", "Erro: " + errorMessage + " (Código: " + error.networkResponse.statusCode + ")");
                            if (getContext() != null) {
                                Toast.makeText(this, "Erro ao buscar evento: " + errorMessage, Toast.LENGTH_LONG).show();
                            }
                        } else {
                            if (getContext() != null) {
                                Toast.makeText(this, "Erro desconhecido ao buscar evento.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
            );

            // Adiciona a requisição à fila
            Volley.newRequestQueue(this).add(request);


    }




}
