package com.example.morri.messingaround;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.Date;

/**
 * Created by morri on 3/24/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "UBS.db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //For each new table, add the create table statement here
        db.execSQL(RegisterContract.SQL_CREATE_TABLE);
        db.execSQL(GroupContract.SQL_CREATE_TABLE);
        db.execSQL(IncludesContract.SQL_CREATE_TABLE);
        db.execSQL(ItemServiceContract.SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //For each new table, add the drop table statement here
        db.execSQL(RegisterContract.SQL_DELETE_TABLE);
        db.execSQL(GroupContract.SQL_DELETE_TABLE);
        db.execSQL(IncludesContract.SQL_DELETE_TABLE);
        db.execSQL(ItemServiceContract.SQL_DELETE_TABLE);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    //Creates a brand new user by inserting by manually inserting all the associated data
    public boolean insertUser(String fname, String lname, String email, String phoneNo, String studentID, String username, String password,
                              String question1, String answer1, String question2, String answer2){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(RegisterContract.COL_2, fname);
        values.put(RegisterContract.COL_3, lname);
        values.put(RegisterContract.COL_4, email);
        values.put(RegisterContract.COL_5, phoneNo);
        values.put(RegisterContract.COL_6, studentID);
        values.put(RegisterContract.COL_7, username);
        values.put(RegisterContract.COL_8, password);
        values.put(RegisterContract.COL_9, question1);
        values.put(RegisterContract.COL_10, answer1);
        values.put(RegisterContract.COL_11, question2);
        values.put(RegisterContract.COL_12, answer2);
        long result = db.insert(RegisterContract.USER_TABLE, null, values);
        if(result == -1){
            return false;
        } else {
            return true;
        }
    }

    //Creates a brand new group by inserting group name and the user ID of the group creator
    public boolean insertGroup(String name, int AdminID){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        Date date = new Date();
        values.put(GroupContract.COL_2, name);
        values.put(GroupContract.COL_3, date.toString());
        values.put(GroupContract.COL_4, AdminID);
        long result = db.insert(GroupContract.GROUP_TABLE, null, values);
        if(result == -1){
            return false;
        } else {
            return true;
        }
    }

    //Adds a user to a group's member list by passing the user's id, the group's id, and the user's role in the group
    public boolean addUserToGroup(int userID, int groupID, String role){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(IncludesContract.COL_1, userID);
        values.put(IncludesContract.COL_2, groupID);
        values.put(IncludesContract.COL_3, role);
        long result = db.insert(IncludesContract.INCLUDES_TABLE, null, values);
        if(result == -1){
            return false;
        } else {
            return true;
        }
    }

    //Creates a brand new item/service by manually inserting all of the necessary information
    public boolean insertItem(String name, String description, int quantity, double price, int sellerID){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        Date date = new Date();
        values.put(ItemServiceContract.COL_2, name);
        values.put(ItemServiceContract.COL_3, description);
        values.put(ItemServiceContract.COL_4, date.toString());
        values.put(ItemServiceContract.COL_5, quantity);
        values.put(ItemServiceContract.COL_6, price);
        values.put(ItemServiceContract.COL_7, sellerID);
        long result = db.insert(ItemServiceContract.ITEMS_TABLE, null, values);
        if(result == -1){
            return false;
        } else {
            return true;
        }
    }

    //Returns all of the information from the Users table. Each row represents a different user.
    public Cursor getAllUserData(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + RegisterContract.USER_TABLE, null);
        return res;
    }

    //Returns all of the information from the Groups table. Each row represents a different group.
    public Cursor getAllGroupData(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + GroupContract.GROUP_TABLE, null);
        return res;
    }

    //Returns all of the information from the items/services table. Each row represents a different item.
    public Cursor getAllItemData(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + ItemServiceContract.ITEMS_TABLE, null);
        return res;
    }

    //Returns all of the username-password tuples from the users table. Each row represents a single user's data.
    public Cursor getUserLogin(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT " + RegisterContract.COL_7 + ", " + RegisterContract.COL_8 + " FROM " + RegisterContract.USER_TABLE, null);
        return res;
    }

    //Returns the results of searching the users table by a string that matches a user's first or last name.
    //Each row represents a different user that matched the search criteria.
    public Cursor searchUsers(String search){
        SQLiteDatabase db = this.getReadableDatabase();
        String formatSearch = "%" + search + "%";
        Cursor res = db.query(RegisterContract.USER_TABLE, null, "Fname LIKE ? OR Lname LIKE ?", new String[]{formatSearch,
                        formatSearch},
                null, null, null, null);
        return res;
    }

    //Returns the results of searching the groups table by a string that matches a group's name.
    //Each row represents a different group that matched the search criteria.
    public Cursor searchGroups(String search){
        SQLiteDatabase db = this.getReadableDatabase();
        String formatSearch = "%" + search + "%";
        Cursor res = db.query(GroupContract.GROUP_TABLE, null, "Name LIKE ?", new String[]{formatSearch}, null, null, null, null);
        return res;
    }

    //Returns the results of searching the items_services table by a string that matches an item's name.
    //Each row represents a different item/service that matched the search criteria.
    public Cursor searchItems(String search){
        SQLiteDatabase db = this.getReadableDatabase();
        String formatSearch = "%" + search + "%";
        Cursor res = db.query(ItemServiceContract.ITEMS_TABLE, null, "Name LIKE ?", new String[]{formatSearch}, null, null, null, null);
        return res;
    }

    //A generalizaed search method that takes a table name, a where condition string that contains a '?', and a string that fills the '?'
    //Each row represents a member from the specified table that matches the where/search condition
    public Cursor generalSearch(String tableName, String where, String search){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.query(tableName, null, where, new String[]{search}, null, null, null, null);
        return res;
    }

    //FIX THIS LATER!
    //Directly handles a SQL query as input
    public Cursor bloodyRawQuery(String query){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery(query, null);
        return res;
    }
    //fix this too
    //Takes a SQL query string along with an array of arguments to fill the '?'
    public Cursor rawQuery(String query, String[] arguments){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery(query, arguments);
        return res;
    }

    //Updates a user with the specified ID by passing in all the necessary values for updating the table.
    public boolean updateUser(String id, String fname, String lname, String email, String phoneNo,
                              String studentID, String username, String password, String question1,
                              String answer1, String question2, String answer2){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(RegisterContract.COL_2, fname);
        values.put(RegisterContract.COL_3, lname);
        values.put(RegisterContract.COL_4, email);
        values.put(RegisterContract.COL_5, phoneNo);
        values.put(RegisterContract.COL_6, studentID);
        values.put(RegisterContract.COL_7, username);
        values.put(RegisterContract.COL_8, password);
        values.put(RegisterContract.COL_9, question1);
        values.put(RegisterContract.COL_10, answer1);
        values.put(RegisterContract.COL_11, question2);
        values.put(RegisterContract.COL_12, answer2);
        db.update(RegisterContract.USER_TABLE, values, "ID = ?", new String[] {id});
        return true;
    }

    //Updates the current password for the specified user to the newPass.
    public boolean updatePassword(String username, String newPass){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(RegisterContract.COL_8, newPass);
        db.update(RegisterContract.USER_TABLE, values, "Username = ?", new String[] {username});
        return true;
    }

    //Updates the group specified by the ID by passing in all the necessary information for updating it.
    public boolean updateGroup(String id, String name, String adminID){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(GroupContract.COL_2, name);
        values.put(GroupContract.COL_4, adminID);
        db.update(GroupContract.GROUP_TABLE, values, "ID = ?", new String[]{id});
        return true;
    }

    //Updates the table of group membership with all of the necessary data.
    public boolean updateIncludes(String userID, String groupID, String role){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(IncludesContract.COL_3, role);
        db.update(IncludesContract.INCLUDES_TABLE, values, "UserID = ? AND GroupID = ?", new String[]{userID, groupID});
        return true;
    }

    //Updates the item with the specified id by providing all the necessary information
    public boolean updateItems(String id, String name, String description, String quantity, String price){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ItemServiceContract.COL_2, name);
        values.put(ItemServiceContract.COL_3, description);
        values.put(ItemServiceContract.COL_5, quantity);
        values.put(ItemServiceContract.COL_6, price);
        db.update(ItemServiceContract.ITEMS_TABLE, values, "ID = ?", new String[]{id});
        return true;
    }

    //Deletes the user with the associated ID from the users table
    public Integer deleteUser(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(RegisterContract.USER_TABLE, "ID = ?", new String[]{id});
    }

    //Deletes the group with the associated ID from the groups table
    public Integer deleteGroup(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(GroupContract.GROUP_TABLE, "ID = ?", new String[]{id});
    }

    //Deletes the user with the associated ID from a group's membership
    public Integer removeUserFromGroup (String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(IncludesContract.INCLUDES_TABLE, "UserID = ?", new String[]{id});
    }

    //Deletes the item/service with the associated ID from the items table
    public Integer deleteItem(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.delete(ItemServiceContract.ITEMS_TABLE, "ID = ?", new String[]{id});
    }

    //Displays a popup message with the given title and message.
    public void showMessage(String title, String message, Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
