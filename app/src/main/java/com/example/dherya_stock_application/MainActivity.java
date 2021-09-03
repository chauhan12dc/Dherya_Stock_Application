package com.example.dherya_stock_application;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyTask myTask = new MyTask();
        myTask.execute();
    }
   public class MyTask extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... strings) {
            String result = "";
            try {
                URL url = new URL("https://71iztxw7wh.execute-api.us-east-1.amazonaws.com/interview/favorite-stocks");
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();


                if (httpsURLConnection.getResponseCode() == 200)
                {
                    InputStream inputStream = httpsURLConnection.getInputStream();
                    InputStreamReader isw = new InputStreamReader(inputStream);

                    int data = isw.read();


                    while (data != -1) {
                        result += (char) data;
                        data = isw.read();

                    }

                    // return the data to onPostExecute method
                    return result;
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {

            try {
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

