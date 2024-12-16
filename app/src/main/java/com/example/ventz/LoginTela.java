package com.example.ventz;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ventz.model.Dados;

import org.json.JSONException;


public class LoginTela extends AppCompatActivity {

    private RequestQueue requestQueue;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_login_tela);

       

        EditText txtEmail = findViewById(R.id.txtEmail);
        EditText txtSenha = findViewById(R.id.txtSenha);
        Button btnCadastro = findViewById(R.id.btnTelaCadastro);
        Button btnLogin = findViewById(R.id.btnLogin);

        requestQueue = Volley.newRequestQueue(this);

        Dados.getInstance().setUrl("http://54.94.207.128:8080");

        // Botão para ir para a tela de cadastro
        btnCadastro.setOnClickListener(v -> {
            Intent intent = new Intent(this, CadastroTela.class);
            startActivity(intent);
            finish();
        });

        // Botão de login
        btnLogin.setOnClickListener(v -> {
            String email = txtEmail.getText().toString();
            String senha = txtSenha.getText().toString();

            if (email.isEmpty() || senha.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                return;
            }

            url = Dados.getInstance().getUrl() + "/usuarios/buscarPorEmailESenha";

            // Adiciona as credenciais como parâmetros na URL
            String loginUrl = url + "?email=" + email + "&senha=" + senha;

            // Faz a requisição GET usando JsonObjectRequest para lidar com a resposta JSON
            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.GET,
                    loginUrl,
                    null,
                    response -> {
                        try {
                            // Obtém o ID do usuário do objeto JSON
                            int userId = response.getInt("idUsuario");
                            String nome = response.getString("nome");
                            String emailApi = response.getString("email");
                            String cpfApi = response.getString("cpf");
                            String senhaApi = response.getString("senha");

                            Toast.makeText(LoginTela.this, "Login realizado com sucesso! Nome: " + nome + " ID: " + userId, Toast.LENGTH_SHORT).show();

                            // Salva o ID do usuário em uma instância global ou em SharedPreferences
                            Dados.getInstance().setIdUsuarioLogado(userId);
                            Dados.getInstance().setNomeAtual(nome);
                            Dados.getInstance().setCpfAtual(cpfApi);
                            Dados.getInstance().setEmailAtual(emailApi);
                            Dados.getInstance().setSenhaAtual(senhaApi);

                            Intent intent = new Intent(LoginTela.this, MainActivity.class); // Substitua por sua próxima tela
                            startActivity(intent);
                            finish();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(LoginTela.this, "Erro ao processar resposta JSON", Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> {
                        // Caso de erro (credenciais inválidas ou outra falha)
                        if (error.networkResponse != null && error.networkResponse.statusCode == 400) {
                            Toast.makeText(LoginTela.this, "Credenciais inválidas", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginTela.this, "Erro de conexão", Toast.LENGTH_SHORT).show();
                        }
                    }
            );

            // Adiciona a requisição à fila
            requestQueue.add(request);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }
}