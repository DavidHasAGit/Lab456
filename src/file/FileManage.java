package file;

import java.io.*;
import java.util.Scanner;

public class FileManage {
    File file;
    public FileManage() {
        file = new File("Tickets.txt");
    }

    public File getFile() {
        return file;
    }
    public void printContent() {
        try {
            Scanner myReader = new Scanner(file);
            for (int i = 1; myReader.hasNextLine(); i++) {
                String data = myReader.nextLine();
                System.out.println(i + ". " + data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
