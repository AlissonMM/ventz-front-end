package com.example.ventz.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ventz.R;

import java.util.List;

public class IngressoAdapter extends ArrayAdapter<Ingresso> {
    private final List<Ingresso> ingressos;
    private final Context context;

    public IngressoAdapter(Context context, List<Ingresso> ingressos) {
        super(context, 0, ingressos);
        this.ingressos = ingressos;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.item_ingresso, parent, false); // Usando o layout do item de ingresso
        }

        Ingresso ingresso = getItem(position);

        if (ingresso != null) {
            TextView eventoId = view.findViewById(R.id.eventoId);
            TextView usuarioId = view.findViewById(R.id.usuarioId);
            TextView disponibilidade = view.findViewById(R.id.disponibilidade);

            // Exibindo o ID do evento e do usuário
            eventoId.setText("Ingresso paro o Evento ID: " + String.valueOf(ingresso.getEvento()));
            usuarioId.setText("Usuário ID: " + String.valueOf(ingresso.getUsuario()));

            // Exibindo a disponibilidade
            if (ingresso.getDisponivel()) {
                disponibilidade.setText("Disponibilidade: Disponível");
            } else {
                disponibilidade.setText("Disponibilidade: Indisponível");
            }
        }

        return view;
    }
}
