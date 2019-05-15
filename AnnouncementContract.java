package com.example.morri.messingaround;

/**
 * Created by robdi on 4/27/2018.
 */

public final class AnnouncementContract {

    private AnnouncementContract(){}

    public static final String TABLE_NAME = "Announcements";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "GroupID";
    public static final String COL_3 = "Message";
    public static final String COL_4 = "Subject";
    public static final String COL_5 = "CreatedDate";

    public static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_2 + " INTEGER, " +
                    COL_3 + " TEXT, " +
                    COL_4 + " TEXT, " +
                    COL_5 + " TEXT);";

    public static final String SQL_DELETE_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;
}
