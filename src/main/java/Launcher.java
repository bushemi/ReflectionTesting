import dto.Animal;
import dto.Human;
import services.JsonService;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.Month;

public class Launcher {

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        Human human = new Human(null, "rootovski", "ski",
                LocalDate.of(1956, Month.DECEMBER,30));
        Animal animal = new Animal(15, "barsik");
//        AnnotationService annotationService =
//                new AnnotationService();
//annotationService.parseFieldValuesToJson(human);
        jsonTesting(human);
        jsonTesting(animal);
//        System.out.println(human);

    }


    public static void jsonTesting(Object human) {

        JsonService jsonService = JsonService.getInstance();
        System.out.println(human);
        String s = JsonService.toJson(human);
        System.out.println(s);
//        human = jsonService.fromJson(s);
        System.out.println(human.toString());
    }
}
