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

        valueSetter.getEditor().setOnAction(event -> { validateAndCommitInput(); });

        valueSetter.getEditor().focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                validateAndCommitInput();
            }
        });
    }

    private void validateAndCommitInput() {
        String text = valueSetter.getEditor().getText();
        if (isNumericalValue(text)) {
            valueSetter.getValueFactory().setValue(Double.parseDouble(text));
        } else {
            valueSetter.getValueFactory().setValue(valueSetter.getValueFactory().getValue());
            valueSetter.getEditor().textProperty().set(valueSetter.getValueFactory().getValue().toString());
        }
    }

    private boolean isNumericalValue(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
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

    private boolean isNumericalValue(Double value) {
        try {
            Integer.parseInt(value.toString());
            return true;
        } catch (NumberFormatException e) {
            try {
                Double.parseDouble(value.toString());
                return true;
            } catch (NumberFormatException e2) {
                return false;
            }
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
            /*if (typeLabel.getText().contains("decimal")) {
                value = valueSetter.getValue().toString().split("\\.")[0];
            } else*/
                value = valueSetter.getValue().toString();
        else
            value = null;

        return new UserInputEnvironmentVariableDTO(nameLabel.getText().substring(6), value);
    }
}
