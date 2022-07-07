package com.example.mysnackautomatapp;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataManager {

    private static ArrayList<String> listOfProducts = new ArrayList<>(Arrays.asList("bueno", "twix"));

    public void saveProduct(String product) {
        Log.i("DataManger", "Saving product: $product");
        listOfProducts.add(product);
    }

    public static List getNames() {
        return listOfProducts;
    }


}
