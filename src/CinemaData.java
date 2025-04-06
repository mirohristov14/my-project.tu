import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CinemaData implements Serializable {
    public List<Hall> halls;
    public List<Event> events;
    public List<Ticket> tickets;
    public CinemaManager cinemaManager;

    public CinemaData() {
        this.halls = new ArrayList<>();
        this.events = new ArrayList<>();
        this.tickets = new ArrayList<>();
        this.cinemaManager = new CinemaManager();
    }
}
