package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by proca on 28/11/2016.
 */

public class DT {
    public static long stringToUTC(String data){//data no formato aaaa-mm-dd

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(data));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return c.getTime().getTime();

    }

    public static String UTCToString(long data){//data no formato aaaa-mm-dd
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date resultdate = new Date(data);
        return sdf.format(resultdate);
    }

}
