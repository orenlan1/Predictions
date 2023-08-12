package world.translator;

import generated.PRDEntity;
import world.entity.api.EntityDefinition;
import world.entity.impl.EntityDefinitionImpl;
import world.property.api.PropertyDefinition;

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
}
