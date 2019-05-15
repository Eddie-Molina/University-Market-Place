package com.example.morri.messingaround;

/**
 * Created by morri on 4/22/2018.
 */

public final class CurrentUserContract {

    private CurrentUserContract(){}

    public static final String CURRENT_USER_TABLE = "current_user";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "Fname";
    public static final String COL_3 = "Lname";
    public static final String COL_4 = "Email";
    public static final String COL_5 = "PhoneNo";
    public static final String COL_6 = "StudentID";
    public static final String COL_7 = "Username";
    public static final String COL_8 = "Password";

    public static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + CURRENT_USER_TABLE + " (" +
                    COL_1 + " INTEGER PRIMARY KEY, " +
                    COL_2 + " TEXT, " +
                    COL_3 + " TEXT, " +
                    COL_4 + " TEXT, " +
                    COL_5 + " TEXT, " +
                    COL_6 + " TEXT, " +
                    COL_7 + " TEXT, " +
                    COL_8 + " TEXT);";

    public static final String SQL_DELETE_TABLE =
            "DROP TABLE IF EXISTS " + CURRENT_USER_TABLE;
}
