package option;

import file.AdditionalFileManage;
import file.FileManage;

import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Sorting {
    public static boolean sortingExist = false;
    public void getTicketsSorted() {
        FileManage mainFile = new FileManage();
        sortingExist = true;
        AdditionalFileManage additionalFile = new AdditionalFileManage();
        Map<Integer, Integer> indexAndCost = getIndexAndCostOfAllTickets(mainFile);
        additionalFile.writeSortedTicketsInAdditionalFile(indexAndCost);
    }
    private Map<Integer, Integer> getIndexAndCostOfAllTickets(FileManage mainFile) {
        Filters filters = new Filters();
        Map<Integer, Integer> indexAndCost = null;
        try {
            Scanner myReader = new Scanner(mainFile.getFile());
            boolean filersExist = Filters.getAnyFilterExist();
            indexAndCost = new TreeMap<Integer, Integer>();
            for (int i = 1; myReader.hasNextLine(); i++) {
                String data = myReader.nextLine();
                if (filersExist) {
                    if (filters.withFilter(data)) {
                        indexAndCost.put(filters.getIntFromString(data), i);
                    }
                } else {
                    indexAndCost.put(filters.getIntFromString(data), i);
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return indexAndCost;
    }

    public static boolean sortingExist() {
        return sortingExist;
    }
    public void ResetSorting() {
        sortingExist = false;
    }
}
