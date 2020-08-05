package com.example.legutkoapplication.activity;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

//import com.ajts.androidmads.library.SQLiteToExcel;
//import com.example.legutkoapplication.Utils;
//import com.example.legutkoapplication.database.DBHelper;
//
//import java.io.File;
//
//public class ExportFileDb {
//
//
//    private int CAMERA_REQUEST_CODE;
//    private Uri fileUri;
//    private File file;
//    private Button button;
//    //-----------------------------
//    Context mBase;
//    DBHelper dbHelper;
//    String directory_path = Environment.getExternalStorageDirectory().getPath() + "/dataTab/";
//    SQLiteToExcel sqliteToExcel;
//    public static String DB_NAME = "abc";
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
//
//    public Context getApplicationContext() {
//        return mBase.getApplicationContext();
//    }
//
//
//    public void export() {
//        export();
//
//    }
//}
//
