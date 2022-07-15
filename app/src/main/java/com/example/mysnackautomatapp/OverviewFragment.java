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
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.mysnackautomatapp.dbController.DBControllerLager;

import java.util.HashMap;
import java.util.List;

public class OverviewFragment extends Fragment {

    EditText txtProdName, txtProdCat;
    ListView lstProduktLager;
    DBControllerLager dbControllerLager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {

        return inflater.inflate(R.layout.add_product_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        Log.d("AddEntryFragment", "onViewCreated");

        view.findViewById(R.id.btnSaveProducts);
        view.findViewById(R.id.btnSaveProducts);

        lstProduktLager = view.findViewById(R.id.lstPProducts);

        Button btnSaveProduct = view.findViewById(R.id.btnSaveProducts);

        txtProdName = view.findViewById(R.id.txtProdName);
        txtProdCat = view.findViewById(R.id.txtProdCat);

        btnSaveProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveLagerProducts();


            }
        });

        clear();
        dbControllerLager = new DBControllerLager(getContext().getApplicationContext());
        saveLagerProducts();
    }

    public void saveLagerProducts() {
        try {
            if (dbControllerLager == null)
                dbControllerLager = new DBControllerLager(getContext().getApplicationContext());
            if (TextUtils.isEmpty(txtProdName.getText().toString()) ||
                    TextUtils.isEmpty(txtProdCat.getText().toString())) {
                Toast.makeText(getContext(), "Please enter product name and the respective category to save", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean result = dbControllerLager.addLagerProduct(txtProdName.getText().toString(), txtProdCat.getText().toString());
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
        txtProdCat.setText("");
        txtProdName.setText("");
    }

    public void setProducts() {
        try {
            if (dbControllerLager == null)
                dbControllerLager = new DBControllerLager(getContext().getApplicationContext());

            List<HashMap<String, String>> data = dbControllerLager.getLagerProducts();
            if (data.size() != 0) {
                SimpleAdapter adapter = new SimpleAdapter(
                        getContext(), data, R.layout.lst_template,
                        new String[]{"id", "name", "category"}, new int[]{
                        R.id.labelID,
                        R.id.labelName,
                        R.id.labelCat});


                lstProduktLager.setAdapter(adapter);
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
