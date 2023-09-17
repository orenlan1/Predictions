package components.execution.environment.variable.string;

import components.execution.environment.variable.EnvVariableCardController;
import dto.PropertyDTO;
import dto.UserInputEnvironmentVariableDTO;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

public class StringEnvVariableCardController implements EnvVariableCardController {

    @FXML
    private GridPane envVariableCardGridPane;

    @FXML
    private Label nameLabel;

    @FXML
    private CheckBox setCheckBox;

    @FXML
    private TextArea textSetter;

    @FXML
    private Label typeLabel;

    private final SimpleBooleanProperty isSetterChecked;

    public StringEnvVariableCardController() {
        isSetterChecked = new SimpleBooleanProperty(false);
    }

    @FXML
    public void initialize() {
        textSetter.disableProperty().bind(isSetterChecked.not());
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
            value = textSetter.getText();
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

    @Override
    public void setValue(String value) {
        setCheckBox.setSelected(true);
        setChecked(new ActionEvent());
        textSetter.setText(value);
    }
}