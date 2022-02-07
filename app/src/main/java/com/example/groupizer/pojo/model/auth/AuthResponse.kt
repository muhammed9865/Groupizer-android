package com.example.groupizer.pojo.model.auth

import android.os.Parcel
import android.os.Parcelable

data class AuthResponse(
    val id: Int?,
    val email: String,
    val name: String,
    val password: String,
    val token: String
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id!!)
        parcel.writeString(email)
        parcel.writeString(name)
        parcel.writeString(password)
        parcel.writeString(token)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AuthResponse> {
        override fun createFromParcel(parcel: Parcel): AuthResponse {
            return AuthResponse(parcel)
        }

        override fun newArray(size: Int): Array<AuthResponse?> {
            return arrayOfNulls(size)
        }
    }
}