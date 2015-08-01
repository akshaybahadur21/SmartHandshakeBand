package com.example.prawaansingh.summerproject;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLException;

public class Contact {

    //private variables
    int _id;
    String _name;
    String _desc;

    // Empty constructor
    public Contact(){

    }
    // constructor
    public Contact(int id, String name, String _desc){
        this._id = id;
        this._name = name;
        this._desc = _desc;
    }

    // constructor
    public Contact(String name, String _desc){
        this._name = name;
        this._desc = _desc;
    }
    // getting ID
    public int getID(){
        return this._id;
    }

    // setting id
    public void setID(int id){
        this._id = id;
    }

    // getting name
    public String getName(){
        return this._name;
    }

    // setting name
    public void setName(String name){
        this._name = name;
    }

    // getting phone number
    public String getDesc(){
        return this._desc;
    }

    // setting phone number
    public void setDesc(String desc){
        this._desc = desc;
    }


}
