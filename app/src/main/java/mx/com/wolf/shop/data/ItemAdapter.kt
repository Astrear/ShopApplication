package mx.com.wolf.shop.data

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.layout_item.view.*
import mx.com.wolf.shop.R
import android.graphics.drawable.Drawable
import android.util.Log
import com.squareup.picasso.Picasso
import java.io.InputStream
import java.net.URL


/**
 * Created by Jose Barrera on 24/06/2018.
 * Copyright (c) 2018 Anzen Digital All rights reserved.
 */
class ItemAdapter(private var itemList: MutableList<Item>)
    : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    class ItemViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val itemName: TextView = view.item_name
        val itemImage: ImageView = view.item_image
        val itemDescription: TextView = view.item_description
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_item, parent, false)

        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = itemList[position]
        holder.itemName.text = item.name
        holder.itemDescription.text = item.description
        Picasso.get().load(item.image).into(holder.itemImage)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun LoadImageFromWebOperations(url: String): Drawable? {
        return try {
            val inputStream = URL(url).content as InputStream
            Drawable.createFromStream(inputStream, "src name")
        } catch (e: Exception) {
            Log.i("Adapter", e.message)
            null
        }

    }
}