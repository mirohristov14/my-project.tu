import java.io.Serializable;
import java.util.Objects;

/**
 * Представлява събитие (представление) в киното.
 * Съдържа информация за името, датата и залата, в която се провежда.
 * Класът е сериализируем, което позволява неговото записване и зареждане от файл.
 */
public class Event implements Serializable {
    private static final long serialVersionUID = 1L;

    /** Име на събитието */
    private String name;

    /** Дата на събитието (като текст, напр. "2025-05-20") */
    private String date;

    /** Залата, в която се провежда събитието */
    private Hall hall;

    /**
     * Създава ново събитие с име, дата и зала.
     *
     * @param name Име на събитието.
     * @param date Дата на събитието.
     * @param hall Зала, в която ще се проведе събитието.
     */
    public Event(String name, String date, Hall hall) {
        this.name = name;
        this.date = date;
        this.hall = hall;
    }

    /**
     * Връща името на събитието.
     *
     * @return Името на събитието.
     */
    public String getName() { return name; }

    /**
     * Връща датата на събитието.
     *
     * @return Датата на събитието.
     */
    public String getDate() { return date; }

    /**
     * Връща залата, в която се провежда събитието.
     *
     * @return Залата на събитието.
     */
    public Hall getHall() { return hall; }

    /**
     * Връща текстово описание на събитието.
     *
     * @return Стринг с име, дата и зала на събитието.
     */
    @Override
    public String toString() {
        return "Представление: " + name + " на " + date + " в " + hall;
    }

    /**
     * Проверява дали текущото събитие е еквивалентно на друго по име, дата и номер на зала.
     *
     * @param obj Обектът, с който се сравнява.
     * @return true ако събитията са еднакви, false в противен случай.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Event other = (Event) obj;
        return name.equals(other.name) &&
                date.equals(other.date) &&
                hall.getHallNumber() == other.hall.getHallNumber();
    }

    /**
     * Връща хеш код на събитието, базиран на името, датата и номера на залата.
     *
     * @return Хеш код на обекта.
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, date, hall.getHallNumber());
    }
}
