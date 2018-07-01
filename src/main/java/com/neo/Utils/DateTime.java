package com.neo.Utils;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class DateTime {
    public SimpleDateFormat simpleDateFormat;

    public DateTime() {
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    public String getFormatedTime(long timestamp){
        Date date=new Date(timestamp);
        return simpleDateFormat.format(date);
    }
}
