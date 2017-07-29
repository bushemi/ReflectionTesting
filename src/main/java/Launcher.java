import dto.Animal;
import dto.Human;
import dto.UserProfile;
import services.JsonService;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.Month;

public class Launcher {

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        Human human = new Human();
        human.setBirthDate(LocalDate.of(1956, Month.DECEMBER, 30));
        human.setFirstName("admin");
        human.setLastName("rootovski");
        human.setHobby("ski");

        Animal animal = new Animal(15, "barsik");

        UserProfile user = new UserProfile(149, "admin", "12345", "bigboss8@mail.ru");
        jsonTesting(human);
        jsonTesting(animal);
        jsonTesting(user);


    }


    public static void jsonTesting(Object human) {

        JsonService jsonService = JsonService.getInstance();
        System.out.println(human);
        String s = JsonService.toJson(human);
        System.out.println(s);
        human = JsonService.fromJson(s, human.getClass());
        System.out.println(human.toString());
    }
}
