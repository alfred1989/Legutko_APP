package com.blogspot.atifsoftwares.writepdf_java;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.legutkoapplication.MainActivity;
import com.example.legutkoapplication.MenuActivity;
import com.example.legutkoapplication.R;

public class ErrorConnectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error_connect);



        getSupportActionBar().hide();

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                try{
                    Thread.sleep(2*400);

                    startActivity(new Intent(ErrorConnectActivity.this, MenuActivity.class));
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        });

        t.start();

    }
}
