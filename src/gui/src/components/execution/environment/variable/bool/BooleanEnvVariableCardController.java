package components.execution.environment.variable.bool;

import components.execution.environment.variable.EnvVariableCardController;
import dto.PropertyDTO;
import dto.UserInputEnvironmentVariableDTO;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.GridPane;

public class BooleanEnvVariableCardController implements EnvVariableCardController {

    @FXML
    private GridPane envVariableCardGridPane;

    @FXML
    private Label nameLabel;

    @FXML
    private CheckBox setCheckBox;

    @FXML
    private Label typeLabel;

    @FXML
    private MenuButton validitySetter;

    private final SimpleBooleanProperty isSetterChecked;

    public BooleanEnvVariableCardController() {
        isSetterChecked = new SimpleBooleanProperty(false);
    }

    @FXML
    public void initialize() {
        validitySetter.getItems().forEach(item -> {
            item.setOnAction(event -> {
                validitySetter.setText(((MenuItem) event.getSource()).getText());
            });
        });
        validitySetter.disableProperty().bind(isSetterChecked.not());
    }

    @FXML
    public void setChecked(ActionEvent event) {
        isSetterChecked.set(isSetterChecked.not().getValue());
    }

    @Override
    public void setCard(PropertyDTO dto) {
        setNameLabel(dto.getPropertyName());
        setTypeLabel(dto.getPropertyType());
    }

    @Override
    public UserInputEnvironmentVariableDTO getInput() {
        String value;
        if (setCheckBox.isSelected())
            value = validitySetter.getText();
        else
            value = null;

        return new UserInputEnvironmentVariableDTO(nameLabel.getText().substring(6), value);
    }

    public void setNameLabel(String name) {
        nameLabel.textProperty().set("Name: " + name);
    }

    public void setTypeLabel(String type) {
        typeLabel.textProperty().set("Type: " + type);
    }
}
