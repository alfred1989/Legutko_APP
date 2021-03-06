package com.example.legutkoapplication.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
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
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.ImageDisplay;
import com.ajts.androidmads.library.SQLiteToExcel;
import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialog;
import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialogListener;
import com.example.legutkoapplication.MainActivity;
import com.example.legutkoapplication.R;
import com.example.legutkoapplication.RefreshingActivity;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class ProductUpdateActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, itemClickListener {
    private static final int WRITE_EXTERNAL_STORAGE_CODE = 2;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 2;

    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    //----------------------------------------------------
    DBHelperInitializer dbHelper;
    SQLiteToExcel sqliteToExcel;
    String directory_path = Environment.getExternalStorageDirectory().getPath() + "/dataTab/";
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
    TextView descriptionField;
    EditText standardPlantationField;
    EditText commentField;
    EditText batchField;
    EditText codeField;
    EditText plantationIdField;
    EditText descriptionInPLField;
    EditText symbolField;
    EditText historical_dataField;
    EditText contractField;
    EditText recently_addedField;
    EditText plantation_areaField;
    Switch switchStandardPlantation;
    Switch switchStandardPlantationTypical;
    Switch switchOffPresence_Incorrect;
    Switch switchOffPresence_Typical;
    private ImageView mImageView;
    private Button mButtonCapture;
    String mCurrentPhotoPath;

    private BroadcastReceiver br;
    public int numberOfClicks = 0;
    String timeStamp = new SimpleDateFormat("yyyy_MM_dd", Locale.getDefault()).format(System.currentTimeMillis());
    RecyclerView folderRecycler;
    TextView empty;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("WrongViewCast")
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

        empty = findViewById(R.id.empty);

        folderRecycler = findViewById(R.id.folderRecycler);
        folderRecycler.addItemDecoration(new MarginDecoration(this));
        folderRecycler.hasFixedSize();
        ArrayList<imageFolder> folds = getPicturePaths();

        if (folds.isEmpty()) {
            empty.setVisibility(View.VISIBLE);
        } else {
            RecyclerView.Adapter folderAdapter = new pictureFolderAdapter(folds, ProductUpdateActivity.this, this);
            folderRecycler.setAdapter(folderAdapter);
        }

        changeStatusBarColor();

        dbHelper = new DBHelperInitializer(this);
        mImageView = findViewById(R.id.imageView);
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
        descriptionInPLField = findViewById(R.id.descriptionInPL);
        symbolField = findViewById(R.id.symbol);
        historical_dataField = findViewById(R.id.historical_dataField_activiti);
        contractField = findViewById(R.id.contract);
        recently_addedField = findViewById(R.id.recently_added);
        plantation_areaField = findViewById(R.id.plantation_area);


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
        descriptionInPLField.setText(product.getDescriptionInPL());
        symbolField.setText(product.getSymbol());
        historical_dataField.setText(product.getHistorical_data());
        contractField.setText(product.getContract());
        recently_addedField.setText(product.getRecently_added());
        plantation_areaField.setText(product.getPlantation_area());

        String nameSpilt = product.getName();
        String[] nameSpiltTab = nameSpilt.split("/");
        nameField.setText(nameSpiltTab[0]);
        System.out.println(nameSpiltTab[0] + "spilt");

        if (product.getStandardPlantation() == null) {
            standardPlantationField.setBackgroundColor(Color.parseColor("#FF8C00"));
            findViewById(R.id.standard_plantation).setBackgroundColor(Color.parseColor("#FF8C00"));
        }
        if (product.getStandardPlantation().equals("Typowa")) {
            standardPlantationField.setBackgroundColor(Color.parseColor("#3da549"));
            findViewById(R.id.standard_plantation).setBackgroundColor(Color.parseColor("#3da549"));
        }
        if (product.getStandardPlantation().equals("Nietypowa")) {

            standardPlantationField.setBackgroundColor(Color.parseColor("#FF0000"));
            findViewById(R.id.standard_plantation).setBackgroundColor(Color.parseColor("#FF0000"));
        }

//        if (product.getComment() == null) {
//
//            findViewById(R.id.comment).setBackgroundColor(Color.parseColor("#ff5130"));
//
//        }

        System.out.println();
        if (product.getDescription() == null) {

            findViewById(R.id.description).setBackgroundColor(Color.parseColor("#ff5130"));
        }
        String nameFileImage = product.getProducer() + "_" + product.getCode();
        System.out.println(nameFileImage + "   nameFileImage ");


        //------------------------------------------------------------------------------------------
        System.out.println("---------------------------------------------------------------------------");
        System.out.println(product.getProducer() + "product.getProducer())");
        System.out.println(product.getSpecies() + "product.getSpecies())");
        System.out.println(product.getName() + "product.getName())");
        System.out.println(product.getVariety() + "product.getVariety())");
        System.out.println(product.getColor() + "product.getColor())");
        System.out.println(product.getGroup() + "product.getGroup())");
        System.out.println(product.getSubgroup() + "product.getSubgroup())");
        System.out.println(product.getEstimatedCrop() + "product.getEstimatedCrop())");
        System.out.println(product.getOffPresence() + "product.getOffPresence())");
        System.out.println(product.getOffPercentage() + "product.getOffPercentage())");
        System.out.println(product.getDescription() + "product.getDescription())");
        System.out.println(product.getStandardPlantation() + "product.getStandardPlantation())");
        System.out.println(product.getComment() + "product.getComment())");
        System.out.println(product.getBatch() + "product.getBatch())");
        System.out.println(product.getCode() + "product.getCode())");
        System.out.println(product.getPlantationId() + "product.getPlantationId())");
        System.out.println(product.getDescriptionInPL() + "product.getDescriptionInPL())");
        System.out.println(product.getSymbol() + "product.getSymbol())");
        System.out.println(product.getHistorical_data() + "product.getHistorical_data())");
        System.out.println(product.getContract() + "product.getContract())");
        System.out.println(product.getRecently_added() + "product.getRecently_added())");
        System.out.println("---------------------------------------------------------------------------");

        //------------------------------------------------------------------------------------------


    }


    private void saveToTxtFile(String a) {
        String timeStamp = new SimpleDateFormat("yyyy_MM_dd", Locale.getDefault()).format(System.currentTimeMillis());
        String nameFile = speciesField.getText().toString().trim();
        String nameProducent = producerField.getText().toString().trim();
        String fileName = nameProducent + "_" + nameFile + "_" + timeStamp + ".txt";
        try {
            File path = Environment.getExternalStorageDirectory();
            File dir = new File(directory_path + "/My_report/" + producerField.getText().toString().trim());
            dir.mkdir();
            System.out.println(dir + " dir");
            System.out.println(directory_path + " directory_path");
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
        String descriptionInPL = descriptionInPLField.getText().toString().trim();
        String symbol = symbolField.getText().toString().trim();
        String historical_data = historical_dataField.getText().toString().trim();
        String contract = contractField.getText().toString().trim();
        String recently_added = recently_addedField.getText().toString().trim();
        String plantation_area = plantation_areaField.getText().toString().trim();
//        System.out.println(own_seed_batch + "   own_seed_batch ");
        String test = standardPlantation;
        if (test == null) {
            System.out.println("Hello word");
        }
        if (description == null) {
            descriptionField.setBackgroundColor(Color.RED);
            descriptionField.setText("-");
        }
        String descriptionInEnglish = descriptionField.getText().toString() + "|";
        description = descriptionInEnglish;
//        String  commentaryInPL = own_seed_batch;  //własna partia nasion
//        System.out.println(commentaryInPL+ "   commentaryInPL");
//        descriptionInEnglish = own_seed_batch;
        System.out.println();
        historical_data = historical_data + "; " + timeStamp + " " + recently_added;
        description = timeStamp + " " + recently_added;


        return new Product(productId, producer, species, name, variety, color, group, subgroup, estimatedCrop,
                offPresence, offPercentage, description, standardPlantation, comment, batch, code, plantationId,
                descriptionInPL, symbol, historical_data, contract, recently_added, plantation_area);
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
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (switchStandardPlantation.isChecked()) {
            standardPlantationField.setText("Nietypowa");
            saveReport();
        } else {
            standardPlantationField.setText("Typowa");
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

    public void ok(View view) {
        startActivity(new Intent(ProductUpdateActivity.this, ProductUpdateActivity.class));
        finish();
    }


    public void saveAndExportFile(final View view) throws InterruptedException {
        String fileDataTab = Environment.getExternalStorageDirectory().getPath();
        String fileMy_Files = Environment.getExternalStorageDirectory().getPath();
        File file = new File(fileDataTab);
        File storageDir = Environment.getExternalStoragePublicDirectory(fileDataTab);
        File mFolder = new File(fileDataTab);
        if (!mFolder.exists()) {
            mFolder.mkdir();
        }
        File Directory = new File(fileDataTab + "/dataTab");
        Directory.mkdirs();

        File mFolderI = new File(fileDataTab);
        if (!mFolder.exists()) {
            mFolder.mkdir();
        }
        File DirectoryI = new File(fileDataTab + "/My Files");
        DirectoryI.mkdirs();
        System.out.println(fileDataTab + "   fileDataTab");
        if (!file.exists()) {  //sprawdza czy lokalizacja pliku nie istnieje
            System.out.println(!file.exists() + " iiiiiiiiiiiiiiiiii");
            storageDir.mkdirs();   //tworzy lokalizacje
            if (storageDir.mkdirs() == true) {
                System.out.println("lokalizacja fileDataTab udana");
            } else {
                System.out.println("lokalizacja fileDataTab nie udana");
            }
        }

        File storageDirMy_Files = Environment.getExternalStoragePublicDirectory(fileMy_Files);

        System.out.println(storageDirMy_Files + "   storageDirMy_Files");
        if (!storageDirMy_Files.exists()) {  //sprawdza czy lokalizacja pliku nie istnieje
            storageDirMy_Files.mkdirs();   //tworzy lokalizacje
            if (storageDirMy_Files.mkdirs() == true) {
                System.out.println("lokalizacja storageDirMy_Files udana");
            }
        }
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        updateProduct();
        getSupportActionBar().hide();

        sqliteToExcel = new SQLiteToExcel(getApplicationContext(), DBHelperInitializer.DB_NAME, directory_path);
        sqliteToExcel.exportAllTables("data.xls", new SQLiteToExcel.ExportListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onCompleted(String filePath) {
                new TTFancyGifDialog.Builder(ProductUpdateActivity.this)
                        .setTitle("Baza danych została zapisana w pamięci urządzenia")
                        .setPositiveBtnText("Ok")
                        .setPositiveBtnBackground("#22b573")
                        .setGifResource(R.drawable.gif1)      //pass your gif, png or jpg
                        .isCancellable(true)
                        .OnPositiveClicked(new TTFancyGifDialogListener() {
                            @Override
                            public void OnClick() {
                                Toast.makeText(ProductUpdateActivity.this, "Ok", Toast.LENGTH_SHORT).show();
                            }
                        })

                        .build();

            }

            @Override
            public void onError(Exception e) {

                Utils.showSnackBar(view, e.getMessage());
                new TTFancyGifDialog.Builder(ProductUpdateActivity.this)
                        .setTitle("Błąd baza danych nie została zapisana")
                        .setPositiveBtnText("Ok")
                        .setPositiveBtnBackground("#22b573")
                        .setGifResource(R.drawable.gif2)      //pass your gif, png or jpg
                        .isCancellable(true)
                        .OnPositiveClicked(new TTFancyGifDialogListener() {
                            @Override
                            public void OnClick() {
                                Toast.makeText(ProductUpdateActivity.this, "Ok", Toast.LENGTH_SHORT).show();
                            }
                        })

                        .build();
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.unregisterReceiver(br);
    }

    public void onCaptureButton(View v) {
        String REPORTS_PHOTOS_DIR = directory_path;   //zapis zdjecia w folderze data

        System.out.println(directory_path + "   directory_path");
        String fileDataTab = Environment.getExternalStorageDirectory().getPath();
        File mFolder = new File(REPORTS_PHOTOS_DIR);
        if (!mFolder.exists()) {
            mFolder.mkdir();
        }
        File Directory = new File(REPORTS_PHOTOS_DIR);
        Directory.mkdirs();
        File storageDirMy_Filess = Environment.getExternalStoragePublicDirectory(REPORTS_PHOTOS_DIR);

        if (!storageDirMy_Filess.exists()) {  //sprawdza czy lokalizacja pliku nie istnieje
            storageDirMy_Filess.mkdir();   //tworzy lokalizacje
            if (storageDirMy_Filess.mkdir() == true) {
                System.out.println("lokalizacja storageDirMy_Files udana");
            }
        }
        dispatchTakeFullSizePictureIntent();
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
//            mButtonCapture.setEnabled(true);
        }
    }

    private void dispatchTakeThumbNailPictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name

        Product product = getProductFromViewFields();
        String file_foto_name = product.getProducer() + "_" + product.getCode() + "_" + product.getSpecies() + "_";
        String nameFileImage = product.getProducer() + "_" + product.getCode();
        String REPORTS_PHOTOS_DIR = Environment.DIRECTORY_PICTURES + "/reports_photos/" + nameFileImage;
//        String REPORTS_PHOTOS_DIR =  nameFileImage;
        System.out.println(Environment.DIRECTORY_PICTURES + "/" + nameFileImage + "   Environment.DIRECTORY_PICTURES");

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = file_foto_name + "_" + timeStamp;
        File storageDir = Environment.getExternalStoragePublicDirectory(REPORTS_PHOTOS_DIR);
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
        galleryAddPic();
        return image;
    }

    private void galleryAddPic() {
        try {
            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            File f = new File(mCurrentPhotoPath);
            Uri contentUri = Uri.fromFile(f);
            mediaScanIntent.setData(contentUri);
            this.sendBroadcast(mediaScanIntent);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
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

    public void open_gallery_app(View view) {
        Intent intent = new Intent();
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.setType("image/*");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void opegnJPG(View view) {
        String name_product = returnNameFile();
        String REPORTS_PHOTOS_DIR = Environment.DIRECTORY_PICTURES;
        File file = new File(REPORTS_PHOTOS_DIR);
        Uri uri_path = Uri.fromFile(file);
        String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension
                (MimeTypeMap.getFileExtensionFromUrl(REPORTS_PHOTOS_DIR));


        Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
        intent.setType(mimeType);
        intent.setDataAndType(uri_path, mimeType);
        startActivity(intent);
    }

    static class MyBroadCastReviecer extends BroadcastReceiver {

        private static final String TAG = "MyBroadCastReceiver";

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager cm =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
//            activeNetwork.isConnectedOrConnecting();
        }
    }

    public String returnNameFile() {
        Product product = getProductFromViewFields();

        String nameFileImage = product.getProducer() + "_" + product.getCode();
        String localFile = nameFileImage;
        System.out.println(localFile + "  localFilelocalFilelocalFilelocalFilelocalFile");
        return localFile;
    }

    private ArrayList<imageFolder> getPicturePaths() {
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

                //String folderpaths =  datapath.replace(name,"");
                String folderpaths = datapath.substring(0, datapath.lastIndexOf(folder + "/"));
                folderpaths = folderpaths + folder + "/";
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
        String name_product = returnNameFile();
//        File file = new File(name_product);
//        Uri uri_path = Uri.fromFile(file);
//        String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension
//                (MimeTypeMap.getFileExtensionFromUrl(name_product));
//
//
//        Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
//        intent.setType(mimeType);
//        intent.setDataAndType(uri_path, mimeType);
//        startActivity(intent);
        Intent move = new Intent(ProductUpdateActivity.this, ImageDisplay.class);

        String REPORTS_PHOTOS_DIR = Environment.DIRECTORY_PICTURES + "/reports_photos/" + name_product;

        move.putExtra("folderPath", REPORTS_PHOTOS_DIR);
        System.out.println(Environment.DIRECTORY_PICTURES + "/" + name_product + "  pictureFolderPath");
//        move.putExtra("folderName",name_product);

        //move.putExtra("recyclerItemSize",getCardsOptimalWidth(4));
        startActivity(move);
    }


    /**
     * Default status bar height 24dp,with code API level 24
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void changeStatusBarColor() {
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.black));

    }

}
