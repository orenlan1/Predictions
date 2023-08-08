package world.environment.impl;

import world.environment.api.ActiveEnvironment;
import world.property.api.PropertyInstance;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ActiveEnvironmentImpl implements ActiveEnvironment {
    Map<String, PropertyInstance> environmentVariables;

    public ActiveEnvironmentImpl() { environmentVariables = new HashMap<>(); }

    @Override
    public Optional<PropertyInstance> getProperty(String name) {
        return Optional.ofNullable(environmentVariables.get(name));
    }

    @Override
    public void addPropertyInstance(PropertyInstance propertyInstance) {
        environmentVariables.put(propertyInstance.getPropertyDefinition().getName(), propertyInstance);
    }
}
