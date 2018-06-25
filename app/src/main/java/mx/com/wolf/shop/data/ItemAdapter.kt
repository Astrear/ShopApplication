package mx.com.wolf.shop.data

import android.databinding.DataBindingUtil
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
import mx.com.wolf.shop.databinding.LayoutItemBinding
import java.io.InputStream
import java.net.URL


/**
 * Created by Jose Barrera on 24/06/2018.
 * Copyright (c) 2018 Anzen Digital All rights reserved.
 */
class ItemAdapter(private var itemList: MutableList<Item>)
    : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    private var layoutInflater: LayoutInflater? = null

    class ItemViewHolder(val layoutItemBinding: LayoutItemBinding): RecyclerView.ViewHolder(layoutItemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.context)

        val binding: LayoutItemBinding = DataBindingUtil.inflate(layoutInflater!!, R.layout.layout_item, parent, false)

        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_item, parent, false)

        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = itemList[position]
        holder.layoutItemBinding.item = item
        holder.layoutItemBinding.itemImage.setOnClickListener {
            Log.i("image", "clicked")
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}