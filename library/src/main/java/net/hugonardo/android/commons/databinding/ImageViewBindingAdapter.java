package net.hugonardo.android.commons.databinding;

import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.widget.ImageView;

public class ImageViewBindingAdapter {

    @BindingAdapter({"android:src"})
    public static void loadImage(ImageView iv, Bitmap bitmap) {
        iv.setImageBitmap(bitmap);
    }
}
