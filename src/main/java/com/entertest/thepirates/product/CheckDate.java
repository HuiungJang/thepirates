package com.entertest.thepirates.product;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Calendar;
import java.util.Date;

import java.net.URL;
import java.util.Locale;

public class CheckDate {
    // 현재시간
    public String checkExpiredTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Date d = new Date();
        return sdf.format(d);
    }

    public String date(int dateCount) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d = new Date();
        String date =sdf.format(d);

        Calendar c = Calendar.getInstance();
        c.setTime(sdf.parse(date));
        c.add(Calendar.DATE, dateCount);  //하루를 더해준다.

        date = sdf.format(c.getTime());

        return date;
    }
    // 요일 구하기
    public String day(int year,int month, int date){
        LocalDate localDate = LocalDate.of(year,month,date);
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        return dayOfWeek.getDisplayName(TextStyle.FULL, Locale.KOREAN);
    }



}
