package com.example.axis_inside.tf_exp_app.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

/**
 * Created by axis-inside on 4/7/17.
 */
@Dao
public interface SmsDao {

    @Query("select * from sms")
    List<Sms> loadAllSms();

    @Query("select * from sms where id = :id")
    Sms loadSmsById(int id);

    @Insert(onConflict = IGNORE)
    void insertSms(Sms sms);

    @Insert(onConflict = IGNORE)
    void insertOrReplaceUsers(Sms... users);

    @Query("DELETE FROM Sms")
    void deleteAll();
}
