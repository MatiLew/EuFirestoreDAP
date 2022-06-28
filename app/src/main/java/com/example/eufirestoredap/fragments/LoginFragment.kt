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
import android.widget.EditText
import android.widget.TextView
import androidx.navigation.findNavController
import com.example.eufirestoredap.Filosofia
import com.example.eufirestoredap.viewModels.LoginViewModel
import com.example.eufirestoredap.R
import com.example.eufirestoredap.Users
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class LoginFragment : Fragment() {

    lateinit var v: View
    lateinit var logInButton: Button
    lateinit var registerText: TextView
    lateinit var userName: EditText
    lateinit var userPassword: EditText
    var userList: MutableList<Users> = ArrayList()

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var viewModel: LoginViewModel
    private val db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v =  inflater.inflate(R.layout.fragment_login, container, false)

        logInButton = v.findViewById(R.id.loginButton)
        registerText = v.findViewById(R.id.registerText)
        userName = v.findViewById(R.id.editTextName)
        userPassword = v.findViewById(R.id.editTextPassword)

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        var user: Users = Users("name", "pass")

        db.collection("Users")
            .get()
            .addOnSuccessListener { snapshot ->
                if (snapshot != null) {
                    for (user in snapshot) {
                        userList.add(user.toObject())
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }

        registerText.setOnClickListener {
            onRegisterClick()
        }

        logInButton.setOnClickListener {
            onLogInClick()
        }
    }

    fun onRegisterClick() {
        val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
        v.findNavController().navigate(action)
    }

    fun onLogInClick() {
        var user: Users = Users("mati", "lewkow")


    }

}