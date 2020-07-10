package fr.syrows.modernshop.utils.converters;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class ConvertibleAdapter<T extends Convertible<?>> implements JsonDeserializer<T>, JsonSerializer<T> {

    private static final String CLASS_NAME_PROPERTY = "className";

    @Override
    public T deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {

        JsonObject object = element.getAsJsonObject();
        String className = object.get(CLASS_NAME_PROPERTY).getAsString();

        Class<T> _class = this.getClass(className);

        return context.deserialize(object, _class);
    }

    @Override
    public JsonElement serialize(T element, Type type, JsonSerializationContext context) {

        Class<?> _class = element.getType();

        JsonObject object = context.serialize(element).getAsJsonObject();
        object.addProperty(CLASS_NAME_PROPERTY, _class.getName());

        return object;
    }

    @SuppressWarnings("unchecked")
    private Class<T> getClass(String className) {

        try { return (Class<T>) Class.forName(className);
        } catch (ClassNotFoundException e) { throw new JsonParseException(e); }
    }
}
