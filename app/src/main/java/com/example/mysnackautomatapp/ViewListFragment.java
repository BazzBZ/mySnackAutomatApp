package com.example.mysnackautomatapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class ViewListFragment extends Fragment {

    //boolean reversed = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle){

        return inflater.inflate(R.layout.view_list_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);

        MainActivity invoker = (MainActivity) getActivity();

        ListView listView = view.findViewById(R.id.listView);

        ArrayAdapter adapter = new ArrayAdapter(
                invoker,
                android.R.layout.simple_list_item_1,
                invoker.getDataManager().getNames()
        );

        listView.setAdapter(adapter);
    }
}
