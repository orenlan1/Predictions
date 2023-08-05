package world.action;

import world.entity.Entity;
import world.properties.Property;

import java.util.Objects;

public class ChangePropertyValueAction implements Action {
    private String actionName;
    private String propertyName;
    //private Expression value;

    public ChangePropertyValueAction(String actionName, String propertyName, String value) {
        this.actionName = actionName;
        this.propertyName = propertyName;
    }


    @Override
    public String show() {
        return actionName;
    }


    @Override
    public void activate(Entity entity) {
        if (Objects.equals(actionName, "increase")) {
            increaseOrDecrease(entity, propertyName, exp, 1);
        }
        else if (Objects.equals(actionName, "decrease")) {
            increaseOrDecrease(entity, propertyName, exp, 0);
        }
        else if (Objects.equals(actionName, "calculate")) {
            calculate(entity, propertyName);
        }
    }

    private void increaseOrDecrease(Entity entity, String property, Expression exp, int inc) {
        Property prop = entity.getProperty(property);
        if (inc == 1) {
            prop += exp.evaluate();
        } else {
            prop -= exp.evaluate();
        }
    }

    private void calculate(Entity entity, String resProp) {
        Calculate.eval(entity, entity.getProperty(resProp));
    }

}
