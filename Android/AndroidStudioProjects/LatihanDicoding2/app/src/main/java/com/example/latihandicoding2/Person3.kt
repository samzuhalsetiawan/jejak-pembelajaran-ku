package com.example.latihandicoding2

import android.os.Parcel
import android.os.Parcelable

data class Person3(
    val name: String?,
    val kode: Int,
    val angkaFavorite: ArrayList<Int>
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readInt(),
        ArrayList<Int>()
    ) {
        parcel.readList(angkaFavorite, angkaFavorite::class.java.classLoader)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(kode)
        parcel.writeList(angkaFavorite)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Person3> {
        override fun createFromParcel(parcel: Parcel): Person3 {
            return Person3(parcel)
        }

        override fun newArray(size: Int): Array<Person3?> {
            return arrayOfNulls(size)
        }
    }
}
