package com.example.legutkoapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.legutkoapplication.activity.New_meniu;


public class MainActivity extends AppCompatActivity {

    EditText usernameField;
    EditText passwordField;
    ImageView imageView;
    private DialogInterface.OnClickListener mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //------------------------------------------------------------------------------------------

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                try{
                    Thread.sleep(2*650);

                    startActivity(new Intent(MainActivity.this, New_meniu.class));
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        });

        t.start();

        }
    }
