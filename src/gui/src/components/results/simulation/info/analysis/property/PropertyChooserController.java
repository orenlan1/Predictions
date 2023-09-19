package components.results.simulation.info.analysis.property;

import components.execution.NewExecutionController;
import components.results.simulation.info.analysis.property.histogram.HistogramController;
import dto.*;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import world.exceptions.EntityPropertyNotExistException;

import java.net.URL;
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

    private BorderPane analysisScreen;
    private BooleanProperty disableButtons;
    private PastSimulationDTO dto;
    private StringProperty entitySelectorProperty;
    private StringProperty propertySelectorProperty;
    private NewExecutionController newExecutionController;

    public PropertyChooserController() {
        disableButtons = new SimpleBooleanProperty(false);
        entitySelectorProperty = new SimpleStringProperty();
        propertySelectorProperty = new SimpleStringProperty();
    }

    @FXML
    public void initialize() {
        entitySelectorProperty.bind(entitySelector.textProperty());
        propertySelectorProperty.bind(propertySelector.textProperty());
        propertySelector.disableProperty().bind(entitySelectorProperty.isEqualTo("Entity"));
        disableButtons.bind(entitySelectorProperty.isEqualTo("Entity")
                .or(propertySelectorProperty.isEqualTo("Property")));
        histogramButton.disableProperty().bind(disableButtons);
        consistencyButton.disableProperty().bind(disableButtons);
        meanButton.disableProperty().bind(disableButtons);

        entitySelector.textProperty().addListener((obs, oldValue, newValue) -> {
            if (!(oldValue.equals(newValue)))
                clearAnalysisScreen();
        });
        propertySelector.textProperty().addListener((obs, oldValue, newValue) -> {
            if (!(oldValue.equals(newValue)))
                clearAnalysisScreen();
        });
    }

    public void setNewExecutionController(NewExecutionController newExecutionController) {
        this.newExecutionController = newExecutionController;
    }

    public void setAnalysisScreen(BorderPane borderPane) { this.analysisScreen = borderPane; }

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
    public void showHistogram(ActionEvent event) throws Exception {
        URL histogramFXML = getClass().getResource("/components/results/simulation/info/analysis/property/histogram/histogram.fxml");
        FXMLLoader histogramLoader = new FXMLLoader(histogramFXML);
        ScrollPane histogramNode = histogramLoader.load();
        HistogramController histogramController = histogramLoader.getController();

        Map<Object, Integer> histogram = newExecutionController.getHistogram(dto.getId(), entitySelector.getText(), propertySelector.getText()).getValueToAmount();
        for (Object key : histogram.keySet()) {
            histogramController.addPair(key, histogram.get(key));
        }

        histogramNode.getStylesheets().add("/components/results/simulation/info/analysis/property/histogram/histogram.css");
        analysisScreen.setCenter(histogramNode);
    }

    @FXML
    public void showConsistency(ActionEvent event) {
        Double consistency = newExecutionController.getPredictionsController().getConsistencyAvg(dto.getId(), entitySelector.getText(), propertySelector.getText());
        String message;
        if (consistency == 0)
            message = "Can't calculate the consistency for this entity and property, perhaps there are no entities of this type left";
        else
            message = "the consistency is: " + String.format("%.2f", consistency);

        analysisScreen.setCenter(new Label(message));
    }

    @FXML
    public void showMean(ActionEvent event) {
        String message;
        MeanPropertyDTO meanDTO = null;
        try {
            meanDTO = newExecutionController.getPredictionsController().getMeanOfProperty(dto.getId(), entitySelector.getText(), propertySelector.getText());
        } catch (EntityPropertyNotExistException e) {
            analysisScreen.setCenter(new Label(e.getMessage()));
            return;
        }
        if (meanDTO.isValid())
            message = "The mean is: "  + meanDTO.getMean();
        else
            message = meanDTO.getMessage();


        analysisScreen.setCenter(new Label(message));
    }

    public void clearAnalysisScreen() {
        analysisScreen.setCenter(null);
    }

}
