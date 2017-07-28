import dto.Human;
import services.AnnotationService;
import services.JsonService;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

public class Launcher {

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
Human human = new Human("admin","rootovski","ski",
        LocalDate.of(1956, Month.DECEMBER,30));

        AnnotationService annotationService =
                new AnnotationService();
        annotationService.getListOfMethods(human);
//        List<Field> annotationsList = annotationService.getListOfAnnotations(human);
//annotationsList.forEach(annotationService::getNames);

        System.out.println(human);
    }




    public static void jsonTesting(Human human) {

JsonService jsonService = JsonService.getInstance();
        System.out.println(human);
    String s = jsonService.toJson(human);
        System.out.println(s);
        human = jsonService.fromJson(s);
        System.out.println(human.toString());
    }
}
