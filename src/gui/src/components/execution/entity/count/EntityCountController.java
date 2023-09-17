package components.execution.entity.count;

import dto.EntityInitializationDTO;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

public class EntityCountController {

    @FXML
    private Label nameLabel;

    @FXML
    private Spinner<Integer> populationCount;

    @FXML
    public void initialize() {
        populationCount.getEditor().setOnAction(event -> { validateAndCommitInput(); });

        populationCount.getEditor().focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                validateAndCommitInput();
            }
        });
    }

    private void validateAndCommitInput() {
        String text = populationCount.getEditor().getText();
        if (isDecimalValue(text)) {
            populationCount.getValueFactory().setValue(Integer.parseInt(text));
        } else {
            populationCount.getValueFactory().setValue(populationCount.getValueFactory().getValue());
            populationCount.getEditor().textProperty().set(populationCount.getValueFactory().getValue().toString());
        }
    }

    private boolean isDecimalValue(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void setCard(String name, Integer maxPopulation) {
        nameLabel.textProperty().set(name);
        SpinnerValueFactory<Integer> range =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, maxPopulation, 0, 1);
        populationCount.setValueFactory(range);
    }

    public EntityInitializationDTO getInfo() {
        return new EntityInitializationDTO(nameLabel.getText(), populationCount.getValue());
    }

    public void setEntityCount(Integer count) {
        populationCount.getValueFactory().setValue(count);
        populationCount.getEditor().textProperty().set(count.toString());
    }
}
