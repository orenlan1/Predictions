package world.action.impl;

import world.action.api.ActionType;
import world.entity.api.EntityDefinition;
import world.entity.api.EntityInstance;
import world.expressions.api.Expression;
import world.property.api.AbstractPropertyDefinition;
import world.property.api.PropertyDefinition;

public class DivisionAction extends CalculationAction {


    protected DivisionAction( EntityDefinition entityDefinition, PropertyDefinition propertyDefinition, Expression arg1, Expression arg2) {
        super(ActionType.CALCULATION, entityDefinition, propertyDefinition, arg1, arg2);
    }

    @Override
    public void activate(EntityInstance entityInstance) throws Exception {
        AbstractPropertyDefinition.PropertyType type = propertyDefinition.getType();
        Object arg1Value = arg1.evaluate();
        Object arg2Value = arg2.evaluate();

        if (type.equals(AbstractPropertyDefinition.PropertyType.DECIMAL)) {
            if (arg1.getType().equals("decimal") && arg2.getType().equals("decimal"))
                if ((int) arg2Value != 0)
                    //return (int) arg1Value / (int) arg2Value;
        }
    }
}
