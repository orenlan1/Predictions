package world.rule;

import generated.PRDRule;
import world.action.Action;
import world.entity.Entity;

import java.util.LinkedList;
import java.util.List;

public class Rule {
    private String name;
    private List<Action> actions;
    private double probability;
    private int everyXTicks;


    public Rule(Object some_bullshit) {

    }

    public void performRule(Entity entity, int ticks){
        double prob = Math.random();
        if ((ticks % everyXTicks == 0) && prob < probability) {
            for (Action action : actions)
                action.activate(entity);
        }
    }
}
