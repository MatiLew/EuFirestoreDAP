package com.example.eufirestoredap

import android.os.Parcel
import android.os.Parcelable

data class Filosofia(var nombre: String = "",
                     var escuela: String = "",
                     var siglo: String = "",
                     var foto: String = "",
                     var info: String = "") {
    override fun toString(): String {
        return "$nombre $escuela $siglo $foto $info"
    }
}



