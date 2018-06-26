package mx.com.wolf.shop.data;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
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
        Glide.with(view.getContext())
                .load(imageUrl)
                .thumbnail(0.5F)
                .error(
                        Glide.with(view.getContext()).load(R.drawable.notfound)
                ).into(view);
    }

}
