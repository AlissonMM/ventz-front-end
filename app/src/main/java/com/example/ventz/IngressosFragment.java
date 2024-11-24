package com.example.ventz;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.ventz.model.Dados;
import com.example.ventz.model.Deck;
import com.example.ventz.model.Ingresso;
import com.example.ventz.model.IngressoAdapter;
//import com.example.ventz.model.DeckAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class IngressosFragment extends Fragment {

    private String nomeDeckString = "";
    private String url;
    private RequestQueue requestQueue;
    private int idDeckPorNome = 0;

    public IngressosFragment() {
        // Required empty public constructor
    }

    public static IngressosFragment newInstance(String param1, String param2) {
        IngressosFragment fragment = new IngressosFragment();
        Bundle args = new Bundle();
        args.putString("param1", param1);
        args.putString("param2", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_decks, container, false);
        ListView listView = view.findViewById(R.id.listView);
//        ImageButton btnCriarDeck = view.findViewById(R.id.btnCriarEvento);

        requestQueue = Volley.newRequestQueue(getContext()); // Inicializa a RequestQueue

        // Initialize the Dialog
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.deck_dialog_box);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        EditText txtDataInicio = dialog.findViewById(R.id.txtDataInicio);

        EditText txtDataTermino = dialog.findViewById(R.id.txtDataTermino);

//        // btnMostrarData add on click event
//        btnMostrarData.setOnClickListener(v -> {
//
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH", Locale.getDefault());
//        dateFormat.setLenient(false); // Strict parsing to avoid invalid dates
//
//        try {
//        // Parse txtDataInicio
//        Date txtDataInicioFormatada = dateFormat.parse(txtDataInicio.getText().toString());
//
//        // Parse txtDataTermino
//        Date txtDataTerminoFormatada = dateFormat.parse(txtDataTermino.getText().toString());
//
//        // If both dates are valid, show them
//        Toast.makeText(getContext(),
//            "Valid dates:\nStart: " + txtDataInicioFormatada.toString() +
//            "\nEnd: " + txtDataTerminoFormatada.toString(),
//            Toast.LENGTH_LONG).show();
//
//    } catch (ParseException e) {
//        // Handle invalid date format
//        Toast.makeText(getContext(), "Invalid date format. Use YYYY-MM-DD HH.", Toast.LENGTH_SHORT).show();
//    }
//
//
//           // show text on screen
//            Toast.makeText(getContext(), txtDataInicio.getText(), Toast.LENGTH_SHORT).show();
//        });





        Button btnCancelar = dialog.findViewById(R.id.btnCancelar);
        Button btnCriar = dialog.findViewById(R.id.btnCriar);
        EditText nomeDeck = dialog.findViewById(R.id.textViewNomeDeck);

        // Adapter for ListView of Decks
        List<Deck> decks = new ArrayList<>();
//        DeckAdapter adapter = new DeckAdapter(getContext(), decks);
//        listView.setAdapter(adapter);

        // List with Deck IDs
        List<Integer> listaIdDecks = new ArrayList<>();

        List<Ingresso> ingressos = new ArrayList<>();

            // Adicionando ingressos fictícios
//        ingressos.add(new Ingresso(1, 1, 101, true));   // Evento ID: 1, Usuário ID: 101, Disponível
//        ingressos.add(new Ingresso(2, 1, 102, false));  // Evento ID: 1, Usuário ID: 102, Indisponível

        IngressoAdapter adapter = new IngressoAdapter(getContext(), ingressos);
        listView.setAdapter(adapter);

        int idUsuarioLogado = Dados.getInstance().getIdUsuarioLogado(); // Pega o ID do usuário logado
        buscarIngressosUsuario(ingressos, adapter);

        listView.setOnItemClickListener((parent, view1, position, id) -> {


    // Define o evento atual no singleton Dados
    Dados.getInstance().setIdEventoAtual(ingressos.get(position).getEvento());
    Dados.getInstance().setIdIngressoAtual(ingressos.get(position).getIdIngresso());

    // Verifica se o contexto não é nulo antes de usar
        if (getContext() != null) {
            Toast.makeText(getContext(), "Id Ingresso " + Dados.getInstance().getIdIngressoAtual(), Toast.LENGTH_SHORT).show();
        }

    // Inicia a nova atividade
    Intent intent = new Intent(getContext(), DeckTela.class);
    startActivity(intent);
});
        return view;
    }



    private void buscarIngressosUsuario(List<Ingresso> ingressos, IngressoAdapter adapter) {
        String urlBuscarIngressos =  Dados.getInstance().getUrl() + "/ingressos/buscarTodos";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, urlBuscarIngressos, null,
                response -> {
                    // Limpa a lista de ingressos antes de atualizar
                    ingressos.clear();
                    try {
                        // Itera pelo JSON Array e adiciona os ingressos à lista
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject ingressoJson = response.getJSONObject(i);
                            int idIngresso = ingressoJson.getInt("idIngresso");
                            JSONObject eventoJson = ingressoJson.getJSONObject("evento");
                            int idEvento = eventoJson.getInt("idEvento");
                            int idUsuarioIngresso = ingressoJson.getJSONObject("usuario").getInt("idUsuario");
                            boolean disponivel = ingressoJson.getBoolean("disponivel");

                            // Adiciona o ingresso à lista
                            ingressos.add(new Ingresso(idIngresso, idEvento, idUsuarioIngresso, disponivel));
                        }

                        // Notifica o adapter sobre a atualização
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), "Erro ao processar ingressos", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    ingressos.clear();
                    try {
                        JSONArray response = new JSONArray(); // Apenas para simular a estrutura de sucesso no erro
                        // Itera pelo JSON Array e adiciona os ingressos à lista
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject ingressoJson = response.getJSONObject(i);
                            int idIngresso = ingressoJson.getInt("idIngresso");
                            JSONObject eventoJson = ingressoJson.getJSONObject("evento");
                            int idEvento = eventoJson.getInt("idEvento");
                            int idUsuarioIngresso = ingressoJson.getJSONObject("usuario").getInt("idUsuario");
                            boolean disponivel = ingressoJson.getBoolean("disponivel");

                            // Adiciona o ingresso à lista
                            ingressos.add(new Ingresso(idIngresso, idEvento, idUsuarioIngresso, disponivel));
                        }

                        // Notifica o adapter sobre a atualização
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), "Erro ao processar ingressos (fallback)", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        // Adiciona a requisição à fila
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);
    }
}