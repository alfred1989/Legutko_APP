package com.example.legutkoapplication.activity;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialog;
import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialogListener;
import com.example.legutkoapplication.R;
import com.example.legutkoapplication.RefreshingActivity;
import com.example.legutkoapplication.database.DBHelperInitializer;
import com.example.legutkoapplication.model.Product;
import java.time.LocalDate;

public class ProductAddActivity extends AppCompatActivity {
    DBHelperInitializer dbHelper;
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
    int productId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_add);
        dbHelper = new DBHelperInitializer(this);

        //--------------------------------------camera-----------------------------------
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
        switchStandardPlantationTypical = findViewById(R.id.switch_standard_plantation_typical);
        //----------------------------------Switch Off Presence--------------------------------------------
        switchOffPresence_Incorrect = findViewById(R.id.switch_off_presence);
        ActionBar actionBarr = getSupportActionBar();
        actionBarr.setTitle("Back");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void saveProduct(View view) {
        LocalDate myObj = null; // Create a date object
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            myObj = LocalDate.now();
        }
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
        String test = standardPlantation;
        if (test == null) {
            System.out.println("Hello word");
        }
        if (description == null) {
            descriptionField.setBackgroundColor(Color.RED);
            descriptionField.setText("-");
        }

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            myObj = LocalDate.now();
        }
        String descriptionInEnglish = myObj + " " + descriptionField.getText().toString() + "|";
        description = descriptionInEnglish;
        descriptionInEnglish = descriptionField.getText().toString();
//        String commentaryInPL = own_seed_batch;
        DBHelperInitializer db = new DBHelperInitializer(this);
        Product product = new Product(productId, producer, species, name, variety, color, group, subgroup, estimatedCrop,
                offPresence, offPercentage, description, standardPlantation, comment, batch, code, plantationId,
                descriptionInPL, symbol, historical_data, contract, recently_added, plantation_area);
        long result = db.addProduct(product);
        if (result != -1) {
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
            new TTFancyGifDialog.Builder(ProductAddActivity.this)
                    .setTitle("Baza danych została zapisana w pamięci urządzenia")
                    .setPositiveBtnText("Ok")
                    .setPositiveBtnBackground("#22b573")
                    .setGifResource(R.drawable.gif1)      //pass your gif, png or jpg
                    .isCancellable(true)
                    .OnPositiveClicked(new TTFancyGifDialogListener() {
                        @Override
                        public void OnClick() {
                            Toast.makeText(ProductAddActivity.this,"Ok",Toast.LENGTH_SHORT).show();
                        }
                    })

                    .build();
        } else {
            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
            new TTFancyGifDialog.Builder(ProductAddActivity.this)
                    .setTitle("Błąd baza danych nie została zapisana")

                    .setPositiveBtnBackground("#22b573")
                    .setGifResource(R.drawable.gif2)      //pass your gif, png or jpg
                    .isCancellable(true)
                    .OnPositiveClicked(new TTFancyGifDialogListener() {
                        @Override
                        public void OnClick() {

                        }
                    })

                    .build();
        }
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3 * 600);

                    startActivity(new Intent(ProductAddActivity.this, RefreshingActivity.class));
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }
}


