package com.example.axis_inside.tf_exp_app.localcache;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Axis_Inside on 10-07-2017.
 */

public class SharedPreferenceHelper {

    public static String IMEI = "IMEI";
    public static String LAST_SMS_SYNC_TIME = "LAST_SMS_SYNC_TIME";
    public static String LAST_CALL_SYNC_TIME = "LAST_CALL_SYNC_TIME";
    public static String LAST_MIX_DATA_SYNC_TIME = "LAST_MIX_DATA_SYNC_TIME";
    public static String DEVICE_LOCATION_LATITUDE = "DEVICE_LOCATION_LATITUDE";
    public static String DEVICE_LOCATION_LONGITUDE = "DEVICE_LOCATION_LONGITUDE";

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

    public static String getIMEI(Context mContext){
        return getSharedPreference(mContext).getString(IMEI,"DEFAULT_IMEI");
    }

    public static void setIMEI(Context mContext,String imei){
        getSharedPreference(mContext).edit().putString(IMEI,imei).commit();
    }

    public static void setLastSmsSyncTime(Context mContext, long l){
        getSharedPreference(mContext).edit().putLong(SharedPreferenceHelper.LAST_SMS_SYNC_TIME,l).commit();
    }

    public static long getLastSmsSyncTime(Context context){
        return getSharedPreference(context).getLong(LAST_SMS_SYNC_TIME,0);
    }

    public static void setLastCallSyncTime(Context mContext, long l){
        getSharedPreference(mContext).edit().putLong(LAST_CALL_SYNC_TIME,l).commit();
    }

    public static long getLastCallSyncTime(Context context){
        return getSharedPreference(context).getLong(LAST_CALL_SYNC_TIME,0);
    }

    public static void setNextMixDataSyncTime(Context mContext,long l){
        getSharedPreference(mContext).edit().putLong(LAST_MIX_DATA_SYNC_TIME,l).commit();
    }

    public static long getNextMixDataSyncTime(Context context){
        return getSharedPreference(context).getLong(LAST_MIX_DATA_SYNC_TIME,0);
    }

    public static void setLatitude(Context mContext,String latitude){
        getSharedPreference(mContext).edit().putString(DEVICE_LOCATION_LATITUDE,latitude).commit();
    }

    public static void setLongitude(Context mContext,String longitude){
        getSharedPreference(mContext).edit().putString(DEVICE_LOCATION_LONGITUDE,longitude).commit();
    }

    public static String getLatitude(Context context){
        return getSharedPreference(context).getString(DEVICE_LOCATION_LATITUDE,"");
    }

    public static String getLongitude(Context context){
        return getSharedPreference(context).getString(DEVICE_LOCATION_LONGITUDE,"");
    }
}
