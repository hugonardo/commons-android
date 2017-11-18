package net.hugonardo.android.commons.databinding;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.databinding.BindingAdapter;
import android.view.View;

public class ViewBindingAdapter {

    @BindingAdapter({"android:visibility"})
    public static void setVisible(View view, Boolean visible) {
        view.setVisibility(visible ? VISIBLE : GONE);
    }
}
