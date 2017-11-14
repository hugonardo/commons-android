package net.hugonardo.android.commons.background;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

/**
 * @deprecated Use {@link AsyncTask} directly with Dependency Injection.
 */
@Deprecated
public abstract class FeaturedAsyncTask<Params, Progress, Result>
        extends AsyncTask<Params, Progress, Result> {

    @NonNull
    @SuppressLint("StaticFieldLeak")
    private Context mApplicationContext;

    @NonNull
    private ContentResolver mContentResolver;

    public FeaturedAsyncTask(@NonNull Context context) {
        mApplicationContext = context.getApplicationContext();
        mContentResolver = mApplicationContext.getContentResolver();
    }

    @NonNull
    protected Context getApplicationContext() {
        return mApplicationContext;
    }

    @NonNull
    protected ContentResolver getContentResolver() {
        return mContentResolver;
    }
}
