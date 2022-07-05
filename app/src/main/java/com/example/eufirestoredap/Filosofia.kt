package com.example.eufirestoredap

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.database.Exclude

data class Filosofia(var nombre: String = "",
                     var escuela: String = "",
                     var siglo: String = "",
                     var foto: String = "",
                     var info: String = ""
) {
    constructor() : this("", "", "", "", "")
}




