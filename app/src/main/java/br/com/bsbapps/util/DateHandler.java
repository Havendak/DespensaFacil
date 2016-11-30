package br.com.bsbapps.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Andre Becklas on 30/11/2016.
 *
 * Classe que contém funções de data
 */

public class DateHandler {

    public DateHandler(){
    }

    // Retorna data atual
    public Date getDate(){
        return new Date();
    }

    // Retorna uma data de uma String
    public Date getDate(String formato, String dt){
        SimpleDateFormat sdf = new SimpleDateFormat(formato, Locale.US);
        try {
            return sdf.parse(dt);
        }
        catch (ParseException e) {
            return null;
        }
    }

    // Retorna data atual em formato String
    public String getStringDate(String formato){
        SimpleDateFormat sdf = new SimpleDateFormat(formato, Locale.US);
        return sdf.format(new Date());
    }

    // Retorna uma data em formato String
    public String getStringDate(String formato, Date date){
        SimpleDateFormat sdf = new SimpleDateFormat(formato, Locale.US);
        return sdf.format(date);
    }
}
