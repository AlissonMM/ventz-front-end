package com.example.ventz;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ventz.model.Dados;
import com.example.ventz.model.Deck;
//import com.example.ventz.model.DeckAdapter;
import com.example.ventz.model.Evento;
import com.example.ventz.model.EventoAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HomeFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private String nomeDeckString = "";
    private RequestQueue requestQueue;

    public HomeFragment() {
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ListView listView = view.findViewById(R.id.listView);

        // Initialize the Dialog
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.deck_dialog_box);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        Button btnMostrarData = dialog.findViewById(R.id.btnMostrarData);
        EditText txtDataInicio = dialog.findViewById(R.id.txtDataInicio);

        EditText txtDataTermino = dialog.findViewById(R.id.txtDataTermino);

        Button btnCriarEvento = view.findViewById(R.id.btnCriarEvento);

        Button btnCancelar = dialog.findViewById(R.id.btnCancelar);
        Button btnCriar = dialog.findViewById(R.id.btnCriar);
        EditText nomeDeck = dialog.findViewById(R.id.textViewNomeDeck);

//         String urlBuscarTodosDecks = Dados.getInstance().getUrl() + "/decks/buscarTodos";
//         requestQueue = Volley.newRequestQueue(getContext());

//         List<Deck> decksList = new ArrayList<>();
//         DeckAdapter adapter = new DeckAdapter(getContext(), decksList);
//         listView.setAdapter(adapter);


        List<Evento> eventos = new ArrayList<>();


        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH");

            //create new events

            eventos.add(new Evento(
                    1,
                    "Festival de Música",
                    "Um evento para amantes da música com várias bandas locais e internacionais.",
                    5000,
                    dateFormat.parse("2024/12/01 18"),
                    dateFormat.parse("2024/12/02 02"),
                    "cool"
            ));


            eventos.add(new Evento(
                    2,
                    "Feira de Tecnologia",
                    "Exposição de inovações tecnológicas e startups.",
                    1000,
                    dateFormat.parse("2024/12/11 09"),
                    dateFormat.parse("2024/12/12 18"),
                    "Centro de Bosta, Rio de Janeiro"
            ));

            eventos.add(new Evento(
                    3,
                    "Conferência de Startups",
                    "Conferência sobre novas startups e oportunidades de negócios.",
                    2000,
                    dateFormat.parse("2024/12/15 08"),
                    dateFormat.parse("2024/12/15 20"),
                    "Auditório do Seu Rabo, Belo Horizonte"
            ));

            eventos.add(new Evento(
                    4,
                    "Exposição de Arte Contemporânea",
                    "Mostra de artistas contemporâneos de diversas partes do mundo.",
                    300,
                    dateFormat.parse("2024/12/20 10"),
                    dateFormat.parse("2024/12/20 18"),
                    "Museu de Putaria, Salvador"
            ));

        } catch (Exception e) {
            e.printStackTrace();
        }

        EventoAdapter adapter = new EventoAdapter(getContext(), eventos);
        listView.setAdapter(adapter);


//btnCriarEvento.setOnClickListener(v -> Toast.makeText(getContext(), "Criar", Toast.LENGTH_LONG).show());
        btnCriarEvento.setOnClickListener(v -> dialog.show());
        btnCancelar.setOnClickListener(v -> dialog.dismiss());

        btnCriar.setOnClickListener(v -> {
                    nomeDeckString = nomeDeck.getText().toString().trim();
                    if (nomeDeckString.isEmpty()) {
                        Toast.makeText(getContext(), "O nome do deck não pode estar vazio.", Toast.LENGTH_SHORT).show();
                        return;
                    }
        });

                    // Requisição para buscar todos os decks
//         StringRequest stringRequest = new StringRequest(Request.Method.GET, urlBuscarTodosDecks,
//                 new Response.Listener<String>() {
//                     @Override
//                     public void onResponse(String response) {
//                         try {
//                             Log.d("API Response", response);
//
//                             // Extrair todos os IDs de decks da resposta
//                             List<Integer> deckIds = extractDeckIds(response);
//                             Log.d("Deck IDs", "IDs dos Decks: " + deckIds);
//
//                             // Processar cada deck e extrair o 'nome' e 'idUsuarioFk'
//                             Pattern pattern = Pattern.compile("Deck\\{[^}]*\\}");
//                             Matcher matcher = pattern.matcher(response);
//                             while (matcher.find()) {
//                                 String deckString = matcher.group();
//
//                                 String nomeDeck = extractSecondNome(deckString);
//                                 int idUsuarioFk = extractIdUsuario(deckString);
//                                 int idDeck = extractDeckId(deckString);
//
//                                 Log.d("Deck Info", "Nome do Deck: " + nomeDeck + ", Usuario Criador: " + idUsuarioFk);
//
//                                 decksList.add(new Deck(idDeck, nomeDeck, idUsuarioFk));
//                             }
//
//                             adapter.notifyDataSetChanged();
//
//                         } catch (Exception e) {
//                             e.printStackTrace();
//                             Log.e("Error", "Erro ao processar a resposta da API", e);
//                         }
//                     }
//                 }, new Response.ErrorListener() {
//             @Override
//             public void onErrorResponse(VolleyError error) {
//                 Log.e("API Error", "Erro na requisição", error);
//             }
//         });

//        requestQueue.add(stringRequest);
//
//        listView.setOnItemClickListener((parent, view1, position, id) -> {
//            Dados.getInstance().setIdDeckAtual(decksList.get(position).getIdDeck());
//
//            JSONObject jsonAssociarDeck = new JSONObject();
//            try {
//                jsonAssociarDeck.put("idUsuarioFk", Dados.getInstance().getIdUsuarioLogado());
//                jsonAssociarDeck.put("idDeckFk", Dados.getInstance().getIdDeckAtual());
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//            JsonObjectRequest requestAssociarDeck = new JsonObjectRequest(
//                    Request.Method.POST,
//                    Dados.getInstance().getUrl() + "/deckUsuarios/inserir",
//                    jsonAssociarDeck,
//                    response -> Log.d("AssociarDeck", "Associado com sucesso!"),
//                    error -> {
//                        if (error.networkResponse != null) {
//                            int statusCode = error.networkResponse.statusCode;
//                            Toast.makeText(getContext(), "Erro ao associar o deck ao usuário. Código: " + statusCode, Toast.LENGTH_SHORT).show();
//                        } else {
//                            Toast.makeText(getContext(), "Deck associado ao Usuario", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//            );
//            requestQueue.add(requestAssociarDeck);
//        });


//                }


//    // Método auxiliar para extrair todos os IDs dos decks
//    private List<Integer> extractDeckIds(String response) {
//        List<Integer> deckIds = new ArrayList<>();
//        Pattern pattern = Pattern.compile("idDeck=(\\d+)");
//        Matcher matcher = pattern.matcher(response);
//
//        while (matcher.find()) {
//            int deckId = Integer.parseInt(matcher.group(1));
//            deckIds.add(deckId);
//        }
//        return deckIds;
//    }
//
//    // Extrai o nome do deck a partir da string do deck
//    private String extractSecondNome(String response) {
//        String nomeDeck = "";
//        Pattern nomePattern = Pattern.compile("Deck\\{[^}]*?nome='([^']*)'");
//        Matcher nomeMatcher = nomePattern.matcher(response);
//
//        if (nomeMatcher.find()) {
//            nomeDeck = nomeMatcher.group(1);
//        }
//        return "Deck criado por: " + nomeDeck;
//    }
//
//    // Extrai o idUsuario do deck
//    private int extractIdUsuario(String deckString) {
//        int idUsuario = -1;
//        Pattern idUsuarioPattern = Pattern.compile("idUsuario=(\\d+)");
//        Matcher idUsuarioMatcher = idUsuarioPattern.matcher(deckString);
//        if (idUsuarioMatcher.find()) {
//            idUsuario = Integer.parseInt(idUsuarioMatcher.group(1));
//        }
//        return idUsuario;
//    }
//
//    // Extrai o idDeck do deck
//    private int extractDeckId(String deckString) {
//        int idDeck = -1;
//        Pattern idDeckPattern = Pattern.compile("idDeck=(\\d+)");
//        Matcher idDeckMatcher = idDeckPattern.matcher(deckString);
//        if (idDeckMatcher.find()) {
//            idDeck = Integer.parseInt(idDeckMatcher.group(1));
//        }
//        return idDeck;
//    }
        return view;
    }
}
