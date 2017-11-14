package net.hugonardo.android.commons.logging;

/**
 * @deprecated utilize {@link timber.log.Timber}
 */
@Deprecated
public interface Logger {

    void debug(String message, Object... args);

    void debug(Throwable cause, String message, Object... args);

    void error(String message, Object... args);

    void error(Throwable cause, String message, Object... args);

    void info(String message, Object... args);

    void info(Throwable cause, String message, Object... args);

    void verbose(String message, Object... args);

    void verbose(Throwable cause, String message, Object... args);

    void warning(String message, Object... args);

    void warning(Throwable cause, String message, Object... args);

    void wtf(String message, Object... args);

    void wtf(Throwable cause, String message, Object... args);
}
