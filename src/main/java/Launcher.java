import dto.Human;
import services.JsonService;

import java.time.LocalDate;
import java.time.Month;

public class Launcher {

    public static void main(String[] args) {
Human human = new Human("admin","rootovksi","ski",
        LocalDate.of(1956, Month.DECEMBER,30));
JsonService jsonService = JsonService.getInstance();
    String s = jsonService.toJson(human);
        System.out.println(s);
        human = jsonService.fromJson(s);
        System.out.println(human.toString());
    }
}
