package menu;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class MainMenu {
    private final Map<String, MenuItem> menuItems;
    public MainMenu() {
        menuItems = new LinkedHashMap<>();
        menuItems.put("tickets", new ShowAvailableTickets());
        menuItems.put("orders", new ShowOrderedTickets());
        menuItems.put("exit", new Exit());
    }
    public void execute(String command) {
        MenuItem menuItem = menuItems.get(command);
        if (menuItem != null) {
            menuItem.execute();
        }
        else System.out.println("Invalid");
    }
    public Set<String> getCommands() {
        return menuItems.keySet();
    }
}
