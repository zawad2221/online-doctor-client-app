package com.example.onlinedoctor;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import org.intellij.lang.annotations.JdkConstants;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
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
        Log.d("DEBUGING_TAG","time: "+str);
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
                Log.d("DEBUGING_TAG","time: "+(str.charAt(i)));
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
    public static String getAgeFromDateOfBirth(String dbo)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date birthDate = null;
        try {
            birthDate = sdf.parse(dbo);
        }
        catch (Exception e){

        }


        int years = 0;
        int months = 0;
        int days = 0;

        //create calendar object for birth day
        Calendar birthDay = Calendar.getInstance();
        birthDay.setTimeInMillis(birthDate.getTime());

        //create calendar object for current day
        long currentTime = System.currentTimeMillis();
        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(currentTime);

        //Get difference between years
        years = now.get(Calendar.YEAR) - birthDay.get(Calendar.YEAR);
        int currMonth = now.get(Calendar.MONTH) + 1;
        int birthMonth = birthDay.get(Calendar.MONTH) + 1;

        //Get difference between months
        months = currMonth - birthMonth;

        //if month difference is in negative then reduce years by one
        //and calculate the number of months.
        if (months < 0)
        {
            years--;
            months = 12 - birthMonth + currMonth;
            if (now.get(Calendar.DATE) < birthDay.get(Calendar.DATE))
                months--;
        } else if (months == 0 && now.get(Calendar.DATE) < birthDay.get(Calendar.DATE))
        {
            years--;
            months = 11;
        }

        //Calculate the days
        if (now.get(Calendar.DATE) > birthDay.get(Calendar.DATE))
            days = now.get(Calendar.DATE) - birthDay.get(Calendar.DATE);
        else if (now.get(Calendar.DATE) < birthDay.get(Calendar.DATE))
        {
            int today = now.get(Calendar.DAY_OF_MONTH);
            now.add(Calendar.MONTH, -1);
            days = now.getActualMaximum(Calendar.DAY_OF_MONTH) - birthDay.get(Calendar.DAY_OF_MONTH) + today;
        }
        else
        {
            days = 0;
            if (months == 12)
            {
                years++;
                months = 0;
            }
        }

        return String.format("Year: %s, Month: %s, Day: %s",years, months, days);
    }

}
