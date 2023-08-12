package file.reader;

import java.util.Scanner;
import dto.FileReaderDTO;
import world.EngineFileReader;


public class FileReader {
    public static void ReadFile(boolean firstTime) {
        Scanner scanner = new Scanner(System.in);
        FileReaderDTO dto = null;
        boolean validFile = false;

        System.out.println("Please enter an XML file path, including the \".xml\" at the end: ");
        do {
            try {
                String xmlFileName = scanner.nextLine();
                if (xmlFileName.split("\\.")[1].equals("xml")) {
                    dto = EngineFileReader.checkFileValidation(xmlFileName);
                    validFile = dto.isValid();
                    if (!validFile)
                        System.out.println(dto.getError());
                }
                else {
                    System.out.println("Not a valid xml file");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } while (!validFile && firstTime);

        if (validFile)
            System.out.println("File loaded successfully!");
    }
}