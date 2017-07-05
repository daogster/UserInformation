package com.example.axis_inside.tf_exp_app.db;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * Created by axis-inside on 4/7/17.
 */

public class DateConverter {
    @TypeConverter
    public static Date toDate(Long timestamp){
        return timestamp == null ? null : new Date(timestamp);
    }

    @TypeConverter
    public static Long toTimestamp(Date date){
        return date == null? null : date.getTime();
    }
}
