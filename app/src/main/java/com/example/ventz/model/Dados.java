package com.example.ventz.model;

import java.util.Date;

public class Dados {
    private static Dados instance;
    private String url;
    private int idUsuarioLogado;
    private int idEventoAtual;
    private String tituloEventoAtual;
    private String emailAtual;
    private String cpfAtual;
    private String nomeAtual;
    private String senhaAtual;
    private String enderecoAtual;
    private String descricaoAtual;
    private Date dataInicioAtual;
    private Date dataTerminoAtual;
    private int idIngressoAtual;


    private Dados() {

    }

    // Ele instancia Dados se necessário
    public static synchronized Dados getInstance() {
        if (instance == null) {
            instance = new Dados();
        }
        return instance;
    }

    public static void setInstance(Dados instance) {
        Dados.instance = instance;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getIdUsuarioLogado() {
        return idUsuarioLogado;
    }

    public void setIdUsuarioLogado(int idUsuarioLogado) {
        this.idUsuarioLogado = idUsuarioLogado;
    }

    public int getIdEventoAtual() {
        return idEventoAtual;
    }

    public void setIdEventoAtual(int idEventoAtual) {
        this.idEventoAtual = idEventoAtual;
    }

    public String getTituloEventoAtual() {
        return tituloEventoAtual;
    }

    public void setTituloEventoAtual(String tituloEventoAtual) {
        this.tituloEventoAtual = tituloEventoAtual;
    }

    public String getEmailAtual() {
        return emailAtual;
    }

    public void setEmailAtual(String emailAtual) {
        this.emailAtual = emailAtual;
    }

    public String getCpfAtual() {
        return cpfAtual;
    }

    public void setCpfAtual(String cpfAtual) {
        this.cpfAtual = cpfAtual;
    }

    public String getNomeAtual() {
        return nomeAtual;
    }

    public void setNomeAtual(String nomeAtual) {
        this.nomeAtual = nomeAtual;
    }

    public String getSenhaAtual() {
        return senhaAtual;
    }

    public void setSenhaAtual(String senhaAtual) {
        this.senhaAtual = senhaAtual;
    }

    public String getEnderecoAtual() {
        return enderecoAtual;
    }

    public void setEnderecoAtual(String enderecoAtual) {
        this.enderecoAtual = enderecoAtual;
    }

    public String getDescricaoAtual() {
        return descricaoAtual;
    }

    public void setDescricaoAtual(String descricaoAtual) {
        this.descricaoAtual = descricaoAtual;
    }

    public Date getDataInicioAtual() {
        return dataInicioAtual;
    }

    public void setDataInicioAtual(Date dataInicioAtual) {
        this.dataInicioAtual = dataInicioAtual;
    }

    public Date getDataTerminoAtual() {
        return dataTerminoAtual;
    }

    public void setDataTerminoAtual(Date dataTerminoAtual) {
        this.dataTerminoAtual = dataTerminoAtual;
    }

    public int getIdIngressoAtual() {
        return idIngressoAtual;
    }

    public void setIdIngressoAtual(int idIngressoAtual) {
        this.idIngressoAtual = idIngressoAtual;
    }
}

