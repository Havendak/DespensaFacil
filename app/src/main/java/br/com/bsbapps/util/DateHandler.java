package br.com.bsbapps.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

    // Retorna uma data em formato UTC, milisegundos apos 1970
    public static long stringToUtc(String data) {//data no formato aaaa-mm-dd
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(data));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return c.getTime().getTime();

    }

    // Retorna uma data em formato no formato aaaa-mm-dd
    public static String utcToString(long data){ //deve ser passada uma data no formato UTC
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date resultdate = new Date(data);
        return sdf.format(resultdate);
    }

    // Retorna o Unix Timestamp
    public int getTimestamp(){
        return (int) System.currentTimeMillis()/1000;
    }

    // Retorna o Unix Timestamp da data
    public int getTimestamp(Date dt){
        return (int) dt.getTime()/1000;
    }

    // Retorna uma data calculada pela soma de dias a partir da data atual
    public Date addDate(int days){
        Calendar c = Calendar.getInstance();
        c.setTime(getDate());
        c.add(Calendar.DATE, days);
        return c.getTime();
    }

    // Retorna uma data calculada pela soma de dias a partir de uma data
    public Date addDate(Date dt, int days){
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, days);
        return c.getTime();
    }
}
