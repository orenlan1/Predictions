import file.reader.FileReader;

import java.io.File;
import java.util.Scanner;

public class SimulationMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int userChoice = 0;

        System.out.println("Hello and welcome to our simulator!");
        FileReader.ReadFile(Boolean.TRUE);

        
        //do {
        //    System.out.println("Please select an option:");
        //    System.out.println("1. Load an XML file\n2. Show details of current simulation\n3. Run simulation\n4. Show results of a past simulation\n5. End program");
        //    userChoice = scanner.nextInt();
//
        //    switch (userChoice){
        //        case 1:
        //            //something
        //            break;
        //        case 2:
        //            //something
        //            break;
        //        case 3:
        //            //something
        //            break;
        //        case 4:
        //            //something
        //            break;
        //        case 5:
        //            break;
        //        default:
        //            System.out.println("Invalid choice! Please try again");
        //    }
        //} while(userChoice != 5);



    }
}


/*  TODO!
    fix Expressions
    after that, fix actions and rules (?)
    figure out what the hell is DTO!!!
    translations
    move "main loop" into subclasses and out of main and finish main
*/
