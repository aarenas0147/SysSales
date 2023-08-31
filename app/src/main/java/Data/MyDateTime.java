package Data;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import net.sourceforge.jtds.jdbc.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Alan on 02/01/2021.
 */
public class MyDateTime {

    public static final String TYPE_DATE = "dd/MM/yyyy";
    public static final String TYPE_DATE_2 = "dd-MM-yyyy";
    public static final String TYPE_TIME_LONG = "HH:mm:ss";
    public static final String TYPE_TIME_SHORT = "hh:mm a";
    public static final String TYPE_DATETIME = "dd/MM/yyyy HH:mm:ss";
    public static final String TYPE_DATETIME_2 = "YYYY-MM-dd HH:mm:ss";

    public static Date parse(String dateString, String format)
    {
        Date date = null;
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String format(Date date, String format)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.ROOT);
        return dateFormat.format(date);
    }

    /*public static String format(Date date, String format)
    {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format, Locale.ROOT);
            LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            return localDate.format(formatter);
        }
        else
        {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.ROOT);
            return dateFormat.format(date);
        }
    }*/

    public static Date parseNet(String netDatetime)
    {
        netDatetime = netDatetime.replaceAll("[^0-9]", "");
        Date result = null;
        if (!TextUtils.isEmpty(netDatetime)) {
            result = new Date(Long.parseLong(netDatetime));
        }
        return result;
    }

    public static Date getCurrentDatetime()
    {
        return Calendar.getInstance().getTime();
    }

    public static Date toLocalTimeZone(Date date)   //Peru
    {
        TimeZone timeZone = TimeZone.getTimeZone("GMT-5");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat(TYPE_DATETIME);
        dateFormat.setTimeZone(timeZone);

        return parse(dateFormat.format(date), TYPE_DATETIME);
    }
}
