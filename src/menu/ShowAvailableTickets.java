package menu;
import file.AdditionalFileManage;
import file.FileManage;
import file.FileManageAccounts;
import option.Filters;
import option.Sorting;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;

public class ShowAvailableTickets implements MenuItem {
    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        Filters filters = new Filters();
        FileManage file = new FileManage();
        AdditionalFileManage additionalFile = new AdditionalFileManage();
        Sorting sort = new Sorting();
        System.out.println();
        file.printContent();
        boolean cont = true;

        while (cont) {
            System.out.print("""

                    \tAvailable operations:
                    \t1. Choose ticket\t2. Add filters
                    \t3. Reset filters\t4. Sort tickets by cost
                    \t5. Reset sorting\t6. ExitChoice
                    \tChoice:\s""");
            switch (scanner.nextInt()) {
                case 1 -> {
                    if (!Sorting.sortingExist && !Filters.getAnyFilterExist()) {
                        ChooseTicket(file.getFile(), scanner);
                    } else ChooseTicket(additionalFile.getFile(), scanner);
                }
                case 2 -> {
                    filters.AddFilters();
                    additionalFile.formFileWithFilteredTickets();
                    additionalFile.printContent();
                }
                case 3 -> {
                    filters.resetFilters();
                    if (Sorting.sortingExist) {
                        sort.getTicketsSorted();
                        additionalFile.printContent();
                    } else file.printContent();
                }
                case 4 -> {
                    sort.getTicketsSorted();
                    additionalFile.printContent();
                }
                case 5 -> {
                    sort.ResetSorting();
                    if (Filters.getAnyFilterExist()) {
                        additionalFile.formFileWithFilteredTickets();
                        additionalFile.printContent();
                    } else file.printContent();
                }
                case 6 -> {
                    filters.resetFilters();
                    sort.ResetSorting();
                    cont = false;
                }
                default -> {
                    System.out.println("Incorrect data");
                    cont = false;
                }
            }
        }
    }
    public void ChooseTicket(File file, Scanner scanner) {
        System.out.print("\n\t\tEnter the ticket number you want to select: ");
        int ticketNum = scanner.nextInt();
        if (ticketNum < 1) {
            System.out.print("\t\tThis ticket does not exist\n\t\t");
            return;
        }
        try {
            Scanner myReader = new Scanner(file);
            FileManageAccounts login = new FileManageAccounts();
            int i;
            for (i = 1; myReader.hasNextLine(); i++) {
                if (i != ticketNum) {
                    myReader.nextLine();
                } else {
                    String data = myReader.nextLine();
                    System.out.print("\t\tYour ticket:\n\t\t");
                    System.out.println(data);
                    if (login.enterAccount()) {
                        ticketOrderWrite(login, getTicketIndexByData(data));
                        System.out.println("\t\t\t\tOrder processed");
                    } else System.out.println("\t\t\t\tThe order has not been processed");
                    break;
                }
            }
            if (i < ticketNum) {
                System.out.print("\t\tThis ticket does not exist\n\t\t");
                myReader.close();
                return;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private int getTicketIndexByData(String data) {
        try {
            FileManage mainFile = new FileManage();
            Scanner myReader = new Scanner(mainFile.getFile());
            for (int i = 1; myReader.hasNextLine(); i++) {
                if (myReader.nextLine().equals(data)) {
                    myReader.close();
                    return i;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return 0;
    }
    private void ticketOrderWrite(FileManageAccounts login, Integer ticketIndex) throws IOException {
        File orders = login.getFileClientOrders();
        File ordersCopy =  login.getFileClientOrdersCopy();
        int personIndex = login.getCurrentPerson();

        Files.copy(orders.toPath(), ordersCopy.toPath());
        try {
            Scanner myReader = new Scanner(ordersCopy);
            FileWriter myWriter = new FileWriter(orders);

            for (int i = 1; i <= personIndex || myReader.hasNextLine(); i++) {
                if (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    if (i != personIndex) myWriter.write(data + "\n");
                    else {
                        if (data.equals("")) myWriter.write(ticketIndex + "\n");
                        else myWriter.write(data + " " + ticketIndex + "\n");
                    }
                } else {
                    myWriter.write("\n");
                    if (i == personIndex) myWriter.write(ticketIndex.toString());
                }
            }
            myReader.close();
            myWriter.close();
            ordersCopy.delete();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
