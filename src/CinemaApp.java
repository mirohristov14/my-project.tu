import java.util.*;
import java.io.*;

/**
* Класът {@code CinemaApp} управлява взаимодействието с потребителя и
* логиката на командите за информационна система за кино.
* <p>
* Поддържа основни и кино-команди чрез хеш-таблици {@code Map<String, Runnable>}.
* Работи с файлове, събития, билети и резервации.
* </p>
*/
public class CinemaApp {
private final Scanner scanner;

/** Основни системни команди: open, close, save, saveas, help */
private final Map<String, Runnable> baseCommands = new HashMap<>();

/** Команди, свързани със събития и резервации */
private final Map<String, Runnable> cinemaCommands = new HashMap<>();

private String lastCommand = "";

private CinemaData data;
private FileManager fileManager;
private String openedFilename;
private boolean fileOpened;

/**
* Конструктор на приложението.
*
* @param scanner Скенер за четене на вход от потребителя.
*/
public CinemaApp(Scanner scanner) {
this.scanner = scanner;
initBaseCommands();
initCinemaCommands();
}

/**
* Стартира приложението в конзолен режим.
* Чете команди от потребителя и ги изпълнява.
*/
public void start() {
while (true) {
System.out.print("> ");
String command = scanner.nextLine().trim();
lastCommand = command;

String base = getBaseCommand(command);
if (base.equals("exit")) {
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

Runnable action = baseCommands.get(base);
if (action != null) {
action.run();
} else {
if (!fileOpened) {
System.out.println("Няма отворен файл. Използвайте командата 'open <файл>'.");
} else {
Runnable cinemaAction = cinemaCommands.get(base);
if (cinemaAction != null) {
cinemaAction.run();
} else {
System.out.println("Невалидна команда. Използвайте 'help' за списък.");
}
}
}
}
}

/**
* Инициализира системните команди в {@code baseCommands}.
*/
private void initBaseCommands() {
baseCommands.put("help", this::showHelp);
baseCommands.put("save", this::save);
baseCommands.put("close", this::close);
baseCommands.put("open", () -> open(lastCommand));
baseCommands.put("saveas", () -> saveAs(lastCommand));
}

/**
* Инициализира кино-командите в {@code cinemaCommands}.
*/
private void initCinemaCommands() {
cinemaCommands.put("add_event", this::addEvent);
cinemaCommands.put("buy_ticket", this::buyTicket);
cinemaCommands.put("book_seat", this::bookSeat);
cinemaCommands.put("cancel_reservation", this::cancelReservation);
cinemaCommands.put("free_seats", this::showFreeSeats);
cinemaCommands.put("occupied_seats", this::showOccupiedSeats);
cinemaCommands.put("show_events", this::showEvents);
cinemaCommands.put("show_tickets", this::showTickets);
cinemaCommands.put("show_reservations", this::showReservations);
cinemaCommands.put("check_ticket", this::checkTicket);
cinemaCommands.put("report", this::generateReport);
cinemaCommands.put("delete_event", this::deleteEvent);
}

private String getBaseCommand(String command) {
if (command.contains(" ")) {
return command.substring(0, command.indexOf(" ")).toLowerCase();
}
return command.toLowerCase();
}

/**
* Зарежда файл със сериализирани данни.
* Ако файлът не съществува, създава нов {@code CinemaData}.
*
* @param command Командата, съдържаща името на файла.
*/
private void open(String command) {
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

/**
* Записва текущите данни във файла, от който са били отворени.
*/
private void save() {
if (!fileOpened) {
System.out.println("Няма отворен файл за записване.");
return;
}
fileManager.saveData(data);
System.out.println("Данните са записани успешно във файл: " + openedFilename);
}

/**
* Записва текущите данни в нов файл.
*
* @param command Командата, съдържаща новото име на файла.
*/
private void saveAs(String command) {
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

/**
* Затваря отворения файл и изчиства текущите данни.
*/
private void close() {
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

private void showHelp() {
System.out.println("Налични команди:");
baseCommands.keySet().forEach(System.out::println);
cinemaCommands.keySet().forEach(System.out::println);
}

/**
* Добавя ново представление по зададена дата, име и зала.
* Извиква се с командата {@code add_event}.
*/
private void addEvent() {
System.out.print("Име на представление: ");
String name = scanner.nextLine();
System.out.print("Дата (гггг-мм-дд): ");
String date = scanner.nextLine();
System.out.print("Номер на зала: ");
int hallNumber = Integer.parseInt(scanner.nextLine());

Hall hall = data.halls.stream()
.filter(h -> h.getHallNumber() == hallNumber)
.findFirst().orElse(null);

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

private void buyTicket() {
System.out.print("Дата: ");
String date = scanner.nextLine();
System.out.print("Име на представление: ");
String name = scanner.nextLine();

Event event = findEvent(date, name);
if (event == null) {
System.out.println("Представление не е намерено.");
return;
}

System.out.print("Ред: ");
int row = Integer.parseInt(scanner.nextLine());
System.out.print("Място: ");
int seat = Integer.parseInt(scanner.nextLine());

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

/**
* Резервира място за събитие и добавя бележка.
*/
private void bookSeat() {
System.out.print("Дата: ");
String date = scanner.nextLine();
System.out.print("Име на представление: ");
String name = scanner.nextLine();

Event event = findEvent(date, name);
if (event == null) {
System.out.println("Представление не е намерено.");
return;
}

System.out.print("Ред: ");
int row = Integer.parseInt(scanner.nextLine());
System.out.print("Място: ");
int seat = Integer.parseInt(scanner.nextLine());
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

/**
* Отменя съществуваща резервация.
*/
private void cancelReservation() {
System.out.print("Дата: ");
String date = scanner.nextLine();
System.out.print("Име на представление: ");
String name = scanner.nextLine();
System.out.print("Ред: ");
int row = Integer.parseInt(scanner.nextLine());
System.out.print("Място: ");
int seat = Integer.parseInt(scanner.nextLine());

data.cinemaManager.cancelReservation(date, name, row, seat);
}

private void showFreeSeats() {
System.out.print("Дата: ");
String date = scanner.nextLine();
System.out.print("Име на представление: ");
String name = scanner.nextLine();

Event event = findEvent(date, name);
if (event == null) {
System.out.println("Представление не е намерено.");
return;
}

Hall hall = event.getHall();
for (int i = 1; i <= hall.getRows(); i++) {
for (int j = 1; j <= hall.getSeatsPerRow(); j++) {
final int r = i;
final int s = j;
boolean busy = data.cinemaManager.isSeatTaken(r, s, event)
|| data.tickets.stream().anyMatch(t ->
t.getEvent().equals(event) && t.getRow() == r && t.getSeat() == s);
if (!busy) {
System.out.println("Ред " + i + ", Място " + j);
}
}
}
}

private void showOccupiedSeats() {
System.out.print("Дата: ");
String date = scanner.nextLine();
System.out.print("Име на представление: ");
String name = scanner.nextLine();

Event event = findEvent(date, name);
if (event != null) {
data.cinemaManager.printOccupiedSeats(event);
} else {
System.out.println("Представление не е намерено.");
}
}

private void showEvents() {
if (data.events.isEmpty()) {
System.out.println("Няма представления.");
} else {
data.events.forEach(System.out::println);
}
}

private void showTickets() {
if (data.tickets.isEmpty()) {
System.out.println("Няма закупени билети.");
} else {
data.tickets.forEach(System.out::println);
}
}

private void showReservations() {
System.out.print("Дата (празно за всички): ");
String date = scanner.nextLine();
System.out.print("Име на представление (празно за всички): ");
String name = scanner.nextLine();

data.cinemaManager.getAllReservations().stream()
.filter(r -> (date.isEmpty() || r.getEvent().getDate().equals(date)) &&
(name.isEmpty() || r.getEvent().getName().equals(name)))
.forEach(System.out::println);
}

private void checkTicket() {
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
/**
* Генерира справка за продадени билети за избран период.
*/
private void generateReport() {
System.out.print("Начална дата (гггг-мм-дд): ");
String from = scanner.nextLine();
System.out.print("Крайна дата (гггг-мм-дд): ");
String to = scanner.nextLine();
System.out.print("Номер на зала (празно за всички): ");
String hallInput = scanner.nextLine();

data.events.stream()
.filter(e -> e.getDate().compareTo(from) >= 0 && e.getDate().compareTo(to) <= 0)
.filter(e -> hallInput.isEmpty() ||
Integer.parseInt(hallInput) == e.getHall().getHallNumber())
.forEach(e -> {
long sold = data.tickets.stream()
.filter(t -> t.getEvent().equals(e)).count();
System.out.println(e.getName() + " на " + e.getDate() + " - Продадени билети: " + sold);
});
}

private void deleteEvent() {
System.out.print("Дата: ");
String date = scanner.nextLine();
System.out.print("Име на представление: ");
String name = scanner.nextLine();

Event event = findEvent(date, name);
if (event != null) {
System.out.print("Сигурни ли сте, че искате да изтриете \"" + name + "\"? (y/n): ");
String confirm = scanner.nextLine();
if (confirm.equalsIgnoreCase("y")) {
data.tickets.removeIf(t -> t.getEvent().equals(event));
data.cinemaManager.getAllReservations().removeIf(r -> r.getEvent().equals(event));
data.events.remove(event);
System.out.println("Представлението е изтрито успешно.");
} else {
System.out.println("Изтриването е отменено.");
}
} else {
System.out.println("Представление не е намерено.");
}
}

private Event findEvent(String date, String name) {
return data.events.stream()
.filter(e -> e.getDate().equals(date) && e.getName().equals(name))
.findFirst().orElse(null);
}

private String generateTicketCode(Event event, int row, int seat) {
return "T-" + event.getDate().replace("-", "") + "-" + row + "-" + seat + "-" + System.currentTimeMillis();
}
}



