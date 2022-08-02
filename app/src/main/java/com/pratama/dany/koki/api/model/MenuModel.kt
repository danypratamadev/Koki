package com.pratama.dany.koki.api.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MenuModel(@SerializedName("id")
                     @Expose val id: Int,
                     @SerializedName("menu")
                     @Expose val menu: String,
                     @SerializedName("kategori")
                     @Expose val kategori: Int,
                     @SerializedName("harga")
                     @Expose val harga: Int,
                     @SerializedName("status")
                     @Expose val status: Int) {
}