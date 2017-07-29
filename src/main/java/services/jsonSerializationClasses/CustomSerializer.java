package services.jsonSerializationClasses;

import com.google.gson.*;
import services.AnnotationService;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomSerializer implements JsonSerializer, JsonDeserializer {
    private AnnotationService annotationService = new AnnotationService();
    private Map<String, String> map = new HashMap();
    private List<String> namesList = new ArrayList<>();

    @Override
    public JsonElement serialize(Object src, Type typeOfSrc, JsonSerializationContext context) {
        System.out.println("ghjgk");
        JsonObject jsonObject = new JsonObject();
        namesList = annotationService.parseFieldNamesToJson(src);
        map = annotationService.parseFieldValuesToJson(src);
        namesList.forEach((e) -> jsonObject.addProperty(e, map.get(e)));
        jsonObject.addProperty(namesList.get(0), map.get(namesList.get(0)));
        return jsonObject;
    }

    @Override
    public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return null;
    }
}
