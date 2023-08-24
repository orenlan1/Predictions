package simulation.environment.variables;

import dto.*;
import predictions.api.PredictionsService;
import simulation.present.SimulationInfo;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class SetEnvironmentVariables {

    public void manageEnvironmentVariables(PredictionsService predictionsService) {
        predictionsService.randomizeEnvProperties();
        PropertiesDTO DTOs = predictionsService.getEnvPropertiesDTO();
        printAndSetEnvProperties(DTOs, predictionsService);
        printFinalEnvVariables(predictionsService);
        }


    public void printAndSetEnvProperties(PropertiesDTO DTOs, PredictionsService predictionsService) {
        List<PropertyDTO> envPropertiesDTOs = DTOs.getPropertiesDTO();
        String updateValue = "";
        int choice = -1;

        Scanner scanner = new Scanner(System.in);
        System.out.println("\nPlease select an environment property to change (by entering its corresponding number)");
        System.out.println("Otherwise it will be set randomly by its range (if exist)");

        do {
            try {
                int i = 0;
                System.out.println("0. done");
                for (PropertyDTO envProperty : envPropertiesDTOs) {
                    System.out.println((i + 1) + ". " + envProperty.getPropertyName());
                    i++;
                }
                choice = scanner.nextInt();
                scanner.nextLine();
                if (choice == 0)
                    return;
                if (choice >= 0 && choice <= i) {
                    PropertyDTO envProperty = envPropertiesDTOs.get(choice - 1);
                    SimulationInfo simulationInfo = new SimulationInfo();
                    simulationInfo.simplifiedPrintPropertyInfo(envProperty);

                    System.out.println("Enter the new value for this environment property according to its type and range (if relevant)");
                    if (envProperty.getPropertyType().equals("boolean")) {
                        System.out.println("select 0 for \"false\" or 1 for \"true\"");
                    }
                    updateValue = scanner.nextLine();
                    EnvVariableSetValidationDTO valid = predictionsService.setEnvironmentVariable(new UserInputEnvironmentVariableDTO(envProperty, updateValue));
                    if (valid.getValidation())
                        System.out.println("\nValue loaded successfully!");
                    else
                        System.out.println("\n" + valid.getMessage());

                    System.out.println("Please select an environment property to change (by entering its corresponding number):");
                } else {
                    System.out.println("\nInvalid choice, please enter a number between 0 and " + i);
                }
            } catch (InputMismatchException e) {
                System.out.println("\nPlease enter the corresponding number instead of the actual environment property name");
                scanner.nextLine();
            }
        } while (true);
    }

    public void printFinalEnvVariables(PredictionsService predictionsService) {
        List<EnvVariablesDTO> DTOs = predictionsService.getEnvVariablesDTOList();
        System.out.println("\nThe environment variables are:");
        for (EnvVariablesDTO dto :DTOs) {
            System.out.println("Environment variable name:\t" + dto.getName());
            System.out.println("Environment variable value:\t" + dto.getValue() + "\n");
        }
    }
}
