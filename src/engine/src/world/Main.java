/*
package world;

import world.action.api.Action;
import world.action.api.ActionType;
import world.action.impl.IncreaseAction;
import world.entity.api.EntityDefinition;
import world.entity.api.EntityInstance;
import world.entity.impl.EntityDefinitionImpl;
import world.exceptions.EntityPropertyNameExistException;
import world.expressions.api.Expression;
import world.expressions.impl.ExpressionImpl;
import world.property.api.PropertyDefinition;
import world.property.api.PropertyInstance;
import world.property.impl.IntegerPropertyDefinition;
import world.property.impl.PropertyInstanceImpl;
import world.property.impl.StringPropertyDefinition;
import world.value.generator.api.ValueGeneratorFactory;
import world.environment.api.EnvironmentVariablesManager;
import world.environment.api.ActiveEnvironment;
import world.environment.impl.ActiveEnvironmentImpl;
import world.environment.impl.EnvironmentVariablesManagerImpl;

import java.util.List;


public class Main {
    public static void main(String[] args) {
        Integer a = ValueGeneratorFactory.createRandomInteger(10,20).generateValue();
        PropertyDefinition cigarettesCritical = new IntegerPropertyDefinition(("cigarettes-critical"),ValueGeneratorFactory.createRandomInteger(10,100));
        int valueFromUser = 30;
        PropertyInstance cigInstance = new PropertyInstanceImpl(cigarettesCritical,valueFromUser);
        String s1 = ValueGeneratorFactory.createRandomString().generateValue();
        PropertyDefinition e1 = new StringPropertyDefinition("e1",ValueGeneratorFactory.createRandomString());


        PropertyDefinition age = new IntegerPropertyDefinition("age",ValueGeneratorFactory.createRandomInteger(15,50), 15, 80);
        EntityDefinition smoker = new EntityDefinitionImpl("smoker",100);
        try {
            smoker.addPropertyDefinition(age);
        } catch (EntityPropertyNameExistException e) {
            System.out.println(e.getMessage());
        }
        smoker.createEntityInstancesPopulation();

        List<EntityInstance> smokersInstances = smoker.getEntityInstances();

        EnvironmentVariablesManager environmentVariablesManager = new EnvironmentVariablesManagerImpl();
        ActiveEnvironment activeEnvironment = environmentVariablesManager.createActiveEnvironment();

        for (PropertyDefinition propertyDefinition : environmentVariablesManager.getEnvironmentVariables()) {
            //collect data from user
            int newValueFromUser = 10;
            activeEnvironment.addPropertyInstance(new PropertyInstanceImpl(propertyDefinition, newValueFromUser));
        }

        PropertyDefinition tax = new IntegerPropertyDefinition("tax", ValueGeneratorFactory.createFixed(17), 0, 100);
        PropertyInstance taxInstance = new PropertyInstanceImpl(tax);

        activeEnvironment.addPropertyInstance(taxInstance);

        Expression exp = new ExpressionImpl("random(4)", tax, activeEnvironment, smoker, ActionType.INCREASE);
        Object obj = exp.evaluate(smokersInstances.get(0));



    }
}
*/
