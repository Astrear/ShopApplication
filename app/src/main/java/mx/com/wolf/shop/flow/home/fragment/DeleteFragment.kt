package mx.com.wolf.shop.flow.home.fragment

import android.app.Fragment
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.CardView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.squareup.picasso.Picasso
import mx.com.wolf.shop.R
import mx.com.wolf.shop.data.Item
import mx.com.wolf.shop.data.source.ItemRepository
import mx.com.wolf.shop.extensions.getSessionToken
import mx.com.wolf.shop.flow.home.HomeActivity
import mx.com.wolf.shop.util.AlertDialog
import mx.com.wolf.shop.util.DialogButton

/**
 * Created by Jose Barrera on 24/06/2018.
 * Copyright (c) 2018 Anzen Digital All rights reserved.
 */
class DeleteFragment: Fragment() {
    companion object {
        val TAG = DeleteFragment::class.simpleName
    }

    val items: MutableMap<String, Int> = mutableMapOf()
    val names: MutableList<String> = mutableListOf()
    private var currentItemId: Int? = -1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val layoutView =  inflater.inflate(R.layout.fragment_delete, container, false)

        val spinner = layoutView.findViewById<Spinner>(R.id.delete_spinner)

        (activity as HomeActivity).itemRepository
                .getItems("JWT ${activity.getSessionToken()}")
                .observe(activity as HomeActivity, Observer {
                    it?.forEachIndexed { index, item ->
                        Log.i(TAG, item.name)
                        items[item.name] = item.id
                        names.add(index, item.name)
                    }
                    spinner.adapter = ArrayAdapter<String>(this@DeleteFragment.activity, R.layout.support_simple_spinner_dropdown_item, names)
                })

        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                currentItemId = items[names[position]]
                (activity as HomeActivity).itemRepository
                        .getItem("JWT ${activity.getSessionToken()}",
                                currentItemId!!,
                                object: ItemRepository.UpdateCallback {
                                    override fun onSuccess(item: Item) {
                                        val itemPreview = layoutView.findViewById<CardView>(R.id.delete_item)
                                        val itemName = layoutView.findViewById<TextView>(R.id.item_name)
                                        val itemImage = layoutView.findViewById<ImageView>(R.id.item_image)
                                        val itemDescription = layoutView.findViewById<TextView>(R.id.item_description)

                                        itemName.text = item.name
                                        itemDescription.text = item.description
                                        Picasso.get().load(item.image).into(itemImage)
                                        itemPreview.visibility = View.VISIBLE
                                    }

                                    override fun onError(message: String) {}
                                })
            }
        }

        val button = layoutView.findViewById<Button>(R.id.button_delete)
        button.setOnClickListener {
            it.isEnabled = false
            AlertDialog(activity).apply {
                setupDialog(
                        getString(R.string.delete_item_title),
                        getString(R.string.delete_item_msg)
                )
                addButton(DialogButton(
                        DialogButton.POSITIVE,
                        getString(R.string.dialog_okbutton),
                        {
                            (activity as HomeActivity).itemRepository
                                    .deleteItem("JWT ${activity.getSessionToken()}", currentItemId!!, object: ItemRepository.DeleteCallback {
                                        override fun onSuccess() {
                                            Log.i(TAG, "Item deleted")
                                        }
                                    })
                        }))

                addButton(DialogButton(
                        DialogButton.NEGATIVE,
                        getString(R.string.dialog_cancelbutton),
                        {
                            it.isEnabled = true
                            Log.i(TAG, "Operation canceled")
                        }
                        ))
            }.show()
        }
        return layoutView
    }
}