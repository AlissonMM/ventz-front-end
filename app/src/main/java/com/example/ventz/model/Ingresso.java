package com.example.ventz.model;

public class Ingresso {

    private int idIngresso;
    private int evento;
    private int usuario;
    private Boolean disponivel;

    // Construtor sem o idIngresso
    public Ingresso(int evento, int usuario, Boolean disponivel) {
        this.evento = evento;
        this.usuario = usuario;
        this.disponivel = disponivel;
    }

    // Construtor com o idIngresso
    public Ingresso(int idIngresso, int evento, int usuario, Boolean disponivel) {
        this.idIngresso = idIngresso;
        this.evento = evento;
        this.usuario = usuario;
        this.disponivel = disponivel;
    }

    // Getters e Setters
    public int getIdIngresso() {
        return idIngresso;
    }

    public void setIdIngresso(int idIngresso) {
        this.idIngresso = idIngresso;
    }

    public int getEvento() {
        return evento;
    }

    public void setEvento(int evento) {
        this.evento = evento;
    }

    public int getUsuario() {
        return usuario;
    }

    public void setUsuario(int usuario) {
        this.usuario = usuario;
    }

    public Boolean getDisponivel() {
        return disponivel;
    }

    public void setDisponivel(Boolean disponivel) {
        this.disponivel = disponivel;
    }

    // Método toString
    @Override
    public String toString() {
        return "Ingresso{" +
                "idIngresso=" + idIngresso +
                ", evento=" + evento +
                ", usuario=" + usuario +
                ", disponivel=" + disponivel +
                '}';
    }
}
