package net.hugonardo.android.commons.application;

import static net.hugonardo.android.commons.os.ProcessUtils.getCurrentProcessName;
import static net.hugonardo.java.commons.text.StringUtils.containsNone;

import android.content.Context;
import pl.com.salsoft.sqlitestudioremote.SQLiteStudioService;
import timber.log.Timber;

class SQLiteStudioInitializer {

    static void init(Context context) {
        if (containsNone(getCurrentProcessName(context), ":")) {
            SQLiteStudioService.instance().start(context);
            Timber.i("Serviço SQLiteStudioService foi iniciado");
        }
    }
}
