package com.example.morri.messingaround;

/**
 * Created by morri on 3/30/2018.
 */

public final class GroupContract {
    private GroupContract(){}

    public static final String GROUP_TABLE = "groups";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "Name";
    public static final String COL_3 = "Creadted_date";
    public static final String COL_4 = "Admin";
    public static final String COL_5 = "Description";
    //attributes of groups go here

    public static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + GROUP_TABLE + " (" +
                    COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_2 + " TEXT, " +
                    COL_3 + " TEXT, " +
                    COL_4 + " INTEGER, " +
                    COL_5 + " TEXT);";
                    //The remainder of the attributes should be added here

    public static final String SQL_DELETE_TABLE =
            "DROP TABLE IF EXISTS " + GROUP_TABLE;
}
