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
import androidx.navigation.findNavController
import com.example.eufirestoredap.R
import com.example.eufirestoredap.viewModels.RegisterViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

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
    val mAuth = FirebaseAuth.getInstance()

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
            registerFirestore(registerName.text.toString(), registerPassword.text.toString())
        }
        loginButton.setOnClickListener {
            onLogInClick()
        }
    }

    fun registerFirestore(registerName: String, registerPassword: String) {
        var email = registerName
        var password = registerPassword

        if (!email.isEmpty() && !password.isEmpty()) {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    progressBar.visibility = View.INVISIBLE
                    val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
                    v.findNavController().navigate(action)
                } else {
                    progressBar.visibility = View.INVISIBLE
                    Snackbar.make(v, "Error al registrarse.", Snackbar.LENGTH_SHORT).show()
                }
            }
        } else {
            Snackbar.make(v, "Por favor, llene los espacios.", Snackbar.LENGTH_SHORT).show()
        }
    }

        fun onLogInClick() {
        val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
        v.findNavController().navigate(action)
    }

}