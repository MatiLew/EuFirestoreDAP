package com.example.eufirestoredap.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.example.eufirestoredap.R
import com.example.eufirestoredap.viewModels.DataViewModel

class DataFragment : Fragment() {
    private lateinit var v: View

    lateinit var infotext: TextView
    lateinit var filoName: TextView
    lateinit var filoSiglo: TextView
    lateinit var filoEscuela: TextView
    lateinit var filoImage: ImageView
    lateinit var backButton: Button

    companion object {
        fun newInstance() = DataFragment()
    }

    private lateinit var viewModel: DataViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_data, container, false)

        infotext = v.findViewById(R.id.filoInfo)
        filoName = v.findViewById(R.id.filoName)
        filoSiglo = v.findViewById(R.id.filoSiglo)
        filoEscuela = v.findViewById(R.id.filoEscuela)
        filoImage = v.findViewById(R.id.filoImage)

        backButton = v.findViewById(R.id.backButton)
        return v
    }

    override fun onStart() {
        super.onStart()
        val information = DataFragmentArgs.fromBundle(requireArguments()).info

        infotext.text = information[0]
        filoName.text = information[2]
        filoSiglo.text = information[3]
        filoEscuela.text = information[1]

        Glide
            .with(this)
            .load(information[4])
            .centerInside()
            .into(filoImage)

        backButton.setOnClickListener{
            val action = DataFragmentDirections.actionDataFragmentToMainFragment()
            v.findNavController().navigate(action)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DataViewModel::class.java)
        // TODO: Use the ViewModel
    }

}