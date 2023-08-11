package world.rule.api;

import world.action.api.Action;
import world.entity.api.EntityInstance;
import world.rule.activation.Activation;

import java.util.List;

public interface Rule {
    String getName();
    Activation getActivation();
    List<Action> getaActionsToPerform();
    void addAction(Action action);
    void performAction(EntityInstance entityInstance) throws Exception;
}
