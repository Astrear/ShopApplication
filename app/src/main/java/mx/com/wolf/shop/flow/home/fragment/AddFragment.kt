package mx.com.wolf.shop.flow.home.fragment

import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.support.v4.content.FileProvider
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import mx.com.wolf.shop.R
import mx.com.wolf.shop.data.Item
import mx.com.wolf.shop.data.source.ItemRepository
import mx.com.wolf.shop.extensions.getSessionToken
import mx.com.wolf.shop.extensions.getText
import mx.com.wolf.shop.flow.home.HomeActivity
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by Jose Barrera on 24/06/2018.
 * Copyright (c) 2018 Anzen Digital All rights reserved.
 */
class AddFragment: Fragment() {

    companion object {
        val TAG = AddFragment::class.simpleName
        const val REQUEST_IMAGE_CAPTURE = 1
        const val REQUEST_STORAGE_ACCES = 2
        const val PERMISSIONS_ALL = 3
    }

    lateinit var addButton: Button
    lateinit var camButton: ImageButton
    lateinit var itemName: TextInputLayout
    lateinit var itemImage: TextInputLayout
    lateinit var itemImageHolder: ImageView
    lateinit var itemDescription: EditText

    lateinit var currentImagePath: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val permissions = arrayOf(android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        (activity as HomeActivity).requestMultPermissions(permissions, PERMISSIONS_ALL)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_add, container, false)

        addButton = view.findViewById(R.id.button_add)

        itemName =view.findViewById(R.id.input_item_name)
        itemImage = view.findViewById(R.id.input_item_image)
        itemImageHolder = view.findViewById(R.id.item_image_preview)
        itemDescription = view.findViewById(R.id.input_item_description)

        itemImage.editText!!.setOnFocusChangeListener { v, hasFocus ->
            val url = (v as TextInputEditText).text.toString()
            if(!hasFocus && !url.isBlank())
                Picasso.get().load(url).error(R.drawable.notfound).into(itemImageHolder)
        }


        addButton.setOnClickListener {
            val name = itemName.getText()
            val imag = itemImage.getText()
            val desc = itemDescription.text.toString()


            if(name.isNotBlank() && imag.isNotBlank() && desc.isNotBlank())
                (activity as HomeActivity).itemRepository.addItem(
                        "JWT ${activity.getSessionToken()}",
                        Item(name, imag, desc),
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
        camButton.setOnClickListener(cameraButtonListener)
        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                Glide.with(activity).load(currentImagePath).into(itemImageHolder)
                itemImage.editText!!.setText(currentImagePath, TextView.BufferType.EDITABLE)
            }
        }
    }

    val cameraButtonListener = View.OnClickListener {
        var file: File? = null
        try {
            file = createImageFile()
        } catch (e: IOException) {
            Log.i(TAG, e.message)
        }


        if(file != null) {
            Log.i(TAG, currentImagePath)
            val imageURI = FileProvider.getUriForFile(activity,"mx.com.wolf.shop", file)
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageURI)
            this.startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {

        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_${timeStamp}_"
        val storageDir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(
                imageFileName, /* prefix */
                ".jpg", /* suffix */
                storageDir      /* directory */
        )

        currentImagePath = image.absolutePath
        return image
    }

    private fun getImageUri(inContext: Context, inImage: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(inContext.contentResolver, inImage, "Title", null)
        return Uri.parse(path)
    }

    private fun getRealPathFromURI(contentUri: Uri): String {
        var cursor: Cursor? = null
        try {
            val images = arrayOf(MediaStore.Images.Media.DATA)

            cursor = activity.contentResolver.query(contentUri, images, null, null, null)

            val index = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()

            return cursor.getString(index)
        } finally {
            if (cursor != null) {
                cursor.close()
            }
        }
    }
}