package components.results.simulation.info.progression;

import components.results.ResultsController;
import dto.PastSimulationDTO;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class ProgressionController {

    @FXML
    private TableView<EntityCount> entityTable;

    @FXML
    private TableColumn<EntityCount, String> countColumn;

    @FXML
    private TableColumn<EntityCount, Integer> entityColumn;

    @FXML
    private Button pauseButton;

    @FXML
    private Button playButton;

    @FXML
    private Button stopButton;

    @FXML
    private Label secondsLabel;

    @FXML
    private Label ticksLabel;

    private PastSimulationDTO dto;
    private IntegerProperty ticksProperty;
    private IntegerProperty secondsProperty;
    private BooleanProperty runningProperty;
    private BooleanProperty pausedProperty;
    private ObservableList<EntityCount> entityCounts;
    private ResultsController resultsController;

    public class EntityCount {
        private final String entityName;
        private final IntegerProperty count;

        public EntityCount(String entityName, int count) {
            this.entityName = entityName;
            this.count = new SimpleIntegerProperty(count);
        }

        public String getEntityName() {
            return entityName;
        }

        public IntegerProperty countProperty() {
            return count;
        }
    }

    public ProgressionController() {
        ticksProperty = new SimpleIntegerProperty(0);
        secondsProperty = new SimpleIntegerProperty(0);
        runningProperty = new SimpleBooleanProperty(true);
        pausedProperty = new SimpleBooleanProperty(false);
    }

    @FXML
    public void initialize() {
        ticksLabel.textProperty().bind(Bindings.concat("", ticksProperty));
        secondsLabel.textProperty().bind(Bindings.concat("", secondsProperty));

        pauseButton.disableProperty().bind(runningProperty.not().or(pausedProperty));
        playButton.disableProperty().bind(runningProperty.not().or(pausedProperty.not()));
        stopButton.disableProperty().bind(runningProperty.not());

        entityCounts = FXCollections.observableArrayList();
        entityColumn.setCellValueFactory(new PropertyValueFactory<>("entityName"));
        countColumn.setCellValueFactory(new PropertyValueFactory<>("count"));
        entityTable.setItems(entityCounts);

        entityTable.setFixedCellSize(40);
    }

    public void setResultsController(ResultsController resultsController) { this.resultsController = resultsController; }

    public void setDto(PastSimulationDTO dto) {
        this.dto = dto;
        runningProperty.set(dto.isRunning());
        ticksProperty.set(dto.getTicks());
        secondsProperty.set(dto.getSeconds());

        entityCounts.clear();
        for (String key : dto.getDynamicPopulation().keySet()) {
            entityCounts.add(new EntityCount(key, dto.getDynamicPopulation().get(key)));
        }
        entityTable.refresh();
    }

    @FXML
    public void pauseSimulation(ActionEvent event) {
        pausedProperty.set(true);
        resultsController.pauseSimulation(dto.getId());
    }

    @FXML
    public void resumeSimulation(ActionEvent event) {
        pausedProperty.set(false);
        resultsController.resumeSimulation(dto.getId());
    }

    @FXML
    public void stopSimulation(ActionEvent event) {
        resultsController.stopSimulation(dto.getId());
    }
}
