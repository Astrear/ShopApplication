package mx.com.wolf.shop.data

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import mx.com.wolf.shop.R
import mx.com.wolf.shop.databinding.LayoutItemBinding


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
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = itemList[position]
        holder.layoutItemBinding.item = item
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}