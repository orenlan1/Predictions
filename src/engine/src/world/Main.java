package world;

import world.action.api.Action;
import world.action.impl.IncreaseAction;
import world.entity.api.EntityDefinition;
import world.entity.api.EntityInstance;
import world.entity.impl.EntityDefinitionImpl;
import world.entity.impl.EntityInstanceImpl;
import world.expressions.ExpressionDecoder;
import world.expressions.api.Expression;
import world.expressions.impl.ExpressionImpl;
import world.property.api.AbstractPropertyDefinition;
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


        PropertyDefinition age = new IntegerPropertyDefinition("age",ValueGeneratorFactory.createRandomInteger(15,50));
        //PropertyInstance ageInstance = new PropertyInstanceImpl(age);
        EntityDefinition smoker = new EntityDefinitionImpl("smoker",100);
        smoker.addPropertyDefinition(age);
        smoker.createEntityInstancesPopulation();

        List<EntityInstance> smokersInstances = smoker.getEntityInstances();

        EnvironmentVariablesManager environmentVariablesManager = new EnvironmentVariablesManagerImpl();
        ActiveEnvironment activeEnvironment = environmentVariablesManager.createActiveEnvironment();

        for (PropertyDefinition propertyDefinition : environmentVariablesManager.getEnvironmentVariables()) {
            //collect data from user
            int newValueFromUser = 10;
            activeEnvironment.addPropertyInstance(new PropertyInstanceImpl(cigarettesCritical, newValueFromUser));
        }

        PropertyDefinition tax = new IntegerPropertyDefinition("tax", ValueGeneratorFactory.createFixed(17));
        PropertyInstance taxIntance = new PropertyInstanceImpl(tax);

        activeEnvironment.addPropertyInstance(taxIntance);

        Expression exp = new ExpressionImpl("random(4)", tax, activeEnvironment);
        Object obj = exp.evaluate(smokersInstances.get(0));

        //Action action = new IncreaseAction(smoker, exp, age);
//
        //try {
        //    action.activate(smokersInstances.get(0));
        //} catch (Exception e) {
        //    System.out.println(e);
        //}

    }
}
