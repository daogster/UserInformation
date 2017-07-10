package com.example.axis_inside.tf_exp_app;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;

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

}
