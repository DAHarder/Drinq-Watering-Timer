package com.example.drinq.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Holds and date formatter variable and function
 */
public class DateUtils {

    public static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("EEE, LLL d YYYY", Locale.US);

    public static String formatDate(String date) {
        LocalDate dateParse = LocalDate.parse(date);
        String dateReturn = dateParse.format(dtf);
        return dateReturn;
    }
}
