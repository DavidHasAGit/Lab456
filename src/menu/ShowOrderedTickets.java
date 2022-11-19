package menu;

import file.AdditionalFileManage;
import file.FileManageAccounts;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShowOrderedTickets implements MenuItem {
    @Override
    public void execute() {
        FileManageAccounts login = new FileManageAccounts();
        System.out.println("\n\t\tYou need to log in to your account:");
        if (login.logIn()) {
            printAllOrderedTickets(login.findOrdersByPerson());
        }
    }
    private void printAllOrderedTickets(String string) {
        AdditionalFileManage file = new AdditionalFileManage();
        Pattern integerPattern = Pattern.compile("-?\\d+");
        Matcher matcher = integerPattern.matcher(string);
        System.out.println("\n\t\tYour tickets:");
        while (matcher.find()) {
            String data = file.getTicketByIndex(Integer.parseInt(matcher.group()));
            System.out.println("\t\t" + data);
        }
    }
}
