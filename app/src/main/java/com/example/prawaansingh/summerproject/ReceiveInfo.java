package com.example.prawaansingh.summerproject;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ReceiveInfo extends Activity {

    TextView txtName;
    TextView txtPrice;
    TextView txtDesc;
    EditText txtCreatedAt;
    String description;
    String price;
    String name;
    JSONObject product;
    public String id;
    Button list;
    public String n;
    public String d;

    public String pid;

    // Progress Dialog
    private ProgressDialog pDialog;

    // JSON parser class
    JSONParser jsonParser = new JSONParser();

    // single product url
    private static final String url_product_details = "http://192.168.0.103/android_connect/get_product_details.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PRODUCT = "product";
    private static final String TAG_PID = "pid";
    private static final String TAG_NAME = "name";
    private static final String TAG_PRICE = "price";
    private static final String TAG_DESCRIPTION = "description";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receive_info);

        // getting product details from intent
        Intent i = getIntent();
        id = i.getStringExtra("ID");

        //txtPrice = (EditText) findViewById(R.id.inputPrice);
        //txtPrice.setText(id);

        // Getting complete product details in background thread
        ////////////////////new GetProductDetails().execute();

        list = (Button)findViewById(R.id.list);
        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /////////////Intent i = new Intent(ReceiveInfo.this , ListView.class);
                /////////////startActivity(i);
                new GetProductDetails().execute();
            }
        });
    }

    class GetProductDetails extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ReceiveInfo.this);
            pDialog.setMessage("Loading product details. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
            pid = id;
        }


        protected String doInBackground(String... params) {

            // updating UI from Background Thread
            /*runOnUiThread(new Runnable() {
                public void run() {
                    // Check for success tag
                    int success;
                    try {
                        // Building Parameters
                        List<NameValuePair> params = new ArrayList<NameValuePair>();
                        params.add(new BasicNameValuePair("pid", pid));

                        // getting product details by making HTTP request
                        // Note that product details url will use GET request
                        JSONObject json = jsonParser.makeHttpRequest(
                                url_product_detials, "GET", params);

                        // check your log for json response
                        Log.d("Single Product Details", json.toString());

                        // json success tag
                        success = json.getInt(TAG_SUCCESS);
                        if (success == 1) {
                            // successfully received product details
                            JSONArray productObj = json
                                    .getJSONArray(TAG_PRODUCT); // JSON Array

                            // get first product object from JSON Array
                            product = productObj.getJSONObject(0);

                            // product with this pid found
                            // Edit Text
                            txtName = (EditText) findViewById(R.id.inputName);
                            txtPrice = (EditText) findViewById(R.id.inputPrice);
                            txtDesc = (EditText) findViewById(R.id.inputDesc);

                            // display product data in EditText
                            txtName.setText(product.getString(TAG_NAME));
                            txtPrice.setText(product.getString(TAG_PRICE));
                            txtDesc.setText(product.getString(TAG_DESCRIPTION));

                        }else{
                            // product with pid not found
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });*/

            int success;
            try {
                // Building Parameters
                List<NameValuePair> newparams = new ArrayList<NameValuePair>();
                newparams.add(new BasicNameValuePair("pid", pid));

                // getting product details by making HTTP request
                // Note that product details url will use GET request
                JSONObject json = jsonParser.makeHttpRequest(
                        url_product_details, "GET", newparams);

                // check your log for json response
                Log.d("Single Product Details", json.toString());

                // json success tag
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    // successfully received product details
                    JSONArray productObj = json
                            .getJSONArray(TAG_PRODUCT); // JSON Array

                    // get first product object from JSON Array
                    product = productObj.getJSONObject(0);

                    // product with this pid found
                    // Edit Text
                    /*txtName = (EditText) findViewById(R.id.inputName);
                    txtPrice = (EditText) findViewById(R.id.inputPrice);
                    txtDesc = (EditText) findViewById(R.id.inputDesc);

                    // display product data in EditText
                    txtName.setText(product.getString(TAG_NAME));
                    txtPrice.setText(product.getString(TAG_PRICE));
                    txtDesc.setText(product.getString(TAG_DESCRIPTION));*/

                }else{
                    // product with pid not found
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }



        protected void onPostExecute(String file_url) {

            txtName = (EditText) findViewById(R.id.inputName);
            txtPrice = (EditText) findViewById(R.id.inputPrice);
            txtDesc = (EditText) findViewById(R.id.inputDesc);

            // display product data in EditText
            try {
                txtName.setText(product.getString(TAG_NAME));
                txtPrice.setText(product.getString(TAG_PRICE));
                txtDesc.setText(product.getString(TAG_DESCRIPTION));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            // dismiss the dialog once got all details
            pDialog.dismiss();
        }
    }


    /*class GetProductDetails extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ReceiveInfo.this);
            pDialog.setMessage("Loading product details. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
            //////////////////////pid = "500";
        }


        protected String doInBackground(String... params) {

            Toast.makeText(ReceiveInfo.this , "background thread entered" , Toast.LENGTH_SHORT).show();

            int  success;
            try {

                Toast.makeText(ReceiveInfo.this , "try entered" , Toast.LENGTH_SHORT).show();

                // Building Parameters
                List<NameValuePair> newparams = new ArrayList<NameValuePair>();

                Toast.makeText(ReceiveInfo.this , "first entered" , Toast.LENGTH_SHORT).show();

                newparams.add(new BasicNameValuePair("pid", "500"));

                Toast.makeText(ReceiveInfo.this , "second entered" , Toast.LENGTH_SHORT).show();

                // getting product details by making HTTP request
                // Note that product details url will use GET request
                JSONObject json = jsonParser.makeHttpRequest(
                        url_product_details, "GET", newparams);

                Toast.makeText(ReceiveInfo.this , "third entered" , Toast.LENGTH_SHORT).show();

                // check your log for json response
                Log.d("Single Product Details", json.toString());

                // json success tag
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {

                    Toast.makeText(ReceiveInfo.this , "fourth entered" , Toast.LENGTH_SHORT).show();

                    // successfully received product details
                    JSONArray productObj = json
                            .getJSONArray(TAG_PRODUCT); // JSON Array

                    Toast.makeText(ReceiveInfo.this , "fifth entered" , Toast.LENGTH_SHORT).show();

                    // get first product object from JSON Array
                    product = productObj.getJSONObject(0);

                    Toast.makeText(ReceiveInfo.this , "sixth entered" , Toast.LENGTH_SHORT).show();

                }else{
                    // product with pid not found
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }



        protected void onPostExecute(String file_url) {

            txtName = (TextView) findViewById(R.id.inputName);
            txtPrice = (TextView) findViewById(R.id.inputPrice);
            txtDesc = (TextView) findViewById(R.id.inputDesc);

            // display product data in EditText
            try {
                n = product.getString(TAG_NAME);
                d = product.getString(TAG_DESCRIPTION);

                txtName.setText(product.getString(TAG_NAME));
                txtPrice.setText(product.getString(TAG_PRICE));
                txtDesc.setText(product.getString(TAG_DESCRIPTION));

                //saving the product details into database
                saveProductDetails(n , d);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            // dismiss the dialog once got all details
            pDialog.dismiss();
        }
    }*/

    /*public void saveProductDetails()  {

        Toast.makeText(ReceiveInfo.this , "entered" , Toast.LENGTH_LONG).show();

        boolean didItWork = true;
        try {

            Toast.makeText(ReceiveInfo.this , "try" , Toast.LENGTH_LONG).show();
            txtName = (TextView) findViewById(R.id.inputName);
            txtPrice = (TextView) findViewById(R.id.inputPrice);
            txtDesc = (TextView) findViewById(R.id.inputDesc);

            Toast.makeText(ReceiveInfo.this , "done" , Toast.LENGTH_SHORT).show();

            String name = txtName.getText().toString();
            String price = txtPrice.getText().toString();
            String description = txtDesc.getText().toString();

            Toast.makeText(ReceiveInfo.this , "made" , Toast.LENGTH_SHORT).show();

            Contact entry = new Contact(ReceiveInfo.this);
            Toast.makeText(ReceiveInfo.this , "relation" , Toast.LENGTH_SHORT).show();
            entry.open();
            Toast.makeText(ReceiveInfo.this , "opened" , Toast.LENGTH_SHORT).show();
            entry.createEntry("how" , "34134" , "hello");
            Toast.makeText(ReceiveInfo.this , "added" , Toast.LENGTH_SHORT).show();
            entry.close();
        }catch(Exception e)  {
            Toast.makeText(ReceiveInfo.this , "catch" , Toast.LENGTH_SHORT).show();
            didItWork = false;
        }finally{
            if(didItWork)  {
                Dialog d = new ProgressDialog(this);
                d.setTitle("hello");
                TextView tv = new TextView(this);
                tv.setText("success");
                d.setContentView(tv);
                d.show();
                Toast.makeText(ReceiveInfo.this , "done" , Toast.LENGTH_SHORT).show();
            }
            else Toast.makeText(ReceiveInfo.this , "failed" , Toast.LENGTH_SHORT).show();
        }
    }*/

    public void saveProductDetails(String n , String d)  {

        DatabaseHandler db = new DatabaseHandler(this);
        int isPresent = 0;

        // Reading all contacts
        Log.d("Reading: ", "Reading all contacts..");
        List<Contact> contacts = db.getAllContacts();

        for (Contact cn : contacts) {
            String log = cn.getName();
            if(log.equals(n))  {isPresent = 1;}
        }

        if(isPresent == 0)   {
            Log.d("Insert: ", "Inserting ..");
            db.addContact(new Contact(n , d));
            Toast.makeText(ReceiveInfo.this , "done saving" , Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(ReceiveInfo.this , "already present" , Toast.LENGTH_SHORT).show();
        }
    }
}

