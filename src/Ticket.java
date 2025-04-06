import java.io.Serializable;

public class Ticket implements Serializable {
    private static final long serialVersionUID = 1L;

    private String code;
    private int row;
    private int seat;
    private Event event;

    public Ticket(String code, int row, int seat, Event event) {
        this.code = code;
        this.row = row;
        this.seat = seat;
        this.event = event;
    }

    public String getCode() { return code; }
    public int getRow() { return row; }
    public int getSeat() { return seat; }
    public Event getEvent() { return event; }

    @Override
    public String toString() {
        return "Билет " + code + ": Ред " + row + ", Място " + seat +
                " за " + event.getName() + " на " + event.getDate();
    }
}
