package mx.com.wolf.shop.flow.home.fragment

import android.app.Fragment
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mx.com.wolf.shop.R
import mx.com.wolf.shop.data.Item
import mx.com.wolf.shop.data.ItemAdapter
import mx.com.wolf.shop.data.source.ItemRepository
import mx.com.wolf.shop.data.source.local.ItemViewModel
import mx.com.wolf.shop.flow.home.HomeActivity

/**
 * Created by Jose Barrera on 24/06/2018.
 * Copyright (c) 2018 Anzen Digital All rights reserved.
 */
class ListFragment: Fragment() {

    companion object {
        val TAG = ListFragment::class.simpleName
    }

    private val list = mutableListOf<Item>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("Test", "on listfragment")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_list, container, false)
        Log.i(TAG, "On CreateView")

        val recyclerView: RecyclerView = view.findViewById(R.id.item_list)
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity.applicationContext)
            itemAnimator = DefaultItemAnimator()
            adapter = ItemAdapter(list)
        }

        (activity as HomeActivity).itemRepository.getItems().observe(activity as HomeActivity, Observer {

            if(it != null) {
                list.clear()
                list.addAll(it)
            }
            recyclerView.adapter.notifyDataSetChanged()
        })
        return view
    }
}