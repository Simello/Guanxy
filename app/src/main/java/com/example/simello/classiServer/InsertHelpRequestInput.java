package com.example.simello.classiServer;

/**
 * Created by Sunfury on 10/03/15.
 */
public final class InsertHelpRequestInput extends BaseInput {

    private static final long serialVersionUID = -2794341848919295083L;

    private String idUser;
    private double latitude;
    private double longitude;
    private String message;

    public InsertHelpRequestInput(){};

    public InsertHelpRequestInput(String userId, double latitude, double longitude, String message) {
        this.idUser = userId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.message = message;
    }

    public String getIdUser() {
        return idUser;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "InsertHelpRequestInput [idUser=" + idUser + ", latitude=" + latitude + ", longitude=" + longitude + ", message=" + message + "]";
    }
}
