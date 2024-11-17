package com.example.motherskitchen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class Location_page : BottomSheetDialogFragment() {
    private var onLocationSelectedListener: ((String) -> Unit)? = null
        fun setOnLocationSelectedListener(listener: (String) -> Unit) {
            onLocationSelectedListener = listener
        }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         val view= inflater.inflate(R.layout.activity_location_page, container, false)

        val useCurrentLocationLayout = view.findViewById<LinearLayout>(R.id.useCurrentLocationLayout)
        useCurrentLocationLayout.setOnClickListener{
            val currentActivity = activity as? home_page
            if (currentActivity != null) {
                // Call getCurrentLocation if the cast is successful
                currentActivity.getCurrentLocation { address ->
                    if (address.isNotEmpty()) {
                        onLocationSelectedListener?.invoke(address)
                        dismiss()
                    } else {
                        Toast.makeText(activity, "Failed to retrieve address", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                // If the cast fails, show this message
                Toast.makeText(activity, "Activity is NOT of type home_page", Toast.LENGTH_SHORT).show()
            }
        }
     return view
    }
}