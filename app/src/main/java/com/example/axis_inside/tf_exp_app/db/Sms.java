package com.example.axis_inside.tf_exp_app.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by axis-inside on 4/7/17.
 */
@Entity
public class Sms {
    public @PrimaryKey(autoGenerate = true) int id;
    public String title;
    public String details;
    public long smsReceivedDate;
    public long smsSentDate;

}
