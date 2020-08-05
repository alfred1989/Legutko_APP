package com.example.legutkoapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.legutkoapplication.MainActivity;
import com.example.legutkoapplication.R;
import com.hanks.htextview.HTextView;
import com.hanks.htextview.HTextViewType;
import com.hanks.htextview.animatetext.HText;

public class Confirmation_of_sending_the_message extends AppCompatActivity {
HTextView hTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation_of_sending_the_message);





        hTextView = (HTextView) findViewById(R.id.text);
        hTextView.setTypeface(FontManager.getInstance(getAssets()).getFont("fonts/font-name.ttf"));
// be sure to set custom typeface before setting the animate type, otherwise the font may not be updated.
        hTextView.setAnimateType(HTextViewType.LINE);
        hTextView.animateText("Baza została wysłana"); // animate

    }


}