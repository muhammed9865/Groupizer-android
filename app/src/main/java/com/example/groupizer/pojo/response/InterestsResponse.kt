package com.example.groupizer.pojo.response

import android.os.Parcel
import android.os.Parcelable

data class InterestsResponse(
    val id: Int,
    val title: String,
    val users: List<Int>? = null
)
