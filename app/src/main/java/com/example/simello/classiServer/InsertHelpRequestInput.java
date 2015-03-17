package com.example.simello.classiServer;

/**
 * Created by Sunfury on 10/03/15.
 */
public final class InsertHelpRequestInput extends BaseInput {

    private static final long serialVersionUID = -2794341848919295083L;

    private String userId;
    private double latitude;
    private double longitude;
    private String message;

    public InsertHelpRequestInput(){};

    public InsertHelpRequestInput(String userId, double latitude, double longitude, String message) {
        this.userId = userId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.message = message;
    }

    public String getUserId() {
        return userId;
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
}
