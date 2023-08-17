package simulation.present;

import dto.EntityDTO;
import dto.PropertyDTO;
import dto.RuleDTO;
import dto.SimulationInfoDTO;
import predictions.api.PredictionsService;
import world.rule.api.Rule;

import java.util.List;

public class SimulationInfo {

    public void showSimulationInfo(PredictionsService predictionsService) {
        SimulationInfoDTO simulationInfoDTO = predictionsService.getSimulationInformation();
        System.out.println("Information about the simulation defined in the xml file:\n\n");
        List<EntityDTO> entityDTOList = simulationInfoDTO.getEntitiesList();
        this.printEntitiesInfo(entityDTOList);
        List<RuleDTO> ruleDTOList = simulationInfoDTO.getRulesList();
    }

    public void printEntitiesInfo(List<EntityDTO> entityDTOList) {
        System.out.println("Entites:\n");
        for ( EntityDTO entityDTO : entityDTOList) {
            System.out.println("Entity name: " + entityDTO.getEntityName());
            System.out.print("Population: " + entityDTO.getPopulation());
            System.out.println("Properties:");
            for (PropertyDTO propertyDTO : entityDTO.getPropertiesList()) {
                System.out.println("Property name: " + propertyDTO.getPropertyName());
                System.out.println("Property type: " + propertyDTO.getPropertyType());
                if ( propertyDTO.getFrom() != null) {
                    System.out.println("Range: from: " + propertyDTO.getFrom() + " to: " + propertyDTO.getTo());
                }
                System.out.println("Random initialize: " + propertyDTO.getRandomInitialize().toString());
            }
        }
    }

    public void printRulesInfo(List<RuleDTO> ruleDTOList) {
        System.out.println("Rules:\n");
        for ( RuleDTO ruleDTO : ruleDTOList) {
            System.out.println("Rule name: " + ruleDTO.getRuleName());
            System.out.println("Activation:");
            System.out.println("             Ticks: " + ruleDTO.getTicks());
            System.out.println("             Probability: " + ruleDTO.getProbability());
            System.out.println("Number of actions: " + ruleDTO.getActionsNumber());
            System.out.println("Actions names:");
            for ( String name : ruleDTO.getActionsNamesList()) {
                System.out.println("               " + name);
            }
        }
    }


}
