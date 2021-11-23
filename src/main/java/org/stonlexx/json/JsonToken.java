package org.stonlexx.json;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.stonlexx.json.exception.JsonParseException;
import org.stonlexx.json.parse.JsonParser;

import java.lang.reflect.Array;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Getter
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public enum JsonToken {

    STRING(new Class[]{String.class}),

    INT(new Class[]{int.class, Integer.class}),

    DOUBLE(new Class[]{double.class, Double.class}),

    ARRAY(new Class[]{Array.class, Collection.class}),

    TIME(new Class[]{Timestamp.class, Date.class, Time.class}),

    OBJECT(new Class[]{Object.class}),
    ;

    public static final String JSON_JOIN_SPLITTER   = ", ";
    public static final String OBJECT_JSON_FORMAT   = "{%s}";
    public static final JsonToken[] VALUES          = JsonToken.values();

    @NonNull
    Class<?>[] primitives;


    public static JsonToken from(@NonNull Class<?> primitive) {
        for (JsonToken jsonToken : VALUES) {
            for (Class<?> tokenPrimitive : jsonToken.primitives) {

                if (primitive == tokenPrimitive || primitive.isAssignableFrom(tokenPrimitive)) {
                    return jsonToken;
                }
            }
        }

        return OBJECT;
    }

    public static Object fromAccessPrimitive(@NonNull String value, @NonNull Class<?> type) {
        JsonToken jsonToken = JsonToken.from(type);
        switch (jsonToken) {

            case STRING: {
                return value;
            }

            case INT: {
                return Integer.parseInt(value);
            }

            case DOUBLE: {
                return Double.parseDouble(value);
            }

            case TIME: {
                return Long.parseLong(value);
            }

            case ARRAY: {
                return value.substring(1, value.length() - 1).split(JSON_JOIN_SPLITTER);
            }
        }

        return null;
    }

    public String toJsonFormat(@NonNull JsonParser jsonParser, @NonNull Object object) {
        if (this == OBJECT) {
            if (!(object instanceof JsonSerializable)) {
                throw new JsonParseException(object.getClass() + " is not JsonSerializable");
            }

            JsonMap jsonMap = new JsonMap();
            ((JsonSerializable) object).serialize(jsonMap);

            String value = jsonMap.keySet()
                    .stream()
                    .map(key -> key + ":" + jsonParser.parse(jsonMap.get(key)))
                    .collect(Collectors.joining(", "));

            return String.format(OBJECT_JSON_FORMAT, value);
        }

        return formatJsonElement(object);
    }

    private String formatJsonElement(Object object) {
        if (this == STRING) {
            return "\"" + object.toString() + "\"";
        }

        return object.toString();
    }

}
