package com.furkanbalci.mesat.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Glide ile resim çekme classı.
 */
public class GlideUtil {

    /**
     * Belirtilen imageView'e url'deki resmi yerleştirir.
     */
    public static void downloadAndShow(Context context, String url, ImageView imageView) {
        Glide.with(context).load(url).into(imageView);
    }

}
