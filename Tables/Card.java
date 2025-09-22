package com.mercatopokemon.Tables;

import javafx.beans.property.SimpleStringProperty;

public class Card {
    private final SimpleStringProperty nome;
    private final SimpleStringProperty codesp;
    private final SimpleStringProperty numero;
    private final SimpleStringProperty codL;
    private final SimpleStringProperty nomeE;

    public Card(String nome, String codesp, String numero, String codL, String nomeE) {
        this.nome = new SimpleStringProperty(nome);
        this.codesp = new SimpleStringProperty(codesp);
        this.numero = new SimpleStringProperty(numero);
        this.codL = new SimpleStringProperty(codL);
        this.nomeE = new SimpleStringProperty(nomeE);
    }

    public String getNomeE() {
        return nomeE.get();
    }

    public void setNomeE(String v) {
        nomeE.set(v);
    }

    public String getNome() {
        return nome.get();
    }
    public void setNome(String v) {
        nome.set(v);
    }

    public String getCodesp() {
        return codesp.get();
    }
    public void setCodesp(String v) {
        codesp.set(v);
    }

    public String getNumero() {
        return numero.get();
    }
    public void setNumero(String v) {
        numero.set(v);
    }

    public String getCodL() {
        return codL.get();
    }
    public void setCodL(String v) {
        codL.set(v);
    }
}

