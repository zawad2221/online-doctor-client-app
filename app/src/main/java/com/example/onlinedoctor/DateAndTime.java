package com.example.onlinedoctor;

import android.os.Build;

import androidx.annotation.RequiresApi;

import org.intellij.lang.annotations.JdkConstants;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateAndTime {

    public enum DAYS_OF_WEEK{
        SATURDAY,
        SUNDAY,
        MONDAY,
        TUESDAY,
        WEDNESDAY,
        THURSDAY,
        FRIDAY

    }


    public static String convert24to12(String str)
    {
        String time ="";
// Get Hours
        int h1 = (int)str.charAt(0) - '0';
        int h2 = (int)str.charAt(1)- '0';

        int hh = h1 * 10 + h2;


        // Finding out the Meridien of time
        // ie. AM or PM
        String Meridien;
        if (hh < 12) {
            Meridien = "AM";
        }
        else
            Meridien = "PM";

        hh %= 12;

        // Handle 00 and 12 case separately
        if (hh == 0) {
            time = "12";

            // Printing minutes and seconds
            for (int i = 2; i < 5; ++i) {
                time = time+ (str.charAt(i));
            }
        }
        else {
            time = time+ (hh);
            // Printing minutes and seconds
            for (int i = 2; i < 5; ++i) {
                time = time+ (str.charAt(i));
            }
        }

        // After time is printed
        // cout Meridien
        return (time+" "+Meridien);
    }
    public static String convert12to24(String input)
            throws ParseException
    {

        // Format of the date defined in the input String
        DateFormat dateFormat
                = new SimpleDateFormat("hh:mm:ss aa");

        // Change the pattern into 24 hour formate
        DateFormat formate
                = new SimpleDateFormat("HH:mm:ss");
        Date time = null;
        String output = "";

        // Converting the input String to Date
        time = dateFormat.parse(input);

        // Changing the format of date
        // and storing it in
        // String
        output = formate.format(time);
        return output;
    }
    public static boolean isTimeOneGreaterThanTimeTwo(String time1, String time2){
        SimpleDateFormat parser = new SimpleDateFormat("HH:mm:ss");
        Date date1, date2;
        try {
            date1 = parser.parse(time1 );
            date2 = parser.parse(time2 );
            if(date1.after(date2)){
                return true;
            }
            else {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }


    }
    public static String getLocalTime(){
        return new SimpleDateFormat("HH:mm:ss").format(new GregorianCalendar().getTime());
    }
    public static String getLocalDate(){
        return new SimpleDateFormat("yyyy-MM-dd").format(new GregorianCalendar().getTime());
    }


    public static String getDateOfNextDayOfWeek(DateAndTime.DAYS_OF_WEEK days_of_week){
        int daysOfWeek=0;
        daysOfWeek=getDaysOfWeek(days_of_week);
        int diff = 0;
        Calendar date = Calendar.getInstance();

        diff = daysOfWeek - date.get(Calendar.DAY_OF_WEEK);
        System.out.println("diff: "+diff);
        if (diff <= 0) {
            diff += 7;
        }

        date.add(Calendar.DAY_OF_MONTH, diff);
        String d = String.format( "%tY-%<tm-%<te", date);
        return d;
    }

    private static int getDaysOfWeek(DateAndTime.DAYS_OF_WEEK days_of_week){
        int daysOfWeek=0;
        switch (days_of_week){
            case SATURDAY:
                daysOfWeek = Calendar.SATURDAY;
                break;
            case SUNDAY:
                daysOfWeek = Calendar.SUNDAY;
                break;
            case MONDAY:
                daysOfWeek = Calendar.MONDAY;
                break;
            case TUESDAY:
                daysOfWeek = Calendar.TUESDAY;
                break;
            case WEDNESDAY:
                daysOfWeek = Calendar.WEDNESDAY;
                break;
            case THURSDAY:
                daysOfWeek = Calendar.THURSDAY;
                break;
            case FRIDAY:
                daysOfWeek = Calendar.FRIDAY;
                break;

        }
        return daysOfWeek;

    }
    public static DateAndTime.DAYS_OF_WEEK getDayOfWeekOnString(String day){
        switch (day){
            case "saturday":
                return DateAndTime.DAYS_OF_WEEK.SATURDAY;
            case "sunday":
                return DateAndTime.DAYS_OF_WEEK.SUNDAY;
            case "monday":
                return DateAndTime.DAYS_OF_WEEK.MONDAY;
            case "tuesday":
                return DateAndTime.DAYS_OF_WEEK.TUESDAY;
            case "wednesday":
                return DateAndTime.DAYS_OF_WEEK.WEDNESDAY;
            case "thursday":
                return DateAndTime.DAYS_OF_WEEK.THURSDAY;
            case "friday":
                return DateAndTime.DAYS_OF_WEEK.FRIDAY;
            default: return null;

        }
    }

    public static boolean isDaysOfWeekIsToday(DateAndTime.DAYS_OF_WEEK days_of_week){
        int daysOfWeek = getDaysOfWeek(days_of_week);
        if(daysOfWeek==Calendar.getInstance().get(Calendar.DAY_OF_WEEK)){
            return true;
        }
        else {
            return false;
        }

    }

}
