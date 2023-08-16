package world.factory;

import dto.EntityDTO;
import dto.PropertyDTO;
import dto.RuleDTO;
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
        Number from = null;
        Number to = null;
        if (propertyDefinition instanceof IntegerPropertyDefinition) {
            from =  ((IntegerPropertyDefinition) propertyDefinition).getFrom();
            to = ((IntegerPropertyDefinition) propertyDefinition).getTo();
        } else if (propertyDefinition instanceof FloatPropertyDefinition) {
            from = ((FloatPropertyDefinition) propertyDefinition).getFrom();
            to = ((FloatPropertyDefinition) propertyDefinition).getTo();
        }
        return new PropertyDTO(propertyDefinition.getName(),propertyDefinition.getType().toString(),from,to,);
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


}
