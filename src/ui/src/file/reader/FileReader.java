package file.reader;

import java.util.Scanner;
import dto.FileReaderDTO;
import predictions.api.PredictionsService;
import world.EngineFileReader;


public class FileReader {
    public static void ReadFile(PredictionsService admin, boolean firstTime) {
        Scanner scanner = new Scanner(System.in);
        FileReaderDTO dto = null;
        boolean validFile = false;

        System.out.println("Please enter an XML file path, including the \".xml\" at the end: ");
        do {
            try {
                String xmlFileName = scanner.nextLine();
                if (xmlFileName.contains(".") && xmlFileName.split("\\.")[1].equals("xml")) {
                    dto = admin.readFileAndLoad(xmlFileName);
                    validFile = dto.isValid();
                    if (!validFile) {
                        System.out.println("Error while loading xml file:");
                        System.out.println(dto.getError());
                    }
                }
                else {
                    System.out.println("Not a valid xml file");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            } finally {
                if (!validFile && firstTime)
                    System.out.println("Please fix the xml file and enter his path again: ");
            }
        } while (!validFile && firstTime);

        if (validFile)
            System.out.println("File loaded successfully!");
    }
}