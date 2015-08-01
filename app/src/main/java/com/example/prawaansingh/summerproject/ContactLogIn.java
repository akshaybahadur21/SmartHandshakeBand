package com.example.prawaansingh.summerproject;

import android.app.Activity;

/**
 * Created by Sony on 25-05-2015.
 */
public class ContactLogIn {


    //private variables
    int _id;
    String _uname;
    String _pass;

    // Empty constructor
    public ContactLogIn(){

    }
    // constructor
    public ContactLogIn(int id, String uname, String _pass){
        this._id = id;
        this._uname = uname;
        this._pass = _pass;
    }

    // constructor
    public ContactLogIn(String uname, String _pass){
        this._uname = uname;
        this._pass = _pass;
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
    public String getUname(){
        return this._uname;
    }

    // setting name
    public void setUname(String uname){
        this._uname = uname;
    }

    // getting phone number
    public String getPass(){
        return this._pass;
    }

    // setting phone number
    public void setPass(String pass){
        this._pass = pass;
    }


}
