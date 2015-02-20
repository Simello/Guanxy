package com.example.simello.controller.punteggi;


import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.simello.guanxy.GuanxyActivity;

/**
 * Created by simello & sunfury on 19/12/14.
 */
public class Utente
{
    private static String nome;
    private static Utente user = null;


    //@Todo è da completare la parte dell'user
    private Utente(String nome)
    {
        this.nome = nome;

    }

    public static Utente getIstance(String nome)
    {
        if(user == null)
        {
            user = new Utente(nome);
        }
        return user;
    }

    public static void setNome(String nome)
    {

        Utente.nome = nome;

    }

    public static String getNome()
    {
        return Utente.nome;
    }

    public static Utente getUser()
    {
        return user;
    }
}
