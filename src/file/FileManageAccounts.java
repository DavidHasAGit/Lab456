package file;

import option.Filters;
import option.Sorting;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileManagedAccounts {
    File fileLogins = new File("Logins.txt");
    File filePasswords = new File("Passwords.txt");
    File fileClientOrders = new File("ClientOrders.txt");
    File fileClientOrdersCopy = new File("ClientOrdersCopy.txt");
    int currentPerson = 0;

    public boolean enterAccount() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("""

                    \tTo save the order, you need to:
                    \t1. Log in\t2. Create account
                    \tChoice:\s""");
        switch (scanner.nextInt()) {
            case 1 -> {
                logIn();
            }
            case 2 -> {
                register();
            }
            default -> {
                System.out.println("Incorrect data");
                cont = false;
            }
        }
    }




    public boolean logIn() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your login: ");
        String login = scanner.nextLine();
        String truePassword;
        int loginPosition = findLogin(login);
        if (loginPosition > 0) {
            currentPerson = loginPosition;
            truePassword = findPasswordByLogin(loginPosition);
        }
        else {
            System.out.print("This account does not exist");
            return false;
        }
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();
        if (!password.equals(truePassword)) {
            System.out.print("Wrong password");
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
