package services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import services.jsonSerializationClasses.CustomSerializer;

public class JsonService {
    private static JsonService ourInstance = new JsonService();


    public static JsonService getInstance() {
        return ourInstance;
    }

    private JsonService() {

    }

    private static <T> Gson init(Class<T> clazz) {
        GsonBuilder builder = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(clazz, new CustomSerializer());

        return builder.create();
    }

    public static String toJson(Object object) {
        Gson json = init(object.getClass());
        return new String(json.toJson(object));
    }

    public static <T> T fromJson(String jsonString, Class<T> clazz) {
        Gson json = init(clazz);

        T object;
        object = json.fromJson(jsonString, clazz);

        return object;
    }

}
