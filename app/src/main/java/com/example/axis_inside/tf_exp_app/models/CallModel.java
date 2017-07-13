package com.example.axis_inside.tf_exp_app.models;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;
import com.example.axis_inside.tf_exp_app.Constants;

/**
 * Created by Axis_Inside on 12-07-2017.
 */
@DynamoDBTable(tableName = Constants.DYNAMO_DB_CALL_TABLE)
public class CallModel {
    public static String ID_C = "_id";
    public static String NUMBER_C = "number";
    public static String TYPE_C = "type";
    public static String DATE_C = "date";
    public static String NUMBER_TYPE_C = "numbertype";
    public static String COUNTRY_ISO_C = "countryiso";
    public static String DURATION_C = "duration";
    public static String SIM_ID_C = "simid";
    public static String NAME_C = "name";

    private String _id;
    private String number;
    private String type;
    private String date;
    private String numbertype;
    private String countryiso;
    private String duration;
    private String simid;
    private String name;

    @DynamoDBHashKey(attributeName = "imei")
    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public double getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(double timeStamp) {
        this.timeStamp = timeStamp;
    }

    private String imei;
    private double timeStamp;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNumbertype() {
        return numbertype;
    }

    public void setNumbertype(String numbertype) {
        this.numbertype = numbertype;
    }

    public String getCountryiso() {
        return countryiso;
    }

    public void setCountryiso(String countryiso) {
        this.countryiso = countryiso;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getSimid() {
        return simid;
    }

    public void setSimid(String simid) {
        this.simid = simid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
