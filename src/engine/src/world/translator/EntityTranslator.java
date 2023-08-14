package world.translator;

import generated.PRDEntities;
import generated.PRDEntity;
import world.entity.api.EntityDefinition;
import world.entity.impl.EntityDefinitionImpl;
import world.property.api.PropertyDefinition;

import java.util.ArrayList;
import java.util.List;

public class EntityTranslator {
    public static EntityDefinition TranslateEntityDefinition(PRDEntity prdEntity) throws Exception {
        String name = prdEntity.getName();
        int population = prdEntity.getPRDPopulation();
        List<PropertyDefinition> lst = PropertyTranslator.translateProperties(prdEntity.getPRDProperties());

        EntityDefinition entityDefinition = new EntityDefinitionImpl(name, population);
        for (PropertyDefinition propertyDefinition : lst) {
            entityDefinition.addPropertyDefinition(propertyDefinition);
        }
        return entityDefinition;
    }

    public static List<EntityDefinition> translateEntities (PRDEntities prdEntities) throws Exception {
        List<EntityDefinition> lst = new ArrayList<>();
        for (PRDEntity prdEntity : prdEntities.getPRDEntity()) {
            lst.add(EntityTranslator.TranslateEntityDefinition(prdEntity));
        }
        return lst;
    }
}
