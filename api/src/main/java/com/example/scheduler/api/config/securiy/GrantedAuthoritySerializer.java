package com.example.scheduler.api.config.securiy;

import com.google.gson.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.lang.reflect.Type;

public class GrantedAuthoritySerializer implements JsonDeserializer<GrantedAuthority>, JsonSerializer<GrantedAuthority> {
    private static final String ROLE_PREFIX = "ROLE_";

    @Override
    public GrantedAuthority deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String role = json.getAsJsonObject().get("role").getAsString();

        return new SimpleGrantedAuthority(role);
    }

    @Override
    public JsonElement serialize(GrantedAuthority src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.addProperty("role", src.getAuthority());
        return object;
    }
}
