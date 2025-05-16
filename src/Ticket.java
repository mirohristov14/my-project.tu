import java.io.Serializable;

/**
 * Представлява билет за конкретно събитие в  системата.
 * Съдържа уникален код, ред и място в залата, както и препратка към събитието.
 * Класът е сериализируем за запазване на данни във файл.
 */
public class Ticket implements Serializable {
    private static final long serialVersionUID = 1L;

    /** Уникален идентификационен код на билета */
    private String code;

    /** Редът в залата, за който е валиден билетът */
    private int row;

    /** Номерът на мястото в реда, за което е валиден билетът */
    private int seat;

    /** Събитието, към което се отнася билетът */
    private Event event;

    /**
     * Създава нов билет със зададен код, ред, място и събитие.
     *
     * @param code  Уникален код на билета.
     * @param row   Редът, за който е билетът.
     * @param seat  Мястото в реда.
     * @param event Събитието, към което е билетът.
     */
    public Ticket(String code, int row, int seat, Event event) {
        this.code = code;
        this.row = row;
        this.seat = seat;
        this.event = event;
    }

    /**
     * Връща уникалния код на билета.
     *
     * @return Кодът на билета.
     */
    public String getCode() { return code; }

    /**
     * Връща реда, на който се намира мястото.
     *
     * @return Редът на мястото.
     */
    public int getRow() { return row; }

    /**
     * Връща мястото (седалката) в реда.
     *
     * @return Номерът на мястото.
     */
    public int getSeat() { return seat; }

    /**
     * Връща събитието, към което принадлежи този билет.
     *
     * @return Събитието.
     */
    public Event getEvent() { return event; }

    /**
     * Връща текстово описание на билета, подходящо за извеждане в конзолата.
     *
     * @return Стринг с информация за реда, мястото и събитието.
     */
    @Override
    public String toString() {
        return "Билет " + code + ": Ред " + row + ", Място " + seat +
                " за " + event.getName() + " на " + event.getDate();
    }
}
