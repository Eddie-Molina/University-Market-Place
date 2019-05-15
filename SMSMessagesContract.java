package com.example.morri.messingaround;
import android.provider.BaseColumns;


public class SMSMessagesContract {



        private SMSMessagesContract(){}

        public static final String USER_TABLE = "user_messages";
        public static final String COL_1 = "owner";
        public static final String COL_2 = "number";
        public static final String COL_3 = "messages";

        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE " + USER_TABLE + " (" +
                        COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COL_2 + " TEXT, " +
                        COL_3 + " TEXT);";

        public static final String SQL_DELETE_TABLE =
                "DROP TABLE IF EXISTS " + USER_TABLE;

}
