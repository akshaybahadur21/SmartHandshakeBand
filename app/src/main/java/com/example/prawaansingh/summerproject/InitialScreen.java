package com.example.prawaansingh.summerproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;


public class InitialScreen extends Activity {

    //DatabaseHelper db = new DatabaseHelper(this);
    String str;
    String pass;
    Button login;
    Button signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_screen);

        final EditText a = (EditText) findViewById(R.id.TFusername);
        str = a.getText().toString();
        final EditText b = (EditText) findViewById(R.id.TFpassword);
        pass = b.getText().toString();

        login = (Button) findViewById(R.id.Blogin);
        signup = (Button) findViewById(R.id.Bsignup);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(InitialScreen.this , AllInOne.class);
                startActivityForResult(i , 100);
            }
        });




    }
}
