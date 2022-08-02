package com.pratama.dany.koki

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.pratama.dany.koki.adapter.MasakAdapter
import com.pratama.dany.koki.api.ApiEndPoint
import com.pratama.dany.koki.api.model.MasakResult
import kotlinx.android.synthetic.main.activity_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.pratama.dany.koki.api.model.KokiResult
import com.pratama.dany.koki.api.model.PublicResult
import com.pratama.dany.koki.model.MasakModel
import java.util.*
import kotlin.collections.ArrayList

class HomeActivity : AppCompatActivity() {

    private val fbd : FirebaseFirestore = FirebaseFirestore.getInstance()
    private var idCabang: Int = 0
    private var namaCabang: String = ""
    private val listMenu = ArrayList<MasakModel>()
    private val listMinum = ArrayList<MasakModel>()
    private lateinit var mdialog: Dialog

    private var idKoki = ArrayList<String>()
    private var namaKoki = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val sharedPreferences = getSharedPreferences("SIGN_IN", Context.MODE_PRIVATE)
        idCabang = sharedPreferences.getInt("cabang", 0)
        namaCabang = sharedPreferences.getString("namacabang", "").toString()

        rvMenu?.layoutManager = LinearLayoutManager(this)
        rvMenu?.setHasFixedSize(true)
        rvMenu?.isNestedScrollingEnabled = false
        rvMinum?.layoutManager = LinearLayoutManager(this)
        rvMinum?.setHasFixedSize(true)
        rvMinum?.isNestedScrollingEnabled = false

        showProgressDialog()

        Handler().postDelayed({

            fbd.collection(namaCabang).document("transaksi").addSnapshotListener { snapshot, exception ->

                if(exception != null){
                    Log.w("Select Door", "listen:error", exception)
                    return@addSnapshotListener
                }

                getDataMasak()
                getDataKoki()

            }

        }, 300)

    }

    private fun getDataMasak(){

        val data = ApiEndPoint.REGISTER_API.getDataMasak(
            "sfYZONlALBQsBMU6dGzlJVWG17M6qx27",
            idCabang
        )

        data.enqueue(object: Callback<MasakResult> {

            override fun onResponse(call: Call<MasakResult>, response: Response<MasakResult>) {

                if(response.isSuccessful){

                    val status = response.body()?.status
                    val message = response.body()?.message
                    val result = response.body()?.result

                    if(status!!){

                        listMenu.clear()
                        listMinum.clear()
                        val listMenuT = ArrayList<MasakModel>()
                        val listMinumT = ArrayList<MasakModel>()

                        for (i in result!!.indices){

                            if(result[i].kategori == 1){

                                val model = MasakModel(result[i].id, result[i].menu, result[i].meja, result[i].bungkus, result[i].jumlah,
                                    result[i].kategori, result[i].note, result[i].status)
                                listMenuT.add(model)

                            } else {

                                val model = MasakModel(result[i].id, result[i].menu, result[i].meja, result[i].bungkus, result[i].jumlah,
                                    result[i].kategori, result[i].note, result[i].status)
                                listMinumT.add(model)

                            }

                        }

                        listMenu.addAll(listMenuT)
                        listMinum.addAll(listMinumT)

                        val menuAdapter = MasakAdapter(this@HomeActivity, listMenu)
                        rvMenu?.adapter = menuAdapter

                        val minumAdapter = MasakAdapter(this@HomeActivity, listMinum)
                        rvMinum?.adapter = minumAdapter
                        mdialog.dismiss()
                        shimmerMakan?.visibility = View.GONE
                        shimmerMinum?.visibility = View.GONE

                    } else {

                        listMenu.clear()
                        listMinum.clear()
                        val menuAdapter = MasakAdapter(this@HomeActivity, listMenu)
                        rvMenu?.adapter = menuAdapter

                        val minumAdapter = MasakAdapter(this@HomeActivity, listMinum)
                        rvMinum?.adapter = minumAdapter

                        Toast.makeText(this@HomeActivity, message, Toast.LENGTH_SHORT).show()
                        mdialog.dismiss()
                        shimmerMakan?.visibility = View.GONE
                        shimmerMinum?.visibility = View.GONE

                    }

                } else {

                    Toast.makeText(this@HomeActivity, "Gagal!!", Toast.LENGTH_SHORT).show()
                    mdialog.dismiss()

                }

            }

            override fun onFailure(call: Call<MasakResult>, t: Throwable) {

                Toast.makeText(this@HomeActivity, "Periksa kembali jaringan internet!", Toast.LENGTH_SHORT).show()
                mdialog.dismiss()
                Log.e("Error Rest Api:", t.toString())

            }

        })

    }

    private fun getDataKoki(){

        val data = ApiEndPoint.REGISTER_API.getKoki(
            "sfYZONlALBQsBMU6dGzlJVWG17M6qx27",
            idCabang
        )

        data.enqueue(object: Callback<KokiResult> {

            override fun onResponse(call: Call<KokiResult>, response: Response<KokiResult>) {

                if(response.isSuccessful){

                    val status = response.body()?.status
                    val message = response.body()?.message
                    val result = response.body()?.result

                    if(status!!){

                        idKoki.clear()
                        namaKoki.clear()
                        var listId = ArrayList<String>()
                        var listNama = ArrayList<String>()
                        for(i in result!!.indices){

                            listId.add(result[i].id)
                            listNama.add(result[i].nama)

                        }

                        idKoki.addAll(listId)
                        namaKoki.addAll(listNama)

                    } else {

                        Toast.makeText(this@HomeActivity, message, Toast.LENGTH_SHORT).show()

                    }

                } else {

                    Toast.makeText(this@HomeActivity, "Gagal!!", Toast.LENGTH_SHORT).show()

                }

            }

            override fun onFailure(call: Call<KokiResult>, t: Throwable) {

                Toast.makeText(this@HomeActivity, "Periksa kembali jaringan internet!", Toast.LENGTH_SHORT).show()
                Log.e("Error Rest Api:", t.toString())

            }

        })

    }

    fun showPopUpKoki(idMasak: Int) {

        val adapter = ArrayAdapter<CharSequence>(this@HomeActivity, android.R.layout.simple_spinner_item,
            namaKoki as List<CharSequence>
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        mdialog = Dialog(this)
        mdialog.setCancelable(true)
        mdialog.setContentView(R.layout.alert_koki)

        val spNamaKoki: Spinner = mdialog.findViewById(R.id.spNamaKoki)
        val pilihKoki: Button = mdialog.findViewById(R.id.pilihKoki)

        spNamaKoki.adapter = adapter
        pilihKoki.isEnabled = false

        spNamaKoki.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                val item = p0?.getItemAtPosition(p2)

                if(item.toString() != "Pilih no meja"){

                    pilihKoki.isEnabled = true
                    pilihKoki.setTextColor(resources.getColor(R.color.Blue600))

                } else {

                    pilihKoki.isEnabled = false
                    pilihKoki.setTextColor(resources.getColor(R.color.Gray500))

                }

            }


        }

        pilihKoki.setOnClickListener{

            val posisi = spNamaKoki.selectedItemPosition
            val idKoki = idKoki[posisi]

            updateDataMasak(idMasak, idKoki)

            mdialog.dismiss()

        }

        mdialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        mdialog.window!!.attributes.windowAnimations = R.style.DialogAnimation
        mdialog.show()

    }

    private fun updateDataMasak(idMasak: Int, idKoki: String){

        val data = ApiEndPoint.REGISTER_API.updateDataMasak(
            "sfYZONlALBQsBMU6dGzlJVWG17M6qx27", idMasak, 1, idKoki)

        data.enqueue(object: Callback<PublicResult> {

            override fun onResponse(call: Call<PublicResult>, response: Response<PublicResult>) {

                if(response.isSuccessful){

                    val status = response.body()?.status
                    val message = response.body()?.message

                    if(status!!){

                        val update = hashMapOf(

                            "update" to randomString()

                        )

                        fbd.collection(namaCabang).document("transaksi").set(update)

                    } else {

                        Toast.makeText(this@HomeActivity, message, Toast.LENGTH_SHORT).show()

                    }

                } else {

                    Toast.makeText(this@HomeActivity, "Gagal!!", Toast.LENGTH_SHORT).show()

                }

            }

            override fun onFailure(call: Call<PublicResult>, t: Throwable) {

                Toast.makeText(this@HomeActivity, "Periksa kembali jaringan internet!", Toast.LENGTH_SHORT).show()
                Log.e("Error Rest Api:", t.toString())

            }

        })

    }

    fun updateDataMasak(idMasak: Int){

        val data = ApiEndPoint.REGISTER_API.updateDataMasak(
            "sfYZONlALBQsBMU6dGzlJVWG17M6qx27", idMasak, 2)

        data.enqueue(object: Callback<PublicResult> {

            override fun onResponse(call: Call<PublicResult>, response: Response<PublicResult>) {

                if(response.isSuccessful){

                    val status = response.body()?.status
                    val message = response.body()?.message

                    if(status!!){

                        val update = hashMapOf(

                            "update" to randomString()

                        )

                        fbd.collection(namaCabang).document("transaksi").set(update)
                        Toast.makeText(this@HomeActivity, message, Toast.LENGTH_LONG).show()

                    } else {

                        Toast.makeText(this@HomeActivity, message, Toast.LENGTH_SHORT).show()

                    }

                } else {

                    Toast.makeText(this@HomeActivity, "Gagal!!", Toast.LENGTH_SHORT).show()

                }

            }

            override fun onFailure(call: Call<PublicResult>, t: Throwable) {

                Toast.makeText(this@HomeActivity, "Periksa kembali jaringan internet!", Toast.LENGTH_SHORT).show()
                Log.e("Error Rest Api:", t.toString())

            }

        })

    }

    private fun randomString() : String {

        val char = "ABCDEF012GHIJKL345MNOPQR678STUVWXYZ9".toCharArray()
        val string = StringBuilder()
        val random = Random()
        for (i in 0..3) {
            val hasil = char[random.nextInt(char.size)]
            string.append(hasil)
        }

        return string.toString()

    }

    private fun showProgressDialog(){

        mdialog = Dialog(this)
        mdialog.setCancelable(false)
        mdialog.setContentView(R.layout.progress_dialog)

        val caption = mdialog.findViewById<TextView>(R.id.caption)
        caption.text = "Mengambil data..."

        mdialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        mdialog.window!!.attributes.windowAnimations = R.style.DialogAnimation
        mdialog.show()

    }

}
