package components.main;

import dto.FileReaderDTO;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.stage.Stage;
import predictions.api.PredictionsService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.File;

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
        //loadedFilePath.textProperty().bind(loadedFilePathProperty);
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
    void loadFileButtonAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select xml simulation file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("xml files","*.xml"));
        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile == null) {
          return; /// exception throw
        }
        FileReaderDTO DTO = predictionsService.readFileAndLoad(selectedFile.getAbsolutePath());
        if (DTO.isValid()) {
            String absolutePath = selectedFile.getAbsolutePath();
            loadedFilePathProperty.set(absolutePath);
            isFileSelected.set(true);
        } else {
            Popup popUp = new Popup();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, DTO.getError());
            alert.show();
        }

    }
}
