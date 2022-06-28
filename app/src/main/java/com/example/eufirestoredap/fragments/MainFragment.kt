package com.example.eufirestoredap.fragments

import android.content.ContentValues
import android.content.ContentValues.TAG
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eufirestoredap.FiloAdapters
import com.example.eufirestoredap.Filosofia
import com.example.eufirestoredap.R
import com.example.eufirestoredap.viewModels.MainViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class MainFragment : Fragment() {

    private lateinit var v: View
    lateinit var recFilo: RecyclerView
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var filoListAdapter: FiloAdapters
    lateinit var uploadButton: FloatingActionButton
    var filoList: MutableList<Filosofia> = ArrayList()


    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private val db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_main, container, false)
        recFilo = v.findViewById(R.id.rec_filo)
        uploadButton = v.findViewById(R.id.btn_add)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }


    override fun onStart() {
        super.onStart()
        // readData()

        var filosofo: Filosofia = Filosofia("nombre", "escuela", "siglo", "foto", "esto")
        for(filosofo in viewModel.filosofos) {
            db.collection("Filosofos").add(filosofo)
        }

        var docRef = db.collection("Filosofos").document("nombre")
        docRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.d("Test", "Listen failed.", e)
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {
                val filosofo  = snapshot.toObject<Filosofia>()
                Log.d("Test", "DocumentSnapshot data: ${filosofo.toString()}")
            } else {
                Log.d("Test", "Current data: null")
            }
        }


        db.collection("Filosofos")
            .get()
            .addOnSuccessListener { snapshot ->
                if (snapshot != null) {
                    for (filosofo in snapshot) {
                        filoList.add(filosofo.toObject())
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }


        linearLayoutManager = LinearLayoutManager(context)
        recFilo.layoutManager = linearLayoutManager

        filoListAdapter = FiloAdapters(viewModel.filosofos, requireContext()) { pos ->
            onItemClick(pos)
        }

        uploadButton.setOnClickListener {
            onButtonClick()
        }

        recFilo.adapter = filoListAdapter
    }


    fun onItemClick(position: Int) {
        Snackbar.make(v, position.toString(), Snackbar.LENGTH_SHORT).show()
        val filo = viewModel.filosofos[position]
        val filoArray = arrayOf<String>(filo.info, filo.escuela, filo.nombre, filo.siglo, filo.foto)

        val action = MainFragmentDirections.actionMainFragmentToDataFragment(filoArray)
        v.findNavController().navigate(action)
    }

    fun onButtonClick() {
        val action = MainFragmentDirections.actionMainFragmentToUploadFragment()
        v.findNavController().navigate(action)
    }
}