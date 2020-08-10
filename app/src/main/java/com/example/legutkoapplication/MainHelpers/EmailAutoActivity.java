package com.example.legutkoapplication.MainHelpers;

import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import com.example.legutkoapplication.R;
import com.example.legutkoapplication.activity.Confirmation_of_sending_the_message;

import java.io.File;



public class EmailAutoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_auto);

        sendMessage();
//        sendMessageII();
//        sendMessageIII();
        boolean wynik =true;
        if(sendMessage()==wynik)
        startActivity(new Intent(EmailAutoActivity.this, Confirmation_of_sending_the_message.class));

        System.out.println("udało sie wyslac");
    }

    private boolean sendMessage() {
        final String login = "legutkodatatab@gmail.com";
        final String password = "Jutrosin63930?";
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

        return true;
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