package com.example.axis_inside.tf_exp_app;

/**
 * Created by axis-inside on 6/6/17.
 */

public class Constants {
    public static final int SUCCESS_RESULT = 0;
    public static final int FAILURE_RESULT = 1;
    public static final String PACKAGE_NAME =
            "com.example.axis_inside.tf_exp_app";
    public static final String RECEIVER = PACKAGE_NAME + ".MainActivity.AddressResultReceiver";
    public static final String RESULT_DATA_KEY = PACKAGE_NAME +
            ".RESULT_DATA_KEY";
    public static final String LOCATION_DATA_EXTRA = PACKAGE_NAME +
            ".LOCATION_DATA_EXTRA";


    public static final String IDENTITY_POOL_ID = "us-west-2:99b0c053-94c1-4ded-bf00-31f2748eda2d";
    // Note that spaces are not allowed in the table name
    public static final String TEST_TABLE_NAME = "TF_USER_LOCATION";

    public static final String DYNAMO_DB_SMS_TABLE = "TF_USER_SMS";
    public static final String DYNAMO_DB_CALL_TABLE = "TF_USER_CALL";
    public static final String DYNAMO_DB_MIX_DATA_TABLE = "TF_USER_MIXDATA";

}
