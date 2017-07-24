package services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.Human;

public class JsonService {
    private static JsonService ourInstance = new JsonService();
    private Gson json = new GsonBuilder().create();

    public static JsonService getInstance() {
        return ourInstance;
    }

    private JsonService() {
    }
    public String toJson(Object object){
        return new String(json.toJson(object));
    }
    public Human fromJson(String jsonString){

        Human human = json.fromJson(jsonString,Human.class);

        return human;
    }

}
