package com.example.groupizer.pojo.model.interest

import com.google.gson.annotations.SerializedName

data class InterestsList(
    @SerializedName("id")
    val ids: List<Int>
)
