import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Класът {@code CinemaManager} управлява всички резервации за кино събития.
 * Поддържа добавяне, премахване, проверка за заети/свободни места
 * и извеждане на информация за заетите места по събитие.
 * Класът е сериализируем и може да бъде съхранен или зареден от файл.
 */
public class CinemaManager implements Serializable {
    private static final long serialVersionUID = 1L;

    /** Списък с всички резервации */
    private List<Reservation> reservations;

    /**
     * Създава нов мениджър на кино с празен списък от резервации.
     */
    public CinemaManager() {
        this.reservations = new ArrayList<>();
    }

    /**
     * Добавя нова резервация към системата.
     *
     * @param reservation Резервацията, която да се добави.
     */
    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    /**
     * Проверява дали дадено място е вече заето за конкретно събитие.
     *
     * @param row   Редът на мястото.
     * @param seat  Номерът на мястото.
     * @param event Събитието.
     * @return {@code true} ако мястото е заето, {@code false} иначе.
     */
    public boolean isSeatTaken(int row, int seat, Event event) {
        return reservations.stream().anyMatch(res ->
                res.getRow() == row &&
                        res.getSeat() == seat &&
                        res.getEvent().equals(event));
    }

    /**
     * Отменя резервация по дата, име на събитие, ред и място.
     * Извежда съобщение дали е успешно премахната.
     *
     * @param date      Датата на събитието.
     * @param eventName Името на събитието.
     * @param row       Редът на мястото.
     * @param seat      Номерът на мястото.
     */
    public void cancelReservation(String date, String eventName, int row, int seat) {
        boolean removed = reservations.removeIf(res ->
                res.getEvent().getDate().equals(date) &&
                        res.getEvent().getName().equals(eventName) &&
                        res.getRow() == row &&
                        res.getSeat() == seat);

        if (removed) {
            System.out.println("Резервацията е отменена успешно!");
        } else {
            System.out.println("Не е намерена резервация за това място.");
        }
    }

    /**
     * Проверява дали дадено място е свободно за конкретно събитие.
     *
     * @param row   Редът на мястото.
     * @param seat  Номерът на мястото.
     * @param event Събитието.
     * @return {@code true} ако мястото е свободно, {@code false} ако е заето.
     */
    public boolean isSeatFree(int row, int seat, Event event) {
        return !isSeatTaken(row, seat, event);
    }

    /**
     * Извежда списък на всички заети места за дадено събитие,
     * групирани по редове.
     *
     * @param event Събитието, за което да се покажат заетите места.
     */
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

    /**
     * Връща списък с всички резервации.
     * Връща копие на списъка, за да се запази инкапсулацията.
     *
     * @return Копие на списъка с резервации.
     */
    public List<Reservation> getAllReservations() {
        return new ArrayList<>(reservations);
    }
}
