package com.example.eufirestoredap.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.example.eufirestoredap.R
import com.example.eufirestoredap.viewModels.DataViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DataFragment : Fragment() {
    private lateinit var v: View

    private lateinit var infotext: TextView
    private lateinit var filoName: TextView
    lateinit var filoSiglo: TextView
    lateinit var filoEscuela: TextView
    lateinit var newInfo: EditText
    private lateinit var filoImage: ImageView
    lateinit var backButton: Button
    private lateinit var filoId: String

    lateinit var mMenu: ImageView

    companion object {
        fun newInstance() = DataFragment()
    }

    private lateinit var viewModel: DataViewModel
    private val db = Firebase.firestore

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
        mMenu = v.findViewById(R.id.mMenu)

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

        backButton.setOnClickListener {
            val action = DataFragmentDirections.actionDataFragmentToMainFragment()
            v.findNavController().navigate(action)
        }

        mMenu.setOnClickListener {
            popUpMenus(it, infotext.text.toString())
        }
    }

    private fun popUpMenus(v: View, info: String) {
        val popUpMenu = PopupMenu(context, v)
        popUpMenu.inflate(R.menu.filo_menu)
        popUpMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.editText -> {
                    Snackbar.make(v, "Edit Text Clicked", Snackbar.LENGTH_SHORT).show()
                    editText(info)
                    true
                }
                R.id.deleteItem -> {
                    deleteItem()
                    true
                }
                else -> true
            }
        }
        popUpMenu.show()
        val popup = PopupMenu::class.java.getDeclaredField("mPopup")
        popup.isAccessible = true
        val menu = popup.get(popUpMenu)
        menu.javaClass.getDeclaredMethod("setForceShowIcon", Boolean::class.java)
            .invoke(menu, true)
    }

    private fun editText(info: String) {
        val action = DataFragmentDirections.actionDataFragmentToUpdateFragment(info)
        v. findNavController().navigate(action)
    }

    private fun deleteItem() {
       val deleteQuery =  db.collection("Filosfos")
            .whereEqualTo("nombre", filoName.text.toString())
            .whereEqualTo("info", infotext.text.toString())
            .get()
       deleteQuery.addOnSuccessListener {
           for (document in it) {
               db.collection("Filosofos").document(document.id).delete()
                   .addOnSuccessListener {
                   Snackbar.make(v, "Borrado con exito", Snackbar.LENGTH_SHORT).show()
               }
                   .addOnFailureListener {
                       Snackbar.make(v, "Error al borrar", Snackbar.LENGTH_SHORT).show()
                   }
           }
       }
        deleteQuery.addOnFailureListener {
            Snackbar.make(v, "Error al obtener documento", Snackbar.LENGTH_SHORT).show()
        }
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DataViewModel::class.java)
        // TODO: Use the ViewModel
    }
}


