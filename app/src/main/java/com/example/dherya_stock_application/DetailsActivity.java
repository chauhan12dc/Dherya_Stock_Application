package com.example.dherya_stock_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class DetailsActivity extends AppCompatActivity {

    String selectedName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = this.getIntent();

        selectedName =intent.getStringExtra("SelectedName");
        setTitle(selectedName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this,10000);
                MyTask myTask = new MyTask();
                myTask.execute();
            }
        },400);


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    public class MyTask extends AsyncTask<String,String,String> {

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

            ArrayList<Stocks> stocksArrayList = new ArrayList<>();
            ListView listView = findViewById(R.id.stock_list);
            try {
                JSONObject jsonObject = new JSONObject(s).getJSONObject(selectedName);
                Iterator<String> stringIterator = jsonObject.keys();
                Map<String,String> map = new HashMap<>();
                while (stringIterator.hasNext())
                {
                    String Key = stringIterator.next();
                    String Value = jsonObject.getString(Key).toString();

                    map.put(Key,Value);

                }
                ((TextView)findViewById(R.id.symbolName)).setText(selectedName);
                ((TextView)findViewById(R.id.fullName)).setText(map.get("name"));
                ((TextView)findViewById(R.id.currentPrice)).setText(map.get("price"));
                ((TextView)findViewById(R.id.lowPrice)).setText(map.get("low"));
                ((TextView)findViewById(R.id.highPrice)).setText(map.get("high"));





            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}