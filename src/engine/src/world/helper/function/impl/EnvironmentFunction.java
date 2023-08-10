package world.helper.function.impl;

import world.environment.api.ActiveEnvironment;

public class EnvironmentFunction extends HelperFunctionImpl {

    public EnvironmentFunction() {
        super("environment", 1, "String");
    }

    @Override
    public Object invoke(Object arg, ActiveEnvironment activeEnvironment) {
        String envPropertyName = null;
        if (arg instanceof String)
            envPropertyName = (String) arg;
        //else
        //    throw new Exception();

        return activeEnvironment.getProperty(envPropertyName).orElseThrow(() -> new RuntimeException("Something")).getValue();
    }
}
