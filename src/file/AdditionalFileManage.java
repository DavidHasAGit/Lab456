package file;

import option.Filters;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

public class AdditionalFileManage extends FileManage {
    public AdditionalFileManage() {
        file = new File("CertainTickets.txt");
    }
    public void writeSortedTicketsInAdditionalFile(Map<Integer, Integer> indexAndCost) {
        try {
            FileWriter myWriter = new FileWriter(file);
            System.out.println();
            for (Integer index : indexAndCost.values())
                myWriter.write(getTicketByIndex(index) + "\n");
            myWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public String getTicketByIndex(int index) {
        String data = null;
        try {
            FileManage mainFile = new FileManage();
            Scanner myReader = new Scanner(mainFile.file);
            for (int i = 1; myReader.hasNextLine(); i++) {
                if (i != index) {
                    myReader.nextLine();
                } else {
                    data = myReader.nextLine();
                    break;
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return data;
    }
    public void formFileWithFilteredTickets() {
        try {
            FileManage mainFile = new FileManage();
            Scanner myReader = new Scanner(mainFile.file);
            FileWriter myWriter = new FileWriter(file);
            Filters filters = new Filters();

            for (int i = 0; myReader.hasNextLine();) {
                String data = myReader.nextLine();
                if (filters.withFilter(data)) {
                    i++;
                    myWriter.write(data + "\n");
                }
            }
            myWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public File getFile() {
        return file;
    }
}
