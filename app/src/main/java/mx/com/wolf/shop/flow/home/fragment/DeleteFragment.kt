package mx.com.wolf.shop.flow.home.fragment

import android.app.Fragment
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import mx.com.wolf.shop.R
import mx.com.wolf.shop.data.Item
import mx.com.wolf.shop.data.ItemAdapter
import mx.com.wolf.shop.data.source.ItemRepository
import mx.com.wolf.shop.flow.home.HomeActivity

/**
 * Created by Jose Barrera on 24/06/2018.
 * Copyright (c) 2018 Anzen Digital All rights reserved.
 */
class DeleteFragment: Fragment() {
    companion object {
        val TAG = DeleteFragment::class.simpleName
    }

    private val list = mutableListOf<Item>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("Test", "on deletefragment")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_list, container, false)
        Log.i(TAG, "On CreateView")

        val spiner = view.findViewById<Spinner>(R.id.spiner_item)
        val items: MutableMap<String, Int> = mutableMapOf()
        val names: Array<String> = arrayOf()

        (activity as HomeActivity).itemRepository.getItems().observe(activity as HomeActivity, Observer {
            it!!.forEachIndexed {index, item ->
                items[item.name] = item.id
                names[index] = item.name
            }

            spiner.adapter = ArrayAdapter<String>(this@DeleteFragment.activity, R.layout.support_simple_spinner_dropdown_item, names)
        })

        spiner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val itemId = items[names[position]]
                (activity as HomeActivity).itemRepository.deleteItem(itemId!!, object: ItemRepository.DeleteCallback {
                    override fun onSuccess() {
                        Log.i(TAG, "Item deleted")
                    }
                })
            }

        }
        return view
    }
}