package com.navispeed.greg.requieris;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    public static KeyList keyList;

    private TextView textView = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ListView viewById = (ListView) findViewById(R.id.list);
        this.textView = (TextView) findViewById(R.id.textView);

        keyList = new KeyList(this);

        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                keyList.getList());

        viewById.setAdapter(adapter);

        final Handler handler = new Handler();

        final MainActivity mainActivity = this;

// this will run when timer elapses
        TimerTask myTimerTask = new TimerTask() {
            @Override
            public void run() {
                // post a runnable to the handler
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        String text = (30 - System.currentTimeMillis() / 1000 % 30) + "";
                        textView.setText(text);

                        ArrayAdapter adapter = new ArrayAdapter<String>(mainActivity,
                                android.R.layout.simple_list_item_1,
                                keyList.getList());

                        viewById.setAdapter(adapter);
                    }
                });
            }
        };

// new timer
        Timer timer = new Timer();

// schedule timer
        timer.schedule(myTimerTask, 1000, 1);

        Button viewById1 = (Button) findViewById(R.id.button);
        viewById1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddKey.class);
                startActivity(intent);
            }
        });

    }
}
