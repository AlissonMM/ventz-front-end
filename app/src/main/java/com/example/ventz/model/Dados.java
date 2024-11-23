package com.example.ventz.model;

public class Dados {
    private static Dados instance;
    private String url;
    private int idUsuarioLogado;
    private int idEventoAtual;
    private String nomeEventoAtual;
    private String emailAtual;
    private String cpfAtual;
    private String nomeAtual;
    private String senhaAtual;

    private int idIngressoAtual;

    private Dados() { }

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

    public String getNomeEventoAtual() {
        return nomeEventoAtual;
    }

    public void setNomeEventoAtual(String nomeEventoAtual) {
        this.nomeEventoAtual = nomeEventoAtual;
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

    public int getIdIngressoAtual() {
        return idIngressoAtual;
    }

    public void setIdIngressoAtual(int idIngressoAtual) {
        this.idIngressoAtual = idIngressoAtual;
    }
}