package net.hugonardo.android.commons.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import net.hugonardo.java.commons.network.NetworkMonitor;

public class LiveNetworkMonitor implements NetworkMonitor {

    private final Context mApplicationContext;

    public LiveNetworkMonitor(Context context) {
        mApplicationContext = context.getApplicationContext();
    }

    public boolean isConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) mApplicationContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }
}