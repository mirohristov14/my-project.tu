import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Класът {@code CinemaData} служи като контейнер за всички основни
 * елементи на кино системата – зали, събития, билети и мениджър на резервации.
 * Използва се основно при сериализация и десериализация, за да се
 * запази цялото състояние на системата.
 */
public class CinemaData implements Serializable {
    private static final long serialVersionUID = 1L;

    /** Списък със съществуващите зали в киното. */
    public List<Hall> halls;

    /** Списък с всички събития (представления). */
    public List<Event> events;

    /** Списък с всички продадени билети. */
    public List<Ticket> tickets;

    /** Мениджър на резервациите, отговарящ за управление на места. */
    public CinemaManager cinemaManager;

    /**
     * Конструктор по подразбиране. Инициализира празни списъци за
     * зали, събития и билети, както и нов {@link CinemaManager}.
     */
    public CinemaData() {
        this.halls = new ArrayList<>();
        this.events = new ArrayList<>();
        this.tickets = new ArrayList<>();
        this.cinemaManager = new CinemaManager();
    }
}
