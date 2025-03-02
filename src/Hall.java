public class Hall {
    private int hallNumber;  // Номер на залата
    private int rows;        // Брой редове
    private int seatsPerRow; // Брой места на ред

    // Конструктор
    public Hall(int hallNumber, int rows, int seatsPerRow) {
        this.hallNumber = hallNumber;
        this.rows = rows;
        this.seatsPerRow = seatsPerRow;
    }

    // Гетери 
    public int getHallNumber() {
        return hallNumber;
    }

    public int getRows() {
        return rows;
    }

    public int getSeatsPerRow() {
        return seatsPerRow;
    }

    // Метод за показване на информация за залата
    @Override
    public String toString() {
        return "Зала " + hallNumber + ": " + rows + " реда, " + seatsPerRow + " места на ред.";
    }
}
