import java.io.Serializable;
import java.util.Objects;

public class Event implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String date;
    private Hall hall;

    public Event(String name, String date, Hall hall) {
        this.name = name;
        this.date = date;
        this.hall = hall;
    }

    public String getName() { return name; }
    public String getDate() { return date; }
    public Hall getHall() { return hall; }

    @Override
    public String toString() {
        return "Представление: " + name + " на " + date + " в " + hall;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Event other = (Event) obj;
        return name.equals(other.name) &&
                date.equals(other.date) &&
                hall.getHallNumber() == other.hall.getHallNumber();
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, date, hall.getHallNumber());
    }
}
