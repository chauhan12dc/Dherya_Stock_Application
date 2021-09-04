package com.example.dherya_stock_application;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ListView;
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

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                MyTask myTask = new MyTask();
                myTask.execute();
                Toast.makeText(MainActivity.this, "check", Toast.LENGTH_SHORT).show();
                handler.postDelayed(this,10000);
            }
        },10000);

        ListView listView = findViewById(R.id.stock_list);


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

            ArrayList<Stocks> stocksArrayList = new ArrayList<>();
            ListView listView = findViewById(R.id.stock_list);
            try {
                JSONObject jsonObject = new JSONObject(s);
                Iterator<String> stringIterator = jsonObject.keys();
                while (stringIterator.hasNext())
                {
                    String SName = stringIterator.next();

                    Map<String,String> map = new HashMap<String, String>();
                    map.put("SName",SName);

                    JSONObject jsonObject1 = new JSONObject(jsonObject.getJSONObject(SName).toString());
                    Iterator<String> stringIterator1 = jsonObject1.keys();
                    while (stringIterator1.hasNext())
                    {
                        String Key = stringIterator1.next();
                        String Value = jsonObject1.getString(Key).toString();

                        map.put(Key,Value);

                    }
                    Stocks stocks = new Stocks(map.get("SName"),map.get("name"),map.get("price"),map.get("low"),map.get("high"));

                    stocksArrayList.add(stocks);
                }
                ListAdapter listAdapter = new ListAdapter(MainActivity.this,stocksArrayList);
                listView.setAdapter(listAdapter);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

