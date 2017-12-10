package me.eumenides.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Eumenides on 2017/12/4.
 */
public class TimeUtils {
    public static Date convertString2Date(String timeStr){
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return format.parse(timeStr);
        } catch (ParseException e) {
            return null;
        }
    }
    public static String convertDate2String(Date date){
        try {
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return format.format(date);
        } catch (Exception e) {
            return "";
        }
    }

}
