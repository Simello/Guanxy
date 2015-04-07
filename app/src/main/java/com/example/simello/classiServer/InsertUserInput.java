package com.example.simello.classiServer;

/**
 * Created by Sunfury on 10/03/15.
 */
public class InsertUserInput extends BaseInput {

    private static final long serialVersionUID = 1996672327971317596L;

    private String idUser;
    private String nickname;
    private double latitude;
    private double longitude;
    private String prefix;

    public InsertUserInput() {}

    public InsertUserInput(String idUser, String prefix , String nickname, double latitude, double longitude) {
        this.idUser = idUser;
        this.prefix = prefix;
        this.nickname = nickname;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser( String idUser ) {
        this.idUser = idUser;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix( String prefix ) {
        this.prefix = prefix;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude( Double latitude ) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude( Double longitude ) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "InsertUserInput [idUser=" + idUser + ", nickname=" + nickname + ", prefix=" + prefix + ", latitude=" + latitude + ", longitude=" + longitude + "]";
    }
}