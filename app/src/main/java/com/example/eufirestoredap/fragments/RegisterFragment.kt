package com.example.eufirestoredap.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import com.example.eufirestoredap.R
import com.example.eufirestoredap.Users
import com.example.eufirestoredap.viewModels.RegisterViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import java.security.KeyFactory.getInstance
import java.util.Calendar.getInstance

class RegisterFragment : Fragment() {

    lateinit var v: View
    lateinit var registerName: EditText
    lateinit var registerPassword: EditText
    lateinit var registerButton: Button
    lateinit var progressBar: ProgressBar
    lateinit var loginButton: TextView

    companion object {
        fun newInstance() = RegisterFragment()
    }

    private lateinit var viewModel: RegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v =  inflater.inflate(R.layout.fragment_register, container, false)

        registerName = v.findViewById(R.id.registerName)
        registerPassword = v.findViewById(R.id.registerPassword)
        registerButton = v.findViewById(R.id.registerButton)
        progressBar = v.findViewById(R.id.registerProgressBar)
        loginButton = v.findViewById(R.id.loginButton)

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
        progressBar.visibility = View.INVISIBLE
        registerButton.setOnClickListener {
            saveFirestore(registerName.text.toString(), registerPassword.text.toString())
        }
        loginButton.setOnClickListener {
            onLogInClick()
        }
    }

    fun saveFirestore(registerName: String, registerPassword: String) {
        val db = FirebaseFirestore.getInstance()
        var user: Users = Users("nombres", "hola")

        user.name = registerName
        user.password = registerPassword

        progressBar.visibility = View.VISIBLE

        db.collection("Users")
            .add(user)
            .addOnSuccessListener {
                Snackbar.make(v, "Registro exitoso.", Snackbar.LENGTH_SHORT).show()
                progressBar.visibility = View.INVISIBLE
            }
            .addOnFailureListener {
                Snackbar.make(v, "Error al registrarse.", Snackbar.LENGTH_SHORT).show()
                progressBar.visibility = View.INVISIBLE
            }

    }

    fun onLogInClick() {
        val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
        v.findNavController().navigate(action)
    }

}