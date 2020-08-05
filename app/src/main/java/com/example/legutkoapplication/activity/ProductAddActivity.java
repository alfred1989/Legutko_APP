package com.example.legutkoapplication.activity;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.legutkoapplication.MenuActivity;
import com.example.legutkoapplication.R;
//import com.example.legutkoapplication.database.DBHelper;
import com.example.legutkoapplication.database.DBHelperInitializer;
import com.example.legutkoapplication.model.Product;

import java.time.LocalDate;

public class ProductAddActivity extends AppCompatActivity {
    int productId;
    EditText producerField_add;
    EditText speciesField_add;
    EditText nameField_add;
    EditText varietyField_add;
    EditText colorField_add;
    EditText groupField_add;
    EditText subgroupField_add;
    EditText estimatedCropField_add;
    EditText offPresenceField_add;
    EditText offPercentageField_add;
    EditText descriptionField_add;
    EditText standardPlantationField_add;
    TextView commentField_add;
    EditText batchField_add;
    EditText codeField_add;
    TextView plantationIdField_add;
    TextView commentaryInPLField_add;
    TextView descriptionInEnglishField_add;
    TextView dField_add;
    TextView commentPLField_add;
    TextView symbolField_add;
    TextView own_seed_batchField_add;


    int id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_add);
        producerField_add = findViewById(R.id.producer);
        speciesField_add = findViewById(R.id.species);
        nameField_add = findViewById(R.id.name);
        varietyField_add = findViewById(R.id.variety);
        colorField_add = findViewById(R.id.color);
        groupField_add = findViewById(R.id.group);
        subgroupField_add = findViewById(R.id.subgroup);
        estimatedCropField_add = findViewById(R.id.estimated_crop);
        offPresenceField_add = findViewById(R.id.off_presence);
        offPercentageField_add = findViewById(R.id.off_percentage);
        descriptionField_add = findViewById(R.id.description);
        standardPlantationField_add = findViewById(R.id.standard_plantation);
        commentField_add = findViewById(R.id.comment);
        batchField_add = findViewById(R.id.batch);
        codeField_add = findViewById(R.id.code);
        plantationIdField_add = findViewById(R.id.plantation_id);
        commentaryInPLField_add = findViewById(R.id.commentaryInPL);
        descriptionInEnglishField_add = findViewById(R.id.descriptionInEnglish);
        dField_add = findViewById(R.id.d);
        commentPLField_add = findViewById(R.id.commentPL);
        symbolField_add = findViewById(R.id.symbol);
        own_seed_batchField_add = findViewById(R.id.own_seed_batch);


        ActionBar actionBarr = getSupportActionBar();
        actionBarr.setTitle("Back");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // R.menu.mymenu is a reference to an xml file named mymenu.xml which should be inside your res/menu directory.
        // If you don't have res/menu, just create a directory named "menu" inside res
        getMenuInflater().inflate(R.menu.add, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities

    //
//
//
    public void saveProduct(View view) {
        LocalDate myObj = null; // Create a date object
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            myObj = LocalDate.now();
        }
        String producer = producerField_add.getText().toString();
        String species = speciesField_add.getText().toString().trim();
        String name = nameField_add.getText().toString().trim();
//        String variety = varietyField_add.getText().toString().trim();
        String variety = "0";
        String color = colorField_add.getText().toString().trim();
        String group = groupField_add.getText().toString().trim();
        String subgroup = subgroupField_add.getText().toString().trim();
        String estimatedCrop = estimatedCropField_add.getText().toString().trim();
        String offPresence = offPresenceField_add.getText().toString().trim();
        String offPercentage = offPercentageField_add.getText().toString().trim();
        String description = descriptionField_add.getText().toString().trim();
        String standardPlantation = standardPlantationField_add.getText().toString().trim();
        String comment = commentField_add.getText().toString().trim();
        String batch = batchField_add.getText().toString().trim();
        String code = codeField_add.getText().toString().trim();
        String plantationId = plantationIdField_add.getText().toString().trim();
        String symbol = symbolField_add.getText().toString().trim();
        String powierzchnia = commentaryInPLField_add.getText().toString().trim();
        String own_seed_batch = own_seed_batchField_add.getText().toString().trim();
//         String plantation_number   = .getText().toString();
        String   commentaryInPL = own_seed_batch;
        String descriptionInEnglish = myObj + " " + descriptionField_add.getText().toString() +"|";

        // DBHelper db = new DBHelper(this);
        DBHelperInitializer db = new DBHelperInitializer(this);
        Product product =new  Product(productId, producer, species, name, variety, color, group, subgroup, estimatedCrop, offPresence,
                offPercentage, description, standardPlantation, comment, batch, code, plantationId, commentaryInPL, descriptionInEnglish, symbol);
//        Product product = new Product(id, name, producer, code, species,a,b,c,d,e,f,g,h,i,j, off,partia);

        long result = db.addProduct(product);
        if (result != -1) {
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
        }

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(2 * 600);

                    startActivity(new Intent(ProductAddActivity.this, MenuActivity.class));
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        });

        t.start();

    }


//
//
////    public void saveProduct(View view) {
////        String name = nameField.getText().toString();
////        String producer = producerField.getText().toString();
////        String code = codeField.getText().toString();
////        String species = speciesField.getText().toString();
////        String type = typeField       .getText().toString();
////        String variety = varietyField    .getText().toString();
////        String collor = collorField     .getText().toString();
////        String grup = grupField       .getText().toString();
////        String subGrup = subGrupField    .getText().toString();
////        String crop = cropField       .getText().toString();
////        String presence = presenceField   .getText().toString();
////        String description = descriptionField.getText().toString();
////        String plantation = plantationField .getText().toString();
////        String j = jField.getText().toString();
////
////       // DBHelper db = new DBHelper(this);
////        DBHelperInitializer db = new DBHelperInitializer(this);
////        Product product = new Product(id, name, producer, code, species,type,variety,collor,grup,subGrup,crop,presence,description,plantation,j);
////
////        long result = db.addProduct(product);
////        if (result != -1) {
////            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
////        } else {
////            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
////        }
////    }
//
//    public void showAllProducts(View view) {
//        startActivity(new Intent(ProductAddActivity.this, ProductListActivity.class));
//    }
//
//
//

//---------------------------------------------------------------------------------------------------------------------------------------------------------------

}

