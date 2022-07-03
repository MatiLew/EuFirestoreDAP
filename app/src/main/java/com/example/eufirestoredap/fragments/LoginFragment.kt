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
import com.example.eufirestoredap.viewModels.LoginViewModel
import com.example.eufirestoredap.R
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {

    private lateinit var v: View
    private lateinit var logInButton: Button
    private lateinit var registerText: TextView
    private lateinit var userName: EditText
    private lateinit var userPassword: EditText
    private lateinit var progressBar: ProgressBar

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var viewModel: LoginViewModel
    private val mAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v =  inflater.inflate(R.layout.fragment_login, container, false)

        logInButton = v.findViewById(R.id.loginButton)
        registerText = v.findViewById(R.id.registerText)
        userName = v.findViewById(R.id.editTextName)
        userPassword = v.findViewById(R.id.editTextPassword)
        progressBar = v.findViewById(R.id.progressBar)

        return v
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        progressBar.visibility = View.INVISIBLE
        logInButton.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            logInClick(userName.text.toString(), userPassword.text.toString())
        }

        registerText.setOnClickListener {
            onRegisterClick()
        }
    }

   private fun logInClick(userName: String, userPassword: String) {
        if(!userName.isEmpty() && !userPassword.isEmpty()) {
            mAuth.signInWithEmailAndPassword(userName, userPassword).addOnCompleteListener {
                if(it.isSuccessful) {
                    progressBar.visibility = View.INVISIBLE
                    val action = LoginFragmentDirections.actionLoginFragmentToMainFragment()
                    v.findNavController().navigate(action)
                }
                else {
                    progressBar.visibility = View.INVISIBLE
                    Snackbar.make(v, "Datos incorrectos", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
        else {
            progressBar.visibility = View.INVISIBLE
            Snackbar.make(v, "Por favor, llene los espacios.", Snackbar.LENGTH_SHORT).show()
        }
    }

    fun onRegisterClick() {
        val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
        v.findNavController().navigate(action)
    }


}