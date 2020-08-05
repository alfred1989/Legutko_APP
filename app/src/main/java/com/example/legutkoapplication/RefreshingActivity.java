package com.example.legutkoapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.legutkoapplication.activity.ProductListActivity;
import com.example.legutkoapplication.activity.ProductUpdateActivity;

public class RefreshingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refreshing);



        getSupportActionBar().hide();

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                try{
                    Thread.sleep(2*200);

                    startActivity(new Intent(RefreshingActivity.this, ProductListActivity.class));
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        });

        t.start();
    }
}
