package com.example.axis_inside.tf_exp_app.Models;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;
import com.example.axis_inside.tf_exp_app.Constants;

/**
 * Created by Axis_Inside on 10-07-2017.
 */
@DynamoDBTable(tableName = Constants.DYNAMO_DB_SMS_TABLE)
public class SmsModel {

    public static String ID_C = "_id";
    public static String ADDRESS_C = "address";
    public static String PERSON_C = "person";
    public static String DATE_C = "date";
    public static String DATE_SENT_C = "date_sent";
    public static String PROTOCOL_C = "protocol";
    public static String READ_C = "read";
    public static String STATUS_C = "status";
    public static String TYPE_C = "type";
    public static String SUBJECT_C = "subject";
    public static String BODY_C = "body";
    public static String SERVICE_CENTER_C = "service_center";
    public static String SIM_ID_C = "sim_id";
    public static String SEEN_C = "seen";
    public static String IP_MSG_ID_C = "ipmsg_id";



    private String _id;//0
    private String address;//2
    private String person;//4
    private String date;//5

    private String date_sent;//6
    private String protocol;//7
    private String read;//8
    private String status;//9

    private String type;//10
    private String subject;//12
    private String body;//13
    private String service_center;//14

    private String sim_id;//16
    private String seen;//18
    private String ipmsg_id;//19


    private String imei;
    private double timeStamp;

    @DynamoDBHashKey(attributeName = "imei")
    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate_sent() {
        return date_sent;
    }

    public void setDate_sent(String date_sent) {
        this.date_sent = date_sent;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getRead() {
        return read;
    }

    public void setRead(String read) {
        this.read = read;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getService_center() {
        return service_center;
    }

    public void setService_center(String service_center) {
        this.service_center = service_center;
    }

    public String getSim_id() {
        return sim_id;
    }

    public void setSim_id(String sim_id) {
        this.sim_id = sim_id;
    }

    public String getSeen() {
        return seen;
    }

    public void setSeen(String seen) {
        this.seen = seen;
    }

    public String getIpmsg_id() {
        return ipmsg_id;
    }

    public void setIpmsg_id(String ipmsg_id) {
        this.ipmsg_id = ipmsg_id;
    }

    public double getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(double timeStamp) {
        this.timeStamp = timeStamp;
    }
}
