import java.io.Serializable;

public class Hall implements Serializable {
    private static final long serialVersionUID = 1L;

    private int hallNumber;
    private int rows;
    private int seatsPerRow;

    public Hall(int hallNumber, int rows, int seatsPerRow) {
        this.hallNumber = hallNumber;
        this.rows = rows;
        this.seatsPerRow = seatsPerRow;
    }

    public int getHallNumber() { return hallNumber; }
    public int getRows() { return rows; }
    public int getSeatsPerRow() { return seatsPerRow; }

    @Override
    public String toString() {
        return "Зала " + hallNumber + ": " + rows + " реда, " + seatsPerRow + " места на ред.";
    }
}
