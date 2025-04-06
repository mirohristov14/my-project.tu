import java.io.Serializable;

public class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;

    private int row;
    private int seat;
    private Event event;
    private String note;

    public Reservation(int row, int seat, Event event, String note) {
        this.row = row;
        this.seat = seat;
        this.event = event;
        this.note = note;
    }

    public int getRow() { return row; }
    public int getSeat() { return seat; }
    public Event getEvent() { return event; }
    public String getNote() { return note; }

    @Override
    public String toString() {
        return "Резервация: Ред " + row + ", Място " + seat +
                " за " + event.getName() + " на " + event.getDate() +
                " (Бележка: " + note + ")";
    }
}
