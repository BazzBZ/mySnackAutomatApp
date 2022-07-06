package com.example.mysnackautomatapp

import android.util.Log

class DataManager {

    private var listOfProducts: MutableList<String> = listOf("Bueno", "Twix", "Cola").toMutableList()

    fun saveProduct(product: String){
        Log.i("DataManger", "Saving product: $product")
        listOfProducts.add(product)
    }

    fun getNames(): MutableList<String>{
        return listOfProducts
    }




}