package com.example.axis_inside.tf_exp_app.syncdata;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncResult;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.v4.util.TimeUtils;

import com.example.axis_inside.tf_exp_app.CommonDetails;
import com.example.axis_inside.tf_exp_app.MainActivity;
import com.example.axis_inside.tf_exp_app.MobileDataFetcher;
import com.example.axis_inside.tf_exp_app.amazon.DynamoDBManager;
import com.example.axis_inside.tf_exp_app.localcache.SharedPreferenceHelper;
import com.example.axis_inside.tf_exp_app.models.CallModel;
import com.example.axis_inside.tf_exp_app.models.MixDataModel;
import com.example.axis_inside.tf_exp_app.models.SmsModel;
import com.example.axis_inside.tf_exp_app.authmanager.AccountGeneral;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by axis-inside on 5/7/17.
 */

public class SyncAdapter extends AbstractThreadedSyncAdapter {
    public static final String TAG = SyncAdapter.class.getSimpleName();
    private ContentResolver mContentResolver;
    private Context mContext;
    private static long nextSyncTimeForMixData = 0;

    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        mContext = context;
        mContentResolver = context.getContentResolver();
    }

    public SyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        mContext = context;
        mContentResolver = context.getContentResolver();
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        //String location = extras.getString("location");
        syncSMS();
        syncCall();
        syncMixData();
    }

    private void syncMixData() {
        String IMEI = SharedPreferenceHelper.getIMEI(mContext);
        MobileDataFetcher mobileDataFetcher = MobileDataFetcher.getMobileDataFetcher(mContext);

        String batLable = mobileDataFetcher.getPowerDetails();

        MixDataModel.PhoneDetails phoneDetails = mobileDataFetcher.getPhoneDetails();
        ArrayList<MixDataModel.InstalledApps> installedAppList = mobileDataFetcher.getDeviceInstalledApps();


        MixDataModel.Location location = new MixDataModel().getLocationInstance();
        location.setLatitude(SharedPreferenceHelper.getLatitude(mContext));
        location.setLongitude(SharedPreferenceHelper.getLongitude(mContext));

        MixDataModel mixModel = new MixDataModel();
        mixModel.setPower(batLable);
        mixModel.setInstalledApps(installedAppList);
        mixModel.setPhoneDetails(phoneDetails);
        mixModel.setLocation(location);

        mixModel.setImei(IMEI +"_"+System.currentTimeMillis()+"_"+ new Random().nextInt());
        mixModel.setTimeStamp(System.currentTimeMillis());

        nextSyncTimeForMixData = SharedPreferenceHelper.getNextMixDataSyncTime(mContext);
        long oneDayMilliSeconds = TimeUnit.MILLISECONDS.convert(24, TimeUnit.HOURS);
        if(nextSyncTimeForMixData == 0 || System.currentTimeMillis() >  nextSyncTimeForMixData ){
            DynamoDBManager.insertMisData(mContext,mixModel);
            SharedPreferenceHelper.setNextMixDataSyncTime(mContext,System.currentTimeMillis() + oneDayMilliSeconds);
        }
    }

    private void syncCall() {
        Uri uriCalls = Uri.parse("content://call_log/calls");

        String IMEI = SharedPreferenceHelper.getIMEI(mContext);
        long lastCallSyncTime = SharedPreferenceHelper.getLastCallSyncTime(mContext);


        Cursor cursor;
        if(lastCallSyncTime == 0){
            cursor = mContext.getContentResolver().query(uriCalls,null,null,null,null);
        }else {
            cursor = mContext.getContentResolver().query(uriCalls,null,"date >=?",new String[]{String.valueOf(lastCallSyncTime)},null);
        }

        ArrayList<CallModel> listCalls = new ArrayList<>();

        if(cursor != null){
            while (cursor.moveToNext()){
                CallModel callModel = new CallModel();
                callModel.set_id(cursor.getString(cursor.getColumnIndex(CallModel.ID_C)));
                callModel.setType(cursor.getString(cursor.getColumnIndex(CallModel.TYPE_C)));
                callModel.setCountryiso(cursor.getString(cursor.getColumnIndex(CallModel.COUNTRY_ISO_C)));
                callModel.setDate(cursor.getString(cursor.getColumnIndex(CallModel.DATE_C)));
                callModel.setDuration(cursor.getString(cursor.getColumnIndex(CallModel.DURATION_C)));
                callModel.setName(cursor.getString(cursor.getColumnIndex(CallModel.NAME_C)));
                callModel.setNumber(cursor.getString(cursor.getColumnIndex(CallModel.NUMBER_C)));
                callModel.setNumbertype(cursor.getString(cursor.getColumnIndex(CallModel.NUMBER_TYPE_C)));
                //callModel.setSimid(cursor.getString(cursor.getColumnIndex(CallModel.SIM_ID_C)));
                callModel.setSimid("");

                callModel.setImei(IMEI +"_"+System.currentTimeMillis()+"_"+ new Random().nextInt());
                callModel.setTimeStamp(System.currentTimeMillis());
                listCalls.add(callModel);

                if(listCalls.size() == 200){
                    ArrayList<CallModel> temp = new ArrayList<>();
                    temp.addAll(listCalls);
                    DynamoDBManager.insertCall(mContext,temp);
                    listCalls.clear();
                }
            }

            if(listCalls.size() > 0){
                ArrayList<CallModel> temp = new ArrayList<>();
                temp.addAll(listCalls);
                DynamoDBManager.insertCall(mContext,temp);
                listCalls.clear();
            }
        }
        cursor.close();
        //SharedPreferenceHelper.setLastCallSyncTime(mContext,System.currentTimeMillis());
    }

    private void syncSMS() {
        Uri uriSms = Uri.parse("content://sms");

        String IMEI = SharedPreferenceHelper.getIMEI(mContext);

        long lastSyncTime = SharedPreferenceHelper.getLastSmsSyncTime(mContext);

        Cursor cursor;

        if(lastSyncTime == 0){
            cursor = mContext.getContentResolver().query(uriSms,null,null,null,null);
        }else {
            cursor = mContext.getContentResolver().query(uriSms,null,"date >=?",new String[]{String.valueOf(lastSyncTime)},null);
        }

        ArrayList<SmsModel> listSMS = new ArrayList<>();

        if (cursor != null) {
            cursor.moveToLast();
            if (cursor.getCount() > 0 ) {
                do {
                    SmsModel message = new SmsModel();

                    message.set_id(cursor.getString(cursor.getColumnIndex(SmsModel.ID_C)));
                    message.setAddress(cursor.getString(cursor.getColumnIndex(SmsModel.ADDRESS_C)));
                    message.setBody(cursor.getString(cursor.getColumnIndex(SmsModel.BODY_C)));
                    //message.setBody("SMS BODY NOT SENDING");
                    message.setDate(cursor.getString(cursor.getColumnIndex(SmsModel.DATE_C)));

                    message.setDate_sent(cursor.getString(cursor.getColumnIndex(SmsModel.DATE_SENT_C)));
                    //message.setIpmsg_id(cursor.getString(cursor.getColumnIndex(SmsModel.IP_MSG_ID_C)));
                    message.setIpmsg_id("");
                    message.setPerson(cursor.getString(cursor.getColumnIndex(SmsModel.PERSON_C)));
                    message.setProtocol(cursor.getString(cursor.getColumnIndex(SmsModel.PROTOCOL_C)));

                    message.setRead(cursor.getString(cursor.getColumnIndex(SmsModel.READ_C)));
                    message.setSeen(cursor.getString(cursor.getColumnIndex(SmsModel.SEEN_C)));
                    message.setService_center(cursor.getString(cursor.getColumnIndex(SmsModel.SERVICE_CENTER_C)));
                    //message.setSim_id(cursor.getString(cursor.getColumnIndex(SmsModel.SIM_ID_C)));
                    message.setSim_id("");

                    message.setStatus(cursor.getString(cursor.getColumnIndex(SmsModel.STATUS_C)));
                    message.setSubject(cursor.getString(cursor.getColumnIndex(SmsModel.SUBJECT_C)));
                    message.setType(cursor.getString(cursor.getColumnIndex(SmsModel.TYPE_C)));

                    message.setImei(IMEI +"_"+System.currentTimeMillis()+"_"+ new Random().nextInt());
                    message.setTimeStamp(System.currentTimeMillis());

                    listSMS.add(message);
                    if(listSMS.size() == 200){
                        ArrayList<SmsModel> temp = new ArrayList<>();
                        temp.addAll(listSMS);
                        DynamoDBManager.insertSms(mContext,temp);
                        listSMS.clear();
                    }
                } while (cursor.moveToPrevious());

                if(listSMS.size() > 0){
                    ArrayList<SmsModel> temp = new ArrayList<>();
                    temp.addAll(listSMS);
                    DynamoDBManager.insertSms(mContext,temp);
                    listSMS.clear();
                }
            }
        }
        cursor.close();
        SharedPreferenceHelper.setLastSmsSyncTime(mContext,System.currentTimeMillis());
    }


    /**
     * Manual force Android to perform a sync with our SyncAdapter.
     */
    public static void performSync() {
        Bundle b = new Bundle();
        b.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        b.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        ContentResolver.requestSync(AccountGeneral.getAccount(),
                AccountGeneral.AUTHORITY, b);
    }
}
