package com.pratama.dany.koki.adapter

import android.content.Context
import android.os.Handler
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.os.postDelayed
import androidx.recyclerview.widget.RecyclerView
import com.github.ybq.android.spinkit.SpinKitView
import com.pratama.dany.koki.HomeActivity
import com.pratama.dany.koki.model.MasakModel
import kotlin.collections.ArrayList
import com.pratama.dany.koki.CountUpTimer
import com.pratama.dany.koki.R
import com.wang.avi.AVLoadingIndicatorView


class MasakAdapter(val context: Context, val listPesan: ArrayList<MasakModel>) :
    RecyclerView.Adapter<MasakAdapter.KokiViewholder>() {

    private var nomorUrut = 0
    private val homeActivity = context as HomeActivity

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KokiViewholder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.masak_layout, parent, false)
        return KokiViewholder(view)

    }

    override fun onBindViewHolder(holder: KokiViewholder, position: Int) {

        val currentItem = listPesan[position]
        val timeSwap = 0L
        val handler: Handler = Handler()

        nomorUrut++
        if(nomorUrut % 2 == 0){

            holder.cardKoki.setBackgroundColor(context.resources.getColor(R.color.Gray50))

        }

        holder.jmlPesan.text = currentItem.meja.toString()
        if(currentItem.bungkus == 1){
            holder.bungkusPesan.text = "BUNGKUS"
        } else {
            holder.bungkusPesan.text = "DITEMPAT"
        }
        holder.namaMenu.text = "${currentItem.jmlPesan}x \t ${currentItem.namaMenu}"
        holder.noteMenu.text = "Catatan: " + currentItem.note

        if(currentItem.status == 0){
            holder.masak.text = "Mulai"
            holder.masak.background = context.resources.getDrawable(R.drawable.back_button_blue_stroke)
            holder.masak.setTextColor(context.resources.getColor(R.color.BlueA700))
        } else if(currentItem.status == 1) {
            holder.masak.text = "Selesai"
            holder.masak.background = context.resources.getDrawable(R.drawable.back_button_red_stroke)
            holder.masak.setTextColor(context.resources.getColor(R.color.RedPrimary))
            holder.progressMasak.visibility = View.VISIBLE
        } else {
            holder.masak.text = "Done"
            holder.masak.background = context.resources.getDrawable(R.drawable.back_button_green_stroke)
            holder.masak.setTextColor(context.resources.getColor(R.color.GreenA700))
        }

        if(currentItem.note.equals("-")){
            holder.noteMenu.visibility = View.GONE
        }

        holder.masak.setOnClickListener{

            if(currentItem.status == 0){
                homeActivity.showPopUpKoki(currentItem.idMasak)
            } else {
                homeActivity.updateDataMasak(currentItem.idMasak)
            }

        }

        holder.itemView.setOnClickListener{



        }

    }

    override fun getItemCount(): Int {
        return listPesan.size
    }

    class KokiViewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val cardKoki = itemView.findViewById<LinearLayout>(R.id.cardKoki)
        val jmlPesan = itemView.findViewById<TextView>(R.id.jmlPesan)
        val namaMenu = itemView.findViewById<TextView>(R.id.namaMenu)
        val noteMenu = itemView.findViewById<TextView>(R.id.noteMenu)
        val bungkusPesan = itemView.findViewById<TextView>(R.id.bungkusPesan)
        val progressMasak = itemView.findViewById<SpinKitView>(R.id.progressMasak)
        val masak = itemView.findViewById<TextView>(R.id.masak)

    }

}