import java.util.ArrayList;
import java.util.List;

public class CinemaManager {
    private List<Reservation> reservations;

    public CinemaManager() {
        this.reservations = new ArrayList<>();
    }

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    public boolean isSeatTaken(int row, int seat, Event event) {
        return reservations.stream()
                .anyMatch(res -> res.getRow() == row
                        && res.getSeat() == seat
                        && res.getEvent().equals(event));
    }

    public void cancelReservation(String date, String eventName, int row, int seat) {
        boolean removed = reservations.removeIf(res ->
                res.getEvent().getDate().equals(date)
                        && res.getEvent().getName().equals(eventName)
                        && res.getRow() == row
                        && res.getSeat() == seat);

        if (removed) {
            System.out.println("Резервацията е отменена успешно!");
        } else {
            System.out.println("Не е намерена резервация за това място.");
        }
    }

    public boolean isSeatFree(int row, int seat, Event event) {
        return !isSeatTaken(row, seat, event);
    }

    public void printOccupiedSeats(Event event) {
        Hall hall = event.getHall();
        boolean hasReservations = false;

        System.out.println("\nЗаети места за '" + event.getName() + "' на " + event.getDate() + ":");

        for (int row = 1; row <= hall.getRows(); row++) {
            StringBuilder occupiedSeats = new StringBuilder("Ред " + row + ": ");
            boolean rowHasSeats = false;

            for (int seat = 1; seat <= hall.getSeatsPerRow(); seat++) {
                if (isSeatTaken(row, seat, event)) {
                    if (rowHasSeats) {
                        occupiedSeats.append(", ");
                    }
                    occupiedSeats.append(seat);
                    rowHasSeats = true;
                    hasReservations = true;
                }
            }

            if (rowHasSeats) {
                System.out.println(occupiedSeats);
            }
        }

        if (!hasReservations) {
            System.out.println("Няма заети места за това представление.");
        }
    }

    public List<Reservation> getAllReservations() {
        return new ArrayList<>(reservations);
    }
}
