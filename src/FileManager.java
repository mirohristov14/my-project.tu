import java.io.*;

/**
 * Класът {@code FileManager} отговаря за запазване и зареждане
 * на обект от тип {@link CinemaData} чрез сериализация във файл.
 * Използва се за съхраняване на състоянието на кино системата между сесиите.
 */
public class FileManager {
    /** Името на файла, в който се записва или от който се чете обектът. */
    private final String fileName;

    /**
     * Конструктор, който задава име на файла за работа с данни.
     *
     * @param fileName името на файла за сериализация/десериализация
     */
    public FileManager(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Записва обект от тип {@link CinemaData} във файл чрез сериализация.
     *
     * @param data обектът с данни, който ще се запише
     */
    public void saveData(CinemaData data) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeObject(data);
        } catch (IOException e) {
            System.out.println("Грешка при записване: " + e.getMessage());
        }
    }

    /**
     * Зарежда обект от тип {@link CinemaData} от файл чрез десериализация.
     *
     * @return зареденият обект или {@code null}, ако възникне грешка или файлът не съществува
     */
    public CinemaData loadData() {
        File file = new File(fileName);
        if (!file.exists()) return null;

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            return (CinemaData) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Грешка при зареждане: " + e.getMessage());
            return null;
        }
    }
}
