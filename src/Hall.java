import java.io.Serializable;

/**
 * Представлява кино зала, в която се провеждат събития.
 * Съдържа информация за номера на залата, броя на редовете и местата на ред.
 * Класът е сериализируем, за да се позволява запис и зареждане от файл.
 */
public class Hall implements Serializable {
    private static final long serialVersionUID = 1L;

    /** Уникален номер на залата */
    private int hallNumber;

    /** Брой редове в залата */
    private int rows;

    /** Брой места на всеки ред */
    private int seatsPerRow;

    /**
     * Създава нова зала с указан номер, брой редове и места на ред.
     *
     * @param hallNumber  Номер на залата.
     * @param rows        Брой редове в залата.
     * @param seatsPerRow Брой места на всеки ред.
     */
    public Hall(int hallNumber, int rows, int seatsPerRow) {
        this.hallNumber = hallNumber;
        this.rows = rows;
        this.seatsPerRow = seatsPerRow;
    }

    /**
     * Връща номера на залата.
     *
     * @return Номерът на залата.
     */
    public int getHallNumber() { return hallNumber; }

    /**
     * Връща броя на редовете в залата.
     *
     * @return Брой редове.
     */
    public int getRows() { return rows; }

    /**
     * Връща броя на местата на всеки ред.
     *
     * @return Брой места на ред.
     */
    public int getSeatsPerRow() { return seatsPerRow; }

    /**
     * Връща текстово описание на залата.
     *
     * @return Стринг с номер на залата, брой редове и места на ред.
     */
    @Override
    public String toString() {
        return "Зала " + hallNumber + ": " + rows + " реда, " + seatsPerRow + " места на ред.";
    }
}
