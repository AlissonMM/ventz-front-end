package com.example.ventz.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ventz.R;

import java.text.SimpleDateFormat;
import java.util.List;

public class EventoAdapter extends ArrayAdapter<Evento> {
    private final List<Evento> eventos;
    private final Context context;

    public EventoAdapter(Context context, List<Evento> eventos) {
        super(context, 0, eventos);
        this.eventos = eventos;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.item_evento, parent, false); // Usando o layout correto
        }

        Evento evento = getItem(position);

        if (evento != null) {
            TextView nomeEvento = view.findViewById(R.id.nomeEvento);
            TextView lugarEvento = view.findViewById(R.id.lugarEvento);
            TextView dataInicio = view.findViewById(R.id.dataInicio);
            TextView dataTermino = view.findViewById(R.id.dataTermino);

            // Configurando o nome do evento
            nomeEvento.setText(evento.getTituloEvento());

            // Configurando o lugar do evento
            lugarEvento.setText("Endereço: " + evento.getEndereco());

            // Formatando as datas de início e término
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            String dataInicioFormatted = dateFormat.format(evento.getDataInicio());
            String dataTerminoFormatted = dateFormat.format(evento.getDataTermino());

            // Exibindo as datas
            dataInicio.setText("Início: " + dataInicioFormatted);
            dataTermino.setText("Término: " + dataTerminoFormatted);
        }

        return view;
    }
}
