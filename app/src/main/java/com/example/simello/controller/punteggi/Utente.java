package com.example.simello.controller.punteggi;

/**
 * Created by simello & sunfury on 19/12/14.
 */
public class Utente
{
    private String nome;

    public Utente(String nome)
    {
        setNome(nome);
    }


    public String getNome() {
        return nome;
    }

    private void setNome(String nome) {
        this.nome = nome;
    }
}
