package net.hugonardo.android.commons.content;

import static net.hugonardo.java.commons.Objects.nonNull;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.Calendar;
import java.util.Date;

public class CursorReader {

    public static void close(@Nullable Cursor cursor) {
        if (nonNull(cursor)) {
            cursor.close();
        }
    }

    public static int count(@Nullable Cursor cursor) {
        return nonNull(cursor) ? cursor.getCount() : 0;
    }

    public static boolean empty(@Nullable Cursor cursor) {
        return count(cursor) == 0;
    }

    public static boolean nonEmpty(@Nullable Cursor cursor) {
        return count(cursor) > 0;
    }

    @Nullable
    public static Boolean readBoolean(@NonNull Cursor cursor, @NonNull String columnName) {
        Long longValue = readLong(cursor, columnName);
        return longValue != null
                ? longValue.equals(1L)
                : null;
    }

    @Nullable
    public static Calendar readCalendarFromMillis(@NonNull Cursor cursor, @NonNull String columnName) {
        Long timeInMillis = readLong(cursor, columnName);
        if (timeInMillis == null) {
            return null;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMillis);
        return calendar;
    }

    @Nullable
    public static Date readDateFromMillis(@NonNull Cursor cursor, @NonNull String columnName) {
        Long timeInMillis = readLong(cursor, columnName);
        return timeInMillis != null
                ? new Date(timeInMillis)
                : null;
    }

    @Nullable
    public static Double readDouble(@NonNull Cursor cursor, @NonNull String columnName) {
        int columnIndex = cursor.getColumnIndex(columnName);
        return hasValue(cursor, columnIndex)
                ? cursor.getDouble(columnIndex)
                : null;
    }

    public static int readInt(@NonNull Cursor cursor, @NonNull String columnName) {
        Integer count = readInteger(cursor, columnName);
        return nonNull(count) ? count : 0;
    }

    @Nullable
    public static Integer readInteger(@NonNull Cursor cursor, @NonNull String columnName) {
        int columnIndex = cursor.getColumnIndex(columnName);
        return hasValue(cursor, columnIndex)
                ? cursor.getInt(columnIndex)
                : null;
    }

    @Nullable
    public static Long readLong(@NonNull Cursor cursor, @NonNull String columnName) {
        int columnIndex = cursor.getColumnIndex(columnName);
        return hasValue(cursor, columnIndex)
                ? cursor.getLong(columnIndex)
                : null;
    }

    @Nullable
    public static String readString(@NonNull Cursor cursor, @NonNull String columnName) {
        int columnIndex = cursor.getColumnIndex(columnName);
        return hasValue(cursor, columnIndex)
                ? cursor.getString(columnIndex)
                : null;
    }

    private static boolean hasValue(Cursor cursor, int columnIndex) {
        return (columnIndex > -1) && !cursor.isNull(columnIndex);
    }
}
