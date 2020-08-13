package com.example.legutkoapplication.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.SyncStateContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NavUtils;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.ImageDisplay;
import com.ajts.androidmads.library.SQLiteToExcel;
import com.example.legutkoapplication.Language;
import com.example.legutkoapplication.MainHelpers.EmailAutoActivity;
import com.example.legutkoapplication.R;
import com.example.legutkoapplication.RefreshingActivity;
import com.example.legutkoapplication.TranslateAPI;
import com.example.legutkoapplication.Utils;
import com.example.legutkoapplication.database.DBHelperInitializer;
import com.example.legutkoapplication.model.Product;
import com.utils.MarginDecoration;
import com.utils.PicHolder;
import com.utils.imageFolder;
import com.utils.itemClickListener;
import com.utils.pictureFacer;
import com.utils.pictureFolderAdapter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class ProductUpdateActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, itemClickListener {
    private static final int WRITE_EXTERNAL_STORAGE_CODE = 2;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 2;
    //    static final String REPORTS_PHOTOS_DIR = Environment.DIRECTORY_PICTURES + "/reports_photos/";
//-----------
    RecyclerView folderRecycler;
    TextView empty;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    //----------------------------------------------------
    DBHelperInitializer dbHelper;
    SQLiteToExcel sqliteToExcel;
    String directory_path = Environment.getExternalStorageDirectory().getPath() + "/dataTab/";
    Context context;
    int productId;

    EditText producerField;
    EditText speciesField;
    EditText nameField;
    EditText varietyField;
    EditText colorField;
    EditText groupField;
    EditText subgroupField;
    EditText estimatedCropField;
    EditText offPresenceField;
    EditText offPercentageField;
    EditText descriptionField;
    EditText standardPlantationField;
    TextView commentField;
    EditText batchField;
    EditText codeField;
    TextView plantationIdField;
    TextView commentaryInPLField;
    TextView descriptionInEnglishField;
    TextView dField;
    TextView commentPLField;
    TextView symbolField;
    TextView own_seed_batchField;
    Switch switchStandardPlantation;
    Switch switchStandardPlantationTypical;
    Switch switchOffPresence_Incorrect;
    Switch switchOffPresence_Typical;
    private ImageView mImageView;
    private Button mButtonCapture;
    String mCurrentPhotoPath;

    //    static final String REPORTS_PHOTOS_DIR = Environment.DIRECTORY_PICTURES + "/reports_photos/";
    //Need to store our BroadcastReceiver object for use when unregistering
    private BroadcastReceiver br;
    private Context ctx;
    public int numberOfClicks = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product__update);


        if (ContextCompat.checkSelfPermission(ProductUpdateActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(ProductUpdateActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        //____________________________________________________________________________________

//        empty = findViewById(R.id.empty);

//        folderRecycler = findViewById(R.id.folderRecycler);
////        folderRecycler.addItemDecoration(new MarginDecoration(this));
////        folderRecycler.hasFixedSize();
//        ArrayList<imageFolder> folds = getPicturePaths();
//
//
//        if (folds.isEmpty()) {
//            empty.setVisibility(View.VISIBLE);
//        } else {
//            RecyclerView.Adapter folderAdapter = new pictureFolderAdapter(folds, ProductUpdateActivity.this, this);
//            folderRecycler.setAdapter(folderAdapter);
//
//        }


//--------------------------------------------------------------------------------------------------


        dbHelper = new DBHelperInitializer(this);

        //--------------------------------------camera-----------------------------------
        mImageView = findViewById(R.id.imageView);
        mButtonCapture = findViewById(R.id.button_photo);
        br = new MyBroadCastReviecer();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        this.registerReceiver(br, filter);

        producerField = findViewById(R.id.producer);
        speciesField = findViewById(R.id.species);
        nameField = findViewById(R.id.name);
        varietyField = findViewById(R.id.variety);
        colorField = findViewById(R.id.color);
        groupField = findViewById(R.id.group);
        subgroupField = findViewById(R.id.subgroup);
        estimatedCropField = findViewById(R.id.estimated_crop);
        offPresenceField = findViewById(R.id.off_presence);
        offPercentageField = findViewById(R.id.off_percentage);
        descriptionField = findViewById(R.id.description);
        standardPlantationField = findViewById(R.id.standard_plantation);
        commentField = findViewById(R.id.comment);
        batchField = findViewById(R.id.batch);
        codeField = findViewById(R.id.code);
        plantationIdField = findViewById(R.id.plantation_id);
        commentaryInPLField = findViewById(R.id.commentaryInPL);
        descriptionInEnglishField = findViewById(R.id.descriptionInEnglish);
//        dField = findViewById(R.id.d);
        commentPLField = findViewById(R.id.commentPL);
        symbolField = findViewById(R.id.symbol);
        own_seed_batchField = findViewById(R.id.own_seed_batch);


        System.out.println(commentaryInPLField + " commentaryInPLFieldcommentaryInPLFieldcommentaryInPLFieldcommentaryInPLFieldcommentaryInPLField");
        //----------------------------Switch Standard Plantanion--------------------------------------------------
        switchStandardPlantation = findViewById(R.id.switch_standard_plantation);
        switchStandardPlantation.setOnCheckedChangeListener(this);
        switchStandardPlantationTypical = findViewById(R.id.switch_standard_plantation_typical);
        switchStandardPlantationTypical.setOnCheckedChangeListener(this);

        //----------------------------------Switch Off Presence--------------------------------------------
        switchOffPresence_Incorrect = findViewById(R.id.switch_off_presence);
        switchOffPresence_Incorrect.setOnCheckedChangeListener(this);

        Product product = (Product) getIntent().getExtras().getSerializable("PRODUCT");

        productId = product.getId();

        producerField.setText(product.getProducer());
        speciesField.setText(product.getSpecies());
        nameField.setText(product.getName());
        varietyField.setText(product.getVariety());
        colorField.setText(product.getColor());
        groupField.setText(product.getGroup());
        subgroupField.setText(product.getSubgroup());
        estimatedCropField.setText(product.getEstimatedCrop());
        offPresenceField.setText(product.getOffPresence());
        offPercentageField.setText(product.getOffPercentage());
        descriptionField.setText(product.getDescription());
        standardPlantationField.setText(product.getStandardPlantation());
        commentField.setText(product.getComment());
        batchField.setText(product.getBatch());
        codeField.setText(product.getCode());
        plantationIdField.setText(product.getPlantationId());
        commentaryInPLField.setText(product.getCommentaryInPL());
        descriptionInEnglishField.setText(product.getDescriptionInPL());
        commentPLField.setText(product.getDescriptionInPL());
        symbolField.setText(product.getSymbol());
        own_seed_batchField.setText(product.getDescriptionInPL());


        String nameSpilt = product.getName();
        String [] nameSpiltTab = nameSpilt.split("/");
        nameField.setText(nameSpiltTab[0]);
        System.out.println(nameSpiltTab[0] + "spilt");
        System.out.println(product.getDescriptionInPL() + " own_seed_batchField.setText(product.getDescriptionInPL())");

//        if (
//                product.getEstimatedCrop() == null ||
//                product.getDescription() == null ||
//                product.getStandardPlantation() == null ||
//                product.getComment() == null) {
//            findViewById(R.id.button_save).setEnabled(false);
//
//        }

        System.out.println(product.getEstimatedCrop() + "," +
                product.getDescription() + "," +
                product.getStandardPlantation() + "," +
                product.getComment() + "+++++++++++++++++++++++++++++++++++++++++++++++++++");
        if (product.getStandardPlantation() == null || product.getStandardPlantation().equals("Incorrect")) {
            standardPlantationField.setBackgroundColor(Color.parseColor("#FF0000"));
            findViewById(R.id.standard_plantation).setBackgroundColor(Color.parseColor("#FF0000"));
            findViewById(R.id.standard_plantation).setBackgroundColor(Color.parseColor("#FF0000"));
            findViewById(R.id.estimated_crop).setBackgroundColor(Color.parseColor("#FF0000"));
        }
//        }else if (product.getStandardPlantation()== null){standardPlantationField.setBackgroundColor(Color.parseColor("#FFFFFF"));
//            findViewById(R.id.standard_plantation).setBackgroundColor(Color.parseColor("##FFFFFF"));}

        if (product.getComment() == null) {

            findViewById(R.id.comment).setBackgroundColor(Color.parseColor("#ff5130"));

        }

        System.out.println();
        if (product.getDescription() == null) {

            findViewById(R.id.description).setBackgroundColor(Color.parseColor("#ff5130"));
        }


    }


    private void saveToTxtFile(String a) {
//        String test = "test";
        String timeStamp = new SimpleDateFormat("yyyy_MM_dd", Locale.getDefault()).format(System.currentTimeMillis());
        String nameFile = speciesField.getText().toString().trim();
        String nameProducent = producerField.getText().toString().trim();
//        LocalDate myObj = LocalDate.now();
        String fileName = nameProducent + "_" + nameFile + "_" + timeStamp + ".txt";
//        String file_name = fileName + ".txt";
        try {
//            File path = Environment.getExternalStorageDirectory();
            File path = Environment.getExternalStorageDirectory();
            File dir = new File(directory_path + "/My_report/" + producerField.getText().toString().trim());
            dir.mkdir();


            System.out.println(dir + " dir");
            System.out.println(directory_path + " directory_path");
//            String fileName =nameProducent+"_"+nameFile + ".dotx";
            File file = new File(dir, fileName);
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(a);
            bw.close();
            Toast.makeText(this, fileName + "is saved to \n" + dir, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.item_save) {
//            updateProduct();
//        }
//        if (id == R.id.back_item) {
//            startActivity(new Intent(ProductUpdateActivity.this, ProductListActivity.class));
//        }
//        return super.onOptionsItemSelected(item);
//    }

    private Product getProductFromViewFields() {
        String producer = producerField.getText().toString().trim();
        String species = speciesField.getText().toString().trim();
        String name = nameField.getText().toString().trim();
        String variety = varietyField.getText().toString().trim();
        String color = colorField.getText().toString().trim();
        String group = groupField.getText().toString().trim();
        String subgroup = subgroupField.getText().toString().trim();
        String estimatedCrop = estimatedCropField.getText().toString().trim();
        String offPresence = offPresenceField.getText().toString().trim();
        String offPercentage = offPercentageField.getText().toString().trim();
        String description = descriptionField.getText().toString().trim();
        String standardPlantation = standardPlantationField.getText().toString().trim();
        String comment = commentField.getText().toString().trim();
        String batch = batchField.getText().toString().trim();
        String code = codeField.getText().toString().trim();
        String plantationId = plantationIdField.getText().toString().trim();
        String symbol = symbolField.getText().toString().trim();
        String powierzchnia = commentaryInPLField.getText().toString().trim();
        String own_seed_batch = own_seed_batchField.getText().toString().trim();
        System.out.println(own_seed_batch + "   own_seed_batch ");
        //   String  descriptionInEnglish = descriptionInEnglish.getText().toString().trim();

//        if (standardPlantationField.equals("Incorrect")) {
//            standardPlantationField.setBackgroundColor(Color.parseColor("#FF0000"));
//            findViewById(R.id.standard_plantation).setBackgroundColor(Color.parseColor("#FF0000"));
//
//        }
//        if (standardPlantation.equals("Incorrect")) {
//            standardPlantationField.setBackgroundColor(Color.parseColor("#FF0000"));
//            findViewById(R.id.standard_plantation).setBackgroundColor(Color.parseColor("#FF0000"));
//
//        }

//        String test = commentPLField.getText().toString();
        String test = standardPlantation;


        if (test == null) {
            System.out.println("Hello word");
        }
//        if (comment == null) {
////            commentField.setBackgroundColor(Color.RED);
//
//
//        }
        if (description == null) {
            descriptionField.setBackgroundColor(Color.RED);
            descriptionField.setText("-");
        }


//        TranslateAPI translateAPIcomment = new TranslateAPI(Language.AUTO_DETECT, Language.POLISH,
//                commentField.getText().toString());
//
//        translateAPIcomment.setTranslateListener(new TranslateAPI.TranslateListener() {
//            @Override
//            public String onSuccess(String translatedText) {
//
//                dField.setText(translatedText);
//                String test = dField.getText().toString();
//                System.out.println(test + "-----------------sssssssssssssssssssssss");
//
//                return test;
//            }
//
//            @Override
//            public void onFailure(String ErrorText) {
//
//            }
//
//
//        });

//        TranslateAPI translateAPIdescription = new TranslateAPI(Language.AUTO_DETECT, Language.POLISH,
//                descriptionField.getText().toString());
//
//
//        translateAPIdescription.setTranslateListener(new TranslateAPI.TranslateListener() {
//            @Override
//            public String onSuccess(String translatedText) {
//
//                commentPLField.setText(translatedText);
//                String test = commentPLField.getText().toString();
//                System.out.println(test + "-----------------sssssssssssssssssssssss");
//
//                return test;
//            }
//
//            @Override
//            public void onFailure(String ErrorText) {
//
//            }
//
//
//        });

        LocalDate myObj = null; // Create a date object
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            myObj = LocalDate.now();
        }
        System.out.println(myObj); // Display the current date
//        System.out.println(descriptionField.getText() + "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        //String commentaryInPL = dField.getText().toString();
//        String descriptionInEnglish = myObj + " " + commentPLField.getText().toString();
        String descriptionInEnglish = myObj + " " + descriptionField.getText().toString() +"|";
//        System.out.println(commentaryInPL + "------------commentaryInPL");
//        System.out.println(descriptionInEnglish + "------------descriptionInEnglish");
//        System.out.println(standardPlantation + "standardPlantationstandardPlantationstandardPlantationstandardPlantationstandardPlantationstandardPlantationstandardPlantationstandardPlantationstandardPlantationstandardPlantationstandardPlantationstandardPlantationstandardPlantationstandardPlantationstandardPlantationstandardPlantationstandardPlantationstandardPlantationstandardPlantation");


        //comment = commentaryInPL;
        description = descriptionInEnglish;


        descriptionInEnglish = descriptionField.getText().toString();
        String   commentaryInPL = own_seed_batch;
//        System.out.println(comment + "------------com");
//        System.out.println(description + "------------des");
        System.out.println(commentaryInPL+ "   commentaryInPL");
//        if (standardPlantation.equals("Incorrect")) {
//        }
//        String sprawdzam = estimatedCropField.getText().toString();
//        int len = sprawdzam.length();
//        if (len == 0 )
//                   {
//            findViewById(R.id.button_save).setEnabled(false);
//
//        }
//
//
//        System.out.println( len+" estimatedCropField.getText() estimatedCropField.getText() estimatedCropField.getText()");
        System.out.println(producer + " producer");
        System.out.println(species + " species");
        System.out.println(name + " name");
        System.out.println(variety + " variety");
        System.out.println(color + " color");
        System.out.println(group + " group");
        System.out.println(subgroup + " subgroup");
        System.out.println(estimatedCrop + " estimatedCrop");
        System.out.println(offPresence + " offPresence");
        System.out.println(offPercentage + " offPercentage");
        System.out.println(description + " description");
        System.out.println(standardPlantation + " standardPlantation");
        System.out.println(comment + " comment");
        System.out.println(batch + " batch");
        System.out.println(code + " code");
        System.out.println(plantationId + " plantationId");
        System.out.println(commentaryInPL + " commentaryInPL");
        System.out.println(descriptionInEnglish + " descriptionInEnglish");
        System.out.println(symbol + " symbol");
        descriptionInEnglish = own_seed_batch;

        return new Product(productId, producer, species, name, variety, color, group, subgroup, estimatedCrop, offPresence,
                offPercentage, description, standardPlantation, comment, batch, code, plantationId, commentaryInPL, descriptionInEnglish, symbol);
//        return new Product(productId, producer, species, name, variety, color, group, subgroup, estimatedCrop, offPresence,
//                offPercentage, description, standardPlantation, comment, batch, code, plantationId,commentaryInPL, descriptionInEnglish);


    }

    private void updateProduct() {
        Product product = getProductFromViewFields();
        int result = dbHelper.updateProduct(product);

        if (result > 0) {
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
        }
    }

    public void saveReport() {


        Product product = getProductFromViewFields();

        String timeStamp = new SimpleDateFormat("yyyy_MM_dd", Locale.getDefault()).format(System.currentTimeMillis());
        String newLine = "\n\n";
//        FILE_NAME = producer + code + saveField + ".txt";  //zapis pliku pod nazwa kodu np 123.doc
//
//        String report = "Raport z dn. " + timeStamp + " Nr reprodukcji: " + product.getCode() + newLine +
//                "Nazwa towaru:" + product.getName() + newLine +
//                "Producent: " + product.getProducer() + newLine +
//                "Numer reprodukcji: " + product.getCode() + newLine +
//                "Gatunek łac.: " + product.getSpecies() + newLine +
//                "Partia: " + product.getBatch() + newLine +
//                "Odmiana: " + product.getVariety() + newLine +
//                "Kolor: " + product.getColor() + newLine +
//                "Grupa EN: " + product.getGroup() + newLine +
//                "Podgrupa EN: " + product.getSubgroup() + newLine +
//                "Obecność off: " + product.getOffPresence() + newLine +
//                "% off: " + product.getOffPercentage() + newLine +
//                "Opis: " + product.getDescription();
//-------------------------------------------



        String report = "Report from " + timeStamp + " Reproduction Number: " + product.getCode() + newLine +
                "Product name:" + product.getName() + newLine +
                "Producer: " + product.getProducer() + newLine +
                "Reproduction Number: " + product.getCode() + newLine +
                "Latin Species: " + product.getSpecies() + newLine +
                "Party: " + product.getBatch() + newLine +
                "Variety: " + product.getVariety() + newLine +
                "Color: " + product.getColor() + newLine +
                "Group EN: " + product.getGroup() + newLine +
                "Subgroup EN: " + product.getSubgroup() + newLine +
                "Presence off: " + product.getOffPresence() + newLine +
                "% off: " + product.getOffPercentage() + newLine +
                "Description: " + product.getDescriptionInPL();









        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED) {
                String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permissions, WRITE_EXTERNAL_STORAGE_CODE);
            } else {
                saveToTxtFile(report);
            }
        } else {
            saveToTxtFile(report);
        }
        System.out.println(product.getCode() + "    product.getCode() product.getCode() product.getCode() product.getCode()");
//        getSupportActionBar().hide();
//
//        Thread t = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(2 * 1000);
//                    startActivity(new Intent(ProductUpdateActivity.this, ProductListActivity.class));
//                    finish();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//        t.start();
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (switchStandardPlantation.isChecked()) {
            standardPlantationField.setText("Nietypowa");

//            standardPlantationField.setTextColor(Color.parseColor("#FF0000"));


            saveReport();
        }
        if (switchStandardPlantationTypical.isChecked()) {
            standardPlantationField.setText("Typowa");

//            standardPlantationField.setTextColor(Color.parseColor("#000000"));
        }


        if (switchOffPresence_Incorrect.isChecked()) {
            offPresenceField.setText("Tak ");
            findViewById(R.id.off_percentage).setEnabled(true);

        } else {
            offPresenceField.setText(null);
            findViewById(R.id.off_percentage).setEnabled(false);
            findViewById(R.id.off_presence).setEnabled(false);
        }
    }
//    public void text_translation(String text){
//        String textToBeTranslated = "hello";
//        String languagePair = "en-pl";
//        //Executing the translation function
//        Translate(textToBeTranslated,languagePair);
//
//    }

//    public String Translate(String textToBeTranslated, String languagePair){
//        TranslatorBackgroundTask translatorBackgroundTask= new TranslatorBackgroundTask(context);
//
//        String translationResult = String.valueOf(translatorBackgroundTask.execute(textToBeTranslated,languagePair)); // Returns the translated text as a String
//        Log.d("Translation Result:",translationResult); // Logs the result in Android Monitor
//
//
//        System.out.println("===============================================translated_from_English_into_Polish====================================================");
//        return translationResult;
//
//    }
//    String REPORTS_PHOTOS_DIR = Environment.DIRECTORY_PICTURES + "/reports_photos";


    public void ok(View view) {
        startActivity(new Intent(ProductUpdateActivity.this, ProductUpdateActivity.class));
        finish();
//        dispatchTakeFullSizePictureIntent();
    }


    public void saveAndExportFile(final View view) throws InterruptedException {
//        if (TextUtils.isEmpty(estimatedCropField.getText()) ||
//                TextUtils.isEmpty(descriptionField.getText()) ||
//                TextUtils.isEmpty(standardPlantationField.getText())
//        ) {
//
//
//            Toast.makeText(this, "Please complete the data", Toast.LENGTH_SHORT).show();
//
//        } else {
        //--------creat file dataTab-----------------------
//        String fileDataTab = Environment.getExternalStorageDirectory().getPath() + "/dataTab";

        String fileDataTab = Environment.getExternalStorageDirectory().getPath();
//        String fileMy_Files = Environment.getExternalStorageDirectory().getPath() + "/My Files";
        String fileMy_Files = Environment.getExternalStorageDirectory().getPath();
        File file = new File(fileDataTab);
        File storageDir = Environment.getExternalStoragePublicDirectory(fileDataTab);
//----------------------------------------------------------------------------


        File mFolder = new File(fileDataTab);
        if (!mFolder.exists()) {
            mFolder.mkdir();
        }
        File Directory = new File(fileDataTab+"/dataTab");
        Directory.mkdirs();

        File mFolderI = new File(fileDataTab);
        if (!mFolder.exists()) {
            mFolder.mkdir();
        }
        File DirectoryI = new File(fileDataTab+"/My Files");
        DirectoryI.mkdirs();









//----------------------------------------------------------------------------
        System.out.println(fileDataTab + "   fileDataTab");
        if (!file.exists()) {  //sprawdza czy lokalizacja pliku nie istnieje
            System.out.println(!file.exists() + " iiiiiiiiiiiiiiiiii");
            storageDir.mkdirs();   //tworzy lokalizacje
            if( storageDir.mkdirs()==true){
                System.out.println("lokalizacja fileDataTab udana");
            }else {
                System.out.println("lokalizacja fileDataTab nie udana");
            }
        }

        File storageDirMy_Files = Environment.getExternalStoragePublicDirectory(fileMy_Files);

        System.out.println(storageDirMy_Files + "   storageDirMy_Files");
        if (!storageDirMy_Files.exists()) {  //sprawdza czy lokalizacja pliku nie istnieje
            storageDirMy_Files.mkdirs();   //tworzy lokalizacje
            if(storageDirMy_Files.mkdirs()==true){
                System.out.println("lokalizacja storageDirMy_Files udana");
            }
        }
        //-------the end create file dataTab--------------
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);


        updateProduct();
          getSupportActionBar().hide();

//-----------------w przypadku gdy chcemy sie upewnić czy napewno dane są dobrze wprowadzone-----------------------------
//        numberOfClicks++;
//        if (numberOfClicks % 2 == 1) {
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setTitle("attention");
//            builder.setMessage("You provided bad logging data correct them");
//            // add a button
//            builder.setPositiveButton("OK", null);
//            // create and show the alert dialog
//            AlertDialog dialog = builder.create();
//            dialog.show();
//        }
//                if (numberOfClicks % 2 == 0) {
//                    Thread.sleep(2 * 1000);
//                    startActivity(new Intent(ProductUpdateActivity.this, RefreshingActivity.class));
//                    finish();
        //-------------------------------------------------------------------------------------------------------------------------


        sqliteToExcel = new SQLiteToExcel(getApplicationContext(), DBHelperInitializer.DB_NAME, directory_path);
        sqliteToExcel.exportAllTables("data.xls", new SQLiteToExcel.ExportListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onCompleted(String filePath) {
                Utils.showSnackBar(view, "Successfully Exported");
                AlertDialog alertDialog = new AlertDialog.Builder(ProductUpdateActivity.this).create();
                alertDialog.setTitle("Stan bazy danych");
                alertDialog.setMessage("Wszelkie zmiany w bazie danych zostały zapisane");


                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
                    }
                });

                alertDialog.show();

            }

            @Override
            public void onError(Exception e) {

                Utils.showSnackBar(view, e.getMessage());
                AlertDialog alertDialog = new AlertDialog.Builder(ProductUpdateActivity.this).create();
                alertDialog.setTitle("Stan bazy danych");
                alertDialog.setMessage("Niestety dane nie zostały zapisane" + e.getMessage());


                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
                    }
                });

                alertDialog.show();
            }
        });


    }




//        Intent intent = new Intent(Intent.ACTION_SENDTO);
//        intent.setData(Uri.parse("mailto:feedback@gmail.com"));
//        intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
//        if (intent.resolveActivity(getPackageManager()) != null) {
//            startActivity(intent);


//    }

//        Thread t = new Thread(new Runnable() {
//        Thread t = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(2 * 1000);
//                   startActivity(new Intent(ProductUpdateActivity.this, RefreshingActivity.class));
//                    finish();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//        t.start();


//            sqliteToExcel = new SQLiteToExcel(getApplicationContext(), DBHelperInitializer.DB_NAME, directory_path);
//            sqliteToExcel.exportAllTables("data.xls", new SQLiteToExcel.ExportListener() {
//                @Override
//                public void onStart() {
//
//                }
//
//                @Override
//                public void onCompleted(String filePath) {
//                    Utils.showSnackBar(view, "Successfully Exported");
//                }
//
//                @Override
//                public void onError(Exception e) {
//                    Utils.showSnackBar(view, e.getMessage());
//                }
//            });

//--------------------------------------------------------------------


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Unregister broadcast receiver
        this.unregisterReceiver(br);
    }

    public void onCaptureButton(View v) {
        String REPORTS_PHOTOS_DIR = directory_path;   //zapis zdjecia w folderze data
//        String REPORTS_PHOTOS_DIR = Environment.DIRECTORY_PICTURES + "/reports_photos";
        String fileDataTab = Environment.getExternalStorageDirectory().getPath();
//        String fileMy_Files = Environment.getExternalStorageDirectory().getPath() + "/My Files";
        String fileMy_Files = Environment.getExternalStorageDirectory().getPath();
        File file = new File(fileDataTab);
        File storageDir = Environment.getExternalStoragePublicDirectory(fileDataTab);
//---------------------------------------------------------------------------


        File mFolder = new File(REPORTS_PHOTOS_DIR);
        if (!mFolder.exists()) {
            mFolder.mkdir();
        }
        File Directory = new File(REPORTS_PHOTOS_DIR);
        Directory.mkdirs();











//----------------------------------------------------------------------------


        File storageDirMy_Filess = Environment.getExternalStoragePublicDirectory(REPORTS_PHOTOS_DIR);

        if (!storageDirMy_Filess.exists()) {  //sprawdza czy lokalizacja pliku nie istnieje
            storageDirMy_Filess.mkdir();   //tworzy lokalizacje
            if(storageDirMy_Filess.mkdir()==true){
                System.out.println("lokalizacja storageDirMy_Files udana");
            }
        }
//        if (TextUtils.isEmpty(estimatedCropField.getText()) ||
//                TextUtils.isEmpty(descriptionField.getText()) ||
//                TextUtils.isEmpty(standardPlantationField.getText())
//        ) {
//
//
//            Toast.makeText(this, "Please complete the data", Toast.LENGTH_SHORT).show();
//
//        } else {
     //       mButtonCapture.setEnabled(true);
            dispatchTakeFullSizePictureIntent();

            System.out.println(numberOfClicks + " numberOfClicksnumberOfClicksnumberOfClicksv");
//
//
//
//        //dispatchTakeThumbNailPictureIntent();
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("I'm sorry");
//        builder.setMessage("I'm sorry, but if you want to take a picture, fill in the description and comments first     " + "\n" +
//                "(PL->Przykro mi, ale jeśli chcesz zrobić zdjęcie, najpierw wypełnij opis i komentarze)");
//        // add a button
//        builder.setPositiveButton("OK", null);
//        // create and show the alert dialog
//        AlertDialog dialog = builder.create();
//        dialog.show();
//        }
    }


    public void onSendButton(View v) {
        //Create a send Intent and view Chooser dialog
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
        intent.setType("text/plain");

        String title = "Send with";
        //Create intent to show the chooser dialog
        Intent chooser = Intent.createChooser(intent, title);
        // Verify the original intent will resolve to at least one activity
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(chooser);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            System.out.println("if I----------------------------------");
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageView.setImageBitmap(imageBitmap);
            mButtonCapture.setEnabled(true);
        } else if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            System.out.println("if II----------------------------------");

            setPic();
            mButtonCapture.setEnabled(true);
        }
    }

    private void dispatchTakeThumbNailPictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }


//    private File createImageFile() throws IOException {
//        // Create an image file name
//
//        Product product = getProductFromViewFields();
//
//        String[] tecxtSpilt = product.getName().split("/");
//        String nameProduktSpilt_I = tecxtSpilt[0];
//        String nameProductSpilt_II = tecxtSpilt[1];
//        String nameProductSpilt_III = tecxtSpilt[2];
////        String sumTextName = nameProduktSpilt_I;
////        String sumTextName = nameProduktSpilt_I + "__" + nameProductSpilt_II + "__" + nameProductSpilt_III;
//
////        String file_foto_name = product.getProducer()+"_"+product.getCode()+"_"+ sumTextName;
//        String file_foto_name = "new";
//        System.out.println( file_foto_name+"  file_foto_name");
////        System.out.println( sumTextName+"  sumTextName");
//
//        String nameFileImage = product.getProducer() + "_" + product.getCode();
//        String REPORTS_PHOTOS_DIR = Environment.DIRECTORY_PICTURES + "/reports_photos/" + nameFileImage;
//
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String imageFileName =file_foto_name;
////        String imageFileName = product.getProducer()+"_"+ product.getName()+"_"+product.getCode();
////        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//
//        File storageDir = Environment.getExternalStoragePublicDirectory(REPORTS_PHOTOS_DIR);
//
//        System.out.println("storageDir" + storageDir);
//        if (!storageDir.exists()) {  //sprawdza czy lokalizacja pliku nie istnieje
//            storageDir.mkdir();   //tworzy lokalizacje
//        }
//
//        File image = File.createTempFile(
//                imageFileName,  /* prefix *  nazwa pliku*/
//                ".jpg",         /* suffix */
//                storageDir      /* directory */
//
//        );
//
//        // Save a file: path for use with ACTION_VIEW intents
//        mCurrentPhotoPath = image.getAbsolutePath();
//        System.out.println("--------------------------------------------------------------------------------------------------");
//        System.out.println("Environment.DIRECTORY_PICTURES   " + Environment.DIRECTORY_PICTURES);
//        System.out.println(mCurrentPhotoPath);
//        return image;
//    }
private File createImageFile() throws IOException {
    // Create an image file name

    Product product = getProductFromViewFields();
    String file_foto_name = product.getProducer() + "_" + product.getCode() + "_" + product.getSpecies() + "_";
    String nameFileImage = product.getProducer() + "_" + product.getCode();
    String REPORTS_PHOTOS_DIR = Environment.DIRECTORY_PICTURES + "/reports_photos/" + nameFileImage;

    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    String imageFileName =file_foto_name + "_" + timeStamp;
//        String imageFileName = product.getProducer()+"_"+ product.getName()+"_"+product.getCode();
//        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

    File storageDir = Environment.getExternalStoragePublicDirectory(REPORTS_PHOTOS_DIR);

    System.out.println("storageDir" + storageDir);
    if (!storageDir.exists()) {  //sprawdza czy lokalizacja pliku nie istnieje
        storageDir.mkdir();   //tworzy lokalizacje
    }

    File image = File.createTempFile(
            imageFileName,  /* prefix *  nazwa pliku*/
            ".jpg",         /* suffix */
            storageDir      /* directory */

    );

    // Save a file: path for use with ACTION_VIEW intents
    mCurrentPhotoPath = image.getAbsolutePath();
    System.out.println("--------------------------------------------------------------------------------------------------");
    System.out.println("Environment.DIRECTORY_PICTURES   " + Environment.DIRECTORY_PICTURES);
    System.out.println(mCurrentPhotoPath);
    return image;
}


    private void dispatchTakeFullSizePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        boolean wynikI;
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
             wynikI = true;
            System.out.println(wynikI + " wynik");
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                wynikI = false;
                System.out.println(wynikI + " wynik");
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                //File Provider
                //https://developer.android.com/reference/android/support/v4/content/FileProvider.html
                Uri photoURI = FileProvider.getUriForFile(this,
                        "snowroller.androidcamera.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);

                System.out.println(photoURI + " photoUri");
            }
        }
        wynikI = false;
        System.out.println(wynikI + " wynik");
    }

    private void setPic() {
        // Get the dimensions of the View
        int targetW = mImageView.getWidth();
        int targetH = mImageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        mImageView.setImageBitmap(bitmap);
    }

    public void backListView(View view) {
        startActivity(new Intent(ProductUpdateActivity.this, RefreshingActivity.class));
//        startActivity(new Intent(ProductUpdateActivity.this, EmailAutoActivity.class));

    }

    public void Open_gallery(View view) {




    }

    public void open_gallery_app(View view) {
        Intent intent = new Intent();
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.setType("image/*");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    static class MyBroadCastReviecer extends BroadcastReceiver {

        private static final String TAG = "MyBroadCastReceiver";

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager cm =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null &&
                    activeNetwork.isConnectedOrConnecting();

//            Update CheckBox to reflect connection status.
//            CheckBox checkBox = findViewById(R.id.checkBox);
//            checkBox.setChecked(isConnected);
        }
    }

    public String returnNameFile() {
        Product product = getProductFromViewFields();

        String nameFileImage = product.getProducer() + "_" + product.getCode();
        String localFile = nameFileImage;
        System.out.println(localFile + "  localFilelocalFilelocalFilelocalFilelocalFile");
        return localFile;
    }


//---------------------------------------------------------------------------------------------------
    //Method Gllery product


    public ArrayList<imageFolder> getPicturePaths() {
        ArrayList<imageFolder> picFolders = new ArrayList<>();
        ArrayList<String> picPaths = new ArrayList<>();
        Uri allImagesuri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Images.ImageColumns.DATA, MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME, MediaStore.Images.Media.BUCKET_ID};
        Cursor cursor = this.getContentResolver().query(allImagesuri, projection, null, null, null);
        try {
            if (cursor != null) {
                cursor.moveToFirst();
            }
            do {
                imageFolder folds = new imageFolder();
                String name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME));
                String folder = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
                String datapath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
//                String a = "/storage/emulated/0/Pictures/reports_photos/SHEN_00013/";
                //String folderpaths =  datapath.replace(name,"");
                String folderpaths = datapath.substring(0, datapath.lastIndexOf(folder + "/"));
                folderpaths = folderpaths + folder + "/";
//                String folderpaths ="/storage/emulated/0/DCIM/Camera";


                System.out.println(folderpaths + " folderpa");
                System.out.println(folder + " folder");


                if (!picPaths.contains(folderpaths)) {
                    picPaths.add(folderpaths);

                    folds.setPath(folderpaths);
                    folds.setFolderName(folder);
                    folds.setFirstPic(datapath);//if the folder has only one picture this line helps to set it as first so as to avoid blank image in itemview
                    folds.addpics();
                    picFolders.add(folds);
                } else {
                    for (int i = 0; i < picFolders.size(); i++) {
                        if (picFolders.get(i).getPath().equals(folderpaths)) {
                            picFolders.get(i).setFirstPic(datapath);
                            picFolders.get(i).addpics();
                        }
                    }
                }
            } while (cursor.moveToNext());
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < picFolders.size(); i++) {
            Log.d("picture folders", picFolders.get(i).getFolderName() + " and path = " + picFolders.get(i).getPath() + " " + picFolders.get(i).getNumberOfPics());
        }

        //reverse order ArrayList
       /* ArrayList<imageFolder> reverseFolders = new ArrayList<>();

        for(int i = picFolders.size()-1;i > reverseFolders.size()-1;i--){
            reverseFolders.add(picFolders.get(i));
        }*/
        System.out.println(picFolders + " to jest folderpaths");
        return picFolders;
    }


    @Override
    public void onPicClicked(PicHolder holder, int position, ArrayList<pictureFacer> pics) {

    }

    /**
     * Each time an item in the RecyclerView is clicked this method from the implementation of the transitListerner
     * in this activity is executed, this is possible because this class is passed as a parameter in the creation
     * of the RecyclerView's Adapter, see the adapter class to understand better what is happening here
     *
     * @param pictureFolderPath a String corresponding to a folder path on the device external storage
     */
    @Override
    public void onPicClicked(String pictureFolderPath, String folderName) {
        Intent move = new Intent(ProductUpdateActivity.this, ImageDisplay.class);
        //---------------------------
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            File f = new File("file://"+ Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES));
            Uri contentUri = Uri.fromFile(f);
            mediaScanIntent.setData(contentUri);
            this.sendBroadcast(mediaScanIntent);

            System.out.println("file://"+ Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) );
        }
        else
        {
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + Environment.getExternalStorageDirectory())));
        }



        //------------------------
        String folderPath = returnNameFile();

        pictureFolderPath = "/storage/emulated/0/Pictures/reports_photos/" + folderPath + "/";


        System.out.println(folderPath + "                folderPath folderPath");
        move.putExtra("folderPath", pictureFolderPath);
        move.putExtra("folderName", folderName);
        startActivity(move);
    }


   /* public int getCardsOptimalWidth(int numberOfRows){
        Configuration configuration = MainActivity.this.getResources().getConfiguration();
        int screenWidthDp = configuration.screenWidthDp; //The current width of the available screen space, in dp units, corresponding to screen width resource qualifier.
        int smallestScreenWidthDp = configuration.smallestScreenWidthDp; //The smallest screen size an application will see in normal operation, corresponding to smallest screen width resource qualifier.
        int each = screenWidthDp / numberOfRows;

        return each;
    }*/

   /* private int dpToPx(int dp) {
        float density = MainActivity.this.getResources()
                .getDisplayMetrics()
                .density;
        return Math.round((float) dp * density);
    }*/


}
