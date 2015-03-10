package com.example.simello.classiServer;

/**
 * Created by Sunfury on 10/03/15.
 */
public final class InsertHelpRequestInput extends BaseInput {

    private static final long serialVersionUID = -2794341848919295083L;

    private String userId;
    private long latitude;
    private long longitude;
    private String message;

    public InsertHelpRequestInput(){};

    public InsertHelpRequestInput(String userId, long latitude, long longitude, String message) {
        this.userId = userId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.message = message;
    }

    public String getUserId() {
        return userId;
    }

    public long getLatitude() {
        return latitude;
    }

    public long getLongitude() {
        return longitude;
    }

    public String getMessage() {
        return message;
    }
}
