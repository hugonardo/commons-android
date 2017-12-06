package net.hugonardo.android.commons.databinding;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static net.hugonardo.java.commons.Booleans.isTrue;

import android.databinding.BindingAdapter;
import android.view.View;
import javax.annotation.Nullable;

public class ViewBindingAdapter {

    @BindingAdapter({"android:visibility"})
    public static void setVisible(View view, @Nullable Boolean visible) {
        view.setVisibility(isTrue(visible) ? VISIBLE : GONE);
    }
}
