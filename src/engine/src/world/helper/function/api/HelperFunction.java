package world.helper.function.api;

import world.environment.api.ActiveEnvironment;

import java.util.List;

public interface HelperFunction {
    String getName();
    Object invoke(Object arg, ActiveEnvironment activeEnvironment);
    int getNumOfArgs();
    List<String> getTypeOfArgs();
}
