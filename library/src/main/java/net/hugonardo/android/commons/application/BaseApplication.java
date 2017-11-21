package net.hugonardo.android.commons.application;

import static net.hugonardo.android.commons.di.ReleaseMode.DEBUG;
import static net.hugonardo.java.commons.Objects.areEquals;
import static net.hugonardo.java.commons.Objects.nonNull;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.multidex.MultiDex;
import com.squareup.leakcanary.LeakCanary;
import dagger.android.support.DaggerApplication;
import javax.inject.Inject;
import net.hugonardo.android.commons.di.ReleaseMode;
import timber.log.Timber;
import timber.log.Timber.Tree;

public abstract class BaseApplication extends DaggerApplication {

    @Inject
    @Nullable
    Tree[] mProductionTrees;

    @Inject
    ReleaseMode mReleaseMode;

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        if (areEquals(mReleaseMode, DEBUG)) {
            Timber.plant(new Timber.DebugTree());
            SQLiteStudioInitializer.init(this);
        } else {
            if (nonNull(mProductionTrees)) {
                Timber.plant(mProductionTrees);
            }
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
