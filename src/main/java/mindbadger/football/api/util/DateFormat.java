package mindbadger.football.api.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

//TODO Add test for this class
public class DateFormat {
    private static final SimpleDateFormat fixtureDateFormatter = new SimpleDateFormat("yyyy-MM-dd");

    public static String toString(Date date) {
        if (date == null) {
            throw new IllegalArgumentException("Can't convert null date to a string");
        }
        return fixtureDateFormatter.format(date);
    }

    public static String toString(Calendar calendar) {
        if (calendar == null) {
            throw new IllegalArgumentException("Can't convert null calendar to a string");
        }
        return fixtureDateFormatter.format(calendar.getTime());
    }

    public static Calendar toCalendar (String dateString) {
        try {
            Date fixtureDateDate = fixtureDateFormatter.parse(dateString);
            Calendar cal = Calendar.getInstance();
            cal.setTime(fixtureDateDate);
            return cal;
        } catch (ParseException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("String " + dateString + " cannot be converted to a date");
        }
    }
}
