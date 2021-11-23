package org.stonlexx.json.parse;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.stonlexx.json.JsonMap;
import org.stonlexx.json.JsonSerializable;
import org.stonlexx.json.JsonToken;
import org.stonlexx.json.exception.JsonParseException;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Pattern;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JsonParser {

    public static JsonParser newInstance() {
        return new JsonParser();
    }

    public String parse(@NonNull Object object) {
        return JsonToken.from(object.getClass()).toJsonFormat(this, object);
    }

    public <T> T parse(@NonNull InputStream json, Class<T> type) {
        try (Scanner scanner = new Scanner(json)) {

            if (scanner.hasNext()) {
                return parse(scanner.nextLine(), type);
            }
        }

        throw new JsonParseException("");
    }

    @SuppressWarnings("all")
    public <T> T parse(@NonNull String json, Class<T> type) {
        if (type == null) {
            return null;
        }

        JsonToken jsonToken = JsonToken.from(type);

        if (jsonToken == JsonToken.TIME) {
            try {
                Long timeMillis = (Long) JsonToken.fromAccessPrimitive(json, type);

                if (type.isAssignableFrom(Timestamp.class)) {
                    return (T) new Timestamp(timeMillis);
                }

                if (type.isAssignableFrom(Date.class)) {
                    return (T) new Date(timeMillis);
                }

                if (type.isAssignableFrom(Time.class)) {
                    return (T) new Time(timeMillis);
                }

            } catch (Exception exception) {
                throw new JsonParseException("not time");
            }
        }

        Object parsedPrimitive = PrimitiveParser.parsePrimitive(json);
        if (!parsedPrimitive.toString().equals(json)) {
            return (T) parsedPrimitive;
        }

        if (PrimitiveParser.isObject(json)) {

            if (jsonToken != JsonToken.OBJECT) {
                throw new JsonParseException("not object");
            }

            try {
                JsonSerializable instance = type.asSubclass(JsonSerializable.class).getDeclaredConstructor().newInstance();
                instance.deserialize(JsonMap.fromObjectJson(this, json, type));

                // TODO: Надо будет сделать так, чтобы несериализованные объекты парсились по field'ам

                return (T) instance;
            }
            catch (IllegalAccessException | InvocationTargetException | InstantiationException | NoSuchMethodException exception) {
                throw new JsonParseException("no empty constructor!");
            }
            catch (ClassCastException exception) {
                throw new JsonParseException(type + "is not JsonSerializable");
            }
        }

        return (T) JsonToken.fromAccessPrimitive(json, type);
    }

}
