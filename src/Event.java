public class Event {
    private String name;
    private String date;
    private Hall hall;

    public Event(String name, String date, Hall hall) {
        this.name = name;
        this.date = date;
        this.hall = hall;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public Hall getHall() {
        return hall;
    }

    @Override
    public String toString() {
        return "Представление: " + name + " на " + date + " в " + hall;
    }
}
