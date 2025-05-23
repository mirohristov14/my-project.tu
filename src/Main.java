import java.util.Scanner;

/**
 * Главният клас на приложението за управление на кино система.
 * <p>
 * Служи като входна точка на програмата. Той създава обект от тип {@link CinemaApp}
 * и стартира основния цикъл за четене и изпълнение на потребителски команди.
 * </p>
 */
public class Main {

    /**
     * Метод {@code main} — входна точка на Java приложението.
     * <p>
     * Създава скенер за вход от конзолата, инстанция на {@link CinemaApp}
     * и стартира основната логика чрез метода {@code start()}.
     * </p>
     *
     * @param args аргументи от командния ред (не се използват в тази програма)
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CinemaApp app = new CinemaApp(scanner);
        app.start();
    }
}
