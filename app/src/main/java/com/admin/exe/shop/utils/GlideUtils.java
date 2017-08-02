package com.admin.exe.shop.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

/**
 * Created by admin on 2017/4/28.
 */

public class GlideUtils {

    public static void getCircular(final Context context, String url, final ImageView imgv){
        Glide.with(context).load(url).asBitmap().centerCrop().into(new BitmapImageViewTarget(imgv) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                imgv.setImageDrawable(circularBitmapDrawable);
            }
        });
    }


    public static void getCircular(final Context context, int url, final ImageView imgv){
        Glide.with(context).load(url).asBitmap().centerCrop().into(new BitmapImageViewTarget(imgv) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                imgv.setImageDrawable(circularBitmapDrawable);
            }
        });
    }

}
