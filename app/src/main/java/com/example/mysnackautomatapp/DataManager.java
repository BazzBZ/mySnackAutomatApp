package com.example.mysnackautomatapp;

import android.util.Log;

import java.util.Arrays;
import java.util.List;

public class DataManager {

    private List<String> listOfProducts = Arrays.asList("Bueno", "Twix", "Cola");

    public void saveProduct(String product) {
        Log.i("DataManger", "Saving product: $product");
        listOfProducts.add(product);
    }

    public List getNames() {
        return listOfProducts;
    }


}
