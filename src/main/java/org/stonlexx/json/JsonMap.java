package org.stonlexx.json;

import lombok.NonNull;
import lombok.SneakyThrows;
import org.stonlexx.json.exception.JsonParseException;
import org.stonlexx.json.parse.JsonParser;
import org.stonlexx.json.parse.PrimitiveParser;

import java.util.Map;
import java.util.TreeMap;

public final class JsonMap extends TreeMap<String, Object>
        implements Map<String, Object> {

    @SneakyThrows
    private static Class<?> getObjectValueType(@NonNull Class<?> objectType, @NonNull String key) {
        Object instance = objectType.getDeclaredConstructor().newInstance();

        JsonMap jsonMap = new JsonMap();
        ((JsonSerializable) instance).serialize(jsonMap);

        return jsonMap.containsKey(key) ? jsonMap.get(key).getClass() : null;
    }

    public static JsonMap fromObjectJson(@NonNull JsonParser jsonParser, @NonNull String json, @NonNull Class<?> type) {
        if (!PrimitiveParser.isObject(json)) {
            throw new JsonParseException("not object");
        }

        JsonMap jsonMap = new JsonMap();

        for (String field : json.substring(1, json.length() - 1).split(JsonToken.JSON_JOIN_SPLITTER)) {
            String[] fieldData = field.split(":");

            String key = fieldData[0];
            String value = fieldData[1];

            if (PrimitiveParser.isObject(value)) {
                jsonMap.put(key, jsonParser.parse(value, getObjectValueType(type, key)));
            }

            jsonMap.put(key, PrimitiveParser.parsePrimitive(value));
        }

        return jsonMap;
    }

    @SuppressWarnings("unchecked")
    public <T> T get(@NonNull String key, @NonNull Class<T> type) {
        return (T) get(key);
    }

    public <T> T get(@NonNull String key) {
        return get(key);
    }

}
