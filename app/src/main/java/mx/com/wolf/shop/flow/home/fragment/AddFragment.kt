package mx.com.wolf.shop.flow.home.fragment

import android.app.Fragment
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.squareup.picasso.Picasso
import mx.com.wolf.shop.R
import mx.com.wolf.shop.data.Item
import mx.com.wolf.shop.data.source.ItemRepository
import mx.com.wolf.shop.flow.home.HomeActivity

/**
 * Created by Jose Barrera on 24/06/2018.
 * Copyright (c) 2018 Anzen Digital All rights reserved.
 */
class AddFragment: Fragment() {

    companion object {
        val TAG = AddFragment::class.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("Test", "on addfragment")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_add, container, false)
        Log.i(TAG, "On CreateView")

        val button: Button = view.findViewById(R.id.button_add)

        val imageSRC = view.findViewById<TextInputLayout>(R.id.input_item_image)
        val imageHolder = view.findViewById<ImageView>(R.id.item_image_preview)

        imageSRC.editText!!.setOnFocusChangeListener { v, hasFocus ->
            val url = (v as TextInputEditText).text.toString()
            if(!hasFocus && !url.isBlank())
                Picasso.get().load(url).error(R.drawable.notfound).into(imageHolder)
        }


        button.setOnClickListener {
            val itemName = view.findViewById<TextInputLayout>(R.id.input_item_name)
            val itemDescription = view.findViewById<EditText>(R.id.input_item_description)

            if(itemName.editText!!.text.toString().isNotBlank() &&
                    imageSRC.editText!!.text.toString().isNotBlank() &&
                    itemDescription.text.toString().isNotBlank())
                (activity as HomeActivity).itemRepository.addItem(
                        Item(
                                itemName.editText!!.text.toString(),
                                imageSRC.editText!!.text.toString(),
                                itemDescription.text.toString()
                        ), object: ItemRepository.UpdateCallback {
                    override fun onSuccess(item: Item) {
                        Log.i(TAG, "Item saved successfully with id: ${item.id}")
                    }

                    override fun onError(message: String) {
                        Log.i(TAG, message)
                    }

                })
        }

        return view
    }
}