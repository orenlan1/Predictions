package world.action.impl;

import world.action.api.ActionType;
import world.entity.api.EntityDefinition;
import world.entity.api.EntityInstance;
import world.expressions.api.Expression;
import world.property.api.AbstractPropertyDefinition;
import world.property.api.PropertyDefinition;
import world.property.api.PropertyInstance;

public class MultiplicationAction extends CalculationAction {
    protected MultiplicationAction(ActionType actionType, EntityDefinition entityDefinition, PropertyDefinition propertyDefinition, Expression arg1, Expression arg2) {
        super(ActionType.CALCULATION, entityDefinition, propertyDefinition, arg1, arg2);
    }

    @Override
    public void activate(EntityInstance entityInstance) throws Exception {
        PropertyInstance property = entityInstance.getPropertyByName(propertyDefinition.getName());
        AbstractPropertyDefinition.PropertyType type = propertyDefinition.getType();
        Object arg1Value = arg1.evaluate();
        Object arg2Value = arg2.evaluate();
        if ( (arg1Value instanceof Float || arg2Value instanceof Float) && type.equals(AbstractPropertyDefinition.PropertyType.DECIMAL)) {
            throw new RuntimeException(); //// some simulation exception
        }



        //if (type.equals(AbstractPropertyDefinition.PropertyType.DECIMAL)) {
            //    if (arg1.getType().equals("decimal") && arg2.getType().equals("decimal"))
            //        property.updateValue((int) arg1Value * (int) arg2Value);
    }
}
