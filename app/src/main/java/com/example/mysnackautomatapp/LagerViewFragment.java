package com.example.mysnackautomatapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.mysnackautomatapp.dbController.DBControllerProdukt;


import java.util.HashMap;
import java.util.List;

public class LagerViewFragment extends Fragment {

    EditText txtPName, txtPCat, txtPPrice, txtPMHD, txtPAmount;
    ListView lstProdukt;
    DBControllerProdukt dbControllerProdukt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {

        return inflater.inflate(R.layout.lager_view_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        Log.d("LagerViewFragment", "onViewCreated");

        Button btnSave = view.findViewById(R.id.btnSave);

        txtPCat = view.findViewById(R.id.txtProductCategory);
        txtPName = view.findViewById(R.id.txtProductName);
        txtPPrice = view.findViewById(R.id.txtProductPrice);
        txtPMHD = view.findViewById(R.id.txtProductMHD);
        txtPAmount = view.findViewById(R.id.txtProductBestand);

        lstProdukt = view.findViewById(R.id.lstProducts);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveProduct();
            }
        });

        clear();
        dbControllerProdukt = new DBControllerProdukt(getContext().getApplicationContext());
        setProducts();


    }

    public void saveProduct() {
        try {
            if (dbControllerProdukt == null)
                dbControllerProdukt = new DBControllerProdukt(getContext().getApplicationContext());
            if (TextUtils.isEmpty(txtPCat.getText().toString()) ||
                    TextUtils.isEmpty(txtPName.getText().toString()) ||
                    TextUtils.isEmpty(txtPPrice.getText().toString()) ||
                    TextUtils.isEmpty(txtPMHD.getText().toString())) {
                Toast.makeText(getContext(), "Please enter product name, its category & price to save", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean result = dbControllerProdukt.addProduct(txtPName.getText().toString(), txtPCat.getText().toString(), txtPPrice.getText().toString(), txtPMHD.getText().toString(),txtPAmount.getText().toString());
            if (result) {
                clear();
                Toast.makeText(getContext(), "Product saved successfully", Toast.LENGTH_SHORT).show();
                setProducts();
            } else
                Toast.makeText(getContext(), "Cannot save product", Toast.LENGTH_SHORT).show();

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void clear() {
        txtPName.setText("");
        txtPCat.setText("");
        txtPPrice.setText("");
        txtPAmount.setText("");
        txtPAmount.setText("");
    }

    public void setProducts() {
        try {
            if (dbControllerProdukt == null)
                dbControllerProdukt = new DBControllerProdukt(getContext().getApplicationContext());

            List<HashMap<String, String>> data = dbControllerProdukt.getProducts();
            if (data.size() != 0) {
                SimpleAdapter adapter = new SimpleAdapter(
                        getContext(), data, R.layout.lst_template,
                        new String[]{"id", "product", "category","amount" , "price", "mhd"}, new int[]{
                        R.id.lblId,
                        R.id.lblName,
                        R.id.lblCategory,
                        R.id.lblAmount,
                        R.id.lblPrice,
                        R.id.lblMHD});


                lstProdukt.setAdapter(adapter);
            }

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        Log.d("Add Entry Fragment", "onPause");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("Add Entry Fragment", "onDestroy");


    }
}