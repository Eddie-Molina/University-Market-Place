package com.example.morri.messingaround;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.util.Date;


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
        db.execSQL(CurrentUserContract.SQL_CREATE_TABLE);
        db.execSQL(SMSMessagesContract.SQL_CREATE_TABLE);
        db.execSQL(AnnouncementContract.SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //For each new table, add the drop table statement here
        db.execSQL(RegisterContract.SQL_DELETE_TABLE);
        db.execSQL(GroupContract.SQL_DELETE_TABLE);
        db.execSQL(IncludesContract.SQL_DELETE_TABLE);
        db.execSQL(ItemServiceContract.SQL_DELETE_TABLE);
        db.execSQL(CurrentUserContract.SQL_DELETE_TABLE);
        db.execSQL(AnnouncementContract.SQL_DELETE_TABLE);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public boolean setCurrentUser(int id, String fname, String lname, String email, String phoneNo, String studentID, String username,
                                  String password){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(CurrentUserContract.COL_1, id);
        values.put(CurrentUserContract.COL_2, fname);
        values.put(CurrentUserContract.COL_3, lname);
        values.put(CurrentUserContract.COL_4, email);
        values.put(CurrentUserContract.COL_5, phoneNo);
        values.put(CurrentUserContract.COL_6, studentID);
        values.put(CurrentUserContract.COL_7, username);
        values.put(CurrentUserContract.COL_8, password);
        long result = db.insert(CurrentUserContract.CURRENT_USER_TABLE, null, values);
        if(result == -1){
            return false;
        } else {
            return true;
        }
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
    public boolean insertGroup(String name, int AdminID, String description){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        Date date = new Date();
        values.put(GroupContract.COL_2, name);
        values.put(GroupContract.COL_3, date.toString());
        values.put(GroupContract.COL_4, AdminID);
        values.put(GroupContract.COL_5, description);
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

    

    public static byte[] getBitmapAsByteArray(Bitmap bitmap){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }

    //Creates a brand new item/service by manually inserting all of the necessary information
    public boolean insertItem(String name, String description, int quantity, double price, int sellerID, Bitmap img){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        Date date = new Date();
        byte[] image = getBitmapAsByteArray(img);
        values.put(ItemServiceContract.COL_2, name);
        values.put(ItemServiceContract.COL_3, description);
        values.put(ItemServiceContract.COL_4, date.toString());
        values.put(ItemServiceContract.COL_5, quantity);
        values.put(ItemServiceContract.COL_6, price);
        values.put(ItemServiceContract.COL_7, sellerID);
        values.put(ItemServiceContract.COL_8, image);
        long result = db.insert(ItemServiceContract.ITEMS_TABLE, null, values);
        if(result == -1){
            return false;
        } else {
            return true;
        }
    }

    public boolean insertAnnouncement(int GroupID, String message, String subject){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        Date date = new Date();
        String dateStr = date.toString();
        String groupIDstr = Integer.toString(GroupID);
        values.put(AnnouncementContract.COL_2, groupIDstr);
        values.put(AnnouncementContract.COL_3, message);
        values.put(AnnouncementContract.COL_4, subject);
        values.put(AnnouncementContract.COL_5, dateStr);
        long result = db.insert(AnnouncementContract.TABLE_NAME, null, values);
        if(result == -1){
            return false;
        } else {
            return true;
        }
    }
	
	public Bitmap getItemImage(int i){
        SQLiteDatabase db = this.getReadableDatabase();
        String idStr = Integer.toString(i);
        String query = "SELECT IMAGE FROM " + ItemServiceContract.ITEMS_TABLE + " WHERE ID = ?";
        Cursor res = db.rawQuery(query, new String[]{idStr});
        if (res.moveToNext()){
            byte[] imgByte = res.getBlob(0);
            return BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length);
        }
        return null;
    }

    public boolean put_new_message(String number, String message) {///jakes method for putting messages into db

        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(SMSMessagesContract.COL_2, number);
        values.put(SMSMessagesContract.COL_3, message);
        long result = db.insert(SMSMessagesContract.USER_TABLE, null, values);
        if (result == -1)
        {
            return false;
        }
        else {
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

    public Cursor getCurrentUser(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + CurrentUserContract.CURRENT_USER_TABLE, null);
        return res;
    }

    public Cursor getAnnouncements(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + AnnouncementContract.TABLE_NAME, null);
        return res;
    }
	
	public Cursor getUserGroups(int userID){
        SQLiteDatabase db = this.getReadableDatabase();
        String userIDstr = Integer.toString(userID);
        String query = "SELECT G.ID, I.Role FROM " + GroupContract.GROUP_TABLE + " AS G JOIN " + IncludesContract.INCLUDES_TABLE +
                " AS I ON G.ID = I.GroupID WHERE I.UserID = ?";
        Cursor res = db.rawQuery(query, new String[]{userIDstr});
        return res;
    }

    //Returns all of the username-password tuples from the users table. Each row represents a single user's data.
    public Cursor getUserLogin(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT " + RegisterContract.COL_7 + ", " + RegisterContract.COL_8 + " FROM " + RegisterContract.USER_TABLE, null);
        return res;
    }

    //Returns all of the User IDs for users that are members of the group specified by the group name.
    public Cursor getGroupMemberShip(int groupID, int currentUserID){
        SQLiteDatabase db = this.getReadableDatabase();
        String groupIDstr = Integer.toString(groupID);
        String userID = Integer.toString(currentUserID);
        String query = "SELECT U.Fname, U.Lname, U.Username FROM " + RegisterContract.USER_TABLE + " AS U JOIN " +
                IncludesContract.INCLUDES_TABLE + " AS I ON U.ID = I.UserID WHERE I.GroupID = ? AND I.UserID != ?";
        Cursor res = db.rawQuery(query, new String[]{groupIDstr, userID});
        return res;
    }

    public Cursor getJoinClub(String name, String userID){
        SQLiteDatabase db = this.getReadableDatabase();
        String formatSearch = "%" + name + "%";
        String query = "SELECT G.Name FROM " + GroupContract.GROUP_TABLE + " AS G WHERE G.ID NOT IN (SELECT DISTINCT I.GroupID" +
                " FROM " + IncludesContract.INCLUDES_TABLE + " AS I WHERE I.UserID = ?) AND G.Name LIKE ?";
        Cursor res = db.rawQuery(query, new String[]{userID, formatSearch});
        return res;
    }

    public Cursor getInvites(int userID){
        SQLiteDatabase db = this.getReadableDatabase();
        String userIDstr = Integer.toString(userID);
        String query = "SELECT G.Name FROM " + GroupContract.GROUP_TABLE + " AS G JOIN " + IncludesContract.INCLUDES_TABLE +
                " AS I ON G.ID = I.GroupID WHERE I.UserID = ? AND I.Role = 'Invited'";
        Cursor res = db.rawQuery(query, new String[]{userIDstr});
        return res;
    }

    public Cursor getInvitableUsers(String search, int groupID){
        SQLiteDatabase db = this.getReadableDatabase();
        String formatSearch = "%" + search + "%";
        String groupIDstr = Integer.toString(groupID);
        String query = "SELECT Fname, Lname, Username FROM " + RegisterContract.USER_TABLE + " WHERE ID NOT IN (SELECT UserID FROM " +
                IncludesContract.INCLUDES_TABLE + " WHERE GroupID = ?) AND (Fname LIKE ? OR Lname LIKE ?)";
        Cursor res = db.rawQuery(query, new String[]{groupIDstr, formatSearch, formatSearch});
        return res;
    }

    public Cursor get_messages(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + SMSMessagesContract.USER_TABLE;
        Cursor res = db.rawQuery(query, null);
        return res;
    }

    public Cursor getJoinRequests(int groupID){
        SQLiteDatabase db = this.getReadableDatabase();
        String groupIDstr = Integer.toString(groupID);
        String query = "SELECT U.Fname, U.Lname, U.Username FROM " + RegisterContract.USER_TABLE + " AS U JOIN " + IncludesContract.INCLUDES_TABLE +
                " AS I ON U.ID = I.UserID WHERE I.GroupID = ? AND I.Role = 'Requested'";
        Cursor res = db.rawQuery(query, new String[]{groupIDstr});
        return res;
    }

    public Cursor getRole(int userID, int groupID){
        SQLiteDatabase db = this.getReadableDatabase();
        String userIDstr = Integer.toString(userID);
        String groupIDstr = Integer.toString(groupID);
        String query = "SELECT Role FROM " + IncludesContract.INCLUDES_TABLE + " WHERE UserID = ? AND GroupID = ?";
        Cursor res = db.rawQuery(query, new String[]{userIDstr, groupIDstr});
        return res;
    }

    //Returns the results of searching the group specified by the group name by users' first or last name
    public Cursor searchMembership(String search, int groupID, int userID){
        SQLiteDatabase db = this.getReadableDatabase();
        String formatSearch = "%" + search + "%";
        String groupIDstr = Integer.toString(groupID);
        String userIDstr = Integer.toString(userID);
        String query = " SELECT U.Fname, U.Lname, U.Username FROM " + RegisterContract.USER_TABLE + " AS U JOIN " +
                IncludesContract.INCLUDES_TABLE + " AS I ON U.ID = I.UserID WHERE I.GroupID = ? AND I.UserID != ? AND " +
                "(U.Fname LIKE ? OR U.Lname LIKE ? OR U.Username LIKE ?)";
        Cursor res = db.rawQuery(query, new String[]{groupIDstr, userIDstr, formatSearch, formatSearch, formatSearch});
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

    public Cursor searchUserItems(int userID, String search){
        SQLiteDatabase db= this.getReadableDatabase();
        String userIDstr = Integer.toString(userID);
        String formatSearch = "%" + search + "%";
        String query = "SELECT * FROM " + ItemServiceContract.ITEMS_TABLE + " WHERE SELLER = ? AND NAME LIKE ?";
        Cursor res = db.rawQuery(query, new String[]{userIDstr, formatSearch});
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
    public boolean updateGroup(String id, String name, String adminID, String description){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        if(!name.equals("")){
            values.put(GroupContract.COL_2, name);

        }
        if(!adminID.equals("")){
            values.put(GroupContract.COL_4, adminID);
        }
        if(!description.equals("")){
            values.put(GroupContract.COL_5, description);
        }
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
    public boolean updateItems(String id, String name, String description, String quantity, String price, Bitmap img){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        byte[] image = getBitmapAsByteArray(img);
        values.put(ItemServiceContract.COL_2, name);
        values.put(ItemServiceContract.COL_3, description);
        values.put(ItemServiceContract.COL_5, quantity);
        values.put(ItemServiceContract.COL_6, price);
        values.put(ItemServiceContract.COL_8, image);
        db.update(ItemServiceContract.ITEMS_TABLE, values, "ID = ?", new String[]{id});
        return true;
    }

    public boolean updateItemQuantity(int id, int quantity){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        String idStr = Integer.toString(id);
        String quantityStr = Integer.toString(quantity);
        values.put(ItemServiceContract.COL_5, quantityStr);
        db.update(ItemServiceContract.ITEMS_TABLE, values, "ID = ?", new String[]{idStr});
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
    public Integer removeUserFromGroup (String userID, String groupID){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(IncludesContract.INCLUDES_TABLE, "UserID = ? AND GroupID = ?", new String[]{userID, groupID});
    }

    public Integer deleteGroupMembership (String groupID){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(IncludesContract.INCLUDES_TABLE, "GroupID = ?", new String[]{groupID});
    }

    //Deletes the item/service with the associated ID from the items table
    public Integer deleteItem(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.delete(ItemServiceContract.ITEMS_TABLE, "ID = ?", new String[]{id});
    }

    public Integer deleteAnnouncement(String id){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.delete(AnnouncementContract.TABLE_NAME, "ID = ?", new String[]{id});
    }

    public Integer resetCurrentUser(){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.delete(CurrentUserContract.CURRENT_USER_TABLE, null, null);
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
