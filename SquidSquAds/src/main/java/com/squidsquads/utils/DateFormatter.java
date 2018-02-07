package com.squidsquads.utils;

import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Component
public class DateFormatter {
    private static DateFormat format = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);

    public static String DateToString(Date aDate){
        return format.format(aDate);
    }

    public static Date StringToDate(String dateText) throws ParseException {
        return (Date) format.parse(dateText);
    }
}
