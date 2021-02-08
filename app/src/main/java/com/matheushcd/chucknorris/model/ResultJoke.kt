package com.matheushcd.chucknorris.model

import com.google.gson.annotations.SerializedName

data class ResultJoke (
    @SerializedName("total") val total : Int,
    @SerializedName("result") val result : List<Joke>
)