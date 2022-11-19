import menu.MainMenu;

import java.util.*;

public class Main {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        MainMenu menu = new MainMenu();

        while (true) {
            System.out.println("\nAvailable commands - " + menu.getCommands());
            System.out.println("tickets - show available tickets\norders - show ordered tickets\nexit - exit program");
            System.out.print("Enter command: ");
            String command = scanner.next();
            menu.execute(command);
        }
    }
}
