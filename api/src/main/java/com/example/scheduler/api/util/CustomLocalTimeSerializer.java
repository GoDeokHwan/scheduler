package com.example.scheduler.api.util;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalTime;
import java.util.Optional;
public class CustomLocalTimeSerializer implements JsonSerializer<LocalTime>, JsonDeserializer<LocalTime> {
    @Override
    public JsonElement serialize(LocalTime src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(DateUtils.toTime(src));
    }

    @Override
    public LocalTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return Optional.ofNullable(json.getAsString())
                .map((DateUtils::newAsTime))
                .orElse(null);
    }
}
