import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        List<Hall> halls = new ArrayList<>();
        List<Event> events = new ArrayList<>();
        List<Ticket> tickets = new ArrayList<>();
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
            System.out.println("3. Купи билет");
            System.out.println("4. Покажи всички билети");
            System.out.println("5. Изход");
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
                        Event newEvent = new Event(name, date, selectedHall);
                        events.add(newEvent);
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
                    System.out.print("Въведете дата на представлението: ");
                    String ticketDate = scanner.nextLine();
                    System.out.print("Въведете име на представлението: ");
                    String ticketName = scanner.nextLine();
                    System.out.print("Въведете ред: ");
                    int row = scanner.nextInt();
                    System.out.print("Въведете място: ");
                    int seat = scanner.nextInt();

                    Event selectedEvent = null;
                    for (Event event : events) {
                        if (event.getDate().equals(ticketDate) && event.getName().equals(ticketName)) {
                            selectedEvent = event;
                            break;
                        }
                    }

                    if (selectedEvent != null) {
                        String ticketCode = "TICKET-" + System.currentTimeMillis();
                        Ticket newTicket = new Ticket(ticketCode, row, seat, selectedEvent);
                        tickets.add(newTicket);
                        System.out.println("Билетът е закупен успешно! Код: " + ticketCode);
                    } else {
                        System.out.println("Представлението не е намерено.");
                    }
                    break;

                case 4:
                    System.out.println("\nСписък на билети:");
                    for (Ticket ticket : tickets) {
                        System.out.println(ticket);
                    }
                    break;

                case 5:
                    System.out.println("Излизане от програмата...");
                    return;

                default:
                    System.out.println("Невалидна опция. Моля, опитайте отново.");
            }
        }
    }
}
