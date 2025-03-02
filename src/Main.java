import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Създаване на списък за залите
        List<Hall> halls = new ArrayList<>();

        // Добавяне на няколко зали
        halls.add(new Hall(1, 10, 20));  // Зал 1: 10 реда, 20 места на ред
        halls.add(new Hall(2, 15, 25));  // Зал 2: 15 реда, 25 места на ред
        halls.add(new Hall(3, 12, 18));  // Зал 3: 12 реда, 18 места на ред

        // Показване на информация за всички зали
        System.out.println("Информация за залите:");
        for (Hall hall : halls) {
            System.out.println(hall);
        }
    }
}
