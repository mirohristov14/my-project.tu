import java.util.*;
import java.io.*;

/**
 * Главен клас на системата за управление на кино.
 * <p>
 * Този клас съдържа основния метод {@code main}, който стартира конзолното приложение,
 * приема и обработва команди от потребителя. Командите позволяват работа с файлове
 * (open, save, saveas, close), както и операции свързани с киното (add_event, show_events,
 * buy_ticket, reserve_ticket, cancel_reservation и др.).
 * </p>
 *
 * <p>
 * Поддържани основни команди:
 * <ul>
 *     <li><b>open &lt;filename&gt;</b> – отваря файл със записани събития</li>
 *     <li><b>save</b> – записва текущите данни във вече отворен файл</li>
 *     <li><b>saveas &lt;filename&gt;</b> – записва текущите данни в нов файл</li>
 *     <li><b>close</b> – затваря текущия файл</li>
 *     <li><b>help</b> – показва списък с поддържаните команди</li>
 *     <li><b>exit</b> – излиза от програмата</li>
 *     <li><b>add_event</b>, <b>show_events</b>, <b>buy_ticket</b>, <b>reserve_ticket</b>,
 *         <b>cancel_reservation</b> и др. – специфични за управлението на киното</li>
 * </ul>
 * </p>
 *
 * @param args аргументи от командния ред (не се използват)
 */
public class Main {
    private static CinemaData data;
    private static FileManager fileManager;
    private static String openedFilename;
    private static boolean fileOpened = false;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("> ");
            String command = scanner.nextLine().trim();

            if (command.equalsIgnoreCase("exit")) {
                if (fileOpened) {
                    System.out.print("Искате ли да запазите промените преди изход? (y/n): ");
                    String choice = scanner.nextLine();
                    if (choice.equalsIgnoreCase("y")) {
                        save();
                    }
                }
                System.out.println("Излизане от програмата...");
                break;
            }

            switch (getBaseCommand(command)) {
                case "open":
                    open(command);
                    break;
                case "save":
                    save();
                    break;
                case "saveas":
                    saveAs(command);
                    break;
                case "close":
                    close();
                    break;
                case "help":
                    showHelp();
                    break;
                default:
                    if (!fileOpened) {
                        System.out.println("Няма отворен файл. Използвайте командата 'open <файл>'.");
                    } else {
                        executeCinemaCommand(command, scanner);
                    }
                    break;
            }
        }
        scanner.close();
    }
    private static void open(String command) {
        if (fileOpened) {
            System.out.println("Вече има отворен файл. Моля, първо го затворете с 'close'.");
            return;
        }
        String[] parts = command.split("\\s+", 2);
        if (parts.length < 2) {
            System.out.println("Моля, въведете име на файл: open <файл>");
            return;
        }
        openedFilename = parts[1];
        fileManager = new FileManager(openedFilename);
        data = fileManager.loadData();
        if (data == null) {
            data = new CinemaData();
            data.halls.add(new Hall(1, 10, 20));
            data.halls.add(new Hall(2, 15, 25));
            data.halls.add(new Hall(3, 12, 18));
            data.halls.add(new Hall(4, 8, 15));
            data.halls.add(new Hall(5, 20, 30));
        }
        fileOpened = true;
        System.out.println("Файлът е отворен успешно: " + openedFilename);
    }

    private static void save() {
        if (!fileOpened) {
            System.out.println("Няма отворен файл за записване.");
            return;
        }
        fileManager.saveData(data);
        System.out.println("Данните са записани успешно във файл: " + openedFilename);
    }

    private static void saveAs(String command) {
        if (!fileOpened) {
            System.out.println("Няма отворен файл за записване.");
            return;
        }
        String[] parts = command.split("\\s+", 2);
        if (parts.length < 2) {
            System.out.println("Моля, въведете име на нов файл: saveas <нов файл>");
            return;
        }
        String newFilename = parts[1];
        FileManager newFileManager = new FileManager(newFilename);
        newFileManager.saveData(data);
        System.out.println("Данните са записани успешно в нов файл: " + newFilename);
    }

    private static void close() {
        if (!fileOpened) {
            System.out.println("Няма отворен файл за затваряне.");
            return;
        }
        data = null;
        fileManager = null;
        openedFilename = null;
        fileOpened = false;
        System.out.println("Файлът беше затворен успешно.");
    }

    private static void showHelp() {
        System.out.println("Налични команди:");
        System.out.println("open <файл>    - Отвори файл");
        System.out.println("save           - Запази файл");
        System.out.println("saveas <файл>  - Запази в нов файл");
        System.out.println("close          - Затвори отворения файл");
        System.out.println("exit           - Излез от програмата");
        System.out.println("add_event      - Добави представление");
        System.out.println("show_events    - Покажи всички представления");
        System.out.println("buy_ticket     - Купи билет");
        System.out.println("show_tickets   - Покажи всички билети");
        System.out.println("book_seat      - Резервирай място");
        System.out.println("cancel_reservation - Отмени резервация");
        System.out.println("free_seats     - Показване на свободни места");
        System.out.println("occupied_seats - Показване на заети места");
        System.out.println("show_reservations - Показване на всички резервации");
        System.out.println("check_ticket   - Провери код на билет");
        System.out.println("report         - Генерирай справка за продажби");
        System.out.println("delete_event   - Изтрий представление");
    }

    private static String getBaseCommand(String command) {
        if (command.contains(" ")) {
            return command.substring(0, command.indexOf(" ")).toLowerCase();
        }
        return command.toLowerCase();
    }

    private static void executeCinemaCommand(String command, Scanner scanner) {
        switch (command) {
            case "add_event":
                addEvent(scanner);
                break;
            case "show_events":
                showEvents();
                break;
            case "buy_ticket":
                buyTicket(scanner);
                break;
            case "show_tickets":
                showTickets();
                break;
            case "book_seat":
                bookSeat(scanner);
                break;
            case "cancel_reservation":
                cancelReservation(scanner);
                break;
            case "free_seats":
                showFreeSeats(scanner);
                break;
            case "occupied_seats":
                showOccupiedSeats(scanner);
                break;
            case "show_reservations":
                showReservations(scanner);
                break;
            case "check_ticket":
                checkTicket(scanner);
                break;
            case "report":
                generateReport(scanner);
                break;
            case "delete_event":
                deleteEvent(scanner);
                break;
            default:
                System.out.println("Невалидна команда. Използвайте 'help' за списък.");
        }
    }

    private static void addEvent(Scanner scanner) {
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

        boolean exists = data.events.stream()
                .anyMatch(e -> e.getDate().equals(date) && e.getHall().getHallNumber() == hallNumber);

        if (hall != null && !exists) {
            data.events.add(new Event(name, date, hall));
            System.out.println("Успешно добавено представление.");
        } else if (exists) {
            System.out.println("В тази зала вече има представление на тази дата.");
        } else {
            System.out.println("Няма такава зала.");
        }
    }

    private static void showEvents() {
        if (data.events.isEmpty()) {
            System.out.println("Няма представления.");
        } else {
            data.events.forEach(System.out::println);
        }
    }

    private static void buyTicket(Scanner scanner) {
        System.out.print("Дата: ");
        String date = scanner.nextLine();
        System.out.print("Име на представление: ");
        String name = scanner.nextLine();

        Event event = findEvent(data.events, date, name);
        if (event == null) {
            System.out.println("Представление не е намерено.");
            return;
        }

        System.out.print("Ред: ");
        int row = scanner.nextInt();
        System.out.print("Място: ");
        int seat = scanner.nextInt();
        scanner.nextLine();

        boolean taken = data.tickets.stream().anyMatch(t ->
                t.getEvent().equals(event) && t.getRow() == row && t.getSeat() == seat)
                || data.cinemaManager.isSeatTaken(row, seat, event);

        if (!taken) {
            String code = generateTicketCode(event, row, seat);
            data.tickets.add(new Ticket(code, row, seat, event));
            System.out.println("Купен билет. Код: " + code);
        } else {
            System.out.println("Мястото е заето.");
        }
    }

    private static void showTickets() {
        if (data.tickets.isEmpty()) {
            System.out.println("Няма закупени билети.");
        } else {
            data.tickets.forEach(System.out::println);
        }
    }

    private static void bookSeat(Scanner scanner) {
        System.out.print("Дата: ");
        String date = scanner.nextLine();
        System.out.print("Име на представление: ");
        String name = scanner.nextLine();

        Event event = findEvent(data.events, date, name);
        if (event == null) {
            System.out.println("Представление не е намерено.");
            return;
        }

        System.out.print("Ред: ");
        int row = scanner.nextInt();
        System.out.print("Място: ");
        int seat = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Бележка: ");
        String note = scanner.nextLine();

        boolean free = data.tickets.stream().noneMatch(t ->
                t.getEvent().equals(event) && t.getRow() == row && t.getSeat() == seat)
                && data.cinemaManager.isSeatFree(row, seat, event);

        if (free) {
            data.cinemaManager.addReservation(new Reservation(row, seat, event, note));
            System.out.println("Успешна резервация.");
        } else {
            System.out.println("Мястото е заето.");
        }
    }

    private static void cancelReservation(Scanner scanner) {
        System.out.print("Дата: ");
        String date = scanner.nextLine();
        System.out.print("Име на представление: ");
        String name = scanner.nextLine();
        System.out.print("Ред: ");
        int row = scanner.nextInt();
        System.out.print("Място: ");
        int seat = scanner.nextInt();
        scanner.nextLine();

        data.cinemaManager.cancelReservation(date, name, row, seat);
    }

    private static void showFreeSeats(Scanner scanner) {
        System.out.print("Дата: ");
        String date = scanner.nextLine();
        System.out.print("Име на представление: ");
        String name = scanner.nextLine();

        Event event = findEvent(data.events, date, name);
        if (event == null) {
            System.out.println("Представление не е намерено.");
            return;
        }

        Hall hall = event.getHall();
        for (int i = 1; i <= hall.getRows(); i++) {
            for (int j = 1; j <= hall.getSeatsPerRow(); j++) {
                final int currentRow = i;
                final int currentSeat = j;
                boolean busy = data.cinemaManager.isSeatTaken(currentRow, currentSeat, event)
                        || data.tickets.stream().anyMatch(t -> t.getEvent().equals(event)
                        && t.getRow() == currentRow && t.getSeat() == currentSeat);

                if (!busy) {
                    System.out.println("Ред " + i + ", Място " + j);
                }
            }
        }
    }

    private static void showOccupiedSeats(Scanner scanner) {
        System.out.print("Дата: ");
        String date = scanner.nextLine();
        System.out.print("Име на представление: ");
        String name = scanner.nextLine();

        Event event = findEvent(data.events, date, name);
        if (event != null) {
            data.cinemaManager.printOccupiedSeats(event);
        } else {
            System.out.println("Представление не е намерено.");
        }
    }

    private static void showReservations(Scanner scanner) {
        System.out.print("Дата (празно за всички): ");
        String dateFilter = scanner.nextLine();
        System.out.print("Име на представление (празно за всички): ");
        String nameFilter = scanner.nextLine();

        data.cinemaManager.getAllReservations().stream()
                .filter(r -> (dateFilter.isEmpty() || r.getEvent().getDate().equals(dateFilter)) &&
                        (nameFilter.isEmpty() || r.getEvent().getName().equals(nameFilter)))
                .forEach(System.out::println);
    }

    private static void checkTicket(Scanner scanner) {
        System.out.print("Въведи код на билет: ");
        String code = scanner.nextLine();
        Optional<Ticket> ticket = data.tickets.stream()
                .filter(t -> t.getCode().equals(code))
                .findFirst();
        if (ticket.isPresent()) {
            Ticket t = ticket.get();
            System.out.println("Билетът е валиден: Ред " + t.getRow() + ", Място " + t.getSeat());
        } else {
            System.out.println("Невалиден код на билет!");
        }
    }

    private static void generateReport(Scanner scanner) {
        System.out.print("Начална дата (гггг-мм-дд): ");
        String from = scanner.nextLine();
        System.out.print("Крайна дата (гггг-мм-дд): ");
        String to = scanner.nextLine();
        System.out.print("Номер на зала (празно за всички): ");
        String hallInput = scanner.nextLine();

        String finalFrom = from;
        String finalTo = to;
        String finalHallInput = hallInput;

        data.events.stream()
                .filter(e -> (e.getDate().compareTo(finalFrom) >= 0 && e.getDate().compareTo(finalTo) <= 0))
                .filter(e -> (finalHallInput.isEmpty() || Integer.parseInt(finalHallInput) == e.getHall().getHallNumber()))
                .forEach(e -> {
                    long sold = data.tickets.stream()
                            .filter(t -> t.getEvent().equals(e))
                            .count();
                    System.out.println(e.getName() + " на " + e.getDate() + " - Продадени билети: " + sold);
                });
    }

    private static void deleteEvent(Scanner scanner) {
        System.out.print("Дата на представлението за изтриване: ");
        String date = scanner.nextLine();
        System.out.print("Име на представлението за изтриване: ");
        String name = scanner.nextLine();

        Event eventToRemove = findEvent(data.events, date, name);
        if (eventToRemove != null) {
            System.out.print("Сигурни ли сте, че искате да изтриете \"" + name + "\"? (y/n): ");
            String confirm = scanner.nextLine();
            if (confirm.equalsIgnoreCase("y")) {
                data.tickets.removeIf(ticket -> ticket.getEvent().equals(eventToRemove));
                data.cinemaManager.getAllReservations().removeIf(res -> res.getEvent().equals(eventToRemove));
                data.events.remove(eventToRemove);
                System.out.println("Представлението е изтрито успешно.");
            } else {
                System.out.println("Изтриването е отменено.");
            }
        } else {
            System.out.println("Представлението не е намерено.");
        }
    }

    private static Event findEvent(List<Event> events, String date, String name) {
        return events.stream()
                .filter(e -> e.getDate().equals(date) && e.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    private static String generateTicketCode(Event event, int row, int seat) {
        return "T-" + event.getDate().replace("-", "") + "-" + row + "-" + seat + "-" + System.currentTimeMillis();
    }
}
