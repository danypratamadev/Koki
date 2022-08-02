package com.pratama.dany.koki.model

data class MasakModel(var idMasak: Int = 0, var namaMenu: String = "",
                      var meja: Int = 0, var bungkus: Int = 0, var jmlPesan: Int = 0,
                      var ktgMenu: Int = 0, var note: String,
                      var status: Int = 0) {
}