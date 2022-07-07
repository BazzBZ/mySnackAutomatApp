package com.example.mysnackautomatapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class AddEntryFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle){

        return inflater.inflate(R.layout.add_entry_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle bundle){
        super.onViewCreated(view, bundle);
        Log.d("AddEntryFragment", "onViewCreated");
        view.findViewById(R.id.bAddEntry).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tvAddEntry = view.findViewById(R.id.addEntryTF);
                String addEntry = tvAddEntry.getText().toString();
                Log.d("Add Entry frag", "Product "+ addEntry);


                if(addEntry.isEmpty()){
                    Toast.makeText(getContext(), "Darf nich leer sein", Toast.LENGTH_LONG)
                            .show();
                    return;
                }
                MainActivity invoker = (MainActivity) getActivity();
                invoker.getDataManager().saveProduct(addEntry);

                Toast.makeText(getActivity(), addEntry+ "saved", Toast.LENGTH_LONG);

            }


        });





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
