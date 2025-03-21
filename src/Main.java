import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        List<Hall> halls = new ArrayList<>();
        List<Event> events = new ArrayList<>();
        List<Ticket> tickets = new ArrayList<>();
        List<Reservation> reservations = new ArrayList<>();
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
            System.out.println("8. Изход");
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
                        boolean isSeatAvailable = true;

                        for (Reservation reservation : reservations) {
                            if (reservation.getEvent().equals(selectedEvent) &&
                                    reservation.getRow() == row &&
                                    reservation.getSeat() == seat) {
                                isSeatAvailable = false;
                                break;
                            }
                        }

                        for (Ticket ticket : tickets) {
                            if (ticket.getEvent().equals(selectedEvent) &&
                                    ticket.getRow() == row &&
                                    ticket.getSeat() == seat) {
                                isSeatAvailable = false;
                                break;
                            }
                        }

                        if (isSeatAvailable) {
                            String ticketCode = "TICKET-" + System.currentTimeMillis();
                            Ticket newTicket = new Ticket(ticketCode, row, seat, selectedEvent);
                            tickets.add(newTicket);
                            System.out.println("Билетът е закупен успешно! Код: " + ticketCode);
                        } else {
                            System.out.println("Мястото е вече заето.");
                        }
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
                    System.out.print("Въведете дата на представлението: ");
                    String bookDate = scanner.nextLine();
                    System.out.print("Въведете име на представлението: ");
                    String bookName = scanner.nextLine();
                    System.out.print("Въведете ред: ");
                    int bookRow = scanner.nextInt();
                    System.out.print("Въведете място: ");
                    int bookSeat = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Въведете бележка: ");
                    String note = scanner.nextLine();

                    Event selectedEventForBooking = null;
                    for (Event event : events) {
                        if (event.getDate().equals(bookDate) && event.getName().equals(bookName)) {
                            selectedEventForBooking = event;
                            break;
                        }
                    }

                    if (selectedEventForBooking != null) {
                        boolean isSeatAvailable = true;

                        for (Reservation reservation : reservations) {
                            if (reservation.getEvent().equals(selectedEventForBooking) &&
                                    reservation.getRow() == bookRow &&
                                    reservation.getSeat() == bookSeat) {
                                isSeatAvailable = false;
                                break;
                            }
                        }

                        for (Ticket ticket : tickets) {
                            if (ticket.getEvent().equals(selectedEventForBooking) &&
                                    ticket.getRow() == bookRow &&
                                    ticket.getSeat() == bookSeat) {
                                isSeatAvailable = false;
                                break;
                            }
                        }

                        if (isSeatAvailable) {
                            Reservation newReservation = new Reservation(bookRow, bookSeat, selectedEventForBooking, note);
                            reservations.add(newReservation);
                            System.out.println("Мястото е резервирано успешно!");
                        } else {
                            System.out.println("Мястото е вече заето.");
                        }
                    } else {
                        System.out.println("Представлението не е намерено.");
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

                    Reservation reservationToRemove = null;
                    for (Reservation reservation : reservations) {
                        if (reservation.getEvent().getDate().equals(unbookDate) &&
                                reservation.getEvent().getName().equals(unbookName) &&
                                reservation.getRow() == unbookRow &&
                                reservation.getSeat() == unbookSeat) {
                            reservationToRemove = reservation;
                            break;
                        }
                    }

                    if (reservationToRemove != null) {
                        reservations.remove(reservationToRemove);
                        System.out.println("Резервацията е отменена успешно!");
                    } else {
                        System.out.println("Резервацията не е намерена.");
                    }
                    break;

                case 7:
                    System.out.print("Въведете дата на представлението: ");
                    String freeSeatsDate = scanner.nextLine();
                    System.out.print("Въведете име на представлението: ");
                    String freeSeatsName = scanner.nextLine();

                    Event selectedEventForFreeSeats = null;
                    for (Event event : events) {
                        if (event.getDate().equals(freeSeatsDate) && event.getName().equals(freeSeatsName)) {
                            selectedEventForFreeSeats = event;
                            break;
                        }
                    }

                    if (selectedEventForFreeSeats != null) {
                        System.out.println("Свободни места за " + selectedEventForFreeSeats.getName() + " на " + selectedEventForFreeSeats.getDate() + ":");
                        Hall hall = selectedEventForFreeSeats.getHall();
                        boolean[][] seats = new boolean[hall.getRows()][hall.getSeatsPerRow()];

                        for (Reservation reservation : reservations) {
                            if (reservation.getEvent().equals(selectedEventForFreeSeats)) {
                                seats[reservation.getRow() - 1][reservation.getSeat() - 1] = true;
                            }
                        }

                        for (Ticket ticket : tickets) {
                            if (ticket.getEvent().equals(selectedEventForFreeSeats)) {
                                seats[ticket.getRow() - 1][ticket.getSeat() - 1] = true;
                            }
                        }

                        for (int i = 0; i < hall.getRows(); i++) {
                            for (int j = 0; j < hall.getSeatsPerRow(); j++) {
                                if (!seats[i][j]) {
                                    System.out.println("Ред " + (i + 1) + ", Място " + (j + 1));
                                }
                            }
                        }
                    } else {
                        System.out.println("Представлението не е намерено.");
                    }
                    break;

                case 8:
                    System.out.println("Излизане от програмата...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Невалидна опция. Моля, опитайте отново.");
            }
        }
    }
}
