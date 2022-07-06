package com.example.mysnackautomatapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.widget.TextView
import android.widget.Toast

class AddEntryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_entry_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("AddEntryFragment", "onViewCreated")

        view.findViewById<View>(R.id.bAddEntry).setOnClickListener {
            Log.d("SampleFragment", "button clicked")


            val tVAddEntry = view.findViewById<TextView>(R.id.addEntryTF)
            val addEntry = tVAddEntry.text.toString()
            Log.d("Sample Frag", "Product: $addEntry")


            if (addEntry.isEmpty()) {
                Toast.makeText(context, "Darf nicht leer sein", Toast.LENGTH_LONG)
                return@setOnClickListener
            }

            val invoker = activity as MainActivity
            invoker.getDataManager().saveProduct(addEntry)

            tVAddEntry.text=""


            Toast.makeText(activity, "$addEntry saved", Toast.LENGTH_LONG)


        }



    }

    override fun onDestroy() {
        super.onDestroy()

        Log.d("Add Entry Fragment", "onDestroy")
    }

    override fun onPause() {
        super.onPause()

        Log.d("Add Entry Fragment", "onPause")
    }

}