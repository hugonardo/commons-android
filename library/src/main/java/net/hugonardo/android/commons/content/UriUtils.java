package net.hugonardo.android.commons.content;

import android.net.Uri;

public class UriUtils {

    public static Uri contentAuthority(String authority) {
        return Uri.parse("content://" + authority);
    }
}
