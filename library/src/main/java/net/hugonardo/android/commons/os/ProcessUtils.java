package net.hugonardo.android.commons.os;

import static net.hugonardo.java.commons.text.StringUtils.EMPTY;

import android.app.ActivityManager;
import android.content.Context;
import android.support.annotation.NonNull;

public class ProcessUtils {

    @NonNull
    public static String getCurrentProcessName(@NonNull Context context) {
        String processName = EMPTY;
        int pid = android.os.Process.myPid();
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (manager != null) {
            for (ActivityManager.RunningAppProcessInfo processInfo : manager.getRunningAppProcesses()) {
                if (processInfo.pid == pid) {
                    processName = processInfo.processName;
                    break;
                }
            }
        }
        return processName;
    }
}
