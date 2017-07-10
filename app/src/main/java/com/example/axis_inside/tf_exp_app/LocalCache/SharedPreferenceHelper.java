package com.example.axis_inside.tf_exp_app.LocalCache;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Axis_Inside on 10-07-2017.
 */

public class SharedPreferenceHelper {

    public static String IMEI = "IMEI";
    public static String LAST_SYNC_TIME = "LAST_SYNC_TIME";

    private static SharedPreferences sharedPreferences;

    private static SharedPreferences createSharedPreference(Context context){
        sharedPreferences = context.getSharedPreferences("LOCAL_APP",Context.MODE_PRIVATE);
        return sharedPreferences;
    }

    public static SharedPreferences getSharedPreference(Context context){
        if(sharedPreferences == null){
            createSharedPreference(context);
        }
        return sharedPreferences;
    }
}
