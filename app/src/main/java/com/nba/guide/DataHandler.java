package com.nba.guide;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import java.util.ArrayList;
import java.util.List;


public class DataHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "healthCenternew";

    // Contacts table name
    private static final String TABLE_CONTACTS = "notification";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "pushnotification";
    private static final String KEY_TIME = "time";
    private static final String KEY_READ = "read";

    public DataHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT," +  KEY_TIME + " TEXT,"+KEY_READ+ " TEXT " + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }


    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new contact
    public void addContact(NotificationModel notificationModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_READ, notificationModel.getId());// Contact Name
        values.put(KEY_NAME, notificationModel.getDescription());
        values.put(KEY_TIME, notificationModel.getNotification_date());
        // values.put(KEY_PH_NO, contact.getPhoneNumber()); // Contact Phone
        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        db.close(); // Closing database connection
    }
    //
//    // Getting single contact
//    Contact getContact(int id) {
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
//                        KEY_NAME, KEY_PH_NO }, KEY_ID + "=?",
//                new String[] { String.valueOf(id) }, null, null, null, null);
//        if (cursor != null)
//            cursor.moveToFirst();
//
//        Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),
//                cursor.getString(1), cursor.getString(2));
//        // return contact
//        return contact;
//    }
//
    // Getting All Contacts
    public List<NotificationModel> getAllContacts() {
        List<NotificationModel> contactList = new ArrayList<NotificationModel>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                NotificationModel notificationModel = new NotificationModel();
                notificationModel.setRemove(cursor.getString((cursor.getColumnIndex(KEY_ID))));
                notificationModel.setId(cursor.getString((cursor.getColumnIndex(KEY_READ))));
                notificationModel.setDescription(cursor.getString((cursor.getColumnIndex(KEY_NAME))));
                notificationModel.setNotification_date(cursor.getString((cursor.getColumnIndex(KEY_TIME))));
               // healthCenterModel.setRead(cursor.getString(3));
                // Adding contact to list
                contactList.add(notificationModel);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }


//    public Cursor getinformation(SQLiteDatabase db){
//        Cursor cursor;
//        String[] projections={KEY_NAME};
//        cursor= db.query(UserContract.NewUserInfo.TABLE_NAME, projections, null, null, null, null, null);
//        return cursor;
//    }


//
    // Updating single contact
//    public int updateContact(HealthCenterModel healthCenterModel) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(KEY_READ, healthCenterModel.getRead());
//
//        // updating row
//        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
//                new String[] { String.valueOf(healthCenterModel.getid()) });
//    }
//
//    public void updateIsRead(HealthCenterModel healthCenterModel) {
//
//        String countQuery = "UPDATE " + TABLE_CONTACTS + " SET " + KEY_READ + " = " + "1" + " WHERE " + KEY_ID + " = " + healthCenterModel.getid();
//        SQLiteDatabase db = this.getReadableDatabase();
//        db.execSQL(countQuery);
//    }



////
    // Deleting single contact
    public void deleteContact(NotificationModel notificationModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
                new String[] { String.valueOf(notificationModel.getRemove()) });
        db.close();
    }


    public void deleteAll()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        // db.delete(TABLE_NAME,null,null);
        //db.execSQL("delete * from"+ TABLE_NAME);
//        db.execSQL("DROP TABLE IF EXISTS TABLE_CONTACTS");
        db.execSQL("delete from " + TABLE_CONTACTS);
        db.close();
    }



//
//
//    // Getting contacts Count
//    public int getContactsCount() {
//        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(countQuery, null);
//        cursor.close();

    // return count
    //  return cursor.getCount();
}
