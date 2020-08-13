package com.example.legutkoapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.LoginActivity;
import com.example.legutkoapplication.MainHelpers.EmailAutoActivity;
import com.example.legutkoapplication.MenuActivity;
import com.example.legutkoapplication.R;
import com.example.legutkoapplication.RefreshingActivity;
import com.hitomi.cmlibrary.CircleMenu;
import com.hitomi.cmlibrary.OnMenuSelectedListener;
import com.hitomi.cmlibrary.OnMenuStatusChangeListener;

public class New_meniu extends AppCompatActivity {
    String arrayName[] ={"Facebook",
            "Twitter",
            "Youtube",
            "Windows",
            "Drive",};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_meniu);
        CircleMenu circleMenu = (CircleMenu) findViewById(R.id.circle_menu);
        circleMenu.setMainMenu(Color.parseColor("#258CFF"),R.drawable.ic_menu_24, R.drawable.ic_wrap_text_24)
                .addSubMenu(Color.parseColor("#258CFF"), R.drawable.ic_home_24)
                .addSubMenu(Color.parseColor("#008000"), R.drawable.ic_search_24)
                .addSubMenu(Color.parseColor("#FF0000"), R.drawable.ic_send_24)
                .addSubMenu(Color.parseColor("#FF1493"), R.drawable.ic_add_24)
                .addSubMenu(Color.parseColor("#FF8C00"), R.drawable.ic_image_search_24)
                .setOnMenuSelectedListener(new OnMenuSelectedListener() {
                    @Override
                    public void onMenuSelected(int index) {
                        Toast.makeText(New_meniu.this, arrayName[index], Toast.LENGTH_SHORT).show();
                        System.out.println(index + " index");
                        if(index==1){

                            startActivity(new Intent(New_meniu.this, ProductListActivity.class));

                        }
                        if(index==2){
                            //wysyłanie bazy meilem
                            startActivity(new Intent(New_meniu.this, EmailAutoActivity.class));

                        }
                        if(index==3){
                            //wysyłanie bazy meilem
                            startActivity(new Intent(New_meniu.this, ProductAddActivity.class));

                        }
                    }
                }).setOnMenuStatusChangeListener(new OnMenuStatusChangeListener() {

            @Override
            public void onMenuOpened() {System.out.println("test Clicl" );}


            @Override
            public void onMenuClosed() {}

        });


    }

    public void adminSettengis(View view) {
        startActivity(new Intent(New_meniu.this, LoginActivity.class));
    }
}