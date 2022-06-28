package com.example.eufirestoredap.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.example.eufirestoredap.Filosofia
import com.example.eufirestoredap.R
import com.example.eufirestoredap.viewModels.UploadViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class UploadFragment : Fragment() {

    lateinit var v: View
    lateinit var nameInput  :   EditText
    lateinit var schoolInput:   EditText
    lateinit var sigloInput:    EditText
    lateinit var imageURLInput: EditText
    lateinit var infoInput:     EditText
    lateinit var saveButton:    Button

    companion object {
        fun newInstance() = UploadFragment()
    }

    private lateinit var viewModel: UploadViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v =  inflater.inflate(R.layout.fragment_upload, container, false)

        nameInput = v.findViewById<EditText?>(R.id.editName)
        schoolInput = v.findViewById<EditText?>(R.id.editSchool)
        sigloInput = v.findViewById<EditText?>(R.id.editSiglo)
        imageURLInput = v.findViewById<EditText?>(R.id.editFoto)
        infoInput = v.findViewById<EditText?>(R.id.editInfo)

        saveButton = v.findViewById(R.id.uploadButton)

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UploadViewModel::class.java)
        saveButton.setOnClickListener {
            saveFirestore(nameInput.text.toString(),
                schoolInput.text.toString(),
                sigloInput.text.toString(),
                imageURLInput.text.toString(),
                infoInput.text.toString())
        }
    }

    fun saveFirestore(nameInput: String, schoolInput: String, sigloInput: String, imageURLInput: String, infoInput: String ) {
        val db = FirebaseFirestore.getInstance()
        var filosofo: Filosofia = Filosofia("uno", "dos", "tres", "cuatro", "cinco")

        filosofo.nombre = nameInput
        filosofo.escuela = schoolInput
        filosofo.siglo = sigloInput
        filosofo.foto = imageURLInput
        filosofo.info = infoInput

        db.collection("Filosofos")
            .add(filosofo)
            .addOnSuccessListener {
                Snackbar.make(v, "texto subido.", Snackbar.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Snackbar.make(v, "Fallo al subir.", Snackbar.LENGTH_SHORT).show()
            }

    }

}