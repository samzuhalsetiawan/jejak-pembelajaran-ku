package com.example.latihandicoding2

import android.os.Parcel
import android.os.Parcelable

data class Person(
    val name: String,
    val kode: Int,
    val string2: String?,
    val kode2: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "Unknown",
        parcel.readInt(),
        parcel.readString(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(kode)
        parcel.writeString(string2)
        parcel.writeInt(kode2)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Person> {
        override fun createFromParcel(parcel: Parcel): Person {
            return Person(parcel)
        }

        override fun newArray(size: Int): Array<Person?> {
            return arrayOfNulls(size)
        }
    }
}
