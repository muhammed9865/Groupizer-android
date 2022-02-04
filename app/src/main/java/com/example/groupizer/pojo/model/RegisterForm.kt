package com.example.groupizer.pojo.model

import android.os.Parcel
import android.os.Parcelable

data class RegisterForm(
    val name: String = "",
    val email: String = "",
    val password: String = ""
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(email)
        parcel.writeString(password)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RegisterForm> {
        override fun createFromParcel(parcel: Parcel): RegisterForm {
            return RegisterForm(parcel)
        }

        override fun newArray(size: Int): Array<RegisterForm?> {
            return arrayOfNulls(size)
        }
    }
}