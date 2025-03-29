import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        List<Hall> halls = new ArrayList<>();
        List<Event> events = new ArrayList<>();
        List<Ticket> tickets = new ArrayList<>();
        CinemaManager cinemaManager = new CinemaManager();
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
            System.out.println("5. Резервирай място (book)");
            System.out.println("6. Отмени резервация (unbook)");
            System.out.println("7. Покажи свободни места (freeseats)");
            System.out.println("8. Покажи заети места");
            System.out.println("9. Изход");
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

                    Hall selectedHall = halls.stream()
                            .filter(h -> h.getHallNumber() == hallNumber)
                            .findFirst()
                            .orElse(null);

                    if (selectedHall != null) {
                        events.add(new Event(name, date, selectedHall));
                        System.out.println("Представлението е добавено успешно!");
                    } else {
                        System.out.println("Зала с този номер не е намерена.");
                    }
                    break;

                case 2:
                    if (events.isEmpty()) {
                        System.out.println("Няма добавени представления.");
                    } else {
                        events.forEach(System.out::println);
                    }
                    break;

                case 3:
                    System.out.print("Въведете дата на представлението: ");
                    String ticketDate = scanner.nextLine();
                    System.out.print("Въведете име на представлението: ");
                    String ticketName = scanner.nextLine();

                    Event ticketEvent = findEvent(events, ticketDate, ticketName);
                    if (ticketEvent == null) {
                        System.out.println("Представлението не е намерено.");
                        break;
                    }

                    System.out.print("Въведете ред: ");
                    int row = scanner.nextInt();
                    System.out.print("Въведете място: ");
                    int seat = scanner.nextInt();

                    final Event finalTicketEvent = ticketEvent;
                    final int finalRow = row;
                    final int finalSeat = seat;

                    boolean isSeatAvailable = cinemaManager.isSeatFree(finalRow, finalSeat, finalTicketEvent) &&
                            tickets.stream().noneMatch(t ->
                                    t.getEvent().equals(finalTicketEvent) &&
                                            t.getRow() == finalRow &&
                                            t.getSeat() == finalSeat);

                    if (isSeatAvailable) {
                        String ticketCode = "TICKET-" + System.currentTimeMillis();
                        tickets.add(new Ticket(ticketCode, row, seat, ticketEvent));
                        System.out.println("Билетът е закупен успешно! Код: " + ticketCode);
                    } else {
                        System.out.println("Мястото е заето.");
                    }
                    break;

                case 4:
                    if (tickets.isEmpty()) {
                        System.out.println("Няма закупени билети.");
                    } else {
                        tickets.forEach(System.out::println);
                    }
                    break;

                case 5:
                    System.out.print("Въведете дата на представлението: ");
                    String bookDate = scanner.nextLine();
                    System.out.print("Въведете име на представлението: ");
                    String bookName = scanner.nextLine();

                    Event bookEvent = findEvent(events, bookDate, bookName);
                    if (bookEvent == null) {
                        System.out.println("Представлението не е намерено.");
                        break;
                    }

                    System.out.print("Въведете ред: ");
                    int bookRow = scanner.nextInt();
                    System.out.print("Въведете място: ");
                    int bookSeat = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Въведете бележка: ");
                    String note = scanner.nextLine();

                    final Event finalBookEvent = bookEvent;
                    final int finalBookRow = bookRow;
                    final int finalBookSeat = bookSeat;

                    boolean isSeatFree = cinemaManager.isSeatFree(finalBookRow, finalBookSeat, finalBookEvent) &&
                            tickets.stream().noneMatch(t ->
                                    t.getEvent().equals(finalBookEvent) &&
                                            t.getRow() == finalBookRow &&
                                            t.getSeat() == finalBookSeat);

                    if (isSeatFree) {
                        cinemaManager.addReservation(new Reservation(bookRow, bookSeat, bookEvent, note));
                        System.out.println("Мястото е резервирано успешно!");
                    } else {
                        System.out.println("Мястото е заето.");
                    }
                    break;

                case 6:
                    System.out.print("Въведете дата на представлението: ");
                    String unbookDate = scanner.nextLine();
                    System.out.print("Въведете име на представлението: ");
                    String unbookName = scanner.nextLine();
                    System.out.print("Въведете ред: ");
                    int unbookRow = scanner.nextInt();
                    System.out.print("Въведете място: ");
                    int unbookSeat = scanner.nextInt();

                    cinemaManager.cancelReservation(unbookDate, unbookName, unbookRow, unbookSeat);
                    break;

                case 7:
                    System.out.print("Въведете дата на представлението: ");
                    String freeSeatsDate = scanner.nextLine();
                    System.out.print("Въведете име на представлението: ");
                    String freeSeatsName = scanner.nextLine();

                    Event freeSeatsEvent = findEvent(events, freeSeatsDate, freeSeatsName);
                    if (freeSeatsEvent == null) {
                        System.out.println("Представлението не е намерено.");
                        break;
                    }

                    System.out.println("Свободни места за " + freeSeatsEvent.getName() + " на " + freeSeatsEvent.getDate() + ":");
                    Hall freeSeatsHall = freeSeatsEvent.getHall();
                    final Event finalFreeSeatsEvent = freeSeatsEvent;

                    for (int i = 1; i <= freeSeatsHall.getRows(); i++) {
                        final int currentRow = i;
                        for (int j = 1; j <= freeSeatsHall.getSeatsPerRow(); j++) {
                            final int currentSeat = j;
                            boolean isTaken = cinemaManager.isSeatTaken(currentRow, currentSeat, finalFreeSeatsEvent) ||
                                    tickets.stream().anyMatch(t ->
                                            t.getEvent().equals(finalFreeSeatsEvent) &&
                                                    t.getRow() == currentRow &&
                                                    t.getSeat() == currentSeat);

                            if (!isTaken) {
                                System.out.println("Ред " + currentRow + ", Място " + currentSeat);
                            }
                        }
                    }
                    break;

                case 8:
                    System.out.print("Въведете дата на представлението: ");
                    String occupiedDate = scanner.nextLine();
                    System.out.print("Въведете име на представлението: ");
                    String occupiedName = scanner.nextLine();

                    Event occupiedEvent = findEvent(events, occupiedDate, occupiedName);
                    if (occupiedEvent != null) {
                        cinemaManager.printOccupiedSeats(occupiedEvent);
                    } else {
                        System.out.println("Представлението не е намерено.");
                    }
                    break;

                case 9:
                    System.out.println("Излизане от програмата...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Невалидна опция. Моля, опитайте отново.");
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
