package com.pratama.dany.koki.api.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MemberResult(@SerializedName("status")
                        @Expose val status: Boolean,
                        @SerializedName("message")
                        @Expose val message: String,
                        @SerializedName("result")
                        @Expose val result: MemberModel) {
}