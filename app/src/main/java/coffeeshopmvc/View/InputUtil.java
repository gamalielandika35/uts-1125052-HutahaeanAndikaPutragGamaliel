package coffeeshopmvc.View;

import java.util.Scanner;

public class InputUtil {

    public static String getString(Scanner scanner) {
        return scanner.nextLine();
    }

    public static int getInt(Scanner scanner) {
        return Integer.parseInt(scanner.nextLine());
    }

    public static double getDouble(Scanner scanner) {
        return Double.parseDouble(scanner.nextLine());
    }
}
