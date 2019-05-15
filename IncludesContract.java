package com.example.morri.messingaround;

/**
 * Created by morri on 3/31/2018.
 */

public final class IncludesContract {
    private IncludesContract(){}

    public static final String INCLUDES_TABLE = "includes";
    public static final String COL_1 = "UserID";
    public static final String COL_2 = "GroupID";
    public static final String COL_3 = "Role";

    public static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + INCLUDES_TABLE + " (" +
                    COL_1 + " INTEGER, " +
                    COL_2 + " INTEGER, " +
                    COL_3 + " TEXT, " +
                    "FOREIGN KEY(" + COL_1 + ") REFERENCES users(ID), " +
                    "FOREIGN KEY(" + COL_2 + ") REFERENCES groups(ID));";
                    //the foriegn key constraints are formatted poorly, fix them with references to the actual variables

    public static final String SQL_DELETE_TABLE =
            "DROP TABLE IF EXISTS " + INCLUDES_TABLE;
}
