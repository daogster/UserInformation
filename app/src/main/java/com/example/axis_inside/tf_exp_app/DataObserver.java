package com.example.axis_inside.tf_exp_app;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;

import com.example.axis_inside.tf_exp_app.authmanager.AccountGeneral;

/**
 * Created by Axis_Inside on 10-07-2017.
 */

public class DataObserver extends ContentObserver {
    /**
     * Creates a content observer.
     *
     * @param handler The handler to run {@link #onChange} on, or null if none.
     */
    public DataObserver(Handler handler) {
        super(handler);
    }

    public DataObserver() {
        super(new Handler(Looper.getMainLooper()));
    }

    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
    }

    @Override
    public void onChange(boolean selfChange, Uri uri) {
        super.onChange(selfChange, uri);
        ContentResolver.requestSync(AccountGeneral.getAccount(), AccountGeneral.AUTHORITY, null);
    }
}
