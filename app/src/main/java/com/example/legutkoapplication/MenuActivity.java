package com.example.legutkoapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.ajts.androidmads.library.SQLiteToExcel;
import com.example.legutkoapplication.MainHelpers.EmailAutoActivity;
import com.example.legutkoapplication.activity.ProductAddActivity;
import com.example.legutkoapplication.activity.ProductListActivity;
import com.example.legutkoapplication.activity.PushEmailActivity;
import com.example.legutkoapplication.database.TestAdapter;
import com.example.legutkoapplication.utils.NetworkChangeReceiver;

import java.io.File;

public class MenuActivity extends AppCompatActivity {


    ProgressBar pb_per;
    WebView mWebView;
    View view;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 2;
    static final String REPORTS_PHOTOS_DIR = Environment.DIRECTORY_PICTURES + "/reports_photos";

    private ImageView mImageView;
    private Button mButtonCapture;

    String mCurrentPhotoPath;
    private Uri photoURI;
    //Need to store our BroadcastReceiver object for use when unregistering
    private BroadcastReceiver br;


    //------------------------------------------------------

    private int CAMERA_REQUEST_CODE;
    private Uri fileUri;
    private File file;
    private Button button;
    //-----------------------------
    String directory_path = Environment.getExternalStorageDirectory().getPath() + "/dataTab/";
    SQLiteToExcel sqliteToExcel;
    public static String DB_NAME = "abc";

    //----------------------------\

    private WebView webView = null;


    @Nullable
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        //--------creat file dataTab-----------------------
////        String fileDataTab = Environment.getExternalStorageDirectory().getPath() + "/dataTab";
//        String fileDataTab = Environment.getExternalStorageDirectory().getPath();
////        String fileMy_Files = Environment.getExternalStorageDirectory().getPath() + "/My Files";
//        String fileMy_Files = Environment.getExternalStorageDirectory().getPath();
//        File file = new File(fileDataTab);
//        File storageDir = Environment.getExternalStoragePublicDirectory(fileDataTab);
////----------------------------------------------------------------------------
//
//
//        File mFolder = new File(fileDataTab);
//        if (!mFolder.exists()) {
//            mFolder.mkdir();
//        }
//        File Directory = new File(fileDataTab+"/dataTab");
//        Directory.mkdirs();
//
//        File mFolderI = new File(fileDataTab);
//        if (!mFolder.exists()) {
//            mFolder.mkdir();
//        }
//        File DirectoryI = new File(fileDataTab+"/My Files");
//        DirectoryI.mkdirs();
//
//
//
//
//
//
//
//
//
////----------------------------------------------------------------------------
////        System.out.println(fileDataTab + "   fileDataTab");
////        if (!file.exists()) {  //sprawdza czy lokalizacja pliku nie istnieje
////            System.out.println("Istnieje");
////            storageDir.mkdir();   //tworzy lokalizacje
////            if( storageDir.mkdir()==true){
////                System.out.println("lokalizacja fileDataTab udana");
////            }else {
////                System.out.println("lokalizacja fileDataTab nie udana");
////            }
////        }
////
////        File storageDirMy_Files = Environment.getExternalStoragePublicDirectory(fileMy_Files);
////
////        System.out.println(storageDirMy_Files + "   storageDirMy_Files");
////        if (!storageDirMy_Files.exists()) {  //sprawdza czy lokalizacja pliku nie istnieje
////            storageDirMy_Files.mkdir();   //tworzy lokalizacje
////            if(storageDirMy_Files.mkdir()==true){
////                System.out.println("lokalizacja storageDirMy_Files udana");
////            }
////        }
//        //-------the end create file dataTab--------------



    setContentView(R.layout.activity_menu);
        button = (Button) findViewById(R.id.info);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });


        testInitializer(this);

        IntentFilter intentFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        this.registerReceiver(new NetworkChangeReceiver(this), intentFilter);
    }




    public void openAddProductView(View view) {
        startActivity(new Intent(MenuActivity.this, ProductAddActivity.class));

    }

    public void openProductView(View view) {
        startActivity(new Intent(MenuActivity.this, ProductListActivity.class));
    }

    public void camera(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 1234);


    }

    public void push(View view) {
        startActivity(new Intent(MenuActivity.this, PushEmailActivity.class));
    }

    public void testInitializer(Context context) {
        TestAdapter mDbHelper = new TestAdapter(context);
        mDbHelper.createDatabase();
        mDbHelper.open();

        Cursor testdata = mDbHelper.getTestData();
        String q = testdata.getString(1);
        String w = testdata.getString(0);
        System.out.println("--------------------");
        System.out.println("q  " + q);
        System.out.println("w   " + w);
        mDbHelper.close();
    }

    public void refresh(View view) {


                    startActivity(new Intent(MenuActivity.this, RefreshingActivity.class));



    }

    public void openDialog() {
        ExampleDialog exampleDialog = new ExampleDialog();
        exampleDialog.show(getSupportFragmentManager(), "example dialog");
    }

    public void instruction(View view) {
        startActivity(new Intent(MenuActivity.this, instrActivity.class));
    }


    public void exitApp(View view) {
        Intent intent = new Intent(this, MainActivity.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("Exit me", true);
        startActivity(intent);
        finish();
        System.exit(1);
    }

//    public void export(final View view) {
//        sqliteToExcel = new SQLiteToExcel(getApplicationContext(), DBHelper.DATABASE_NAME, directory_path);
//        sqliteToExcel.exportAllTables("data.xls", new SQLiteToExcel.ExportListener() {
//            @Override
//            public void onStart() {
//
//            }
//
//            @Override
//            public void onCompleted(String filePath) {
//                Utils.showSnackBar(view, "Successfully Exported");
//            }
//
//            @Override
//            public void onError(Exception e) {
//                Utils.showSnackBar(view, e.getMessage());
//            }
//        });
//
//
//    }


    public void updateApp(View view) {
        Uri uri = Uri.parse("https://drive.google.com/drive/u/2/folders/1NgaGdAUmWhrNaI2HxFIVmjFfXO2rI53H"); // missing 'http://' will cause crashed
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }


    public void will_send_database_updates_and_notification(View view) {

//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("attention");
//        builder.setMessage("I am sorry but this feature is not available right now");
//        // add a button
//        builder.setPositiveButton("OK", null);
//        // create and show the alert dialog
//        AlertDialog dialog = builder.create();
//        dialog.show();


        startActivity(new Intent(MenuActivity.this, EmailAutoActivity.class));


//                Intent intent = new Intent(Intent.ACTION_SENDTO);
//        intent.setData(Uri.parse("mailto:feedback@gmail.com"));
//        intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
//        if (intent.resolveActivity(getPackageManager()) != null) {
//            startActivity(intent);
//        }
    }

    public void add_new_record(View view) {

        startActivity(new Intent(MenuActivity.this, ProductAddActivity.class));

    }
}







//    public void export(final View view) {
//        sqliteToExcel = new SQLiteToExcel(getApplicationContext(), DBHelper.DATABASE_NAME, directory_path);
//        sqliteToExcel.exportAllTables("data.xls", new SQLiteToExcel.ExportListener() {
//            @Override
//            public void onStart() {
//
//            }
//
//            @Override
//            public void onCompleted(String filePath) {
//                Utils.showSnackBar(view, "Successfully Exported");
//            }
//
//            @Override
//            public void onError(Exception e) {
//                Utils.showSnackBar(view, e.getMessage());
//            }
//        });
//
//
//    }











//-------------------------------------------------------------------------------------------------------------------------------------------
