package com.example.axis_inside.tf_exp_app.db;

import android.os.AsyncTask;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by axis-inside on 4/7/17.
 */

public class DatabaseInitializer {

    public static void populateAsync(final AppDatabase db){

    }
    public static void populateWithTestData(AppDatabase db) {
        db.smsModel().deleteAll();
        Date today = getTodayPlusDays(0);
        addSMS(db,"TEST_APP","This is a test sms",today.getTime(),today.getTime());
    }

    private static void addSMS(AppDatabase db, String title, String details, long receiveTime, long sentTime) {
        Sms sms = new Sms();
        sms.title = title;
        sms.details = details;
        sms.smsReceivedDate = receiveTime;
        sms.smsSentDate = sentTime;
        db.smsModel().insertSms(sms);
    }

    private static Date getTodayPlusDays(int daysAgo) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, daysAgo);
        return calendar.getTime();
    }

    private static class PopulateDbAsync extends AsyncTask<Void,Void,Void >{
        private final AppDatabase mDb;

        PopulateDbAsync(AppDatabase database){
            mDb = database;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            populateWithTestData(mDb);
            return null;
        }

    }
}
