package com.example.eufirestoredap

import android.os.Parcel
import android.os.Parcelable

class Filosofia(nombre: String, escuela: String, siglo: String, foto: String, info: String):
Parcelable{
    var nombre: String
    var escuela: String
    var siglo: String
    var foto: String
    var info: String

    constructor(): this("","","","","")

    init {
        this.nombre = nombre!!
        this.escuela = escuela!!
        this.siglo = siglo!!
        this.foto = foto!!
        this.info = info!!
    }

    constructor(source: Parcel): this(
    source.readString()!!,
    source.readString()!!,
    source.readString()!!,
    source.readString()!!,
    source.readString()!!
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(nombre)
        writeString(escuela)
        writeString(siglo)
        writeString(foto)
        writeString(info)
    }

    override fun toString(): String{
        return "Filosofos(nombre='$nombre', escuela='$escuela', siglo='$siglo', foto='$foto', info='$info')"
    }

    companion object{
        @JvmField
        val CREATOR: Parcelable.Creator<Filosofia> = object : Parcelable.Creator<Filosofia> {
            override fun createFromParcel(source: Parcel): Filosofia? {
                return Filosofia(source)
            }

            override fun newArray(size: Int): Array<Filosofia?> {
                return arrayOfNulls(size)
            }
        }
    }
}


