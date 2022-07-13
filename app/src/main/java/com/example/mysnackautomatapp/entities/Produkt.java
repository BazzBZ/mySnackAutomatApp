package com.example.mysnackautomatapp.entities;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mysnackautomatapp.R;
import com.example.mysnackautomatapp.dbController.DBControllerProdukt;

import java.util.HashMap;
import java.util.List;

public class Produkt extends AppCompatActivity {

    Button btnSave;
    EditText txtPName, txtPCat, txtPPrice;
    DBControllerProdukt dbControllerProdukt;
    ListView lstProdukt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.products);

        btnSave = findViewById(R.id.btnSave);

        txtProductCategory = findViewById(R.id.txtProductCategory);
        txtProductName = findViewById(R.id.txtProductName);
        txtProductPrice = findViewById(R.id.txtProductPrice);

        lstProducts = findViewById(R.id.lstProducts);

        clear();
        dbController = new DBController(getApplicationContext());
        setProducts();
    }

    public void clear(){
        txtPName.setText("");
        txtPCat.setText("");
        txtPPrice.setText("");
    }

    public void saveProduct(View view){
        try {
            if (dbControllerProdukt == null)
                dbControllerProdukt = new DBControllerProdukt(getApplicationContext());
            if (TextUtils.isEmpty(txtPCat.getText().toString()) ||
                    TextUtils.isEmpty(txtPName.getText().toString()) ||
                    TextUtils.isEmpty(txtPPrice.getText().toString())) {
                Toast.makeText(Produkt.this, "Please enter product name, its category & price to save", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean result = dbControllerProdukt.addProduct(txtPName.getText().toString(), txtPCat.getText().toString(), txtPPrice.getText().toString());
            if (result) {
                clear();
                Toast.makeText(Produkt.this, "Product saved successfully", Toast.LENGTH_SHORT).show();
                setProducts();
            } else
                Toast.makeText(Produkt.this, "Cannot save product", Toast.LENGTH_SHORT).show();

        } catch (Exception ex) {
            Toast.makeText(Produkt.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    public void setProducts() {
        try {
            if(dbControllerProdukt == null)
                dbControllerProdukt = new DBControllerProdukt(getApplicationContext());

            List<HashMap<String, String>> data = dbControllerProdukt.getProducts();
            if (data.size() != 0) {
                SimpleAdapter adapter = new SimpleAdapter(
                        Produkt.this, data, R.layout.lst_template,
                        new String[]{"id", "product", "category", "price"}, new int[]{
                        R.id.lblId,R.id.lblName,
                        R.id.lblCategory, R.id.lblPrice});

                lstProdukt.setAdapter(adapter);
            }

        } catch (Exception ex) {
            Toast.makeText(Produkt.this, ex.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }



}
