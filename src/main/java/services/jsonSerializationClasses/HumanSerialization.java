package services.jsonSerializationClasses;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import dto.Human;
import services.AnnotationService;

import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HumanSerialization extends TypeAdapter<Human> {
    private DateTimeFormatter formatter;
    private String serializedName ="fun";
    private String deserializedName ="hobby";
    private AnnotationService service ;
    private List<Field> fieldList;
    private List<String> namesList;

    public HumanSerialization(String dateFormatPattern) {

        service = new AnnotationService();
    }
private void init(Human human){
//    namesList = service.getNamesList();

    String dateFormatPattern = null;//service.getLocalDateFormatPattern();
    formatter = DateTimeFormatter.ofPattern(dateFormatPattern);
}

    @Override
    public void write(JsonWriter out, Human value) throws IOException {
        if (value == null) {
            out.nullValue();
            return;
        }

        out.beginObject();
        out.name("firstName");
        out.value(value.getFirstName());
        out.name("lastName");
        out.value(value.getLastName());
        out.name("fun");
        out.value(value.getHobby());
        out.name("birthDate");
        out.value(value.getBirthDate().format(formatter));
        out.endObject();
    }


    @Override
    public Human read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
        in.nextNull();
        System.out.println("gh");
        return null;
    }
        Map<String,String> humanMap =  new HashMap<>();
        in.beginObject();
        System.out.println(in.toString());
        while (in.hasNext()) {
            humanMap.put(in.nextName(), in.nextString());
        }
        Human human = new Human(humanMap.get("firstName")
                ,humanMap.get("lastName")
                ,humanMap.get("fun"),(LocalDate.parse(humanMap.get("birthDate"), formatter)));



        in.endObject();
        return human;
    }
}
