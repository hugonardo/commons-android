package net.hugonardo.android.commons.accounts;

import android.support.annotation.NonNull;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TokenTypes {

    private static final TokenTypes sSingleInstance = new TokenTypes();

    /**
     * Key é a chave do TokenType;
     * Value é o label do TokenType.
     */
    private final Map<String, String> mTypes = Collections.synchronizedMap(new HashMap<String, String>());

    private TokenTypes() {
    }

    public TokenTypes registerTokenType(@NonNull String key, @NonNull String label) {
        mTypes.put(key, label);
        return this;
    }

    boolean contains(String authTokenType) {
        return mTypes.containsKey(authTokenType);
    }

    String getLabel(String authTokenType) {
        String label = mTypes.get(authTokenType);
        return label != null ? label : authTokenType + " (Label)";
    }

    boolean isEmpty() {
        return mTypes.isEmpty();
    }

    static TokenTypes getSingleInstance() {
        return sSingleInstance;
    }
}
