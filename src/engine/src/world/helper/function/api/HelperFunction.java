package world.helper.function.api;

import world.environment.api.ActiveEnvironment;

import java.util.List;

public interface HelperFunction {
    String getName();
    Object invoke() throws NumberFormatException;
    int getNumOfArgs();
    List<String> getTypeOfArgs();
}
