package com.example.prawaansingh.summerproject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class AllInOne extends Activity {

    //buttons in the layout
    Button submit;
    Button save;
    Button logout;
    Button receive;
    Button go;
    Button open;
    String ID = "3";

    // Progress Dialog
    private ProgressDialog pDialog;

    //json parser and other thing in the layout
    JSONParser jsonParser = new JSONParser();
    public EditText inputName;
    public EditText inputPrice;
    public EditText inputDesc;
    public EditText inputId;
    public String name ;
    public String price ;
    public String description ;
    public String id;
    String SelfID = "500";
    String pid;
    //TextView t;

    //for the bluetooth
    BluetoothAdapter mBluetoothAdapter;
    BluetoothSocket mmSocket;
    BluetoothDevice mmDevice;
    OutputStream mmOutputStream;
    InputStream mmInputStream;
    Thread workerThread;
    byte[] readBuffer;
    int readBufferPosition;
    volatile boolean stopWorker;

    // url to create new product
    private static String url_create_product = "http://192.168.0.103/android_connect/create_product.php";

    // single product url
    private static final String url_product_detials = "http://192.168.0.103/android_connect/get_product_details.php";

    // url to update product
    private static final String url_update_product = "http://192.168.0.103/android_connect/update_product.php";

    // url to delete product
    private static final String url_delete_product = "http://192.168.0.103/android_connect/delete_product.php";

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
        setContentView(R.layout.all_in_one);

        //getting reference to the text fields
        inputName = ((EditText) findViewById(R.id.inputName));
        inputPrice = ((EditText) findViewById(R.id.inputPrice));
        inputDesc = ((EditText) findViewById(R.id.inputDesc));
        inputId = (EditText)findViewById(R.id.tag);
        //t = (TextView)findViewById(R.id.t);

        //for saving the data
        //create instance of sharedPreferences called settings
        //MyPrefs is the name of the preference file
        //0 will allow us to write to the preferences
        SharedPreferences settings = getSharedPreferences("MyPrefs" , 0);
        inputId.setText(settings.getString("IDValue" , ""));
        inputName.setText(settings.getString("nameValue" , ""));
        inputPrice.setText(settings.getString("priceValue", ""));
        inputDesc.setText(settings.getString("descriptionValue", ""));

        //for buttons
        submit = (Button)findViewById(R.id.btnSubmit);
        save = (Button)findViewById(R.id.btnSave);
        logout = (Button)findViewById(R.id.btnLogout);
        receive = (Button)findViewById(R.id.btnReceive);
        go = (Button)findViewById(R.id.btnGo);
        open = (Button)findViewById(R.id.btnOpen);

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AllInOne.this , ListView.class);
                startActivity(i);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CreateNewProduct().execute();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AllInOne.this , InitialScreen.class);
                startActivity(i);
            }
        });

        receive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //t.setText(ID);
                Intent i = new Intent(AllInOne.this , ReceiveInfo.class);
                i.putExtra("ID" , "500");
                startActivityForResult(i , 100);
            }
        });

        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    findBT();
                    openBT();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SaveProductDetails().execute();
            }
        });
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    class CreateNewProduct extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {

            //inputId = ((EditText)findViewById(R.id.id));
            inputName = ((EditText) findViewById(R.id.inputName));
            inputPrice = ((EditText) findViewById(R.id.inputPrice));
            inputDesc = ((EditText) findViewById(R.id.inputDesc));

            //id = inputId.getText().toString();
            name = inputName.getText().toString();
            price = inputPrice.getText().toString();
            description = inputDesc.getText().toString();

            super.onPreExecute();
            pDialog = new ProgressDialog(AllInOne.this);
            pDialog.setMessage("Creating Product..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         * */
        protected String doInBackground(String... args) {

            // Building Parameters
            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            //params.add(new BasicNameValuePair("id", id));
            params.add(new BasicNameValuePair("name", name));
            params.add(new BasicNameValuePair("price", price));
            params.add(new BasicNameValuePair("description", description));

            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_create_product,
                    "POST", params);

            // check log cat fro response
        /*Log.d("Create Response", json.toString());

        // check for success tag
        try {
            int success = json.getInt(TAG_SUCCESS);

            if (success == 1) {
                // successfully created product
                Intent i = new Intent(getApplicationContext(), AllProductsActivity.class);
                startActivity(i);

                // closing this screen
                finish();
            } else {
                // failed to create product
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }*/

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();
        }

    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    class SaveProductDetails extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {

            inputName = ((EditText) findViewById(R.id.inputName));
            inputPrice = ((EditText) findViewById(R.id.inputPrice));
            inputDesc = ((EditText) findViewById(R.id.inputDesc));

            name = inputName.getText().toString();
            price = inputPrice.getText().toString();
            description = inputDesc.getText().toString();
            pid = SelfID;

            super.onPreExecute();
            pDialog = new ProgressDialog(AllInOne.this);
            pDialog.setMessage("Saving product ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        protected String doInBackground(String... args) {

            // getting updated data from EditTexts
            //String name = txtName.getText().toString();
            //String price = txtPrice.getText().toString();
            //String description = txtDesc.getText().toString();

            // Building Parameters
            List<NameValuePair> newParams = new ArrayList<NameValuePair>();
            newParams.add(new BasicNameValuePair(TAG_PID, pid));
            newParams.add(new BasicNameValuePair(TAG_NAME, name));
            newParams.add(new BasicNameValuePair(TAG_PRICE, price));
            newParams.add(new BasicNameValuePair(TAG_DESCRIPTION, description));

            // sending modified data through http request
            // Notice that update product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_update_product,
                    "POST", newParams);

            // check json success tag
        /*try {
            int success = json.getInt(TAG_SUCCESS);

            if (success == 1) {
                // successfully updated
                Intent i = getIntent();
                // send result code 100 to notify about product update
                setResult(100, i);
                finish();
            } else {
                // failed to update product
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }*/

            return null;
        }

        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product uupdated
            pDialog.dismiss();
        }
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    void findBT() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Toast.makeText(AllInOne.this, "No bluetooth adapter available", Toast.LENGTH_SHORT).show();
        }

        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBluetooth, 0);
        }

        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                if (device.getName().equals("HC-05")) {
                    mmDevice = device;
                    break;
                }
            }
        }
        Toast.makeText(AllInOne.this, "Bluetooth Device Found", Toast.LENGTH_SHORT).show();
    }

    void openBT() throws IOException {
        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"); //Standard SerialPortService ID
        mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);
        mmSocket.connect();
        mmOutputStream = mmSocket.getOutputStream();
        mmInputStream = mmSocket.getInputStream();
        Toast.makeText(AllInOne.this , "Bluetooth connected" , Toast.LENGTH_SHORT).show();
        beginListenForData();
    }

    String beginListenForData()
    {
        final Handler handler = new Handler();
        final byte delimiter = 97; //This is the ASCII code for a newline character

        stopWorker = false;
        readBufferPosition = 0;
        readBuffer = new byte[1024];
        workerThread = new Thread(new Runnable()
        {
            public void run()
            {
                while(!Thread.currentThread().isInterrupted() && !stopWorker)
                {
                    try
                    {
                        int bytesAvailable = mmInputStream.available();
                        if(bytesAvailable > 0)
                        {
                            byte[] packetBytes = new byte[bytesAvailable];
                            mmInputStream.read(packetBytes);
                            for(int i=0;i<bytesAvailable;i++)
                            {
                                byte b = packetBytes[i];
                                if(b == delimiter)
                                {
                                    byte[] encodedBytes = new byte[readBufferPosition];
                                    System.arraycopy(readBuffer, 0, encodedBytes, 0, encodedBytes.length);
                                    final String data = new String(encodedBytes, "US-ASCII");
                                    readBufferPosition = 0;

                                    handler.post(new Runnable()
                                    {
                                        public void run()
                                        {
                                            ID = data;

                                        }
                                    });
                                }
                                else
                                {
                                    readBuffer[readBufferPosition++] = b;
                                }
                            }
                        }
                    }
                    catch (IOException ex)
                    {
                        stopWorker = true;
                    }
                }
            }
        });

        workerThread.start();
        return ID;
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences settings = getSharedPreferences("MyPrefs" , 0);

        //to make changes we need to get the editor
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("IDValue" , inputId.getText().toString());
        editor.putString("nameValue" , inputName.getText().toString());
        editor.putString("priceValue", inputPrice.getText().toString());
        editor.putString("descriptionValue" , inputDesc.getText().toString());
        editor.commit();
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}

