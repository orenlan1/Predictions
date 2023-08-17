import file.reader.FileReader;
import predictions.api.PredictionsService;
import predictions.impl.PredictionsServiceImpl;
import simulation.environment.variables.SetEnvironmentVariables;
import simulation.present.SimulationInfo;

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
                    scanner.nextLine();
                    FileReader.ReadFile(admin, Boolean.FALSE);
                    break;
                case 2:
                    scanner.nextLine();
                    SimulationInfo simulationInfo = new SimulationInfo();
                    simulationInfo.showSimulationInfo(admin);
                    break;
                case 3:
                    scanner.nextLine();
                    SetEnvironmentVariables setter = new SetEnvironmentVariables();
                    setter.manageEnvironmentVariables(admin);
                    //run simulation and save its details
                    break;
                case 4:
                    scanner.nextLine();
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