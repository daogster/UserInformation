package com.example.axis_inside.tf_exp_app;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class DetailsActivity extends AppCompatActivity {

    private static final int TYPE_INCOMING_MESSAGE = 1;
    private ListView messageList;
    private MessageListAdapter messageListAdapter;
    private ArrayList<CommonDetails> recordsStored;
    private ArrayList<CommonDetails> listInboxMessages;
    private ProgressDialog progressDialogInbox;
    private CustomHandler customHandler;
    private FetchMessageThread fetchMessageThread;
    private int currentCount = 0;
    private String detailsType;
    private MobileDataFetcher mobileDataFetcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_details);
        if(getIntent().getExtras() != null){
            Bundle bundle = getIntent().getExtras();
            detailsType = (String) bundle.get("TYPE");
        }
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        mobileDataFetcher = MobileDataFetcher.getMobileDataFetcher(this);
        initViews();
    }

    @Override
    public void onResume(){
            super.onResume();
            populateMessageList();
    }

    private void initViews() {
            customHandler = new CustomHandler(this);
            progressDialogInbox = new ProgressDialog(this);
            recordsStored = new ArrayList<CommonDetails>();
            messageList = (ListView) findViewById(R.id.commonList);
            populateMessageList();
    }

    public void populateMessageList() {
            fetchInboxMessages();
            messageListAdapter = new MessageListAdapter(this,
                    R.layout.content_details_list_item, recordsStored);
            messageList.setAdapter(messageListAdapter);
    }

    private void showProgressDialog(String message) {
            progressDialogInbox.setMessage(message);
            progressDialogInbox.setIndeterminate(true);
            progressDialogInbox.setCancelable(true);
            progressDialogInbox.show();
    }

    private void fetchInboxMessages() {
        if (listInboxMessages == null) {
                showProgressDialog("Fetching Details...");
                startThread();
        } else {
                // messageType = TYPE_INCOMING_MESSAGE;
                recordsStored = listInboxMessages;
                messageListAdapter.setArrayList(recordsStored);
        }
    }

    public class FetchMessageThread extends Thread {
        public int tag = -1;
        public FetchMessageThread(int tag) {
                this.tag = tag;
        }
        @Override
        public void run() {
            //recordsStored = FetchAddressIntentServicerecordsStored = fetchCallLogs(TYPE_INCOMING_MESSAGE);
            if(detailsType.equals("SMS")){
                recordsStored = mobileDataFetcher.fetchInboxSms(TYPE_INCOMING_MESSAGE);
            }else if(detailsType.equals("CALLS")){
                recordsStored = mobileDataFetcher.fetchCallLogs();
            }else if(detailsType.equals("CONTACT")){
                recordsStored = mobileDataFetcher.fetchContactList();
            }else if(detailsType.equals("APPS")){
                recordsStored = mobileDataFetcher.getInstalledAppsList();
            }else if(detailsType.equals("CALENDER")){
                recordsStored = mobileDataFetcher.getCalenderEvents();
            }
            listInboxMessages = recordsStored;
            customHandler.sendEmptyMessage(0);
        }
    }

/*    public ArrayList<CommonDetails> fetchInboxSms(int type) {
            ArrayList<CommonDetails> smsInbox = new ArrayList<CommonDetails>();
            Uri uriSms = Uri.parse("content://sms");
            Cursor cursor = this.getContentResolver()
                    .query(uriSms,
                            new String[] { "_id", "address", "date", "body",
                                    "type", "read" }, "type=" + type, null,
                            "date" + " COLLATE LOCALIZED ASC");
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
    }*/

/*
    public ArrayList<CommonDetails> fetchCallLogs(){
        ArrayList<CommonDetails> callLogs = new ArrayList<>();
        Uri uriCalls = Uri.parse("content://call_log/calls");
        Cursor cursor = this.getContentResolver().query(uriCalls,null,null,null,"date" + " COLLATE LOCALIZED DESC");
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
*/

/*    public ArrayList<CommonDetails> fetchContactList(){
        ArrayList<CommonDetails> contactList = new ArrayList<>();
        Cursor phones = this.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
        while (phones.moveToNext()){
            CommonDetails contact = new CommonDetails();
            contact.title = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            contact.details = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            contactList.add(contact);
        }
        return contactList;
    }*/

/*    public ArrayList<CommonDetails> getInstalledAppsList(){
        ArrayList<CommonDetails> appList = new ArrayList<>();
        final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        final List<ResolveInfo> packages = this.getPackageManager().queryIntentActivities( mainIntent, 0);

        for(ResolveInfo r : packages){
            CommonDetails application = new CommonDetails();
            application.title = (String)((r != null) ? getBaseContext().getPackageManager().getApplicationLabel(r.activityInfo.applicationInfo) : "???");
            application.details = r.activityInfo.applicationInfo.packageName.toString();
            appList.add(application);
        }

        return appList;
    }*/

/*    public ArrayList<CommonDetails> getCalenderEvents(){
        ArrayList<CommonDetails> eventList = new ArrayList<>();
        Cursor cursor = this.getContentResolver()
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
    }*/

    public String getDate(long milliSeconds) {
        SimpleDateFormat formatter = new SimpleDateFormat(
                "dd/MM/yyyy hh:mm:ss a");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

/*    public ArrayList<CommonDetails> getBrowsingData(){
        ArrayList<CommonDetails> browsingDetails = new ArrayList<>();
        Uri uriCustom = Uri.parse("content://com.android.chrome/history");
        //BrowserProvider browserProvider = new BrowserProvider(context);
        Cursor mCur = getContentResolver().query(uriCustom, null, null, null, null);
        if(mCur != null){
            while (mCur.moveToNext()){
                CommonDetails browsingData = new CommonDetails();
                //browsingData.title = mCur.getString(mCur.getColumnIndex(B);
            }
        }
        return browsingDetails;
    }*/

    public synchronized void startThread() {
            if (fetchMessageThread == null) {
                fetchMessageThread = new FetchMessageThread(currentCount);
                fetchMessageThread.start();
            }
    }
    public synchronized void stopThread() {
            if (fetchMessageThread != null) {
                Log.i("Cancel thread", "stop thread");
                FetchMessageThread moribund = fetchMessageThread;
                currentCount = fetchMessageThread.tag == 0 ? 1 : 0;
                fetchMessageThread = null;
                moribund.interrupt();
            }
    }

    static class CustomHandler extends Handler {
            private final WeakReference<DetailsActivity> activityHolder;
            CustomHandler(DetailsActivity inboxListActivity) {
                activityHolder = new WeakReference<DetailsActivity>(inboxListActivity);
            }

            @Override
            public void handleMessage(android.os.Message msg) {
                DetailsActivity detailsActivity = activityHolder.get();
                if (detailsActivity.fetchMessageThread != null
                        && detailsActivity.currentCount == detailsActivity.fetchMessageThread.tag) {
                    Log.i("received result", "received result");
                    detailsActivity.fetchMessageThread = null;
                    detailsActivity.messageListAdapter.setArrayList(detailsActivity.recordsStored);
                    detailsActivity.progressDialogInbox.dismiss();
                }
            }
    }

    private DialogInterface.OnCancelListener dialogCancelListener = new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                progressDialogInbox.dismiss();
                stopThread();
            }
    };

}
