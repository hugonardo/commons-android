/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package net.hugonardo.android.commons.accounts;

import android.accounts.AbstractAccountAuthenticator;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.NonNull;
import net.hugonardo.android.commons.logging.Logger;

/**
 * Service to handle Account authentication. It instantiates the authenticator
 * and returns its IBinder.
 */
public abstract class FeatherAuthenticationService extends Service {

    private AbstractAccountAuthenticator mAuthenticator;

    private Logger mLogger;

    @Override
    public void onCreate() {
        log("SampleSyncAdapter Authentication Service started.");
        mAuthenticator = provideAccountAuthenticator(this);
        registerTokenTypes(TokenTypes.getSingleInstance());
    }

    @Override
    public void onDestroy() {
        log("SampleSyncAdapter Authentication Service stopped.");
    }

    @Override
    public IBinder onBind(Intent intent) {
        log("getBinder()...  returning the FeatherAccountAuthenticator binder for intent " + intent);
        return mAuthenticator.getIBinder();
    }

    @NonNull
    protected abstract AbstractAccountAuthenticator provideAccountAuthenticator(@NonNull Context context);

    protected abstract void registerTokenTypes(TokenTypes tokenTypes);

    protected void setLogger(Logger logger) {
        mLogger = logger;
    }

    private void log(String msg) {
        if (mLogger != null) {
            mLogger.verbose(msg);
        }
    }
}
