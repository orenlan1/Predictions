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
        populationCount.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                populationCount.increment(0); // won't change value, but will commit editor
            }
        });
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
}
