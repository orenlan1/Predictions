package world.entity.impl;

import com.sun.org.apache.xpath.internal.operations.Bool;
import world.entity.api.EntityDefinition;
import world.entity.api.EntityInstance;
import world.grid.Direction;
import world.grid.Grid;
import world.grid.GridCoordinate;
import world.property.api.PropertyDefinition;
import world.property.api.PropertyInstance;
import world.property.impl.PropertyInstanceImpl;

import java.util.*;

public class EntityInstanceImpl implements EntityInstance {
    private final EntityDefinition entityDefinition;
    private Map<String, PropertyInstance> nameToProperty;
    private GridCoordinate coordinate;
    private boolean alive;

    public EntityInstanceImpl(EntityDefinition entityDefinition) {
        this.entityDefinition = entityDefinition;
        nameToProperty = new HashMap<>();
        alive = Boolean.TRUE;
    }

    @Override
    public EntityDefinition getEntityDefinition() {
        return entityDefinition;
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
    
    @Override
    public boolean isAlive() { return alive; }
    
    @Override
    public void kill() {
        alive = Boolean.FALSE;
        //entityDefinition.killInstance();
    }

    @Override
    public void setCoordinate(Grid grid) {
        coordinate.setRandomlyCoordinate(grid, this);
    }

    @Override
    public void setCoordinate(GridCoordinate coordinate) {
        this.coordinate = coordinate;
    }

    public GridCoordinate getCoordinate() {
        return coordinate;
    }

    @Override
    public void moveEntityCoordinate(Grid grid) {
        List<Direction> availableDirections = new ArrayList<>(Arrays.asList(Direction.values()));
        Collections.shuffle(availableDirections);

        for (Direction direction : availableDirections) {
            GridCoordinate newCoordinate = this.coordinate.cloneCoordinate();
            if (newCoordinate.move(direction,grid)) {
                grid.removeCoordinateFromMap(this.coordinate);
                this.coordinate = newCoordinate;
                grid.addCoordinateToMap(this);
            }
        }
    }

    public EntityInstance createDerivedEntityInstance(EntityDefinition createdEntityDefinition) {
        boolean derivedProperty = false;
        EntityInstance newDerivedEntity = new EntityInstanceImpl(createdEntityDefinition);
        List<PropertyDefinition> newEntityProperties = createdEntityDefinition.getPropertiesList();
        newDerivedEntity.setCoordinate(this.getCoordinate());
        for (PropertyDefinition createdProperty : newEntityProperties) {
            for (PropertyDefinition propertyDefinition : entityDefinition.getPropertiesList()) {
                if (propertyDefinition.getName().equals(createdProperty.getName()) && propertyDefinition.getType().equals(createdProperty.getType())) {
                    Object value = nameToProperty.get(propertyDefinition.getName()).getValue();
                    newDerivedEntity.addPropertyInstance(new PropertyInstanceImpl(createdProperty, value));
                    derivedProperty = true;
                }
            }
            if (!derivedProperty) {
                Object value = createdProperty.generateValue();
                newDerivedEntity.addPropertyInstance(new PropertyInstanceImpl(createdProperty,value));
            }
            derivedProperty = false;
        }
        return newDerivedEntity;
    }



}
