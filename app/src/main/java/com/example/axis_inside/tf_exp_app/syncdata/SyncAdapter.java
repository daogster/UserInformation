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

import com.example.axis_inside.tf_exp_app.amazon.DynamoDBManager;
import com.example.axis_inside.tf_exp_app.localcache.SharedPreferenceHelper;
import com.example.axis_inside.tf_exp_app.models.SmsModel;
import com.example.axis_inside.tf_exp_app.authmanager.AccountGeneral;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by axis-inside on 5/7/17.
 */

public class SyncAdapter extends AbstractThreadedSyncAdapter {
    public static final String TAG = SyncAdapter.class.getSimpleName();
    private ContentResolver mContentResolver;
    private Context mContext;

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
/*        Log.v("SyncResult",syncResult.toString());
        int tmp = (new Random().nextInt(15));
        DynamoDBManager.insertUsers(mContext,String.valueOf(tmp));
        Log.v(TAG,"Location Data Added to AWS");*/
    }

    private void syncSMS() {
        Uri uriSms = Uri.parse("content://sms");

        String IMEI = SharedPreferenceHelper.getSharedPreference(mContext).getString(SharedPreferenceHelper.IMEI,"DEFAULT_IMEI");

        long lastSyncTime = SharedPreferenceHelper.getSharedPreference(mContext).getLong(SharedPreferenceHelper.LAST_SYNC_TIME,0);

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
        SharedPreferenceHelper.getSharedPreference(mContext).edit().putLong(SharedPreferenceHelper.LAST_SYNC_TIME,System.currentTimeMillis()).commit();
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
