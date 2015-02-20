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
    static Context context = null;


    /**
     * Costruttore nascosto per il singleton
     * @param nome
     * @param cnt
     */
    //@Todo è da completare la parte dell'user
    private Utente(String nome, Context cnt)
    {
        this.nome = nome;
        this.context = cnt;

    }

    /**
     * Ritorna l'unica istanza di Utente
     * @param nome Nome dell'utente
     * @param cnt Context per poter utilizzare le Shared Prreferences
     * @return Unica istanza di Utente
     */
    public static Utente getIstance(String nome, Context cnt)
    {
        if(user == null)
        {
            user = new Utente(nome, cnt);
        }
        return user;
    }

    /**
     * Setta il nome dell'utente anche nelle Shared Preferences
     * @param nome nome utente
     */
    public static void setNome(String nome)
    {


        SharedPreferences prefs = context.getSharedPreferences(
                "com.example.app", Context.MODE_PRIVATE);
        SharedPreferences.Editor  editor = prefs.edit();
        editor.putString("username", nome);
        Utente.nome = nome;
        editor.apply();

    }

    /**
     * Ritorna il nome dell'utente
     * @return nome utente
     */
    public static String getNome()
    {
        return Utente.nome;
    }

    /**
     * Ritorna l'istanza dell'user
     * @return istanza user
     */
    public static Utente getUser()
    {
        return user;
    }
}
