package com.example.prawaansingh.summerproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper  {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 3;

    // Database Name
    private static final String DATABASE_NAME = "loginInfo";

    // Contacts table name
    private static final String TABLE_CONTACTS_LOGIN = "loginContact";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_UNAME = "uname";
    private static final String KEY_PASS = "pass";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_LOGIN_TABLE = "CREATE TABLE " + TABLE_CONTACTS_LOGIN + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_UNAME + " TEXT,"
                + KEY_PASS + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_LOGIN_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS_LOGIN);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new contact
    void addContactLogIn(ContactLogIn contactLogIn) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_UNAME, contactLogIn.getUname()); // Contact Name
        values.put(KEY_PASS, contactLogIn.getPass()); // Contact Phone

        // Inserting Row
        db.insert(TABLE_CONTACTS_LOGIN, null, values);
        db.close(); // Closing database connection
    }


    // Getting All Contacts
    public List<ContactLogIn> getAllContactsLogIn() {
        List<ContactLogIn> contactListLogin = new ArrayList<ContactLogIn>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS_LOGIN;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ContactLogIn contact = new ContactLogIn();
                contact.setID(Integer.parseInt(cursor.getString(0)));
                contact.setUname(cursor.getString(1));
                contact.setPass(cursor.getString(2));
                // Adding contact to list
                contactListLogin.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactListLogin;
    }

    // Updating single contact
    /*public int updateContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName());
        values.put(KEY_DESC, contact.getDesc());

        // updating row
        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getID()) });
    }*/

    // Deleting single contact
    /*public void deleteContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getID()) });
        db.close();
    }*/


    // Getting contacts Count
    /*public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }*/
}
