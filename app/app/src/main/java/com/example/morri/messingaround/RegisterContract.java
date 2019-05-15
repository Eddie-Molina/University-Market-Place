package com.example.morri.messingaround;

import android.provider.BaseColumns;

/**
 * Created by morri on 3/24/2018.
 */

public final class RegisterContract {

    private RegisterContract() {}

    public static final String USER_TABLE = "users";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "Fname";
    public static final String COL_3 = "Lname";
    public static final String COL_4 = "Email";
    public static final String COL_5 = "PhoneNo";
    public static final String COL_6 = "StudentID";
    public static final String COL_7 = "Username";
    public static final String COL_8 = "Password";
    public static final String COL_9 = "Question1";
    public static final String COL_10 = "Answer1";
    public static final String COL_11 = "Question2";
    public static final String COL_12 = "Answer2";

    public static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + USER_TABLE + " (" +
                    COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_2 + " TEXT, " +
                    COL_3 + " TEXT, " +
                    COL_4 + " TEXT, " +
                    COL_5 + " TEXT, " +
                    COL_6 + " TEXT, " +
                    COL_7 + " TEXT, " +
                    COL_8 + " TEXT, " +
                    COL_9 + " TEXT, " +
                    COL_10 + " TEXT, " +
                    COL_11 + " TEXT, " +
                    COL_12 + " TEXT);";

    public static final String SQL_DELETE_TABLE =
            "DROP TABLE IF EXISTS " + USER_TABLE;
}
