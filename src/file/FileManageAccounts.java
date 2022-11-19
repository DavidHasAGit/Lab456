package file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;

public class FileManageAccounts {
    File fileLogins = new File("Logins.txt");
    File filePasswords = new File("Passwords.txt");
    File fileClientOrders = new File("ClientOrders.txt");
    File fileClientOrdersCopy = new File("ClientOrdersCopy.txt");
    int currentPerson = 0;

    public boolean enterAccount() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("""

                    \t\t\tTo save the order, you need to:
                    \t\t\t1. Log in\t2. Create account
                    \t\t\tChoice:\s""");
        switch (scanner.nextInt()) {
            case 1 -> {
                return logIn();
            }
            case 2 -> {
                return register();
            }
            default -> {
                System.out.println("Incorrect data");
                return false;
            }
        }
    }
    public boolean logIn() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\n\t\t\t\tEnter your login: ");
        String login = scanner.nextLine();
        String truePassword;
        int loginPosition = findLogin(login);
        if (loginPosition > 0) {
            currentPerson = loginPosition;
            truePassword = findPasswordByLogin(loginPosition);
        }
        else {
            System.out.print("\t\t\t\tThis account does not exist");
            return false;
        }
        System.out.print("\t\t\t\tEnter your password: ");
        String password = scanner.nextLine();
        if (!password.equals(truePassword)) {
            System.out.print("\t\t\t\tWrong password");
            currentPerson = 0;
            return false;
        }
        return true;
    }
    private int findLogin(String login) {
        int loginPosition = 0;
        try {
            Scanner myReader = new Scanner(fileLogins);
            for (int i = 1; myReader.hasNextLine(); i++) {
                if (myReader.nextLine().equals(login)) loginPosition = i;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return loginPosition;
    }
    private String findPasswordByLogin(int loginPosition) {
        String password = null;
        try {
            Scanner myReader = new Scanner(filePasswords);
            for (int i = 1; myReader.hasNextLine() && i != loginPosition; i++) myReader.nextLine();
            if (myReader.hasNextLine()) {
                password = myReader.nextLine();
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return password;
    }
    public String findOrdersByPerson() {
        String ordersLine = null;
        try {
            Scanner myReader = new Scanner(fileClientOrders);
            for (int i = 1; myReader.hasNextLine() && i != currentPerson; i++) myReader.nextLine();
            if (myReader.hasNextLine()) {
                ordersLine = myReader.nextLine();
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return ordersLine;
    }
    private boolean register() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\n\t\t\t\tEnter your login: ");
        String login = scanner.nextLine();
        if (findLogin(login) != 0) {
            System.out.print("\t\t\t\tAn account with this login already exists.\n\t\t\t\tIf you want to log in press 1: ");
            if (scanner.nextInt() == 1) return logIn();
            else return false;
        }
        System.out.print("\t\t\t\tEnter your password: ");
        String password = scanner.nextLine();
        addNewClient(login, password);
        currentPerson = findLogin(login);
        return true;
    }
    private void addNewClient(String login, String password) throws IOException {
        addToFile(fileLogins, login);
        addToFile(filePasswords, password);
    }
    private void addToFile(File file, String string) throws IOException {
        File copy = new File("Copy.txt");
        Files.copy(file.toPath(), copy.toPath());
        try {
            Scanner myReader = new Scanner(copy);
            FileWriter myWriter = new FileWriter(file);
            while(myReader.hasNextLine()) {
                String data = myReader.nextLine();
                myWriter.write(data + "\n");
            }
            myWriter.write(string);

            myReader.close();
            myWriter.close();
            copy.delete();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    public File getFileClientOrders() {
        return fileClientOrders;
    }
    public File getFileClientOrdersCopy() {
        return fileClientOrdersCopy;
    }
    public int getCurrentPerson() {
        return currentPerson;
    }
}
