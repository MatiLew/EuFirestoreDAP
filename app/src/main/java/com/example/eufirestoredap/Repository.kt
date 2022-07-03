package com.example.eufirestoredap

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Repository {
    private val db = Firebase.firestore

    fun getFiloData(): LiveData<MutableList<Filosofia>> {
        val mutableData = MutableLiveData<MutableList<Filosofia>>()
        db.collection("Filosofos").get()
            .addOnSuccessListener {result ->
                val listData = mutableListOf<Filosofia>()
                for(document in result) {
                    val name = document.getString("nombre")
                    val school = document.getString("escuela")
                    val siglo = document.getString("siglo")
                    val foto = document.getString("foto")
                    val info = document.getString("info")
                    val filosofo = Filosofia(name!!, school!!, siglo!!, foto!!, info!!)
                    listData.add(filosofo)
                }
                mutableData.value = listData
            }
        return mutableData
    }
}