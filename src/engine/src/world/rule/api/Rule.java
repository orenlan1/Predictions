package world.rule.api;

import world.action.api.Action;
import world.rule.activation.Activation;

import java.util.List;

public interface Rule {
    String getName();
    Activation getActivation();
    List<Action> getaActionsToPerform();
    void addAction(Action action);
}
