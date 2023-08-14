package world.context;

import world.property.api.PropertyInstance;

public interface Context {


    PropertyInstance getEnvironmentVariable(String name);

}
