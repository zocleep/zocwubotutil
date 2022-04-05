import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class InfoTaker {

    static String[] getInfoFromFile() {
        String[] info = new String[2];
        File file = new File("your_info.txt");
        try {
            FileReader fileReader = new FileReader(file);
            Scanner fileScanner = new Scanner(fileReader);
            info[0] = fileScanner.nextLine();
            info[1] = fileScanner.nextLine();
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return info;
    }
}
