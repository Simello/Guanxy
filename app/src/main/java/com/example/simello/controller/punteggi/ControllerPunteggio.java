package com.example.simello.controller.punteggi;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by simello & sunfury on 19/12/14.
 */
public class ControllerPunteggio
{
    private boolean dailyCheck;
    private Date contestDateExpiration;
    private Date todayDate;
    private Date lastUpdatePoints;
    private Punteggio punteggio;
    private Utente utente;

    public ControllerPunteggio()
    {

        //Boh ho fatto una prova
        this.utente = new Utente("Sunfury");
    }


    public Date getContestDateExpiration() {
        return contestDateExpiration;
    }

    public void setContestDateExpiration(Date contestDateExpiration) {
        //@todo chiamo script aggiornamento data scadenza contest
    }

    public Date getTodayDate() {
        return todayDate;
    }

    public void setTodayDate(Date todayDate) {
        Calendar c = Calendar.getInstance();
        todayDate = c.getTime();
    }

    public Date getLastUpdatePoints() {
        return lastUpdatePoints;
    }

    public void setLastUpdatePoints(Date lastUpdatePoints) {
        if(lastUpdatePoints == null)//se non è mai stato fatto un aggiornamento
        {
            lastUpdatePoints = todayDate;//l'ultimo aggiornamento lo imposto ad oggi
            //è inutile che chiamo l'aggiornamento punti poiche' se non e' mai
            //stato fatto allora l'utente è nuovo e non ha anncora punti
        }
        else
        {
            if( lastUpdatePoints.after(todayDate))//se ultimo aggiornamento è prima della data di oggi allora aggiorna
            {
                punteggio.updatePoints();
                lastUpdatePoints=todayDate;
            }
        }
    }
}
//ogni volta che verra cliccato il pulsante punteggi, nel metodo onclick verra' richiamato il metodo dell'aggiornamento del punteggio
//che lo fara' solamente se è passato un giorno dal giorno dell' ultimo aggiornamento