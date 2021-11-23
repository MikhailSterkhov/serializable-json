package org.stonlexx.json.parse;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.util.regex.Pattern;

@UtilityClass
public class PrimitiveParser {

    public static final Pattern STRING_PATTERN = Pattern.compile("\"(.+|)\"");
    public static final Pattern INT_PATTERN = Pattern.compile("[0-9]+");
    public static final Pattern DOUBLE_PATTERN = Pattern.compile("[0-9]+[.][0-9]+");
    public static final Pattern TIME_PATTERN = INT_PATTERN;

    public boolean isString(@NonNull String value) {
        return STRING_PATTERN.matcher(value).matches();
    }

    public boolean isInt(@NonNull String value) {
        return INT_PATTERN.matcher(value).matches();
    }

    public boolean isDouble(@NonNull String value) {
        return DOUBLE_PATTERN.matcher(value).matches();
    }

    public boolean isTime(@NonNull String value) {
        return TIME_PATTERN.matcher(value).matches();
    }

    public boolean isArray(@NonNull String value) {
        return value.startsWith("[") && value.endsWith("]");
    }

    public boolean isObject(@NonNull String value) {
        return value.startsWith("{") && value.endsWith("}");
    }

    public Object parsePrimitive(@NonNull String value) {
        if (isString(value)) {
            return value.substring(1, value.length() - 1);
        }

        else if (isInt(value)) {
            return Integer.parseInt(value);
        }

        else if (isDouble(value)) {
            return Double.parseDouble(value);
        }

        else if (isTime(value)) {
            return Long.parseLong(value);
        }

        else if (isArray(value)) {
            return value.substring(1, value.length() - 1).split(", ");
        }

        return value;
    }
}