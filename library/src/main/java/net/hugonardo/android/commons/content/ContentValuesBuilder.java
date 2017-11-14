package net.hugonardo.android.commons.content;

import android.content.ContentValues;
import android.support.annotation.NonNull;
import java.util.Calendar;
import java.util.Date;

public class ContentValuesBuilder {

    private final boolean mOnlyNonNull;

    private final ContentValues mValues;

    public static ContentValuesBuilder create(boolean onlyNonNull) {
        return new ContentValuesBuilder(onlyNonNull);
    }

    public static ContentValuesBuilder create(@NonNull ContentValues initialValues, boolean onlyNonNull) {
        return new ContentValuesBuilder(initialValues, onlyNonNull);
    }

    private ContentValuesBuilder(boolean onlyNonNull) {
        this(new ContentValues(), onlyNonNull);
    }

    private ContentValuesBuilder(@NonNull ContentValues initialValues, boolean onlyNonNull) {
        mValues = initialValues;
        mOnlyNonNull = onlyNonNull;
    }

    public ContentValues build() {
        return mValues;
    }

    public ContentValuesBuilder put(String key, String value) {
        if (isToPut(value)) {
            mValues.put(key, value);
        }
        return this;
    }

    public ContentValuesBuilder put(String key, Long value) {
        if (isToPut(value)) {
            mValues.put(key, value);
        }
        return this;
    }

    public ContentValuesBuilder put(String key, Boolean value) {
        if (isToPut(value)) {
            mValues.put(key, value);
        }
        return this;
    }

    public ContentValuesBuilder put(String key, Date value) {
        if (isToPut(value)) {
            Long timeMillis = value != null ? value.getTime() : null;
            mValues.put(key, timeMillis);
        }
        return this;
    }

    public ContentValuesBuilder put(String key, Calendar value) {
        if (isToPut(value)) {
            Long timeInMillis = value != null ? value.getTimeInMillis() : null;
            mValues.put(key, timeInMillis);
        }
        return this;
    }

    public ContentValuesBuilder put(String key, Integer value) {
        if (isToPut(value)) {
            mValues.put(key, value);
        }
        return this;
    }

    public ContentValuesBuilder put(String key, Double value) {
        if (isToPut(value)) {
            mValues.put(key, value);
        }
        return this;
    }

    public ContentValuesBuilder put(String key, Enum value) {
        if (isToPut(value)) {
            mValues.put(key, value.name());
        }
        return this;
    }

    public ContentValuesBuilder putAll(ContentValues other) {
        mValues.putAll(other);
        return this;
    }

    public ContentValuesBuilder putNull(String key) {
        if (isToPut(null)) {
            mValues.putNull(key);
        }
        return this;
    }

    private boolean isToPut(Object value) {
        return !mOnlyNonNull || value != null;
    }
}
