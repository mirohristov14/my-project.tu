import java.io.Serializable;

/**
 * Представлява резервация за място на определено събитие (представление).
 * Съдържа информация за ред, място, събитие и допълнителна бележка.
 * Класът е сериализируем, което позволява неговото съхранение и възстановяване от файл.
 */
public class Reservation implements Serializable {
    private static final long serialVersionUID = 8068031896537901293L;


    /** Редът на резервацията в залата */
    private int row;

    /** Номерът на мястото в реда */
    private int seat;

    /** Събитието, за което е направена резервацията */
    private Event event;

    /** Допълнителна бележка, свързана с резервацията */
    private String note;

    /**
     * Създава нова резервация с посочени ред, място, събитие и бележка.
     *
     * @param row  Редът в залата.
     * @param seat Номерът на мястото.
     * @param event Събитието, за което е резервацията.
     * @param note Допълнителна бележка към резервацията.
     */
    public Reservation(int row, int seat, Event event, String note) {
        this.row = row;
        this.seat = seat;
        this.event = event;
        this.note = note;
    }

    /**
     * Връща реда на резервацията.
     *
     * @return Редът.
     */
    public int getRow() { return row; }

    /**
     * Връща мястото на резервацията.
     *
     * @return Номерът на мястото.
     */
    public int getSeat() { return seat; }

    /**
     * Връща събитието, за което е направена резервацията.
     *
     * @return Обект от тип Event.
     */
    public Event getEvent() { return event; }
}
