package world.environment.api;

import world.property.api.PropertyInstance;

import java.util.Optional;

public interface ActiveEnvironment {
    Optional<PropertyInstance> getProperty(String name);
    void addPropertyInstance(PropertyInstance propertyInstance);
}
