package components.main;

import components.details.DetailsController;
import components.execution.NewExecutionController;
import components.queue.management.QueueManagementController;
import dto.*;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.stage.Stage;
import predictions.api.PredictionsService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class PredictionsController {

    @FXML
    private Button detailsButton;

    @FXML
    private Button loadFileButton;

    @FXML
    private Label loadedFilePath;

    @FXML
    private Button newExecutionButton;

    @FXML
    private Button queueManagementButton;

    @FXML
    private Button resultsButton;

    @FXML
    private BorderPane mainBorderPane;

    private QueueManagementController queueManagementController;
    private DetailsController detailsController;
    private NewExecutionController newExecutionController;
    private final SimpleStringProperty loadedFilePathProperty;
    private final SimpleBooleanProperty isFileSelected;

    private Stage primaryStage;

    private PredictionsService predictionsService;

    public PredictionsController() {
        loadedFilePathProperty = new SimpleStringProperty("");
        isFileSelected = new SimpleBooleanProperty(false);
    }

    @FXML
    public void initialize() {
        loadedFilePath.textProperty().bind(Bindings.concat("File path: ", loadedFilePathProperty));
        detailsButton.disableProperty().bind(isFileSelected.not());
        newExecutionButton.disableProperty().bind(isFileSelected.not());
        resultsButton.disableProperty().bind(isFileSelected.not());
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setPredictionsService(PredictionsService predictionsService) {
        this.predictionsService = predictionsService;
    }

    @FXML
    void loadFileButtonAction(ActionEvent event) throws Exception {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select xml simulation file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("xml files","*.xml"));
        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile == null) {
          return;
        }
        FileReaderDTO DTO = predictionsService.readFileAndLoad(selectedFile.getAbsolutePath());
        if (DTO.isValid()) {
            String absolutePath = selectedFile.getAbsolutePath();
            loadedFilePathProperty.set(absolutePath);
            isFileSelected.set(true);
            detailsController.clearDetails(event);
            newExecutionController.clearNewExecution(event);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, DTO.getError());
            alert.setHeaderText(null);
            alert.show();
        }
    }

    @FXML
    public void viewQueueManagement(ActionEvent event) {
        queueManagementController.showQueueManagement(mainBorderPane);
    }

    @FXML
    public void viewDetails(ActionEvent event) {
        detailsController.clearDetails(event);
        detailsController.showDetailsMenu(mainBorderPane);
    }

    @FXML
    public void viewNewExecution(ActionEvent event) throws Exception {
        newExecutionController.setMaxPopulation(getMaximumPopulation());
        newExecutionController.showNewExecution(mainBorderPane);
    }

    public void setQueueManagementController(QueueManagementController queueManagementController) {
        this.queueManagementController = queueManagementController;
    }

    public void setDetailsController(DetailsController detailsController) {
        this.detailsController = detailsController;
    }

    public void setNewExecutionController(NewExecutionController newExecutionController) {
        this.newExecutionController = newExecutionController;
    }

    public List<PropertyDTO> getEnvVariablesDTO() { return predictionsService.getEnvPropertiesDTO().getPropertiesDTO(); }

    public List<EntityDTO> getEntitiesDTO() {
        return predictionsService.getSimulationInformation().getEntitiesList();
    }

    public TerminationDTO getTerminationDTO() { return predictionsService.getSimulationInformation().getTermination(); }

    public List<RuleDTO> getRulesDTO() {return predictionsService.getSimulationInformation().getRulesList(); }

    public void randomizeEnvironmentVariables() {
        predictionsService.randomizeEnvProperties();
    }

    public EnvVariableSetValidationDTO setEnvVariables(List<UserInputEnvironmentVariableDTO> DTOs) {
        return predictionsService.setEnvironmentVariables(DTOs);
    }

    public Integer getMaximumPopulation() { return detailsController.getGridSize(); }

    public void setEntitiesPopulation(List<EntityInitializationDTO> DTOs) {
        predictionsService.setEntitiesPopulation(DTOs);
    }

    public SimulationRunnerDTO runSimulation() {
        return predictionsService.runSimulation();
    }

}
