package com.example.simello.controller.punteggi;


/**
 * Created by simello & sunfury on 19/12/14.
 */
public class Utente
{
    private static String nome;
    private static Utente user = null;

//@Todo è da completare la parte dell'user
    public Utente(String nome)
    {

        this.nome = nome;
    }

    public static void setNome(String nome)
    {
        user.nome = nome;
    }

    public static String getNome()
    {
        return nome;
    }

    public static Utente getUser()
    {
        return user;
    }
}
