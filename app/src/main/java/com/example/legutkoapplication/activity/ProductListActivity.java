package com.example.legutkoapplication.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.legutkoapplication.ProductAdapter;
import com.example.legutkoapplication.R;
import com.example.legutkoapplication.RefreshingActivity;
import com.example.legutkoapplication.database.DBHelperInitializer;
import com.example.legutkoapplication.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductListActivity extends AppCompatActivity {
    DBHelperInitializer db;
    ArrayList<String> productNames;
    ListView productListView;
    List<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        db = new DBHelperInitializer(this);
        productList = db.getAllProducts();
        productNames = new ArrayList<>();
        productListView = findViewById(R.id.lvProduct);

        viewAllProducts();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Back");


        productListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String text = productListView.getItemAtPosition(position).toString();
                Product product = ((ProductAdapter) productListView.getAdapter()).getItem(position);
                Toast.makeText(ProductListActivity.this, "" + text, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ProductListActivity.this, ProductUpdateActivity.class);
                intent.putExtra("PRODUCT", product);
                startActivity(intent);

            }
        });


    }

    private void viewAllProducts() {


        if (productList.size() == 0) {
            Toast.makeText(this, "Brak wpisów w bazie danych", Toast.LENGTH_SHORT).show();
        } else {

            ProductAdapter adapter = new ProductAdapter(this, R.layout.list_item_product, productList);


            productListView.setAdapter(adapter);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        MenuItem searchItem = menu.findItem(R.id.item_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<Product> matchedProducts = new ArrayList<>();
                System.out.println(newText + "   new text");
                String newTextLowerCase = newText.toLowerCase();
                for (Product product : productList) {
//                    if (
//                            product.getName().toLowerCase().contains(newTextLowerCase) ||
//                                    product.getProducer().toLowerCase().contains(newTextLowerCase) ||
//                                    product.getCode().toLowerCase().contains(newTextLowerCase) ||
//                                    product.getJ().toLowerCase().contains(newTextLowerCase) ||
//                                    product.getPlantation().toLowerCase().contains(newTextLowerCase)
//                    ) {
//                        matchedProducts.add(product);
//                    }
                    String producerAndName = product.getProducer() + " " + product.getName();
                    String producerEndCode = product.getProducer() + " " + product.getCode();
                    String nameEndfield = product.getName() + " " + product.getComment();
                    String fieldEndName = product.getComment() + " " + product.getName();

                    if (
                            producerAndName != null && producerAndName.toLowerCase().contains(newTextLowerCase) ||
                                    producerEndCode != null && producerEndCode.toLowerCase().contains(newTextLowerCase) ||
                                    product.getProducer() != null && product.getProducer().toLowerCase().contains(newTextLowerCase) ||
                                    product.getCode() != null && product.getCode().toLowerCase().contains(newTextLowerCase) ||
                                    product.getComment() != null && product.getComment().toLowerCase().contains(newTextLowerCase) ||
                                    nameEndfield != null && nameEndfield.toLowerCase().contains(newTextLowerCase) ||
                                    fieldEndName != null && fieldEndName.toLowerCase().contains(newTextLowerCase) ||

                                    product.getSpecies() != null && product.getSpecies().toLowerCase().contains(newTextLowerCase) ||
                                    product.getName() != null && product.getName().toLowerCase().contains(newTextLowerCase) ||
                                    product.getVariety() != null && product.getVariety().toLowerCase().contains(newTextLowerCase) ||
                                    product.getGroup() != null && product.getGroup().toLowerCase().contains(newTextLowerCase) ||
                                    product.getSubgroup() != null && product.getSubgroup().toLowerCase().contains(newTextLowerCase) ||
                                    product.getOffPresence() != null && product.getOffPresence().toLowerCase().contains(newTextLowerCase) ||
                                    product.getOffPercentage() != null && product.getOffPercentage().toLowerCase().contains(newTextLowerCase) ||
                                    product.getStandardPlantation() != null && product.getStandardPlantation().toLowerCase().contains(newTextLowerCase) ||
                                    product.getBatch() != null && product.getBatch().toLowerCase().contains(newTextLowerCase) ||
                                    product.getPlantationId() != null && product.getPlantationId().toLowerCase().contains(newTextLowerCase) ||
                                    product.getSymbol() != null && product.getSymbol().toLowerCase().contains(newTextLowerCase)
                    ) {
                        matchedProducts.add(product);
                    }
                }

                ProductAdapter adapter = new ProductAdapter(ProductListActivity.this, R.layout.list_item_product, matchedProducts);

                productListView.setAdapter(adapter);

                return true;
            }

        });

        return super.onCreateOptionsMenu(menu);
    }

}