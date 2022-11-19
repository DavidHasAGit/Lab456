package filter;

import java.util.Scanner;

public class Filters {
    static String[] filtersNames = new String[]{"place name", "ticket type", "transport", "meals", "city of departure", "duration", "cost"};
    static String[][] ticketType = new String[][]{
            {""},
            {"vacation", "excursions", "treatment", "shopping", "cruise"},
            {"train", "plane", "bus", "ship"},
            {"breakfast", "all meals", "no meals"},
            {"week", "two weeks", "month"},
            {"Kyiv", "Odesa", "Lviv"},
            {"0", "0"}
    };
    private static final boolean[][] existDefault = new boolean[][]{
            {false},
            {false, false, false, false, false},
            {false, false, false, false},
            {false, false, false},
            {false, false, false},
            {false, false, false},
            {false, false}
    };
    static boolean[][] exist = existDefault;
    static boolean filtersExist = false;
    Scanner scanner;
    public Filters() {
        scanner = new Scanner(System.in);
    }
    public void AddFilters() {
        while (true) {
            System.out.print("\n\t\tFilter tickets by:\n\t");
            for (int i = 0; i < filtersNames.length; i++) {
                if ((i % 2) != 1 && i != 0) System.out.print("\n\t");
                System.out.print("\t" + (i + 1) + ". " + filtersNames[i]);
            }
            System.out.println("\t\t" + (filtersNames.length + 1) + ". exit filters");
            System.out.print("\t\tChoice: ");
            int choice = scanner.nextInt();
            if (choice == (filtersNames.length + 1)) return;
            if (choice > ticketType.length || choice < 1) System.out.println("There is no such option");
            else {
                if (choice == 1) setPlace();
                else if (choice == filtersNames.length) setCost();
                else {
                    System.out.println("\n\t\t\tSet " + filtersNames[choice] + ": ");
                    for (int i = 0; i < ticketType[choice-1].length; i++) {
                        System.out.println("\t\t\t" + (i + 1) + ". " + ticketType[choice-1][i]);
                    }
                    System.out.print("\t\t\tChoice(supports multiple options. Example: 1, 2): ");
                    scanner.nextLine();
                    String choiceText = scanner.nextLine();
                    if (!setFilter(choiceText, choice)) {
                        resetFilters();
                        filtersExist = false;
                        System.out.println("Incorrect data. All filters have been removed");
                    }
                    else filtersExist = true;
                }
            }
        }
    }
    private void setPlace() {
        System.out.print("\t\tEnter place name or it's part: ");
        ticketType[0][0] = scanner.next();
        exist[0][0] = true;
        filtersExist = true;
    }
    private void setCost() {
        System.out.print("\n\t\t\tEnter min cost(0 - no min cost): ");
        int min = scanner.nextInt();
        if (min <= 0) System.out.println("\t\t\tNo minimum value is set");
        else if (min > Integer.parseInt(ticketType[ticketType.length - 1][1])
                && Integer.parseInt(ticketType[ticketType.length - 1][1]) != 0)
            System.out.println("\t\t\tThe minimum value cannot be greater than the maximum.\n\t\t\tNo minimum value is set");
        else {
            ticketType[ticketType.length - 1][0] = Integer.toString(min);
            exist[exist.length - 1][0] = true;
            filtersExist = true;
        }
        System.out.print("\t\t\tEnter max cost(0 - no max cost): ");
        int max = scanner.nextInt();
        if (max <= 0) System.out.println("\t\t\tNo maximum value is set");
        else if (max < Integer.parseInt(ticketType[ticketType.length - 1][0]))
            System.out.println("\t\t\tThe maximum value cannot be less than the minimum.\n\t\t\tNo maximum value is set");
        else {
            ticketType[ticketType.length - 1][1] = Integer.toString(max);
            exist[exist.length - 1][1] = true;
            filtersExist = true;
        }
    }
    private boolean setFilter(String choice, int type) {
        boolean filterExist = false;
        for (int i = 1; i <= ticketType[type].length; i++) {
            if (choice.contains(Integer.toString(i))) {
                exist[type - 1][i - 1] = true;
                filterExist = true;
            }
        }
        return filterExist;
    }

    public void resetFilters() {
        exist = existDefault;
        filtersExist = false;
    }
    public static boolean[][] getExist() {
        return exist;
    }
    public static String[][] getTicketType() {
        return ticketType;
    }
}
