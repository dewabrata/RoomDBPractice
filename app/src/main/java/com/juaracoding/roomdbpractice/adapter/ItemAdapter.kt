package com.juaracoding.roomdbpractice.adapter

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.juaracoding.roomdbpractice.R
import com.juaracoding.roomdbpractice.model.ItemModel

class ItemAdapter (val listItem : List<ItemModel>, val onClick : (ItemModel) -> Unit    ) :  RecyclerView.Adapter<ItemAdapter.ItemViewHolder>(){

    inner class ItemViewHolder(itemview : View) : RecyclerView.ViewHolder(itemview){

        val txtNama = itemview.findViewById<TextView>(R.id.text_view)
        val txtFoto = itemview.findViewById<ImageView>(R.id.image_view)
        val parent = itemview.findViewById<LinearLayout>(R.id.parentItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list,parent,false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
       return listItem.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        holder.txtNama.text = listItem[position].nama
        holder.txtFoto.setImageBitmap(BitmapFactory.decodeFile(listItem[position].foto))

        holder.parent.setOnLongClickListener{
            onClick(listItem[position])
            true
        }

    }


}