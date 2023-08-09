package world.entity.impl;

import world.entity.api.EntityDefinition;
import world.entity.api.EntityInstance;
import world.property.api.PropertyDefinition;
import world.property.api.PropertyInstance;
import world.property.impl.PropertyInstanceImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class EntityInstanceImpl implements EntityInstance {
    private final EntityDefinition entityDefinition;
    private Map<String, PropertyInstance> nameToProperty;


    public EntityInstanceImpl(EntityDefinition entityDefinition) {
        this.entityDefinition = entityDefinition;
        nameToProperty = new HashMap<>();
    }

    @Override
    public PropertyInstance getPropertyByName(String name) {
        return nameToProperty.get(name);
    }

//    private static EntityInstance createEntityInstance(EntityDefinition entityDefinition){
//
//        EntityInstance newEntityInstance = new EntityInstanceImpl(entityDefinition);
//        for (PropertyDefinition propertyDefinition : entityDefinition.getPropertiesList()) {
//            Object value = propertyDefinition.generateValue();
//            newEntityInstance.addPropertyInstance(new PropertyInstanceImpl(propertyDefinition,value));
//        }
//        return newEntityInstance;
//    }

    @Override
    public void addPropertyInstance(PropertyInstance propertyInstance) {
        nameToProperty.put(propertyInstance.getPropertyDefinition().getName(), propertyInstance);
    }
}
