package com.example.simello.classiServer;

/**
 * Created by Sunfury on 10/03/15.
 */
public class InsertUserInput extends BaseInput {

    private static final long serialVersionUID = 1996672327971317596L;

    private String userId;
    private String nickname;
    private long latitude;
    private long longitude;

    public InsertUserInput() {}

    public InsertUserInput(String userId, String nickname, long latitude, long longitude) {
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