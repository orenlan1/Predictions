package components.execution.environment.variable.numerical;

import components.execution.environment.variable.EnvVariableCardController;
import dto.PropertyDTO;
import dto.UserInputEnvironmentVariableDTO;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.GridPane;

public class NumericalEnvVariableCardController implements EnvVariableCardController {

    @FXML
    private GridPane envVariableCardGridPane;

    @FXML
    private Label nameLabel;

    @FXML
    private Label typeLabel;

    @FXML
    private CheckBox setCheckBox;

    @FXML
    private Label fromLabel;

    @FXML
    private Label toLabel;

    @FXML
    private Spinner<Double> valueSetter;

    private final SimpleBooleanProperty isSetterChecked;

    public NumericalEnvVariableCardController() {
        isSetterChecked = new SimpleBooleanProperty(false);
    }

    @FXML
    public void initialize() {
        toLabel.textProperty().set("");
        fromLabel.textProperty().set("");
        setCheckBox.textProperty().set("Set value:");
        valueSetter.disableProperty().bind(isSetterChecked.not());
        valueSetter.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                valueSetter.increment(0); // won't change value, but will commit editor
            }
        });
    }

    @FXML
    public void setChecked(ActionEvent event) {
        isSetterChecked.set(isSetterChecked.not().getValue());
    }

    @Override
    public void setCard(PropertyDTO dto) {
        String type = dto.getPropertyType();
        setNameLabel(dto.getPropertyName());
        setTypeLabel(type);
        setToLabel(dto.getTo(), type);
        setFromLabel(dto.getFrom(), type);


        SpinnerValueFactory<Double> range =
                new SpinnerValueFactory.DoubleSpinnerValueFactory(dto.getFrom(), dto.getTo(), dto.getFrom(), 1);
        valueSetter.setValueFactory(range);
    }

    private boolean isDecimalValue(Double value) {
        try {
            Integer.parseInt(value.toString());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void setNameLabel(String name) {
        nameLabel.textProperty().set("Name: " + name);
    }

    public void setTypeLabel(String type) {
        typeLabel.textProperty().set("Type: " + type);
    }

    public void setToLabel(Double to, String type) {
        if (type.equals("decimal"))
            toLabel.textProperty().set("To: " + to.intValue());
        else
            toLabel.textProperty().set("To: " + String.format("%.2f", to));
    }

    public void setFromLabel(Double from, String type) {
        if (type.equals("decimal"))
            fromLabel.textProperty().set("From: " + from.intValue());
        else
            fromLabel.textProperty().set("From: " + String.format("%.2f", from));
    }

    @Override
    public UserInputEnvironmentVariableDTO getInput() {
        String value;
        if (setCheckBox.isSelected())
            if (typeLabel.getText().contains("decimal")) {
                value = valueSetter.getValue().toString().split("\\.")[0];
            } else
                value = valueSetter.getValue().toString();
        else
            value = null;

        return new UserInputEnvironmentVariableDTO(nameLabel.getText().substring(6), value);
    }
}
