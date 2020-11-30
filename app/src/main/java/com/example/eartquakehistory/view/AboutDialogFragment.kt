package com.example.eartquakehistory.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.eartquakehistory.databinding.AboutDialogFragmentBinding

class AboutDialogFragment : DialogFragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = AboutDialogFragmentBinding
            .inflate(inflater, container,false)
        val view = binding.root
        binding.viewedButton.setOnClickListener {
            dismiss()
        }
        binding.iconAuthorButton.setOnClickListener {
            val url = "https://www.flaticon.es/autores/freepik"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setData(Uri.parse(url))
            startActivity(intent)
        }
        return view
    }
}