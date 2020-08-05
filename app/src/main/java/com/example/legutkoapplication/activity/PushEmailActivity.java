package com.example.legutkoapplication.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import com.blogspot.atifsoftwares.writepdf_java.ErrorConnectActivity;
import com.example.legutkoapplication.MainActivity;
import com.example.legutkoapplication.MenuActivity;
import com.example.legutkoapplication.R;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class PushEmailActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    //    EditText et_email;
//    EditText et_subject;
//    EditText et_message;
//    Button Send;
//    Button Attachment;
//    String email;
//    String subject;
//    String message;
//    String attachmentFile;
//    Uri URI = null;
//    private static final int PICK_FROM_GALLERY = 101;
//    int columnIndex;

    private EditText mEditTextTo;
    private TextView mEditTextSubject;
    private EditText mEditTextMessage;
    Switch anny;
    Switch bright;
    Switch sherry;
    Switch shenfei79;
    Switch dga;
//    Switch pra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_email);
        mEditTextTo = findViewById(R.id.edit_text_to);
        mEditTextSubject = findViewById(R.id.edit_text_subject);
        mEditTextMessage = findViewById(R.id.edit_text_message);
//        anny = findViewById(R.id.anny);
//        bright = findViewById(R.id.bright);
//        sherry = findViewById(R.id.sherry);
//        shenfei79 = findViewById(R.id.shenfei79);
//        dga = findViewById(R.id.dga);
//        pra = findViewById(R.id.pra);

//        Button buttonSend = findViewById(R.id.button_send);





//        buttonSend.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                sendMail();
//            }
//        });
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Back");
//        bright = (Switch) findViewById(R.id.bright);
//        bright.setOnCheckedChangeListener(this);
//        //-----------
//        anny = (Switch) findViewById(R.id.anny);
//        anny.setOnCheckedChangeListener(this);
//        //----------
//        sherry = (Switch) findViewById(R.id.sherry);
//        sherry.setOnCheckedChangeListener(this);
//        //------------
//        shenfei79 = (Switch) findViewById(R.id.shenfei79);
//        shenfei79.setOnCheckedChangeListener(this);
//        //------------------------------
//        dga = (Switch) findViewById(R.id.dga);
//        dga.setOnCheckedChangeListener(this);
        //-------------------
//        pra = (Switch) findViewById(R.id.pra);
//        pra.setOnCheckedChangeListener(this);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.send, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.item_send:
                sendMail();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }



    private void sendMail() {

        TextView textView = findViewById(R.id.testzasiegu);
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();


        if (networkInfo != null && networkInfo.isConnected()) {
            String recipientList = mEditTextTo.getText().toString();
            String[] recipients = recipientList.split(",");

            String subject = mEditTextSubject.getText().toString();
            String message = mEditTextMessage.getText().toString();

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_EMAIL, recipients);
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
            intent.putExtra(Intent.EXTRA_TEXT, message);

            intent.setType("message/rfc822");
            String filename = "data.xls";
            String directory_path = Environment.getExternalStorageDirectory().getPath() + "/dataTab/";
            final File fileLocation = new File(directory_path, filename);
            startActivity(Intent.createChooser(intent, "Choose an email client"));
        } else {
            startActivity(new Intent(PushEmailActivity.this, ErrorConnectActivity.class));
        }
    }

    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (anny.isChecked()) {
            mEditTextTo.setText("annylu89@hotmail.com");
        } else if (anny == null) {
            mEditTextTo.setText(" ");
        } else if (bright.isChecked()) {
            mEditTextTo.setText("info@brightseeds.com.cn");
        } else if (bright == null) {
            mEditTextTo.setText(" ");
        } else if (sherry.isChecked()) {
            mEditTextTo.setText("sherryhan@snseeds.com");
        } else if (sherry == null) {
            mEditTextTo.setText(" ");
        } else if (shenfei79.isChecked()) {
            mEditTextTo.setText("shenfei79@vip.163.com");
        } else if (shenfei79 == null) {
            mEditTextTo.setText(" ");
        } else if (dga.isChecked()) {
            mEditTextTo.setText("dgagrorobert@vip.sina.com");
        } else if (dga == null) {
            mEditTextTo.setText(" ");
//        } else if (pra.isChecked()) {
//            mEditTextTo.setText("prakashbng@vokkalseeds.com, aswath@vokkalseeds.com");
//        } else if (pra == null) {
//            mEditTextTo.setText(" ");
        }
    }
        @Override
        public void onPointerCaptureChanged ( boolean hasCapture){

        }


}


//        et_email = (EditText) findViewById(R.id.et_to);
//        et_subject = (EditText) findViewById(R.id.et_subject);
//        et_message = (EditText) findViewById(R.id.et_message);
//        Attachment = (Button) findViewById(R.id.bt_attachment);
//        Send = (Button) findViewById(R.id.bt_send);
//        //send button listener
//        Send.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                sendEmail();
//            }
//        });
//        //attachment button listener
//        Attachment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openFolder();
//            }
//        });
//    }
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == PICK_FROM_GALLERY && resultCode == RESULT_OK) {
//            Uri selectedImage = data.getData();
//            String[] filePathColumn = {MediaStore.Images.Media.DATA};
//            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
//            cursor.moveToFirst();
//            columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//            attachmentFile = cursor.getString(columnIndex);
//            Log.e("Attachment Path:", attachmentFile);
//            URI = Uri.parse("file://" + attachmentFile);
//            cursor.close();
//        }
//    }
//    public void sendEmail()
//    {
//        try
//        {
//            email = et_email.getText().toString();
//            subject = et_subject.getText().toString();
//            message = et_message.getText().toString();
//            final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
//            emailIntent.setType("plain/text");
//            emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,new String[] { email });
//            emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,subject);
//            if (URI != null) {
//                emailIntent.putExtra(Intent.EXTRA_STREAM, URI);
//            }
//            emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, message);
//            this.startActivity(Intent.createChooser(emailIntent,"Sending email..."));
//        }
//        catch (Throwable t)
//        {
//            Toast.makeText(this, "Request failed try again: " + t.toString(),Toast.LENGTH_LONG).show();
//        }
//    }
//    public void openFolder()
//    {
//        Intent intent = new Intent();
//        intent.setType("data/");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        intent.putExtra("return-data", true);
//        startActivityForResult(Intent.createChooser(intent, "Complete action using"), PICK_FROM_GALLERY);
//    }
//}