package net.hugonardo.android.commons.accounts;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import timber.log.Timber;

public abstract class FeatherAccountAuthenticator extends AbstractAccountAuthenticator {

    private final Context mContext;

    private final TokenTypes mTokenTypes = TokenTypes.getSingleInstance();

    public FeatherAccountAuthenticator(Context context) {
        super(context);
        mContext = context;
    }

    /**
     * Adds an account of the specified accountType.
     *
     * @param response         to send the result back to the AccountManager, will never be null
     * @param accountType      the type of account to add, will never be null
     * @param authTokenType    the type of auth token to retrieve after adding the account, may be null
     * @param requiredFeatures a String array of authenticator-specific features that the added
     *                         account must support, may be null
     * @param options          a Bundle of authenticator-specific options, may be null
     * @return a Bundle result or null if the result is to be returned via the response. The result
     * will contain either:
     * <ul>
     * <li> {@link AccountManager#KEY_INTENT}, or
     * <li> {@link AccountManager#KEY_ACCOUNT_NAME} and {@link AccountManager#KEY_ACCOUNT_TYPE} of
     * the account that was added, or
     * <li> {@link AccountManager#KEY_ERROR_CODE} and {@link AccountManager#KEY_ERROR_MESSAGE} to
     * indicate an error
     * </ul>
     * @throws NetworkErrorException if the authenticator could not honor the request due to a
     *                               network error
     */
    @Override
    public Bundle addAccount(AccountAuthenticatorResponse response, String accountType, String authTokenType,
            String[] requiredFeatures, Bundle options) throws NetworkErrorException {
        log("FeatherAccountAuthenticator#addAccount()");

        final Intent intent = new Intent(mContext, provideAccountAuthenticatorActivityClass());
        intent.putExtra(FeatherAccountAuthenticatorActivity.ARG_ACCOUNT_TYPE, accountType);
        intent.putExtra(FeatherAccountAuthenticatorActivity.ARG_AUTH_TOKEN_TYPE, authTokenType);
        intent.putExtra(FeatherAccountAuthenticatorActivity.ARG_IS_ADDING_NEW_ACCOUNT, true);
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
        if (options != null) {
            intent.putExtra(FeatherAccountAuthenticatorActivity.ARG_ACCOUNT_OPTIONS, options);
        }

        return makeReturnableBundle(intent);
    }

    /**
     * Checks that the user knows the credentials of an account.
     *
     * @param response to send the result back to the AccountManager, will never be null
     * @param account  the account whose credentials are to be checked, will never be null
     * @param options  a Bundle of authenticator-specific options, may be null
     * @return a Bundle result or null if the result is to be returned via the response. The result
     * will contain either:
     * <ul>
     * <li> {@link AccountManager#KEY_INTENT}, or
     * <li> {@link AccountManager#KEY_BOOLEAN_RESULT}, true if the check succeeded, false otherwise
     * <li> {@link AccountManager#KEY_ERROR_CODE} and {@link AccountManager#KEY_ERROR_MESSAGE} to
     * indicate an error
     * </ul>
     * @throws NetworkErrorException if the authenticator could not honor the request due to a
     *                               network error
     */
    @Override
    public Bundle confirmCredentials(AccountAuthenticatorResponse response, Account account, Bundle options)
            throws NetworkErrorException {
        log("FeatherAccountAuthenticator#confirmCredentials()");

        if (options != null && options.containsKey(AccountManager.KEY_PASSWORD)) {
            final String password = options.getString(AccountManager.KEY_PASSWORD);
            final boolean verified = confirmPassword(account.name, password);
            final Bundle result = new Bundle();
            result.putBoolean(AccountManager.KEY_BOOLEAN_RESULT, verified);
            return result;
        }

        // Launch AuthenticatorActivity to confirm credentials
        final Intent intent = new Intent(mContext, provideAccountAuthenticatorActivityClass());
        intent.putExtra(FeatherAccountAuthenticatorActivity.ARG_ACCOUNT_NAME, account.name);
        intent.putExtra(FeatherAccountAuthenticatorActivity.ARG_CONFIRM_CREDENTIALS, true);
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
        final Bundle bundle = new Bundle();
        bundle.putParcelable(AccountManager.KEY_INTENT, intent);
        return bundle;
    }

    /**
     * Returns a Bundle that contains the Intent of the activity that can be used to edit the
     * properties. In order to indicate success the activity should call response.setResult()
     * with a non-null Bundle.
     *
     * @param response    used to set the result for the request. If the Constants.INTENT_KEY
     *                    is set in the bundle then this response field is to be used for sending future
     *                    results if and when the Intent is started.
     * @param accountType the AccountType whose properties are to be edited.
     * @return a Bundle containing the result or the Intent to start to continue the request.
     * If this is null then the request is considered to still be active and the result should
     * sent later using response.
     */
    @Override
    public Bundle editProperties(AccountAuthenticatorResponse response, String accountType) {
        log("FeatherAccountAuthenticator#editProperties()");

        final Intent intent = new Intent(mContext, provideAccountAuthenticatorActivityClass());
        intent.putExtra(FeatherAccountAuthenticatorActivity.ARG_ACCOUNT_TYPE, accountType);
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);

        return makeReturnableBundle(intent);
    }

    /**
     * Gets an authtoken for an account.
     * <p>
     * If not {@code null}, the resultant {@link Bundle} will contain different sets of keys
     * depending on whether a token was successfully issued and, if not, whether one
     * could be issued via some {@link Activity}.
     * <p>
     * If a token cannot be provided without some additional activity, the Bundle should contain
     * {@link AccountManager#KEY_INTENT} with an associated {@link Intent}. On the other hand, if
     * there is no such activity, then a Bundle containing
     * {@link AccountManager#KEY_ERROR_CODE} and {@link AccountManager#KEY_ERROR_MESSAGE} should be
     * returned.
     * <p>
     * If a token can be successfully issued, the implementation should return the
     * {@link AccountManager#KEY_ACCOUNT_NAME} and {@link AccountManager#KEY_ACCOUNT_TYPE} of the
     * account associated with the token as well as the {@link AccountManager#KEY_AUTHTOKEN}. In
     * addition {@link AbstractAccountAuthenticator} implementations that declare themselves
     * {@code android:customTokens=true} may also provide a non-negative {@link
     * #KEY_CUSTOM_TOKEN_EXPIRY} long value containing the expiration timestamp of the expiration
     * time (in millis since the unix epoch).
     * <p>
     * Implementers should assume that tokens will be cached on the basis of account and
     * authTokenType. The system may ignore the contents of the supplied options Bundle when
     * determining to re-use a cached token. Furthermore, implementers should assume a supplied
     * expiration time will be treated as non-binding advice.
     * <p>
     * Finally, note that for android:customTokens=false authenticators, tokens are cached
     * indefinitely until some client calls {@link
     * AccountManager#invalidateAuthToken(String, String)}.
     *
     * @param response      to send the result back to the AccountManager, will never be null
     * @param account       the account whose credentials are to be retrieved, will never be null
     * @param authTokenType the type of auth token to retrieve, will never be null
     * @param options       a Bundle of authenticator-specific options, may be null
     * @return a Bundle result or null if the result is to be returned via the response.
     * @throws NetworkErrorException if the authenticator could not honor the request due to a
     *                               network error
     */
    @Override
    public Bundle getAuthToken(AccountAuthenticatorResponse response, Account account, String authTokenType,
            Bundle options) throws NetworkErrorException {
        log("FeatherAccountAuthenticator#getAuthToken()");

        // If the caller requested an authToken type we don't support, then
        // return an error
        if (mTokenTypes.contains(authTokenType)) {
            final Bundle result = new Bundle();
            result.putString(AccountManager.KEY_ERROR_MESSAGE, "invalid authTokenType");
            return result;
        }

        // Extract the username and password from the Account Manager, and ask
        // the server for an appropriate AuthToken.
        final AccountManager am = AccountManager.get(mContext);

        @SuppressLint("MissingPermission")
        String authToken = am.peekAuthToken(account, authTokenType);

        log("FeatherAccountAuthenticator#getAuthToken() > peekAuthToken returned: " + authToken);

        // Lets give another try to authenticate the user
        if (TextUtils.isEmpty(authToken)) {
            @SuppressLint("MissingPermission")
            final String password = am.getPassword(account);
            if (password != null) {
                try {
                    log("FeatherAccountAuthenticator#getAuthToken() > re-authenticating with the existing password");
                    authToken = signIn(account.name, password, authTokenType);
                } catch (Exception e) {
                    Timber.w(e);
                }
            }
        }

        // If we get an authToken - we return it
        if (!TextUtils.isEmpty(authToken)) {
            final Bundle result = new Bundle();
            result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name);
            result.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type);
            result.putString(AccountManager.KEY_AUTHTOKEN, authToken);
            return result;
        }

        // If we get here, then we couldn't access the user's password - so we
        // need to re-prompt them for their credentials. We do that by creating
        // an intent to display our AuthenticatorActivity.
        final Intent intent = new Intent(mContext, provideAccountAuthenticatorActivityClass());
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
        intent.putExtra(FeatherAccountAuthenticatorActivity.ARG_ACCOUNT_TYPE, account.type);
        intent.putExtra(FeatherAccountAuthenticatorActivity.ARG_AUTH_TOKEN_TYPE, authTokenType);
        intent.putExtra(FeatherAccountAuthenticatorActivity.ARG_ACCOUNT_NAME, account.name);
        final Bundle bundle = new Bundle();
        bundle.putParcelable(AccountManager.KEY_INTENT, intent);
        return bundle;
    }

    /**
     * Ask the authenticator for a localized label for the given authTokenType.
     *
     * @param authTokenType the authTokenType whose label is to be returned, will never be null
     * @return the localized label of the auth token type, may be null if the type isn't known
     */
    @Override
    public String getAuthTokenLabel(String authTokenType) {
        return mTokenTypes.getLabel(authTokenType);
    }

    /**
     * Checks if the account supports all the specified authenticator specific features.
     *
     * @param response to send the result back to the AccountManager, will never be null
     * @param account  the account to check, will never be null
     * @param features an array of features to check, will never be null
     * @return a Bundle result or null if the result is to be returned via the response. The result
     * will contain either:
     * <ul>
     * <li> {@link AccountManager#KEY_INTENT}, or
     * <li> {@link AccountManager#KEY_BOOLEAN_RESULT}, true if the account has all the features,
     * false otherwise
     * <li> {@link AccountManager#KEY_ERROR_CODE} and {@link AccountManager#KEY_ERROR_MESSAGE} to
     * indicate an error
     * </ul>
     * @throws NetworkErrorException if the authenticator could not honor the request due to a
     *                               network error
     */
    @Override
    public Bundle hasFeatures(AccountAuthenticatorResponse response, Account account, String[] features)
            throws NetworkErrorException {
        log("FeatherAccountAuthenticator#hasFeatures()");
        return null;
    }

    /**
     * Update the locally stored credentials for an account.
     *
     * @param response      to send the result back to the AccountManager, will never be null
     * @param account       the account whose credentials are to be updated, will never be null
     * @param authTokenType the type of auth token to retrieve after updating the credentials,
     *                      may be null
     * @param options       a Bundle of authenticator-specific options, may be null
     * @return a Bundle result or null if the result is to be returned via the response. The result
     * will contain either:
     * <ul>
     * <li> {@link AccountManager#KEY_INTENT}, or
     * <li> {@link AccountManager#KEY_ACCOUNT_NAME} and {@link AccountManager#KEY_ACCOUNT_TYPE} of
     * the account whose credentials were updated, or
     * <li> {@link AccountManager#KEY_ERROR_CODE} and {@link AccountManager#KEY_ERROR_MESSAGE} to
     * indicate an error
     * </ul>
     * @throws NetworkErrorException if the authenticator could not honor the request due to a
     *                               network error
     */
    @Override
    public Bundle updateCredentials(AccountAuthenticatorResponse response, Account account, String authTokenType,
            Bundle options) throws NetworkErrorException {
        log("FeatherAccountAuthenticator#updateCredentials()");
        final Intent intent = new Intent(mContext, provideAccountAuthenticatorActivityClass());
        intent.putExtra(FeatherAccountAuthenticatorActivity.ARG_ACCOUNT_NAME, account.name);
        intent.putExtra(FeatherAccountAuthenticatorActivity.ARG_AUTH_TOKEN_TYPE, authTokenType);
        intent.putExtra(FeatherAccountAuthenticatorActivity.ARG_CONFIRM_CREDENTIALS, false);

        return makeReturnableBundle(intent);
    }

    /**
     * Utilizado por {@link #confirmCredentials(AccountAuthenticatorResponse, Account, Bundle)}.
     * Deve ser provida uma forma de validar a senha do usuário.
     *
     * @param username um nome de usuário obtido através do {@link Account#name}.
     * @param password uma senha obtida através das Options, pela chave {@link AccountManager#KEY_PASSWORD}.
     * @return deve retornar {@code true} se a senha for válida, {@code false} caso contrário.
     */
    protected abstract boolean confirmPassword(String username, String password);

    @NonNull
    protected abstract Class<? extends FeatherAccountAuthenticatorActivity> provideAccountAuthenticatorActivityClass();

    /**
     * Utilizado pelo {@link #getAuthToken(AccountAuthenticatorResponse, Account, String, Bundle)}.
     * Deve ser fornecido um método de autenticação do usuário.
     * <p>
     * Lhe será fornecido um username, uma senha e um authTokenType, que podem ser utilizados
     * para requisitar o seu serviço de autenticação.
     *
     * @param username      um nome de usuário obtido através do {@link Account#name}.
     * @param password      uma senha obtida através do {@link AccountManager#getPassword(Account)}.
     * @param authTokenType um TokenType
     * @return Deve retornar um token de autorização
     */
    protected abstract String signIn(@NonNull String username, @NonNull String password,
            @NonNull String authTokenType);

    private void log(String message) {
        Timber.v(message);
    }

    @NonNull
    private Bundle makeReturnableBundle(Intent intent) {
        final Bundle bundle = new Bundle();
        bundle.putParcelable(AccountManager.KEY_INTENT, intent);
        return bundle;
    }
}
