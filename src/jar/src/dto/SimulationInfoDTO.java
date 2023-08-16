package dto;

import java.util.List;

public class SimulationInfoDTO {
    private final List<EntityDTO> entitiesList;
    private final List<RuleDTO> rulesList;

    public SimulationInfoDTO(List<EntityDTO> entitiesList, List<RuleDTO> rulesList) {
        this.entitiesList = entitiesList;
        this.rulesList = rulesList;
    }
    public List<EntityDTO> getEntitiesList() {
        return entitiesList;
    }


    public List<RuleDTO> getRulesList() {
        return rulesList;
    }
}
