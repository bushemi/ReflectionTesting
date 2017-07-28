package services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.Human;
import services.jsonSerializationClasses.HumanSerialization;

public class JsonService {
    private static JsonService ourInstance = new JsonService();
    private Gson json ;

    public static JsonService getInstance() {
        return ourInstance;
    }

    private JsonService() {
        GsonBuilder builder = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(Human.class,new HumanSerialization("dd-MM-yyyy"));

        json = builder.create();
    }
    public String toJson(Object object){
        return new String(json.toJson(object));
    }
    public Human fromJson(String jsonString){

        Human human = json.fromJson(jsonString,Human.class);

        return human;
    }

}
