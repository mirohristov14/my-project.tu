import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        List<Hall> halls = new ArrayList<>();
        List<Event> events = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        halls.add(new Hall(1, 10, 20));
        halls.add(new Hall(2, 15, 25));
        halls.add(new Hall(3, 12, 18));
        halls.add(new Hall(4, 8, 15));
        halls.add(new Hall(5, 20, 30));

        while (true) {
            System.out.println("\nМеню:");
            System.out.println("1. Добави представление");
            System.out.println("2. Покажи всички представления");
            System.out.println("3. Изход");
            System.out.print("Избери опция: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Въведете име на представлението: ");
                    String name = scanner.nextLine();
                    System.out.print("Въведете дата (гггг-мм-дд): ");
                    String date = scanner.nextLine();
                    System.out.print("Въведете номер на залата: ");
                    int hallNumber = scanner.nextInt();

                    Hall selectedHall = null;
                    for (Hall hall : halls) {
                        if (hall.getHallNumber() == hallNumber) {
                            selectedHall = hall;
                            break;
                        }
                    }

                    if (selectedHall != null) {
                        events.add(new Event(name, date, selectedHall));
                        System.out.println("Представлението е добавено успешно!");
                    } else {
                        System.out.println("Зала с този номер не е намерена.");
                    }
                    break;

                case 2:
                    System.out.println("\nСписък на представления:");
                    for (Event event : events) {
                        System.out.println(event);
                    }
                    break;

                case 3:
                    System.out.println("Излизане от програмата...");
                    return;

                default:
                    System.out.println("Невалидна опция. Моля, опитайте отново.");
            }
        }
    }
}

