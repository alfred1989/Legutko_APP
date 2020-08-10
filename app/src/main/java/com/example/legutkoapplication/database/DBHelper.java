package com.example.legutkoapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.legutkoapplication.model.Product;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "intel.db";

    private static final String TABLE_PRODUCT = "tbl_product";

    private static final String KEY_ID = "id";
    private static final String KEY_PRODUCER = "producent";
    private static final String KEY_SPECIES = "gatunek_lac";
    private static final String KEY_NAME = "nazwa_towaru";
    private static final String KEY_VARIETY = "odmiana";
    private static final String KEY_COLOR = "kolor_EN";
    private static final String KEY_GROUP = "grupa_EN";
    private static final String KEY_SUBGROUP = "podgrupa_EN";
    private static final String KEY_ESTIMATED_CROP = "szacowany_plon";
    private static final String KEY_OFF_PRESENCE = "obecność_off";
    private static final String KEY_OFF_PERCENTAGE = "procent_off";
    private static final String KEY_DESCRIPTION = "opis";
    private static final String KEY_STANDARD_PLANTATION = "plantacja_typowa";
    private static final String KEY_COMMENT = "uwagi";
    private static final String KEY_BATCH = "partia";
    private static final String KEY_CODE = "nr_reprodukcji";
    private static final String KEY_PLANTATION_ID = "id_plantacji";
    private static final String KEY_SYMBOL = "symbol";

    private static final String CREATE_TABLE_PRODUCT = " CREATE TABLE " + TABLE_PRODUCT + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "
            + KEY_ID + " TEXT , "
            + KEY_PRODUCER + " TEXT , "
            + KEY_SPECIES + " TEXT , "
            + KEY_NAME + " TEXT , "
            + KEY_VARIETY + " TEXT , "
            + KEY_COLOR + " TEXT , "
            + KEY_GROUP + " TEXT , "
            + KEY_SUBGROUP + " TEXT , "
            + KEY_ESTIMATED_CROP + " TEXT , "
            + KEY_OFF_PRESENCE + " TEXT , "
            + KEY_OFF_PERCENTAGE + " TEXT , "
            + KEY_DESCRIPTION + " TEXT , "
            + KEY_STANDARD_PLANTATION + " TEXT , "
            + KEY_COMMENT + " TEXT , "
            + KEY_BATCH + " TEXT , "
            + KEY_CODE + " TEXT , "
            + KEY_PLANTATION_ID + " TEXT "
            + KEY_SYMBOL + " TEXT "
            + ")";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        System.out.println("HUBERT - onCreate");
//        db.execSQL(CREATE_TABLE_PRODUCT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);
//        onCreate(db);
    }

}


