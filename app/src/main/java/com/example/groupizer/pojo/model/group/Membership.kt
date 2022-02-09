package com.example.groupizer.pojo.model.group

import android.os.Parcel
import android.os.Parcelable
import com.example.groupizer.pojo.model.auth.AuthResponse

data class Membership(
    val group: Int,
    val id: Int,
    var role: String?,
    val user: AuthResponse?
)