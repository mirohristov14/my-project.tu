import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        FileManager fileManager = new FileManager("data.ser");

        CinemaData data = fileManager.loadData();
        if (data == null) {
            data = new CinemaData();
            data.halls.add(new Hall(1, 10, 20));
            data.halls.add(new Hall(2, 15, 25));
            data.halls.add(new Hall(3, 12, 18));
            data.halls.add(new Hall(4, 8, 15));
            data.halls.add(new Hall(5, 20, 30));
        }

        CinemaManager cinemaManager = data.cinemaManager;

        while (true) {
            System.out.println("\nМеню:");
            System.out.println("1. Добави представление");
            System.out.println("2. Покажи всички представления");
            System.out.println("3. Купи билет");
            System.out.println("4. Покажи всички билети");
            System.out.println("5. Резервирай място");
            System.out.println("6. Отмени резервация");
            System.out.println("7. Свободни места");
            System.out.println("8. Заети места");
            System.out.println("9. Запази в файл");
            System.out.println("10. Изтрий представление");
            System.out.println("11. Изход");
            System.out.print("Избери опция: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Име на представление: ");
                    String name = scanner.nextLine();
                    System.out.print("Дата (гггг-мм-дд): ");
                    String date = scanner.nextLine();
                    System.out.print("Номер на зала: ");
                    int hallNumber = scanner.nextInt();
                    scanner.nextLine();

                    Hall hall = data.halls.stream()
                            .filter(h -> h.getHallNumber() == hallNumber)
                            .findFirst()
                            .orElse(null);
                    if (hall != null) {
                        data.events.add(new Event(name, date, hall));
                        System.out.println("Успешно добавено.");
                    } else {
                        System.out.println("Няма такава зала.");
                    }
                    break;

                case 2:
                    if (data.events.isEmpty()) {
                        System.out.println("Няма представления.");
                    } else {
                        data.events.forEach(System.out::println);
                    }
                    break;

                case 3:
                    System.out.print("Дата: ");
                    String tDate = scanner.nextLine();
                    System.out.print("Име: ");
                    String tName = scanner.nextLine();
                    Event event = findEvent(data.events, tDate, tName);
                    if (event == null) {
                        System.out.println("Не е намерено.");
                        break;
                    }
                    System.out.print("Ред: ");
                    int row = scanner.nextInt();
                    System.out.print("Място: ");
                    int seat = scanner.nextInt();
                    scanner.nextLine();

                    boolean taken = data.tickets.stream().anyMatch(t ->
                            t.getEvent().equals(event) && t.getRow() == row && t.getSeat() == seat)
                            || cinemaManager.isSeatTaken(row, seat, event);

                    if (!taken) {
                        String code = "TICKET-" + System.currentTimeMillis();
                        data.tickets.add(new Ticket(code, row, seat, event));
                        System.out.println("Купен билет. Код: " + code);
                    } else {
                        System.out.println("Мястото е заето.");
                    }
                    break;

                case 4:
                    if (data.tickets.isEmpty()) {
                        System.out.println("Няма билети.");
                    } else {
                        data.tickets.forEach(System.out::println);
                    }
                    break;

                case 5:
                    System.out.print("Дата: ");
                    String bDate = scanner.nextLine();
                    System.out.print("Име: ");
                    String bName = scanner.nextLine();
                    Event bookEvent = findEvent(data.events, bDate, bName);
                    if (bookEvent == null) {
                        System.out.println("Не е намерено.");
                        break;
                    }
                    System.out.print("Ред: ");
                    int bRow = scanner.nextInt();
                    System.out.print("Място: ");
                    int bSeat = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Бележка: ");
                    String note = scanner.nextLine();

                    boolean free = data.tickets.stream().noneMatch(t -> t.getEvent().equals(bookEvent)
                            && t.getRow() == bRow && t.getSeat() == bSeat)
                            && cinemaManager.isSeatFree(bRow, bSeat, bookEvent);

                    if (free) {
                        cinemaManager.addReservation(new Reservation(bRow, bSeat, bookEvent, note));
                        System.out.println("Резервация успешна.");
                    } else {
                        System.out.println("Мястото е заето.");
                    }
                    break;

                case 6:
                    System.out.print("Дата: ");
                    String uDate = scanner.nextLine();
                    System.out.print("Име: ");
                    String uName = scanner.nextLine();
                    System.out.print("Ред: ");
                    int uRow = scanner.nextInt();
                    System.out.print("Място: ");
                    int uSeat = scanner.nextInt();
                    scanner.nextLine();
                    cinemaManager.cancelReservation(uDate, uName, uRow, uSeat);
                    break;

                case 7:
                    System.out.print("Дата: ");
                    String fDate = scanner.nextLine();
                    System.out.print("Име: ");
                    String fName = scanner.nextLine();
                    Event foundEvent = findEvent(data.events, fDate, fName);
                    if (foundEvent == null) {
                        System.out.println("Не е намерено.");
                        break;
                    }
                    Hall h = foundEvent.getHall();
                    for (int i = 1; i <= h.getRows(); i++) {
                        for (int j = 1; j <= h.getSeatsPerRow(); j++) {
                            final int currentRow = i;
                            final int currentSeat = j;
                            boolean busy = cinemaManager.isSeatTaken(currentRow, currentSeat, foundEvent)
                                    || data.tickets.stream().anyMatch(t -> t.getEvent().equals(foundEvent)
                                    && t.getRow() == currentRow && t.getSeat() == currentSeat);

                            if (!busy) {
                                System.out.println("Ред " + i + ", Място " + j);
                            }
                        }
                    }
                    break;

                case 8:
                    System.out.print("Дата: ");
                    String oDate = scanner.nextLine();
                    System.out.print("Име: ");
                    String oName = scanner.nextLine();
                    Event oEvent = findEvent(data.events, oDate, oName);
                    if (oEvent != null) {
                        cinemaManager.printOccupiedSeats(oEvent);
                    } else {
                        System.out.println("Не е намерено.");
                    }
                    break;

                case 9:
                    fileManager.saveData(data);
                    System.out.println("Данните са записани.");
                    break;

                case 10:
                    System.out.print("Дата на представлението за изтриване: ");
                    String delDate = scanner.nextLine();
                    System.out.print("Име на представлението за изтриване: ");
                    String delName = scanner.nextLine();

                    Event eventToRemove = findEvent(data.events, delDate, delName);
                    if (eventToRemove != null) {
                        System.out.print("Сигурни ли сте, че искате да изтриете \"" + delName + "\"? (y/n): ");
                        String confirm = scanner.nextLine();

                        if (confirm.equalsIgnoreCase("y")) {
                            data.tickets.removeIf(ticket -> ticket.getEvent().equals(eventToRemove));
                            cinemaManager.getAllReservations().removeIf(res -> res.getEvent().equals(eventToRemove));
                            data.events.remove(eventToRemove);
                            System.out.println("Представлението е изтрито успешно!");
                        } else {
                            System.out.println("Изтриването е отменено.");
                        }
                    } else {
                        System.out.println("Представлението не е намерено.");
                    }
                    break;

                case 11:
                    System.out.print("Запазване на промените преди изход? (y/n): ");
                    String saveChoice = scanner.nextLine();
                    if (saveChoice.equalsIgnoreCase("y")) {
                        fileManager.saveData(data);
                        System.out.println("Данните са запазени успешно!");
                    }
                    System.out.println("Излизане от програмата...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Невалидна опция.");
            }
        }
    }

    private static Event findEvent(List<Event> events, String date, String name) {
        return events.stream()
                .filter(e -> e.getDate().equals(date) && e.getName().equals(name))
                .findFirst()
                .orElse(null);
    }
}
