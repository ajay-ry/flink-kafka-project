package com.travel.deepblue.utilities;


import java.text.ParseException;
import java.time.LocalDate;
import java.time.Period;

public class DateUtility {

    public static boolean isGreater(String dt1,String dt2) throws ParseException {


        String dt_time_arr1[] = dt1.split(" ");
        String dt_arr1[] = dt_time_arr1[0].split("-");

        String dt_time_arr2[] = dt2.split(" ");
        String dt_arr2[] = dt_time_arr2[0].split("-");

        //date format: yyyy-mm-dd hh:mm:ss
        LocalDate ld_dt1 = LocalDate.of(Integer.parseInt(dt_arr1[0]),Integer.parseInt(dt_arr1[1]),Integer.parseInt(dt_arr1[2]));
        LocalDate ld_dt2 = LocalDate.of(Integer.parseInt(dt_arr2[0]),Integer.parseInt(dt_arr2[1]),Integer.parseInt(dt_arr2[2]));
        Period p =Period.between(ld_dt1,ld_dt2);
        return p.isNegative();

    }

}
