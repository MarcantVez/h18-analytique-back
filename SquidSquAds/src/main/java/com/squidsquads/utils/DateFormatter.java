package com.squidsquads.utils;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Lazy
public class DateFormatter {

    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static DateFormat timeFormat = new SimpleDateFormat("HH:mm");
    private static final Map<Integer, String> monthsOfYear;
    private static final Map<Integer, String> daysOfWeek;
    static {
        Map<Integer, String> unmodMonthMap = new HashMap<>();
        Map<Integer, String> unmodDayMap = new HashMap<>();

        unmodMonthMap.put(1, "Janvier");
        unmodMonthMap.put(2, "Février");
        unmodMonthMap.put(3, "Mars");
        unmodMonthMap.put(4, "Avril");
        unmodMonthMap.put(5, "Mai");
        unmodMonthMap.put(6, "Juin");
        unmodMonthMap.put(7, "Juillet");
        unmodMonthMap.put(8, "Août");
        unmodMonthMap.put(9, "Septembre");
        unmodMonthMap.put(10, "Octobre");
        unmodMonthMap.put(11, "Novembre");
        unmodMonthMap.put(12, "Décembre");
        unmodDayMap.put(0, "Dimanche");
        unmodDayMap.put(1, "Lundi");
        unmodDayMap.put(2, "Mardi");
        unmodDayMap.put(3, "Mercredi");
        unmodDayMap.put(4, "Jeudi");
        unmodDayMap.put(5, "Vendredi");
        unmodDayMap.put(6, "Samedi");
        monthsOfYear = Collections.unmodifiableMap(unmodMonthMap);
        daysOfWeek = Collections.unmodifiableMap(unmodDayMap);
    }

    public static String DateToString(Date aDate) {
        return dateFormat.format(aDate);
    }

    public static Date StringToDate(String dateText) {
        try {
            return dateFormat.parse(dateText);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String getHourFromDate(Date aDate){
        return timeFormat.format(aDate);
    }

    public static String getMonthStringFromIndex(Integer monthOfYear){
        return monthsOfYear.get(monthOfYear);
    }

    public static String getDayOfWeekStringFromIndex(Integer dayOfWeek){
        return daysOfWeek.get(dayOfWeek);
    }
}
