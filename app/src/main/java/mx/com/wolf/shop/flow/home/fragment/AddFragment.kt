package mx.com.wolf.shop.flow.home.fragment

import android.app.Fragment
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.support.v4.app.ActivityCompat
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
import mx.com.wolf.shop.extensions.getSessionToken
import mx.com.wolf.shop.flow.home.HomeActivity
import android.R.attr.data
import android.support.v4.app.NotificationCompat.getExtras
import android.graphics.Bitmap
import android.app.Activity
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.support.v7.widget.AppCompatImageButton
import java.io.ByteArrayOutputStream


/**
 * Created by Jose Barrera on 24/06/2018.
 * Copyright (c) 2018 Anzen Digital All rights reserved.
 */
class AddFragment: Fragment() {

    companion object {
        val TAG = AddFragment::class.simpleName
        const val REQUEST_IMAGE_CAPTURE = 1
    }

    lateinit var addButton: Button
    lateinit var camButton: AppCompatImageButton
    lateinit var itemName: String
    lateinit var itemImage: TextInputLayout
    lateinit var itemImageHolder: ImageView
    lateinit var itemDescription: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_DENIED) {
            (activity as HomeActivity).requestWritePermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }

        if(ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED) {
            (activity as HomeActivity).requestWritePermission(android.Manifest.permission.CAMERA)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_add, container, false)
        Log.i(TAG, "On CreateView")

        addButton = view.findViewById(R.id.button_add)

        itemImage = view.findViewById(R.id.input_item_image)
        itemImageHolder = view.findViewById(R.id.item_image_preview)

        itemImage.editText!!.setOnFocusChangeListener { v, hasFocus ->
            val url = (v as TextInputEditText).text.toString()
            if(!hasFocus && !url.isBlank())
                Picasso.get().load(url).error(R.drawable.notfound).into(itemImageHolder)
        }


        addButton.setOnClickListener {
            itemName = view.findViewById<TextInputLayout>(R.id.input_item_name).editText!!.text.toString()
            itemDescription = view.findViewById<EditText>(R.id.input_item_description).text.toString()

            if(itemName.isNotBlank() && itemImage.editText!!.text.toString().isNotBlank() && itemDescription.isNotBlank())
                (activity as HomeActivity).itemRepository.addItem(
                        "JWT ${activity.getSessionToken()}",
                        Item(itemName, itemImage.editText!!.text.toString(), itemDescription),
                        object: ItemRepository.UpdateCallback {
                            override fun onSuccess(item: Item) {
                                Log.i(TAG, "Item saved successfully with id: ${item.id}")
                            }

                            override fun onError(message: String) {
                                Log.i(TAG, message)
                            }
                        })
        }

        camButton = view.findViewById(R.id.button_camera)
        camButton.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            val frag = this
            /** Pass your fragment reference **/
            frag.startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
        }

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                // Do something with imagePath

                val photo = data!!.getExtras().get("data") as Bitmap
                itemImageHolder.setImageBitmap(photo)
                // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
                var selectedImage = getImageUri(activity, photo)
                val realPath = getRealPathFromURI(selectedImage)
                selectedImage = Uri.parse(realPath)

                Log.i("Test", selectedImage.path)
            }
        }
    }

    fun getImageUri(inContext: Context, inImage: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(inContext.contentResolver, inImage, "Title", null)
        return Uri.parse(path)
    }

    fun getRealPathFromURI(contentUri: Uri): String {
        var cursor: Cursor? = null
        try {
            val proj = arrayOf(MediaStore.Images.Media.DATA)
            cursor = activity.contentResolver.query(contentUri, proj, null, null, null)
            val column_index = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor!!.moveToFirst()
            return cursor!!.getString(column_index)
        } finally {
            if (cursor != null) {
                cursor!!.close()
            }
        }
    }
}