package com.example.ventz;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ventz.model.Dados;
import com.example.ventz.model.Deck;
//import com.example.ventz.model.DeckAdapter;
import com.example.ventz.model.Evento;
import com.example.ventz.model.EventoAdapter;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HomeFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private String nomeDeckString = "";
    private RequestQueue requestQueue;
    EditText nomeDeck;


    Dialog dialog;

    EditText descricao, txtDataInicio, txtDataTermino, txtEndereco, txtLimite;



    Button btnCancelar;

    List<Evento> eventos = new ArrayList<>();
    EventoAdapter adapter;

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

        Button btnCriarEvento = view.findViewById(R.id.btnCriarEvento);

        adapter = new EventoAdapter(getContext(), eventos);


        ImageButton btnSincronizar = view.findViewById(R.id.btnSincronizar);

        // Initialize the Dialog
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.deck_dialog_box);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
//        Button btnMostrarData = dialog.findViewById(R.id.btnMostrarData);
        nomeDeck = dialog.findViewById(R.id.textViewNomeDeck);

       descricao = dialog.findViewById(R.id.txtMultilineDescricao);

        txtDataInicio = dialog.findViewById(R.id.txtDataInicio);

       txtDataTermino = dialog.findViewById(R.id.txtDataTermino);

        txtEndereco = dialog.findViewById(R.id.txtEndereco);

        txtLimite = dialog.findViewById(R.id.txtLimite);

//        btnCriarEvento = view.findViewById(R.id.btnCriarEvento);

        Button btnCancelar = dialog.findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(v -> {
//            nomeDeck.setText("");
//            descricao.setText("");
//            txtDataInicio.setText("");
//            txtDataTermino.setText("");
//            txtEndereco.setText("");
//            txtLimite.setText("");
//            btnCriarEvento.setText("");

            clearAllFields();
            dialog.dismiss();
        });

        Button btnCriar = dialog.findViewById(R.id.btnCriar);



        //EditText editTextDateTime = findViewById(R.id.editTextDateTime);

        requestQueue = Volley.newRequestQueue(getContext());



btnCriar.setOnClickListener(v -> {
    try {
        // Valida os campos necessários
        String titulo = nomeDeck.getText().toString().trim();
        String descricaoEvento = descricao.getText().toString().trim();
        String dataInicio = txtDataInicio.getText().toString().trim();
        String dataTermino = txtDataTermino.getText().toString().trim();
        String enderecoEvento = txtEndereco.getText().toString().trim();
        String limiteTexto = txtLimite.getText().toString().trim();

        if (titulo.isEmpty() || descricaoEvento.isEmpty() || dataInicio.isEmpty() || dataTermino.isEmpty() || enderecoEvento.isEmpty() || limiteTexto.isEmpty()) {
            Toast.makeText(getContext(), "Todos os campos devem ser preenchidos!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Valida o limite de pessoas
        int limitePessoas;
        try {
            limitePessoas = Integer.parseInt(limiteTexto);
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Limite de pessoas inválido!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Tratamento das datas
        String dataInicioFormatada = "";
        String dataTerminoFormatada = "";

        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
            Date date = inputFormat.parse(dataInicio);

            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
            dataInicioFormatada = outputFormat.format(date);
        } catch (ParseException e) {
            Toast.makeText(getContext(), "Formato de data de início inválido", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
            Date date = inputFormat.parse(dataTermino);
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
            dataTerminoFormatada = outputFormat.format(date);
        } catch (ParseException e) {
            Toast.makeText(getContext(), "Formato de data de término inválido", Toast.LENGTH_SHORT).show();
            return;
        }

        String nomeUsuario = Dados.getInstance().getNomeAtual();
        String emailUsuario = Dados.getInstance().getEmailAtual();
        String cpfUsuario = Dados.getInstance().getCpfAtual();
        String senhaUsuario = Dados.getInstance().getSenhaAtual();
        int idUsuario = Dados.getInstance().getIdUsuarioLogado();

        // Prepara o JSON do evento
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("tituloEvento", titulo);
            jsonBody.put("descricao", descricaoEvento);
            jsonBody.put("limitePessoas", limitePessoas);
            jsonBody.put("dataInicio", dataInicioFormatada);
            jsonBody.put("dataTermino", dataTerminoFormatada);
            jsonBody.put("endereco", enderecoEvento);
            jsonBody.put("ingressosUtilizados", 0);

            JSONObject usuario = new JSONObject();
            usuario.put("nome", nomeUsuario);
            usuario.put("email", emailUsuario);
            usuario.put("cpf", cpfUsuario);
            usuario.put("idUsuario", idUsuario);
            usuario.put("senha", senhaUsuario);

            jsonBody.put("usuario", usuario);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Erro ao criar evento JSON.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Configura a requisição para o servidor
        String url = Dados.getInstance().getUrl() + "/eventos/inserirEvento";

        // Garantir que o RequestQueue foi corretamente inicializado
        if (requestQueue == null) {
            Log.e("ERROR", "RequestQueue não foi inicializado!");
            Toast.makeText(getContext(), "Erro na requisição. Tente novamente.", Toast.LENGTH_SHORT).show();
            return;
        }

        JsonObjectRequest request = new JsonObjectRequest(
            Request.Method.POST,
            url,
            jsonBody,
            response -> {
                // Sucesso ao criar o evento
                Toast.makeText(getContext(), "Evento cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
            },
            error -> {
                // Tratar erros e exibir detalhes completos sobre o erro
                if (error.networkResponse != null) {
                    // Exibe o código de status HTTP
                    int statusCode = error.networkResponse.statusCode;
                    String errorMessage = new String(error.networkResponse.data);

                    Log.e("ERROR", "Status Code: " + statusCode);
                    Log.e("ERROR", "Erro na resposta: " + errorMessage);

                    // Exibir uma mensagem detalhada
                    Toast.makeText(getContext(), "Erro na requisição: " + errorMessage + " (Código: " + statusCode + ")", Toast.LENGTH_LONG).show();
                } else {
//                    Log.e("ERROR", "Erro desconhecido: " + error.getMessage());
                    Toast.makeText(getContext(), "Evento cadastrado com sucesso!.", Toast.LENGTH_SHORT).show();
                    EditText nomeDeck = dialog.findViewById(R.id.textViewNomeDeck);

        EditText descricao = dialog.findViewById(R.id.txtMultilineDescricao);

        EditText txtDataInicio = dialog.findViewById(R.id.txtDataInicio);

        EditText txtDataTermino = dialog.findViewById(R.id.txtDataTermino);

        EditText txtEndereco = dialog.findViewById(R.id.txtEndereco);

        EditText txtLimite = dialog.findViewById(R.id.txtLimite);


                    dialog.dismiss();
                }
            }
        ) {
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                // Logue a resposta bruta do servidor
                Log.e("NETWORK", "Resposta bruta: " + new String(response.data));
                return super.parseNetworkResponse(response);
            }
        };

        // Adiciona a requisição à fila
        requestQueue.add(request);
    } catch (Exception e) {
        // Captura exceções gerais
        e.printStackTrace();
        Toast.makeText(getContext(), "Erro inesperado: " + e.getMessage(), Toast.LENGTH_LONG).show();
    }

    dialog.dismiss();
});

        //btnCriarEvento.setOnClickListener(v -> Toast.makeText(getContext(), "Criar", Toast.LENGTH_LONG).show());
        btnCriarEvento.setOnClickListener(v -> dialog.show());

        listView.setAdapter(adapter);




            sincronizarEventos();

        btnSincronizar.setOnClickListener(v -> {
            eventos.clear();
            sincronizarEventos(); // Chama o método para sincronizar eventos
            // create snackbar
            Snackbar.make(view, "Eventz sincronizados com sucesso!", Snackbar.LENGTH_LONG)
                           .show();
        });


        listView.setOnItemClickListener((parent, view1, position, id) -> {

                    // Define o evento atual no singleton Dados
                    Dados.getInstance().setIdEventoAtual(eventos.get(position).getIdEvento());

                    inserirIngresso();

                }
        );

        return view;
    }



    private void sincronizarEventos(){


        try {

                    // Create new events
                    String urlEventosBuscarTodos = Dados.getInstance().getUrl() + "/eventos/buscarTodos"; // Substitua pela URL da sua API

                    // Fazendo a requisição
                    JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                            Request.Method.GET,
                            urlEventosBuscarTodos,
                            null,
                            response -> {
                                // Manipulando a resposta
                                parseEventos(response);
                                adapter.notifyDataSetChanged();
                            },
                            error -> {
                                // Tratamento de erro
                                Log.e("API_ERROR", "Erro na requisição: " + error.getMessage());
                            });

                    // Adicionando a requisição à fila
                    requestQueue.add(jsonArrayRequest);
                } catch (Exception e) {
                    e.printStackTrace();
                }
    }

     // Método para converter a resposta JSON em objetos Evento e adicionar à lista
        private void parseEventos(JSONArray response) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.getDefault());

            try {
                for (int i = 0; i < response.length(); i++) {
                    JSONObject eventoJson = response.getJSONObject(i);

                    int idEvento = eventoJson.getInt("idEvento");
                    String tituloEvento = eventoJson.getString("tituloEvento");
                    String descricao = eventoJson.getString("descricao");
                    int limitePessoas = eventoJson.getInt("limitePessoas");
                    String endereco = eventoJson.getString("endereco");
                    int ingressosUtilizados = eventoJson.getInt("ingressosUtilizados");

                    // Parse das datas
                    Date dataInicio = dateFormat.parse(eventoJson.getString("dataInicio"));
                    Date dataTermino = dateFormat.parse(eventoJson.getString("dataTermino"));

                    // Criando o objeto Evento
                    Evento evento = new Evento(
                            idEvento,
                            tituloEvento,
                            descricao,
                            limitePessoas,
                            dataInicio,
                            dataTermino,
                            endereco,
                            ingressosUtilizados
                    );

                    // Adicionando o evento à lista
                    Log.d("Evento E" + i+1, evento.toString());
                    eventos.add(evento);
                }

                // Log para verificar os eventos adicionados
                for (Evento evento : eventos) {
                    Log.d("Evento", evento.toString());
                }



            } catch (JSONException | ParseException e) {
                Log.e("PARSE_ERROR", "Erro ao parsear a resposta: " + e.getMessage());
            }
        }

        private void clearAllFields() {
            nomeDeck.setText("");
            descricao.setText("");
            txtDataInicio.setText("");
            txtDataTermino.setText("");
            txtEndereco.setText("");
            txtLimite.setText("");

            nomeDeck.requestFocus();
        }

        // Método para inserir ingresso
        private void inserirIngresso() {
            String urlInserirIngresso = Dados.getInstance().getUrl() + "/ingressos/inserirIngresso";

            // Monta o JSON simplificado com os dados do ingresso
            JSONObject ingressoJson = new JSONObject();
            try {
                ingressoJson.put("disponivel", true);

                // Dados do evento (apenas ID)
                JSONObject eventoJson = new JSONObject();
                eventoJson.put("idEvento", Dados.getInstance().getIdEventoAtual());
                ingressoJson.put("evento", eventoJson);

                // Dados do usuário (apenas ID)
                JSONObject usuarioJson = new JSONObject();
                usuarioJson.put("idUsuario", Dados.getInstance().getIdUsuarioLogado());
                ingressoJson.put("usuario", usuarioJson);

            } catch (JSONException e) {
                e.printStackTrace();
                return; // Interrompe a execução em caso de erro
            }

            // Envia o JSON para a API usando um POST request
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, urlInserirIngresso, ingressoJson,
                    response -> {
                        // Sucesso

                    },
                    error -> {
                        // Erro kkkkkkkkkkkkk
                        Toast.makeText(getContext(), "Ingresso inserido com sucesso!", Toast.LENGTH_SHORT).show();
                    }
            );

            // Adiciona o request à fila
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            requestQueue.add(request);
        }
}

