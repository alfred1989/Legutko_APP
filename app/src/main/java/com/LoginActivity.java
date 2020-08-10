package com;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.legutkoapplication.MenuActivity;
import com.example.legutkoapplication.R;
import com.example.legutkoapplication.activity.ProductListActivity;

public class LoginActivity extends AppCompatActivity {
EditText field_admin_login;
EditText field_admin_password;
String name_user = "admin";
String password_user = "Legutko12345";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



    }

    public void admin_inpord_data(View view) {
        field_admin_login = findViewById(R.id.login_admin);
        field_admin_password = findViewById(R.id.password_admin);
        String admin_login = field_admin_login.getText().toString();
        String admin_password = field_admin_password.getText().toString();
        System.out.println(admin_login + " admin_login");
        if (admin_login.equals("admin") && admin_password.equals("Legutko")) {
            startActivity(new Intent(LoginActivity.this, MenuActivity.class));
        }
    }
}