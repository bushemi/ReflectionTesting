package services.jsonSerializationClasses;

import com.google.gson.*;
import services.AnnotationService;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomSerializer<T> implements JsonSerializer<T>, JsonDeserializer<T> {
    private AnnotationService annotationService = new AnnotationService();
    private Map<String, String> map = new HashMap();
    private List<String> namesList = new ArrayList<>();

    @Override
    public JsonElement serialize(T src, Type typeOfSrc, JsonSerializationContext context) {
        System.out.println("serialization start");
        JsonObject jsonObject = new JsonObject();
        namesList = annotationService.parseFieldNamesToJson(src.getClass());
        map = annotationService.parseFieldValuesToJson(src);
        namesList.forEach((e) -> jsonObject.addProperty(e, map.get(e)));
        jsonObject.addProperty(namesList.get(0), map.get(namesList.get(0)));
        return jsonObject;
    }

    @Override
    public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        System.out.println("deserialization start");
        Class clazz = null;
        T object = null;
        try {
            clazz = Class.forName(typeOfT.getTypeName());
            object = (T) clazz.newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        namesList = annotationService.parseFieldNamesToJson(clazz);
        namesList.forEach((e) -> map.put(e, json.getAsJsonObject().get(e).getAsString()));

        T finalObject = object;
        namesList.forEach((e) -> {
            try {
                annotationService.inject(e, map.get(e), finalObject);
            } catch (NoSuchFieldException e1) {
                e1.printStackTrace();
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
            }
        });

// парсю объект на поля и парсю текст на названия полей и значения полей
//                пихаю значения в поля
        return finalObject;
    }

}
