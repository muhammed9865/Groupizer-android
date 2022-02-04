package com.example.groupizer.pojo.model

import android.os.Parcel
import android.os.Parcelable

data class LoginForm(
    val email: String,
    val password: String
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(email)
        parcel.writeString(password)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LoginForm> {
        override fun createFromParcel(parcel: Parcel): LoginForm {
            return LoginForm(parcel)
        }

        override fun newArray(size: Int): Array<LoginForm?> {
            return arrayOfNulls(size)
        }
    }
}
