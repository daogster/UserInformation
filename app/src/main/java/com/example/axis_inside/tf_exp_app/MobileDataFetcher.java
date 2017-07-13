package com.example.axis_inside.tf_exp_app;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Build;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;
import android.view.View;

import com.example.axis_inside.tf_exp_app.localcache.SharedPreferenceHelper;
import com.example.axis_inside.tf_exp_app.models.MixDataModel;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.BATTERY_SERVICE;

/**
 * Created by axis-inside on 30/6/17.
 */

public class MobileDataFetcher {

    private Context mContext;
    private static MobileDataFetcher mobileDataFetcher;

    private MobileDataFetcher(Context context){
        mContext = context;
    }

    public static MobileDataFetcher getMobileDataFetcher(Context context){
        if(mobileDataFetcher == null){
            return createMobileDataFetcher(context);
        }
        return mobileDataFetcher;
    }

    private static MobileDataFetcher createMobileDataFetcher(Context context) {
        return new MobileDataFetcher(context);
    }


    public ArrayList<CommonDetails> fetchInboxSms(int type) {
        ArrayList<CommonDetails> smsInbox = new ArrayList<CommonDetails>();
        Uri uriSms = Uri.parse("content://sms");
/*        Cursor cursor = mContext.getContentResolver()
                .query(uriSms,
                        new String[] { "_id", "address", "date", "body",
                                "type", "read" }, "type=" + type, null,
                        "date" + " COLLATE LOCALIZED ASC");*/
        Cursor cursor = mContext.getContentResolver().query(uriSms,null,null,null,null);

        //long temp = System.currentTimeMillis();
        //Cursor cursor = mContext.getContentResolver().query(uriSms,null,"date >=?",new String[]{String.valueOf(temp)},null);

        if (cursor != null) {
            cursor.moveToLast();
            if (cursor.getCount() > 0) {
                do {
                    CommonDetails message = new CommonDetails();
                    message.title = cursor.getString(cursor
                            .getColumnIndex("address"));
                    message.details = cursor.getString(cursor
                            .getColumnIndex("body"));
                    smsInbox.add(message);
                } while (cursor.moveToPrevious());
            }
        }  return smsInbox;
    }

    public ArrayList<CommonDetails> fetchCallLogs(){
        ArrayList<CommonDetails> callLogs = new ArrayList<>();
        Uri uriCalls = Uri.parse("content://call_log/calls");
        Cursor cursor = mContext.getContentResolver().query(uriCalls,null,null,null,"date" + " COLLATE LOCALIZED DESC");
        if(cursor != null){
            //cursor.moveToLast();
            while (cursor.moveToNext()){
                CommonDetails calls = new CommonDetails();
                calls.title = cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME));
                calls.details = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER)) + " Call Duration: " +
                        cursor.getString(cursor.getColumnIndex(CallLog.Calls.DURATION)) + " type " +
                        cursor.getString(cursor.getColumnIndex(CallLog.Calls.TYPE));

                callLogs.add(calls);
            }
        }
        return callLogs;
    }

    public ArrayList<CommonDetails> fetchContactList(){
        ArrayList<CommonDetails> contactList = new ArrayList<>();
        Cursor phones = mContext.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
        while (phones.moveToNext()){
            CommonDetails contact = new CommonDetails();
            contact.title = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            contact.details = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            contactList.add(contact);
        }
        return contactList;
    }


    public ArrayList<CommonDetails> getInstalledAppsList(){
        ArrayList<CommonDetails> appList = new ArrayList<>();
        final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        final List<ResolveInfo> packages = mContext.getPackageManager().queryIntentActivities( mainIntent, 0);

        for(ResolveInfo r : packages){
            CommonDetails application = new CommonDetails();
            application.title = (String)((r != null) ? mContext.getPackageManager().getApplicationLabel(r.activityInfo.applicationInfo) : "???");
            application.details = r.activityInfo.applicationInfo.packageName.toString();
            appList.add(application);
        }

        return appList;
    }


    public ArrayList<CommonDetails> getCalenderEvents(){
        ArrayList<CommonDetails> eventList = new ArrayList<>();
        Cursor cursor = mContext.getContentResolver()
                .query(
                        Uri.parse("content://com.android.calendar/events"),
                        new String[]{"calendar_id", "title", "description",
                                "dtstart", "dtend", "eventLocation"}, null,
                        null, null);

        if(cursor!=null){
            while (cursor.moveToNext()){
                CommonDetails calender = new CommonDetails();
                calender.title= cursor.getString(1);
                calender.details = cursor.getString(2);
                eventList.add(calender);
            }
        }
        return eventList;
    }

    public ArrayList<CommonDetails> getBrowsingData(){
        ArrayList<CommonDetails> browsingDetails = new ArrayList<>();
        Uri uriCustom = Uri.parse("content://com.android.chrome/history");
        //BrowserProvider browserProvider = new BrowserProvider(context);
        Cursor mCur = mContext.getContentResolver().query(uriCustom, null, null, null, null);
        if(mCur != null){
            while (mCur.moveToNext()){
                CommonDetails browsingData = new CommonDetails();
                //browsingData.title = mCur.getString(mCur.getColumnIndex(B);
            }
        }
        return browsingDetails;
    }


    public String getPowerDetails(){
        int batLabel = 0;
/*        BatteryManager batteryManager = (BatteryManager) mContext.getSystemService(mContext.BATTERY_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            batLabel = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
        } else {

        }
        return String.valueOf(batLabel);*/

        IntentFilter iFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = mContext.registerReceiver(null, iFilter);

        int level = batteryStatus != null ? batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) : -1;
        //int scale = batteryStatus != null ? batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1) : -1;

        //float batteryPct = (level / (float) scale)*100;

        return String.valueOf(level);
    }

    public MixDataModel.PhoneDetails getPhoneDetails() {

        //MixDataModel.PhoneDetails phoneDetails = new MixDataModel.PhoneDetails();
        MixDataModel.PhoneDetails phoneDetails = new MixDataModel().getPhoneDetailsInstance();


        String SDK_VERSION = String.valueOf(Build.VERSION.SDK_INT);
        String DEVICE = Build.DEVICE;
        String MODEL = Build.MODEL;
        String SERIAL = Build.SERIAL;

        TelephonyManager m_telephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        String IMEI = m_telephonyManager.getDeviceId();
        String IMSI = m_telephonyManager.getSubscriberId();


        phoneDetails.setDeviceSdkVersion(SDK_VERSION);
        phoneDetails.setDeviceModel(MODEL);
        phoneDetails.setDeviceSerialNumber(SERIAL);
        phoneDetails.setDeviceIMEI(IMEI);
        phoneDetails.setDeviceIMSI(IMSI);

        return phoneDetails;
    }


    public ArrayList<MixDataModel.InstalledApps> getDeviceInstalledApps(){

        ArrayList<MixDataModel.InstalledApps> installedAppList = new ArrayList<>();

        final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        final List<ResolveInfo> packages = mContext.getPackageManager().queryIntentActivities( mainIntent, 0);

        for(ResolveInfo r : packages){
            String appName = (String)((r != null) ? mContext.getPackageManager().getApplicationLabel(r.activityInfo.applicationInfo) : "???");
            String packageName = r.activityInfo.applicationInfo.packageName.toString();

            MixDataModel.InstalledApps installedApps = new MixDataModel().getInstalledAppsInstance();
            installedApps.setAppName(appName);
            installedApps.setPackageName(packageName);

            installedAppList.add(installedApps);
        }

        return installedAppList;
    }
}
