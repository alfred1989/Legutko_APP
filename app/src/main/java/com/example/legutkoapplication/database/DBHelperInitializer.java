package com.example.legutkoapplication.database;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.legutkoapplication.model.Product;

public class DBHelperInitializer extends SQLiteOpenHelper {
    private static String TAG = "DataBaseHelper"; // Tag just for the LogCat window
    public static String DB_NAME = "intel.db"; // Database name
    private static int DB_VERSION = 1; // Database version
    private static final String TABLE_PRODUCT = "tbl_product";

    private final File DB_FILE;
    private SQLiteDatabase mDataBase;
    private final Context mContext;

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
    private static final String KEY_COMMENT = "miejsce_wysiewu";
    private static final String KEY_BATCH = "partia";
    private static final String KEY_CODE = "nr_reprodukcji";
    private static final String KEY_PLANTATION_ID = "id_plantacji";
    private static final String KEY_PLANTATION_AREA = "powierzchnia_plantacji";   //szybka zmiana na powierzchnie plantacji
    private static final String KEY_DESCRIPTION_IN_ENGLISH = "partia_nasion_wlasnych";
    private static final String KEY_SYMBOL = "symbol";
    private static final String KEY_HISTORICAL_DATA = "dane_historyczne";
    private static final String KEY_CONTRACT = "kontrakt";
    private static final String KEY_RECENTLY_ADDED = "ostatnio_dodany";


    public DBHelperInitializer(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        DB_FILE = context.getDatabasePath(DB_NAME);
        this.mContext = context;
    }

    public void createDataBase() throws IOException {
        // If the database does not exist, copy it from the assets.
        boolean mDataBaseExist = checkDataBase();
        if (!mDataBaseExist) {
            this.getReadableDatabase();
            this.close();
            try {
                // Copy the database from assests
                copyDataBase();
                Log.e(TAG, "createDatabase database created");
            } catch (IOException mIOException) {
                throw new Error("ErrorCopyingDataBase");
            }
        }
    }

    // Check that the database file exists in databases folder
    private boolean checkDataBase() {
        return DB_FILE.exists();
    }

    // Copy the database from assets
    private void copyDataBase() throws IOException {
        InputStream mInput = mContext.getAssets().open(DB_NAME);
        OutputStream mOutput = new FileOutputStream(DB_FILE);
        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer)) > 0) {
            mOutput.write(mBuffer, 0, mLength);
        }
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }

    // Open the database, so we can query it
    public boolean openDataBase() throws SQLException {
        // Log.v("DB_PATH", DB_FILE.getAbsolutePath());
        mDataBase = SQLiteDatabase.openDatabase(DB_FILE.getAbsolutePath(), null, SQLiteDatabase.CREATE_IF_NECESSARY);

        // mDataBase = SQLiteDatabase.openDatabase(DB_FILE, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);
        return mDataBase != null;
    }

    @Override
    public synchronized void close() {
        if (mDataBase != null) {
            mDataBase.close();
        }
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from " + TABLE_PRODUCT, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String producer                      = cursor.getString(1);
                String species                       = cursor.getString(2);
                String name                      = cursor.getString(3);
                String variety                       = cursor.getString(4);
                String color                         = cursor.getString(5);
                String group                         = cursor.getString(6);
                String subgroup                      = cursor.getString(7);
                String estimatedCrop                         = cursor.getString(8);
                String offPresence                       = cursor.getString(9);
                String offPercentage                         = cursor.getString(10);
                String description                       = cursor.getString(11);
                String standardPlantation                        = cursor.getString(12);
                String comment                       = cursor.getString(13);
                String batch                         = cursor.getString(14);
                String code                      = cursor.getString(15);
                String plantationId                      = cursor.getString(16);
                String  commentaryInEnglish                      = cursor.getString(17);
                String  descriptionInEnglish                         = cursor.getString(18);
                String symbol                        = cursor.getString(19);
                String historical_data                        = cursor.getString(20);
                String contract = cursor.getString(21);
                String recently_added = cursor.getString(22);
                Product product = new Product(id, producer, species, name, variety, color, group, subgroup, estimatedCrop,
                        offPresence, offPercentage, description, standardPlantation, comment, batch, code, plantationId,
                        commentaryInEnglish,descriptionInEnglish, symbol, historical_data, contract,recently_added);
                productList.add(product);
            } while (cursor.moveToNext());
        }
        return productList;
    }

    private ContentValues getContentValuesOfProduct(Product product) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(KEY_PRODUCER, product.getProducer());
        contentValues.put(KEY_SPECIES, product.getSpecies());
        contentValues.put(KEY_NAME, product.getName());
        contentValues.put(KEY_VARIETY, product.getVariety());
        contentValues.put(KEY_COLOR, product.getColor());
        contentValues.put(KEY_GROUP, product.getGroup());
        contentValues.put(KEY_SUBGROUP, product.getSubgroup());
        contentValues.put(KEY_ESTIMATED_CROP, product.getEstimatedCrop());
        contentValues.put(KEY_OFF_PRESENCE, product.getOffPresence());
        contentValues.put(KEY_OFF_PERCENTAGE, product.getOffPercentage());
        contentValues.put(KEY_DESCRIPTION, product.getDescription());
        contentValues.put(KEY_STANDARD_PLANTATION, product.getStandardPlantation());
        contentValues.put(KEY_COMMENT, product.getComment());
        contentValues.put(KEY_BATCH, product.getBatch());
        contentValues.put(KEY_CODE, product.getCode());
        contentValues.put(KEY_PLANTATION_ID, product.getPlantationId());
        contentValues.put(KEY_PLANTATION_AREA, product.getPlantation_area());
        contentValues.put(KEY_DESCRIPTION_IN_ENGLISH, product.getDescriptionInPL());
        contentValues.put(KEY_SYMBOL, product.getSymbol());
        contentValues.put(KEY_HISTORICAL_DATA, product.getHistorical_data());
        contentValues.put(KEY_CONTRACT, product.getContract());
        contentValues.put(KEY_RECENTLY_ADDED, product.getRecently_added());

        return contentValues;
    }

    public long addProduct(Product product) {
        ContentValues contentValues = getContentValuesOfProduct(product);
        SQLiteDatabase db = getWritableDatabase();
        return db.insert(TABLE_PRODUCT, null, contentValues);
    }

    public int updateProduct(Product product) {
        ContentValues contentValues = getContentValuesOfProduct(product);
        SQLiteDatabase db = getWritableDatabase();
        return db.update(TABLE_PRODUCT, contentValues, "id=?", new String[]{String.valueOf(product.getId())});
    }
}