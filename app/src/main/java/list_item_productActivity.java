//import android.graphics.Color;
//import android.os.Bundle;
//import android.widget.EditText;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.example.legutkoapplication.R;
//import com.example.legutkoapplication.database.DBHelperInitializer;
//import com.example.legutkoapplication.model.Product;
//
//public class list_item_productActivity  extends AppCompatActivity {
//    DBHelperInitializer dbHelper;
//    EditText standardPlantationField;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.list_item_product);
//        Product product = (Product) getIntent().getExtras().getSerializable("PRODUCT");
//
//        standardPlantationField = findViewById(R.id.standard_plantation);
//
//        dbHelper = new DBHelperInitializer(this);
//        standardPlantationField.setText(product.getStandardPlantation());
//        String standardPlantation = standardPlantationField.getText().toString().trim();
//
//        if(standardPlantationField.equals("abc") ){
//            standardPlantationField.setTextColor(Color.parseColor("#FF0000"));
//
//
//        }
//
//
//    }


//}
