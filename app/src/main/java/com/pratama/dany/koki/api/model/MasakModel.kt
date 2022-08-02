package com.pratama.dany.koki.api.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MasakModel(@SerializedName("id")
                      @Expose val id: Int,
                      @SerializedName("menu")
                      @Expose val menu: String,
                      @SerializedName("jumlah")
                      @Expose val jumlah: Int,
                      @SerializedName("meja")
                      @Expose val meja: Int,
                      @SerializedName("packing")
                      @Expose val bungkus: Int,
                      @SerializedName("kategori")
                      @Expose val kategori: Int,
                      @SerializedName("note")
                      @Expose val note: String,
                      @SerializedName("status")
                      @Expose val status: Int) {
}