package simulation.past;

import dto.*;
import predictions.api.PredictionsService;

import java.text.SimpleDateFormat;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class PastSimulationInfo {
    public void showPastSimulations(PredictionsService predictionsService) {
        AllSimulationsDTO allSimulationsDTO = predictionsService.getSimulationsDTO();
        List<PastSimulationDTO> DTOs = allSimulationsDTO.getSimulationsList();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy | hh.mm.ss");
        Scanner scanner = new Scanner(System.in);
        int simulationChoice;

        if (DTOs.size() == 0) {
            System.out.println("There are no past simulations with this xml file!");
            return;
        }

        System.out.println("Please select a past Simulation to show (by entering its ID number)");
        for (PastSimulationDTO pastSimulationDTO : DTOs) {
            String date = sdf.format(pastSimulationDTO.getDate());
            System.out.println("Time of simulation:\t" + date + "\t\tSimulation ID:\t" + pastSimulationDTO.getId());
        }
        try {
            simulationChoice = scanner.nextInt();
            if (simulationChoice <= 0 || simulationChoice > DTOs.size())
                System.out.println("Invalid choice! No such Simulation ID exists");
            else {
                selectShowOptionAndPerform(DTOs.get(simulationChoice - 1).getEntitiesDTO(), scanner, predictionsService);
            }
        }
        catch (InputMismatchException e) {
            System.out.println("\nPlease enter corresponding number");
            scanner.nextLine();
        }
    }


    public void selectShowOptionAndPerform(List<PastEntityDTO> pastEntityDTOS, Scanner scanner, PredictionsService predictionsService) {
        System.out.println("Select 1 to showcase the changes in the population by entity");
        System.out.println("Select 2 to showcase the histogram of a property for a specific entity");
        int showChoice = scanner.nextInt();
        if (showChoice == 1)
            printEntitiesPopulation(pastEntityDTOS);
        else if (showChoice == 2) {
            printPropertyHistogram(pastEntityDTOS, scanner, predictionsService);
        } else
            System.out.println("Invalid choice! Please select onr of the presented options by selecting its corresponding number");
    }


    public void printEntitiesPopulation(List<PastEntityDTO> pastEntityDTOS) {
        for (PastEntityDTO entityDTO : pastEntityDTOS) {
            System.out.println("\nEntity name:\t\t" + entityDTO.getEntityName());
            System.out.println("Initial population:\t" + entityDTO.getPopulation());
            System.out.println("Final population:\t" + entityDTO.getFinalPopulation());
        }
    }


    public void printPropertyHistogram(List<PastEntityDTO> pastEntities, Scanner scanner, PredictionsService predictionsService) throws InputMismatchException {
        System.out.println("Please select an entity:");
        int i = 1;
        for (PastEntityDTO entityDTO : pastEntities) {
            System.out.println(i + ". " + entityDTO.getEntityName());
            i++;
        }
        i = scanner.nextInt();
        if (i <= 0 || i > pastEntities.size())
            System.out.println("Invalid choice! please choose a number between 1 and " + pastEntities.size());
        else {
            System.out.println("Please choose the property of which you wish to see a histogram (by choosing its corresponding number):\n");
            List<PropertyDTO> propertyDTOS = pastEntities.get(i-1).getPropertiesList();
            i = 1;
            for (PropertyDTO propertyDTO : propertyDTOS) {
                System.out.println(i + " Property name:\t" + propertyDTO.getPropertyName());
                i++;
            }
            i = scanner.nextInt();
            if (i <= 0 || i > propertyDTOS.size()) {
                System.out.println("Invalid choice! please choose a number between 1 and " + propertyDTOS.size());
            }
            else {
                System.out.println("Histogram for the property " + propertyDTOS.get(i-1).getPropertyName() + ":");
                HistogramDTO histogramDTO = predictionsService.getHistogram(propertyDTOS.get(i-1).getPropertyName());
                Map<Object, Integer> valueToAmount = histogramDTO.getValueToAmount();
                for (Object key : valueToAmount.keySet()) {
                    System.out.println("Value:\t" + key + "\tAmount:\t" + valueToAmount.get(key));
                }
            }
        }
    }
}