package com.example.prawaansingh.summerproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ListView extends ListActivity {

    DatabaseHandler db = new DatabaseHandler(this);
    String[] listItems = {"hello"};
    int i=0;
    //ListView lv;
    //TextView t;
    Button btn;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view);

        btn = (Button)findViewById(R.id.b);
        List<Contact> contacts = db.getAllContacts();

        for (Contact cn : contacts) {
            String log = cn.getName();
            if(!log.equals(null))   {
                listItems[i] = log;
                i++;
            }
            else {
                break;
            }
        }

        setListAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, listItems));

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(ListView.this , InitialScreen.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onListItemClick(android.widget.ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        DatabaseHandler db = new DatabaseHandler(this);

        /*AlertDialog.Builder myAlert = new AlertDialog.Builder(this);
        myAlert.setMessage((CharSequence) db.getContact((int) id)).create();
        myAlert.show();*/

        Log.d("Reading: ", "Reading all contacts..");
        List<Contact> contacts = db.getAllContacts();

        for(Contact cn : contacts)  {
            if((position+1) == cn.getID())   {
                Toast.makeText(ListView.this , "id matched" , Toast.LENGTH_SHORT).show();
                AlertDialog.Builder myAlert = new AlertDialog.Builder(this);
                myAlert.setMessage("Name: " + cn.getName() + "\n" + "Description: " + cn.getDesc()).create();
                myAlert.show();
            }
            else {
                Toast.makeText(ListView.this , "id not found" , Toast.LENGTH_SHORT).show();
            }
        }
    }
}





