package mx.com.wolf.shop.data;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import mx.com.wolf.shop.R;

/**
 * Created by Jose Barrera on 25/06/18.
 * Copyright (c) 2018 Anzen Digital All rights reserved.
 */

public class ItemBind extends BaseObservable {

    String name;
    String image;
    String description;

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Bindable
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Bindable
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @BindingAdapter("image")
    public static void loadImage(ImageView view, String imageUrl) {
        Picasso.get().load(imageUrl).error(R.drawable.notfound).into(view);
    }

}
