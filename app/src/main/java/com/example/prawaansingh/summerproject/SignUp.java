package com.example.prawaansingh.summerproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class  SignUp extends Activity {

    DatabaseHelper db = new DatabaseHelper(this);
    EditText uname;
    EditText pass1;
    EditText pass2;
    String unamestr;
    String pass1str;
    String pass2str;
    Button signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        uname = (EditText) findViewById(R.id.TFuname);
        pass1 = (EditText) findViewById(R.id.TFpass1);
        pass2 = (EditText) findViewById(R.id.TFpass2);

        unamestr = uname.getText().toString();
        pass1str = pass1.getText().toString();
        pass2str = pass2.getText().toString();

        signUp = (Button)findViewById(R.id.Bsignupbutton);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (pass1str.equals(pass2str) == false) {
                    //pop up message
                    Toast pass = Toast.makeText(SignUp.this, "Passwords Don't Match!", Toast.LENGTH_SHORT);
                    pass.show();
                }
                else {

                    int isPresent = 0;

                    // Reading all contacts
                    Log.d("Reading: ", "Reading all contacts..");
                    List<ContactLogIn> contacts = db.getAllContactsLogIn();

                    for (ContactLogIn cn : contacts) {
                        String uname = cn.getUname();
                        String pass = cn.getPass();
                        if(uname.equals(unamestr) && pass.equals(pass1str))  {isPresent = 1;}
                    }

                    if(isPresent == 1)   {
                        Intent i = new Intent(SignUp.this , InitialScreen.class);
                        startActivity(i);
                    }
                    else  {
                        db.addContactLogIn(new ContactLogIn(unamestr, pass1str));
                        Toast.makeText(SignUp.this, "information saved", Toast.LENGTH_SHORT).show();

                        Intent i = new Intent(SignUp.this , InitialScreen.class);
                        startActivity(i);
                    }
                }
            }
        });
    }
}

