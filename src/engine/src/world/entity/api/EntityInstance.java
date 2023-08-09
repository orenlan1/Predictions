package world.entity.api;

import world.entity.impl.EntityInstanceImpl;
import world.property.api.PropertyDefinition;
import world.property.api.PropertyInstance;
import world.property.impl.PropertyInstanceImpl;


public interface EntityInstance {
    PropertyInstance getPropertyByName(String name);
    void addPropertyInstance(PropertyInstance propertyInstance);

    //static EntityInstance createEntityInstance(EntityDefinition entityDefinition);
    static EntityInstance createEntityInstance(EntityDefinition entityDefinition){
        EntityInstance newEntityInstance = new EntityInstanceImpl(entityDefinition);
        for (PropertyDefinition propertyDefinition : entityDefinition.getPropertiesList()) {
            Object value = propertyDefinition.generateValue();
            newEntityInstance.addPropertyInstance(new PropertyInstanceImpl(propertyDefinition,value));
        }
        return newEntityInstance;
    }

}
