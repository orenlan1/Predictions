package world.helper.function.impl;

import world.environment.api.ActiveEnvironment;
import world.value.generator.api.ValueGeneratorFactory;

public class RandomFunction extends HelperFunctionImpl {

    public RandomFunction() {
        super("random", 1, "int");
    }

    @Override
    public Object invoke(Object arg, ActiveEnvironment activeEnvironment) {
        Integer value = -1;
        try {
            value = Integer.parseInt((String) arg);
        } catch (NumberFormatException ignored) { }

        //else
        //    throw new Exception();

        return ValueGeneratorFactory.createRandomInteger(0, value).generateValue();
    }
}
