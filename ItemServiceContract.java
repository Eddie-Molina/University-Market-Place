package com.example.morri.messingaround;

/**
 * Created by morri on 3/31/2018.
 */

public final class ItemServiceContract {
    private ItemServiceContract(){}

    public static final String ITEMS_TABLE = "items_services";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "Name";
    public static final String COL_3 = "Description";
    public static final String COL_4 = "ListDate";
    public static final String COL_5 = "Quantity";
    public static final String COL_6 = "Price";
    public static final String COL_7 = "Seller";
    public static final String COL_8 = "Image";

    public static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + ITEMS_TABLE + " (" +
                    COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_2 + " TEXT, " +
                    COL_3 + " TEXT, " +
                    COL_4 + " TEXT, " +
                    COL_5 + " INTEGER, " +
                    COL_6 + " REAL, " +
                    COL_7 + " INTEGER, " +
                    COL_8 + " BLOB);"; //+
    public static final String SQL_DELETE_TABLE =
            "DROP TABLE IF EXISTS " + ITEMS_TABLE;
}
