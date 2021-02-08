package com.matheushcd.chucknorris.model

import com.google.gson.annotations.SerializedName

data class Joke (
    @SerializedName("categories") val categories : List<String>,
    @SerializedName("created_at") val created_at : String,
    @SerializedName("icon_url") val icon_url : String,
    @SerializedName("id") val id : String,
    @SerializedName("updated_at") val updated_at : String,
    @SerializedName("url") val url : String,
    @SerializedName("value") val value : String
)