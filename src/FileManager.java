import java.io.*;

public class FileManager {
    private final String fileName;

    public FileManager(String fileName) {
        this.fileName = fileName;
    }

    public void saveData(CinemaData data) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeObject(data);
        } catch (IOException e) {
            System.out.println("Грешка при записване: " + e.getMessage());
        }
    }

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
