/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.hugonardo.android.commons.accounts;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Base class for implementing an Activity that is used to help implement an
 * AbstractAccountAuthenticator. If the AbstractAccountAuthenticator needs to use an activity
 * to handle the request then it can have the activity extend FeatherAccountAuthenticatorActivity.
 * The AbstractAccountAuthenticator passes in the response to the intent using the following:
 * <pre>
 *      intent.putExtra({@link AccountManager#KEY_ACCOUNT_AUTHENTICATOR_RESPONSE}, response);
 * </pre>
 * The activity then sets the result that is to be handed to the response via
 * {@link #setAccountAuthenticatorResult(Bundle)}.
 * This result will be sent as the result of the request when the activity finishes. If this
 * is never set or if it is set to null then error {@link AccountManager#ERROR_CODE_CANCELED}
 * will be called on the response.
 *
 * @see AccountAuthenticatorActivity
 */
public class FeatherAccountAuthenticatorActivity extends AppCompatActivity {

    /**
     * The Intent extra to store username, or a name to identify the account.
     *
     * @see FeatherAccountAuthenticator#updateCredentials(AccountAuthenticatorResponse, Account, String, Bundle)
     * @see FeatherAccountAuthenticator#confirmCredentials(AccountAuthenticatorResponse, Account, Bundle)
     * @see FeatherAccountAuthenticator#editProperties(AccountAuthenticatorResponse, String)
     */
    public final static String ARG_ACCOUNT_NAME = "ACCOUNT_NAME";

    /**
     * The Intent extra to store account type.
     */
    public final static String ARG_ACCOUNT_TYPE = "ACCOUNT_TYPE";

    /**
     * The Intent extra to store authTokenType.
     *
     * @see FeatherAccountAuthenticator#addAccount(AccountAuthenticatorResponse, String, String, String[], Bundle)
     */
    public final static String ARG_AUTH_TOKEN_TYPE = "AUTH_TOKEN_TYPE";

    /**
     * The Intent extra to store a bundle of account options.
     */
    public final static String ARG_ACCOUNT_OPTIONS = "ACCOUNT_OPTIONS";

    public final static String ARG_IS_ADDING_NEW_ACCOUNT = "IS_ADDING_ACCOUNT";

    /**
     * The Intent flag to confirm credentials.
     */
    public static final String ARG_CONFIRM_CREDENTIALS = "CONFIRM_CREDENTIALS";

    private AccountAuthenticatorResponse mAccountAuthenticatorResponse = null;

    private Bundle mResultBundle = null;

    /**
     * Retreives the AccountAuthenticatorResponse from either the intent of the icicle, if the
     * icicle is non-zero.
     *
     * @param icicle the save instance data of this Activity, may be null
     */
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        mAccountAuthenticatorResponse =
                getIntent().getParcelableExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE);

        if (mAccountAuthenticatorResponse != null) {
            mAccountAuthenticatorResponse.onRequestContinued();
        }
    }

    /**
     * Sends the result or a Constants.ERROR_CODE_CANCELED error if a result isn't present.
     */
    public void finish() {
        if (mAccountAuthenticatorResponse != null) {
            // send the result bundle back if set, otherwise send an error.
            if (mResultBundle != null) {
                mAccountAuthenticatorResponse.onResult(mResultBundle);
            } else {
                mAccountAuthenticatorResponse.onError(AccountManager.ERROR_CODE_CANCELED, "canceled");
            }
            mAccountAuthenticatorResponse = null;
        }
        super.finish();
    }

    /**
     * Set the result that is to be sent as the result of the request that caused this
     * Activity to be launched. If result is null or this method is never called then
     * the request will be canceled.
     *
     * @param result this is returned as the result of the AbstractAccountAuthenticator request
     */
    public final void setAccountAuthenticatorResult(Bundle result) {
        mResultBundle = result;
    }
}
