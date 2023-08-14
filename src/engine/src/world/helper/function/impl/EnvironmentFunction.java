package world.helper.function.impl;

import world.environment.api.ActiveEnvironment;
import world.property.api.PropertyInstance;

public class EnvironmentFunction extends HelperFunctionImpl {
    private PropertyInstance propertyInstance;

    public EnvironmentFunction(PropertyInstance propertyInstance) {
        super("environment", 1, "String");
        this.propertyInstance = propertyInstance;
    }

    @Override
    public Object invoke() {
        return propertyInstance.getValue();
    }
}
