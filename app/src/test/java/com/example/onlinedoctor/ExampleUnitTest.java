package com.example.onlinedoctor;



import org.junit.Test;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
//    @Test
//    public void addition_isCorrect() {
//        assertEquals(4, 2 + 2);
//
//        System.out.println("is greater "+DateAndTime.isTimeOneGreaterThanTimeTwo(
//                DateAndTime.getLocalTime(),
//                "22:04:00"
//        ));
//
//        String input_date="01/08/2012";
//        SimpleDateFormat format1=new SimpleDateFormat("dd/MM/yyyy");
//        Date dt1= null;
//        try {
//            dt1 = format1.parse(input_date);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        DateFormat format2=new SimpleDateFormat("EEEE");
//        String finalDay=format2.format(dt1);
//        System.out.println("final day: "+finalDay);
//
//
//            System.out.printf(
//                    "%ta, %<tb %<te, %<tY\n",
//                    NextWednesday.nextDayOfWeek(Calendar.TUESDAY)
//            );
//
//        System.out.println(DateAndTime.getDateOfNextDayOfWeek(DateAndTime.DAYS_OF_WEEK.TUESDAY));
//
//
//    }

    public static void Main(String[] arg){

    }
    static class NextWednesday {
        public static Calendar nextDayOfWeek(int dow) {
            int diff = 0;
            Calendar date = Calendar.getInstance();
//            if(dow == date.get(Calendar.DAY_OF_WEEK)){
//                diff = dow;
//                System.out.println("equal "+diff);
//            }
//            else
                diff = dow - date.get(Calendar.DAY_OF_WEEK);
                System.out.println("else "+diff+" "+dow);
            if (diff < 0) {
                diff += 7;
            }
            if(diff==0){
                Date time = new GregorianCalendar().getTime();
                System.out.println("equal 0: "+new SimpleDateFormat("HH:mm:ss").format(time));

            }
            date.add(Calendar.DAY_OF_MONTH, diff);
            return date;
        }

    }
}