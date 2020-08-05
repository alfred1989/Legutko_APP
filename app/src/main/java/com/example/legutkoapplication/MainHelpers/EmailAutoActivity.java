package com.example.legutkoapplication.MainHelpers;

import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import com.example.legutkoapplication.MenuActivity;
import com.example.legutkoapplication.R;

import java.io.File;



public class EmailAutoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_auto);

        sendMessage();
//        sendMessageII();
//        sendMessageIII();
        startActivity(new Intent(EmailAutoActivity.this, MenuActivity.class));
    }

    private void sendMessage() {
        final String login = "legutkodatatab@gmail.com";
        final String password = "12345Legutko?";
        final String etContent = "Baza danych została zaktualizowana - uzytkownik II";
        final String etRecipient = "hubert.gosciniak@legutko.com.pl";
        String filename = "data_plantacje_Polskie.xls";
        String directory_path = Environment.getExternalStorageDirectory().getPath() + "/dataTab/";
        final File fileLocation = new File(directory_path, filename);


        final ProgressDialog dialog = new ProgressDialog(EmailAutoActivity.this);
        dialog.setTitle("database update");
        dialog.setMessage("Please wait");
        dialog.show();

        Thread sender = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    GMailSender sender = new GMailSender(login, password);
                    sender.sendMail("Email Legutko Baza Polska App",
                            etContent,
                            "legutkodatatab@gmail.com",
                            etRecipient,
                            fileLocation);
                    dialog.dismiss();
                } catch (Exception e) {
                    Log.e("mylog", "Error: " + e.getMessage());
                }
            }
        });
        sender.start();
    }
//    private void sendMessageII() {
//        final String login = "legutkodatatab@gmail.com";
//        final String password = "Legutko12345";
//        final String etContent = "Database has been updated";
//        final String etRecipient = "huqian@legutko.com.pl";
//        String filename = "data.xls";
//        String directory_path = Environment.getExternalStorageDirectory().getPath() + "/dataTab/";
//        final File fileLocation = new File(directory_path, filename);
//
//
//        final ProgressDialog dialog = new ProgressDialog(EmailAutoActivity.this);
//        dialog.setTitle("database update");
//        dialog.setMessage("Please wait");
//        dialog.show();
//
//        Thread sender = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    GMailSender sender = new GMailSender(login, password);
//                    sender.sendMail("Email Legutko China App",
//                            etContent,
//                            "legutkodatatab@gmail.com",
//                            etRecipient,
//                            fileLocation);
//                    dialog.dismiss();
//                } catch (Exception e) {
//                    Log.e("mylog", "Error: " + e.getMessage());
//                }
//            }
//        });
//        sender.start();
//    }

//    private void sendMessageIII() {
//        final String login = "legutkodatatab@gmail.com";
//        final String password = "Legutko12345";
//        final String etContent = "Database has been updated";
//        final String etRecipient = "anna.Cwiklinska@legutko.com.pl";
//        String filename = "data.xls";
//        String directory_path = Environment.getExternalStorageDirectory().getPath() + "/dataTab/";
//        final File fileLocation = new File(directory_path, filename);
//
//
//        final ProgressDialog dialog = new ProgressDialog(EmailAutoActivity.this);
//        dialog.setTitle("database update");
//        dialog.setMessage("Please wait");
//        dialog.show();
//
//        Thread sender = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    GMailSender sender = new GMailSender(login, password);
//                    sender.sendMail("Baza danych została zaktualizowana",
//                            etContent,
//                            "legutkodatatab@gmail.com",
//                            etRecipient,
//                            fileLocation);
//                    dialog.dismiss();
//                } catch (Exception e) {
//                    Log.e("mylog", "Error: " + e.getMessage());
//                }
//            }
//        });
//        sender.start();
//    }
}