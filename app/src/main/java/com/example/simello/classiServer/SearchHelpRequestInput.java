package com.example.simello.classiServer;

/**
 * Created by Sunfury e Super simello on 10/03/15.
 */
public class SearchHelpRequestInput extends BaseInput {

    private String idUser;
    private long latitude;
    private long longitude;

    public SearchHelpRequestInput(){};

    public SearchHelpRequestInput(String idUser, long latitude, long longitude)
    {
        this.idUser = idUser;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getIdUser() {
        return idUser;
    }
    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
    public long getLatitude() {
        return latitude;
    }
    public void setLatitude(long latitude) {
        this.latitude = latitude;
    }
    public long getLongitude() {
        return longitude;
    }
    public void setLongitude(long longitude) {
        this.longitude = longitude;
    }



}
