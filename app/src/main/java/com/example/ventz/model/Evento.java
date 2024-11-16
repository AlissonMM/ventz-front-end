package com.example.ventz.model;

import java.util.Date;

public class Evento {

    private int idEvento;
    private String tituloEvento;
    private String descricao;
    private int limitePessoas;
    private Date dataInicio;
    private Date dataTermino;
    private String endereco;
    private int ingressosUtilizados;

    // Construtor sem o idEvento
    public Evento(String tituloEvento, String descricao, int limitePessoas, Date dataInicio, Date dataTermino, String endereco, int ingressosUtilizados) {
        this.tituloEvento = tituloEvento;
        this.descricao = descricao;
        this.limitePessoas = limitePessoas;
        this.dataInicio = dataInicio;
        this.dataTermino = dataTermino;
        this.endereco = endereco;
        this.ingressosUtilizados = ingressosUtilizados;
    }

    // Construtor com o idEvento
    public Evento(int idEvento, String tituloEvento, String descricao, int limitePessoas, Date dataInicio, Date dataTermino, String endereco, int ingressosUtilizados) {
        this.idEvento = idEvento;
        this.tituloEvento = tituloEvento;
        this.descricao = descricao;
        this.limitePessoas = limitePessoas;
        this.dataInicio = dataInicio;
        this.dataTermino = dataTermino;
        this.endereco = endereco;
        this.ingressosUtilizados = ingressosUtilizados;
    }
    public Evento(int idEvento, String tituloEvento, String descricao, int limitePessoas, Date dataInicio, Date dataTermino, String endereco) {
        this.idEvento = idEvento;
        this.tituloEvento = tituloEvento;
        this.descricao = descricao;
        this.limitePessoas = limitePessoas;
        this.dataInicio = dataInicio;
        this.dataTermino = dataTermino;
        this.endereco = endereco;
    }

    // Getters e Setters
    public int getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(int idEvento) {
        this.idEvento = idEvento;
    }

    public String getTituloEvento() {
        return tituloEvento;
    }

    public void setTituloEvento(String tituloEvento) {
        this.tituloEvento = tituloEvento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getLimitePessoas() {
        return limitePessoas;
    }

    public void setLimitePessoas(int limitePessoas) {
        this.limitePessoas = limitePessoas;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataTermino() {
        return dataTermino;
    }

    public void setDataTermino(Date dataTermino) {
        this.dataTermino = dataTermino;
    }

    public  String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public int getIngressosUtilizados() {
        return ingressosUtilizados;
    }

    public void setIngressosUtilizados(int ingressosUtilizados) {
        this.ingressosUtilizados = ingressosUtilizados;
    }

    // Metodo toString
    @Override
    public String toString() {
        return "Evento{" +
                "idEvento=" + idEvento +
                ", tituloEvento='" + tituloEvento + '\'' +
                ", descricao='" + descricao + '\'' +
                ", limitePessoas=" + limitePessoas +
                ", dataInicio=" + dataInicio +
                ", dataTermino=" + dataTermino +
                ", endereco='" + endereco + '\'' +
                ", ingressosUtilizados=" + ingressosUtilizados +
                '}';
    }
}
