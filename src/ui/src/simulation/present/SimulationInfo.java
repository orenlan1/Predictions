package simulation.present;

import dto.*;
import predictions.api.PredictionsService;

import java.util.List;

public class SimulationInfo {

    public void showSimulationInfo(PredictionsService predictionsService) {
        SimulationInfoDTO simulationInfoDTO = predictionsService.getSimulationInformation();
        System.out.println("\nInformation about the simulation defined in the xml file:\n");
        List<EntityDTO> entityDTOList = simulationInfoDTO.getEntitiesList();
        this.printEntitiesInfo(entityDTOList);
        List<RuleDTO> ruleDTOList = simulationInfoDTO.getRulesList();
        this.printRulesInfo(ruleDTOList);
        TerminationDTO terminationDTO = simulationInfoDTO.getTermination();
        this.printTerminationInfo(terminationDTO);
    }

    public void printEntitiesInfo(List<EntityDTO> entityDTOList) {
        System.out.println("Entities:\n");
        for ( EntityDTO entityDTO : entityDTOList) {
            System.out.println("Entity name:\t\t" + entityDTO.getEntityName());
            System.out.println("Population:\t\t" + entityDTO.getPopulation());
            System.out.println("Properties:");
            for (PropertyDTO propertyDTO : entityDTO.getPropertiesList()) {
                printPropertyInfo(propertyDTO);
            }
        }
    }

    public void printPropertyInfo(PropertyDTO propertyDTO) {
        System.out.println("\nProperty name:\t\t" + propertyDTO.getPropertyName());
        System.out.println("Property type:\t\t" + propertyDTO.getPropertyType());
        if ( propertyDTO.getFrom() != null) {
            if (propertyDTO.getPropertyType().equals("decimal")) {
                int from = propertyDTO.getFrom().intValue();
                int to = propertyDTO.getTo().intValue();
                System.out.println("Range:\t\t\tfrom: " + from + " to: " + to);
            }
            else
                System.out.println("Range:\t\t\tfrom: " + propertyDTO.getFrom() + " to: " + propertyDTO.getTo());
        }
        System.out.println("Random initialize:\t" + propertyDTO.getRandomInitialize().toString());
    }

    public void simplifiedPrintPropertyInfo(PropertyDTO propertyDTO) {
        System.out.println("\nProperty name:\t\t" + propertyDTO.getPropertyName());
        System.out.println("Property type:\t\t" + propertyDTO.getPropertyType());
        if (propertyDTO.getFrom() != null) {
            if (propertyDTO.getPropertyType().equals("decimal")) {
                int from = propertyDTO.getFrom().intValue();
                int to = propertyDTO.getTo().intValue();
                System.out.println("Range:\t\t\tfrom: " + from + " to: " + to);
            }
            else
                System.out.println("Range:\t\t\tfrom: " + propertyDTO.getFrom() + " to: " + propertyDTO.getTo());
        }
    }

    public void printRulesInfo(List<RuleDTO> ruleDTOList) {
        System.out.println("\n\nRules:");
        for (RuleDTO ruleDTO : ruleDTOList) {
            System.out.println("\nRule name:\t\t" + ruleDTO.getRuleName());
            System.out.println("Activation:");
            System.out.println("\t\t\tTicks: " + ruleDTO.getTicks());
            System.out.println("\t\t\tProbability: " + ruleDTO.getProbability());
            System.out.println("Number of actions:\t" + ruleDTO.getActionsNumber());
            System.out.println("Actions names:");
            for (String name : ruleDTO.getActionsNamesList()) {
                System.out.println("\t\t\t" + name.toLowerCase());
            }
            System.out.println("\n");
        }
    }

    public void printTerminationInfo(TerminationDTO terminationDTO) {
        System.out.println("\nTermination:\n");
        if (terminationDTO.getTicksCount() != null) {
            System.out.println("Ticks count:\t" + terminationDTO.getTicksCount());
        }
        if (terminationDTO.getSecondCount() != null) {
            System.out.println("Second count:\t" + terminationDTO.getSecondCount());
        }
    }
}
