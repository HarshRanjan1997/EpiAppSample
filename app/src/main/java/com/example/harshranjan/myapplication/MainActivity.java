package com.example.harshranjan.myapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.harshranjan.myapplication.data.Boundary;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    String queryCreation(Boundary boundary) {
        return "minLat=" + boundary.getMinLat() + "&maxLat=" + boundary.getMaxLat()
                + "&minLag=" + boundary.getMinLag() + "&maxLag=" + boundary.getMaxLag();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);
        final TextView textView = findViewById(R.id.textView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AsyncTask asyncTask = new AsyncTask() {
                    @Override
                    protected Object doInBackground(Object[] objects) {

                        OkHttpClient client = new OkHttpClient();

                        //Test data
                        Boundary boundary = new Boundary();
                        boundary.setMinLat("20");
                        boundary.setMaxLat("30");
                        boundary.setMinLag("10");
                        boundary.setMaxLag("100");

                        Request request = new Request.Builder()
                                .url("http://192.168.137.1:8080/fetchdata/data?" + queryCreation(boundary))
                                .build();

                        Response response = null;
                        String string = null;
                        try {

                            response = client.newCall(request).execute();
                            return response.body().string();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        return null;
                    }

                    @Override
                    protected void onPostExecute(Object o) {

                        o.toString();
                        textView.setText(o.toString());
                    }
                }.execute();
            }
        });

    }
}
