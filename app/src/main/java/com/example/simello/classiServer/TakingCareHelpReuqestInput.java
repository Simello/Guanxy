package com.example.simello.classiServer;

/**
 * Created by Sunfury on 10/03/15.
 */
import java.math.BigInteger;

public class TakingCareHelpReuqestInput extends BaseInput{

    private String idUser;
    private BigInteger idHelpRequest;
    private double latitute;
    private double longitude;

    public TakingCareHelpReuqestInput(String idUser, BigInteger idHelpRequest, double latitute, double longitude){
        this.idUser = idUser;
        this.idHelpRequest = idHelpRequest;
        this.latitute = latitute;
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

    public double getLatitute() {
        return latitute;
    }
    public void setLatitute(double latitute) {
        this.latitute = latitute;
    }
    public double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }




}
