package world.factory;

import dto.EntityDTO;
import dto.PropertyDTO;
import dto.RuleDTO;
import dto.TerminationDTO;
import world.Termination;
import world.action.api.Action;
import world.entity.api.EntityDefinition;
import world.property.api.PropertyDefinition;
import world.property.impl.FloatPropertyDefinition;
import world.property.impl.IntegerPropertyDefinition;
import world.rule.api.Rule;

import java.util.ArrayList;
import java.util.List;

public class DTOFactory {

    public DTOFactory() {
    }

    public PropertyDTO createPropertyDTO(PropertyDefinition propertyDefinition) {
        Double from = null;
        Double to = null;
        if (propertyDefinition instanceof IntegerPropertyDefinition) {
            from = Double.valueOf(((IntegerPropertyDefinition) propertyDefinition).getFrom());
            to = Double.valueOf(((IntegerPropertyDefinition) propertyDefinition).getTo());
        } else if (propertyDefinition instanceof FloatPropertyDefinition) {
            from = Double.valueOf(((FloatPropertyDefinition) propertyDefinition).getFrom());
            to = Double.valueOf(((FloatPropertyDefinition) propertyDefinition).getTo());
        }
        return new PropertyDTO(propertyDefinition.getName(),propertyDefinition.getType().name().toLowerCase(),from,to,propertyDefinition.getRandomInitialize());
    }

    public EntityDTO createEntityDTO(EntityDefinition entityDefinition) {
        List<PropertyDTO> propertyDTOList = new ArrayList<>();
        for ( PropertyDefinition propertyDefinition : entityDefinition.getPropertiesList()) {
            propertyDTOList.add(createPropertyDTO(propertyDefinition));
        }
        return new EntityDTO(entityDefinition.getName(), entityDefinition.getPopulation(),propertyDTOList);
    }

    public RuleDTO creatRuleDTO(Rule rule) {
        List<String> actionsNames = new ArrayList<>();
        for ( Action action : rule.getaActionsToPerform()) {
            actionsNames.add(action.getActionType().toString());
        }
        return new RuleDTO(rule.getName(),rule.getActivation().getTicks(),rule.getActivation().getProbability(), actionsNames.size(), actionsNames);
    }

    public TerminationDTO createTerminationDTO(Termination termination) {
        return new TerminationDTO(termination.getTicksCount(), termination.getSecondCount());
    }


}
