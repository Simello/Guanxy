package com.example.simello.classiServer;

/**
 * Created by Sunfury on 10/03/15.
 */
import java.math.BigInteger;

public class TakingCareHelpReuqestInput extends BaseInput{

    private String idUser;
    private BigInteger idHelpRequest;
    private double latitude;
    private double longitude;

    public TakingCareHelpReuqestInput(String idUser, BigInteger idHelpRequest, double latitude, double longitude){
        this.idUser = idUser;
        this.idHelpRequest = idHelpRequest;
        this.latitude = latitude;
        this.longitude = longitude;
    };

    public String getIdUser() {
        return idUser;
    }
    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
    public BigInteger getIdHelpRequest() {
        return idHelpRequest;
    }
    public void setIdHelpRequest(BigInteger idHelpRequest) {
        this.idHelpRequest = idHelpRequest;
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
