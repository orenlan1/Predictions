package dto;

import java.util.List;

public class RuleDTO {
    private final String ruleName;
    private final Integer ticks;
    private final Double probability;
    private final Integer actionsNumber;
    //private final List<String> actionsNamesList;
    private final List<ActionDTO> actionsDTOs;

    public RuleDTO(String ruleName, Integer ticks, Double probability, Integer actionsNumber, /*List<String> actionsNamesList,*/ List<ActionDTO> actionsDTOs) {
        this.ruleName = ruleName;
        this.ticks = ticks;
        this.probability = probability;
        this.actionsNumber = actionsNumber;
        //this.actionsNamesList = actionsNamesList;
        this.actionsDTOs = actionsDTOs;
    }

    public String getRuleName() {
        return ruleName;
    }

    public Integer getTicks() {
        return ticks;
    }

    public Double getProbability() {
        return probability;
    }

    public Integer getActionsNumber() {
        return actionsNumber;
    }

    /*public List<String> getActionsNamesList() {
        return actionsNamesList;
    }*/

    public List<ActionDTO> getActionsDTOs() {
        return actionsDTOs;
    }
}