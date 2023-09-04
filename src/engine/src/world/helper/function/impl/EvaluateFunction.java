package world.helper.function.impl;

import world.entity.api.EntityInstance;

public class EvaluateFunction extends HelperFunctionImpl {
    private String propertyName;




    public EvaluateFunction() {
        super("evaluate", 1, "String");
    }

    @Override
    public Object invoke() throws NumberFormatException {
        return null;
    }

//    public Object invoke(EntityInstance entityInstance) {
//
//    }
}
