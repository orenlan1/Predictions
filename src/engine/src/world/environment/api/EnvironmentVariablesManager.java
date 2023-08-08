package world.environment.api;

import world.property.api.PropertyDefinition;

import java.util.Collection;

public interface EnvironmentVariablesManager {
    void addEnvironmentVariable(PropertyDefinition propertyDefinition);
    ActiveEnvironment createActiveEnvironment();
    Collection<PropertyDefinition> getEnvironmentVariables();
}
