import file.reader.FileReader;
import predictions.api.PredictionsService;
import predictions.impl.PredictionsServiceImpl;

import java.io.File;
import java.util.Scanner;

public class SimulationMain {
    public static void main(String[] args) {
        PredictionsService admin = new PredictionsServiceImpl();
        Scanner scanner = new Scanner(System.in);
        int userChoice = 0;

        System.out.println("Hello and welcome to our simulator!");
        FileReader.ReadFile(admin, Boolean.TRUE);

        do {
            System.out.println("\nPlease select an option:");
            System.out.println("1. Load an XML file\n2. Show details of current simulation\n3. Run simulation\n4. Show results of a past simulation\n5. End program");
            userChoice = scanner.nextInt();

            switch (userChoice){
                case 1:
                    FileReader.ReadFile(admin, Boolean.FALSE);
                    break;
                case 2:
                    //something
                    break;
                case 3:
                    //something
                    break;
                case 4:
                    //something
                    break;
                case 5:
                    System.out.println("Thank you and goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice! Please try again");
            }
        } while(true);



    }
}


/*  TODO!
    fix Expressions
    after that, fix actions and rules (?)
    figure out what the hell is DTO!!!
    translations
    move "main loop" into subclasses and out of main and finish main
*/
