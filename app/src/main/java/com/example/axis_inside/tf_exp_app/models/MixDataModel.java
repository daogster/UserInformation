package com.example.axis_inside.tf_exp_app.models;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBDocument;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIgnore;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;
import com.example.axis_inside.tf_exp_app.Constants;

import java.util.ArrayList;

/**
 * Created by Axis_Inside on 13-07-2017.
 */
@DynamoDBTable(tableName = Constants.DYNAMO_DB_MIX_DATA_TABLE)
public class MixDataModel {

    private String imei;
    private double timeStamp;
    private String power;
    private ArrayList<InstalledApps> installedApps;
    private PhoneDetails PhoneDetails;
    private Location Location;

    public MixDataModel(){}

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


    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }
    @DynamoDBAttribute(attributeName = "InstalledApps")
    public ArrayList<InstalledApps> getInstalledApps() {
        return installedApps;
    }

    public void setInstalledApps(ArrayList<InstalledApps> installedApps) {
        this.installedApps = installedApps;
    }
    @DynamoDBAttribute(attributeName = "PhoneDetails")
    public PhoneDetails getPhoneDetails() {
        return PhoneDetails;
    }

    public void setPhoneDetails(PhoneDetails phoneDetails) {
        PhoneDetails = phoneDetails;
    }
    @DynamoDBAttribute(attributeName = "Location")
    public Location getLocation() {
        return Location;
    }

    public void setLocation(Location location) {
        Location = location;
    }

    @DynamoDBDocument
    public class InstalledApps{
        private String appName;
        private String packageName;

        public InstalledApps(){}


        public String getAppName() {
            return appName;
        }

        public void setAppName(String appName) {
            this.appName = appName;
        }

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }

    }
    @DynamoDBDocument
    public class PhoneDetails{

        private String deviceSdkVersion;
        private String deviceModel;
        private String deviceSerialNumber;
        private String deviceIMEI;
        private String deviceIMSI;

        public PhoneDetails(){}

        public String getDeviceSdkVersion() {
            return deviceSdkVersion;
        }

        public void setDeviceSdkVersion(String deviceSdkVersion) {
            this.deviceSdkVersion = deviceSdkVersion;
        }

        public String getDeviceModel() {
            return deviceModel;
        }

        public void setDeviceModel(String deviceName) {
            this.deviceModel = deviceName;
        }

        public String getDeviceSerialNumber() {
            return deviceSerialNumber;
        }

        public void setDeviceSerialNumber(String deviceSerialNumber) {
            this.deviceSerialNumber = deviceSerialNumber;
        }

        public String getDeviceIMEI() {
            return deviceIMEI;
        }

        public void setDeviceIMEI(String deviceIMEI) {
            this.deviceIMEI = deviceIMEI;
        }

        public String getDeviceIMSI() {
            return deviceIMSI;
        }

        public void setDeviceIMSI(String deviceIMSI) {
            this.deviceIMSI = deviceIMSI;
        }

    }

    @DynamoDBDocument
    public class Location{
        private String latitude;
        private String longitude;

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public  Location(){}
    }

    @DynamoDBIgnore
    public PhoneDetails getPhoneDetailsInstance(){
        return new PhoneDetails();
    }
    @DynamoDBIgnore
    public Location getLocationInstance(){
        return new Location();
    }
    @DynamoDBIgnore
    public InstalledApps getInstalledAppsInstance(){
        return new InstalledApps();
    }
}
