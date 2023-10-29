package es.upm.miw.bantumi.util;

import androidx.room.TypeConverter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @TypeConverter
    public static Date toDate(String dateString) {
        try {
            return dateString == null ? null : dateFormat.parse(dateString);
        } catch (Exception e) {
            return null;
        }
    }

    @TypeConverter
    public static String toDateString(Date date) {
        return date == null ? null : dateFormat.format(date);
    }
}

