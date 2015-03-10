package com.example.simello.classiServer;

/**
 * Created by Sunfury on 10/03/15.
 */
import java.math.BigInteger;

public class TakingCareHelpReuqestInput extends BaseInput{

    private String idUser;
    private BigInteger idHelpRequest;
    private long latitute;
    private long longitude;

    public TakingCareHelpReuqestInput(){};

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

    public long getLatitute() {
        return latitute;
    }
    public void setLatitute(long latitute) {
        this.latitute = latitute;
    }
    public long getLongitude() {
        return longitude;
    }
    public void setLongitude(long longitude) {
        this.longitude = longitude;
    }




}
