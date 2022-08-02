package com.pratama.dany.koki.api.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class KokiModel(@SerializedName("idkoki")
                      @Expose val id: String,
                     @SerializedName("nama")
                      @Expose val nama: String) {
}