package components.results.analysis.property;

import components.execution.NewExecutionController;
import dto.HistogramDTO;
import dto.PastEntityDTO;
import dto.PastSimulationDTO;
import dto.PropertyDTO;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PropertyChooserController {

    @FXML
    private MenuButton entitySelector;

    @FXML
    private MenuButton propertySelector;

    @FXML
    private Button histogramButton;

    @FXML
    private Button consistencyButton;

    @FXML
    private Button meanButton;

    private BooleanProperty enableButtons = new SimpleBooleanProperty(false);
    private StringProperty entitySelectorProperty = new SimpleStringProperty();
    private StringProperty propertySelectorProperty = new SimpleStringProperty();
    private PastSimulationDTO dto;
    private NewExecutionController newExecutionController;

    @FXML
    public void initialize() {
        entitySelectorProperty.bind(entitySelector.textProperty());
        propertySelectorProperty.bind(propertySelector.textProperty());
        propertySelector.disableProperty().bind(entitySelectorProperty.isEqualTo("Entity"));
        enableButtons.bind(entitySelectorProperty.isEqualTo("Entity")
                .or(propertySelectorProperty.isEqualTo("Property")));
        histogramButton.disableProperty().bind(enableButtons);
        consistencyButton.disableProperty().bind(enableButtons);
        meanButton.disableProperty().bind(enableButtons);

    }

    public void setNewExecutionController(NewExecutionController newExecutionController) {
        this.newExecutionController = newExecutionController;
    }

    public void setDto(PastSimulationDTO dto) {
        this.dto = dto;
        entitySelector.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!"Entity".equals(newValue)) {
                List<String> properties = new ArrayList<>();
                for (PastEntityDTO entity : dto.getEntitiesDTO()) {
                    if (entity.getEntityName().equals(newValue)) {
                        for (PropertyDTO property : entity.getPropertiesList()) {
                            properties.add(property.getPropertyName());
                        }
                    }
                }
                setPropertyMenuItems(properties);
            }
        });
    }

    public void setEntitySelectorItems(List<String> entities) {
        entitySelector.getItems().clear();
        entities.forEach(entity -> entitySelector.getItems().add(new MenuItem(entity)));
        entitySelector.getItems().forEach(item -> {
            item.setOnAction(event -> {
                entitySelector.setText(((MenuItem)event.getSource()).getText());
            });
        });
    }

    public void setPropertyMenuItems(List<String> properties) {
        propertySelector.getItems().clear();
        propertySelector.setText("Property");
        properties.forEach(property -> propertySelector.getItems().add(new MenuItem(property)));
        propertySelector.getItems().forEach(item -> {
            item.setOnAction(event -> {
                propertySelector.setText(((MenuItem)event.getSource()).getText());
            });
        });
    }


    @FXML
    public void showHistogram(ActionEvent event) {
        Map<Object, Integer> histogram = newExecutionController.getHistogram(dto.getId(), entitySelector.getText(), propertySelector.getText()).getValueToAmount();

    }

    @FXML
    public void showConsistency(ActionEvent event) {

    }

    @FXML
    public void showMean(ActionEvent event) {

    }

}
