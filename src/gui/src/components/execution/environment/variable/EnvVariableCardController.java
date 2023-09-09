package components.execution.environment.variable;

import dto.PropertyDTO;
import dto.UserInputEnvironmentVariableDTO;
import javafx.scene.control.Label;

public interface EnvVariableCardController {

    void setCard(PropertyDTO dto);
    UserInputEnvironmentVariableDTO getInput();
}
