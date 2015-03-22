package com.example.simello.classiServer;

/**
 * Created by Sunfury on 10/03/15.
 */
public class InsertUserInput extends BaseInput {

    private static final long serialVersionUID = 1996672327971317596L;

    private String userId;
    private String nickname;
    private double latitude;
    private double longitude;

    public InsertUserInput() {}

    public InsertUserInput(String userId, String nickname, double latitude, double longitude) {
        this.userId = userId;
        this.nickname = nickname;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}