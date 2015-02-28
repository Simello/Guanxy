package com.example.simello.controller.varie;


import android.content.Context;
import android.content.SharedPreferences;

import java.io.Serializable;
import java.util.List;

/**
 * Created by simello & sunfury on 19/12/14.
 */
public class User implements Serializable
{
    private String nickname;
    private static User user = null;
    static Context context = null;
    private String idUser = null;
    private int point;
    private String pin = null;
    private List<Position> positions;


    //@Todo è da completare la parte dell'user
    private User(String nickname, Context cnt, String idUser,int point, List<Position> positions)
    {
        this.nickname = nickname;
        this.context = cnt;
        this.idUser= idUser;
        this.point = point;
        this.positions = positions;

    }

    /**
     * Ritorna l'unica istanza di Utente
     * @param idUser Telefono dell'utente
     * @param nickname Nickname dell'utente
     * @param cnt Context per poter utilizzare le Shared Prreferences
     * @param point Punti dell'utente
     * @return Unica istanza di Utente
     */
    public static User getIstance(String nickname, Context cnt,String idUser, int point, List<Position> positions)
    {
        if(user == null)
        {
            user = new User(nickname, cnt, idUser, point, positions);
        }
        return user;
    }


    /**
     * Setta il nome dell'utente anche nelle Shared Preferences
     * @param nickname nome utente
     */
    public void setNickname(String nickname)
    {


        SharedPreferences prefs = context.getSharedPreferences(
                "com.example.app", Context.MODE_PRIVATE);
        SharedPreferences.Editor  editor = prefs.edit();
        editor.putString("nickname", nickname);
        this.nickname = nickname;
        editor.apply();

    }

    /**
     * Ritorna il nome dell'utente
     * @return nome utente
     */
    public String getNickname()
    {
        return nickname;
    }

    /**
     * Ritona il numero di telefono dell'utente
     * @return Numero di telefono dell'utente
     */
    public String getIdUser() {
        return idUser;
    }

    /**
     * Setta il numero di telefono
     * @param idUser Numero di telefono
     */
    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    /**
     * Ritorna il valore dei punti dell'utente
     * @return Punti dell'utente
     */
    public int getPoint() {
        return point;
    }

    /**
     * Setta i punti dell'utente
     * @param point Punti dell'utente
     */
    public void setPoint(int point) {
        this.point = point;
    }

    /**
     * Ritorna il Pin inviato dal gestore SMS
     * @return Pin SMS
     */
    public String getPin() {
        return pin;
    }

    /**
     * Setta il Pin inviato dal gestore SMS
     * @param pin
     */
    public void setPin(String pin) {
        this.pin = pin;
    }

    public List<Position> getPositions() {
        return positions;
    }

    public void setPositions(List<Position> positions) {
        this.positions = positions;
    }

    public static User getUser()
    {
        return user;
    }

    public Position lcmastPosition()
    {
        return positions.get(0);
    }
}
